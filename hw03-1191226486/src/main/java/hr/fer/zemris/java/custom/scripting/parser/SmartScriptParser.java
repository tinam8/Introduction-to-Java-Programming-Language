package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.EmptyStackException;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;

/**
 * Parser for structured document format.
 * 
 * @author tina
 *
 */
public class SmartScriptParser {

	/**
	 * Represents root of document in parsing.
	 */
	private DocumentNode documentNode;
	/**
	 * Collection that helps with parse tree construction.
	 */
	private ObjectStack stack;

	{
		documentNode = new DocumentNode();
		stack = new ObjectStack();
	}

	/**
	 * Method that gets root node of parsing tree.
	 * 
	 * @return root node; {@link DocumentNode}
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}

	/**
	 * Constructor which accepts a string that contains document body.
	 * 
	 * @param documentBody
	 *            string that contains document body
	 * 
	 * @throws SmartScriptParserException
	 *             if error occurs while parsing.
	 */
	public SmartScriptParser(String documentBody) {
		try {
			Lexer lexer = new Lexer(documentBody);
			parse(lexer);
		} catch (LexerException e) {
			throw new SmartScriptParserException(e.getMessage());
		} catch (Exception e) {
			throw new SmartScriptParserException(e.getMessage());
		}

	}

	/**
	 * Supplementary method thar parses string that conatins document body.
	 * 
	 * @param lexer
	 *            instance od Lexer that provides tokens for documentBody.
	 * @throws SmartScriptParserException
	 *             if any error occurs during parsing.
	 */
	private void parse(Lexer lexer) {
		stack.push(documentNode);
		lexer.nextToken();

		while (lexer.getToken().getType() != TokenType.EOF) {
			if (lexer.getToken().getType() == TokenType.TEXT) {
				addTextNode(lexer);
			} else if (lexer.getToken().getType() == TokenType.BEGINNIG_TAG) {
				analyseTagNode(lexer);
			}


			 // check if adding tag or text produces EOF Token

			if (lexer.getToken().getType() == TokenType.EOF) {
				break;
			}

			lexer.nextToken();
		}

		Node lastPushed;
		try {
			lastPushed = (Node) stack.pop();
		} catch (EmptyStackException e) {
			throw new SmartScriptParserException("Unexpected empty stack while parsing. More ends than for loops.");
		}

		if (!stack.isEmpty() || lastPushed != documentNode) {
			throw new SmartScriptParserException("Error unclosed for loops.");
		}

	}

	/**
	 * Supplementary method that creates instance of TextNode and adds it to the
	 * parse tree.
	 * 
	 * @param lexer
	 *            instance of lexer that is creating tokens for parsins.
	 * @throws SmartScriptParserException
	 *             when an error occurs while creating or adding node.
	 */
	private void addTextNode(Lexer lexer) {
		Node lastPushed;
		try {
			lastPushed = (Node) stack.pop();
		} catch (EmptyStackException e) {
			throw new SmartScriptParserException("Unexpected empty stack while parsing. More ends than for loops.");
		}

		TextNode newNode = new TextNode(lexer.getToken().getValue().toString());
		lastPushed.addChildNode(newNode);
		stack.push(lastPushed);
	}

	/**
	 * Supplementary method that determins what is next tag name and according
	 * to that decides how to continue with analysing.
	 * 
	 * @param lexer
	 *            instance of Lexer that is creating tokens for parsing.
	 * @throws SmartScriptParserException
	 *             when an error occurs while parsing.
	 */
	private void analyseTagNode(Lexer lexer) {
		lexer.nextToken();

		if (lexer.getToken().getType() == TokenType.TAG_END) {
			closeForLoop(lexer);
			return;
		}

		if (lexer.getToken().getType() == TokenType.TAG_ECHO) {
			addEchoNode(lexer);
			return;
		}

		if (lexer.getToken().getType() == TokenType.TAG_FOR) {
			addForLoopNode(lexer);
			return;
		}

	}

	/**
	 * Supplementary method that removes
	 * 
	 * @param lexer
	 *            instance of Lexer that is creating tokens for parsing.
	 * @throws SmartScriptParserException
	 *             when an error occurs while parsing.
	 */
	private void closeForLoop(Lexer lexer) {
		lexer.nextToken();
		if (lexer.getToken().getType() == TokenType.ENDING_TAG) {
			try {
				stack.pop();
			} catch (EmptyStackException e) {
				throw new SmartScriptParserException("No for loop that end tag should close.");
			}
		} else {
			throw new SmartScriptParserException("expexted $} but read " + lexer.getToken().getValue().toString());
		}
	}

	/**
	 * Supplementary method that creates and adds instance of EchoNode.
	 * 
	 * @param lexer
	 *            instance of Lexer that is creating tokens for parsing.
	 * 
	 * @throws SmartScriptParserException
	 *             if Echo tag is inccorect
	 */
	private void addEchoNode(Lexer lexer) {
		lexer.nextToken();
		if (lexer.getToken().getType() == TokenType.ENDING_TAG) {
			throw new SmartScriptParserException("Echo tag can not be empty!");
		}

		ArrayIndexedCollection tokens = new ArrayIndexedCollection();
		tokens.add(lexer.getToken());

		while (lexer.getToken().getType() != TokenType.EOF) {
			lexer.nextToken();

			if (lexer.getToken().getType() == TokenType.ENDING_TAG) {
				Node lastPushed;

				try {
					lastPushed = (Node) stack.pop();
				} catch (EmptyStackException e) {
					throw new SmartScriptParserException(
							"Unexpected empty stack while parsing. More ends thar for loops.");
				}
				lastPushed.addChildNode(createEchoNode(tokens));
				stack.push(lastPushed);
				return;
			}

			tokens.add(lexer.getToken());
		}

		throw new SmartScriptParserException("There is no closing for ECHO tag!");
	}

	/**
	 * Supplementary method that creates and adds instance of ForLoopNode.
	 * 
	 * @param lexer
	 *            instance of Lexer that is creating tokens for parsing.
	 * 
	 * @throws SmartScriptParserException
	 *             if For loop tag is inccorect
	 */
	private void addForLoopNode(Lexer lexer) {
		lexer.nextToken();
		if (lexer.getToken().getType() != TokenType.VARIABLE) {
			throw new SmartScriptParserException(lexer.getToken().getValue().toString() + " is not variable");
		}

		ElementVariable variable = new ElementVariable(lexer.getToken().getValue().toString());
		Element startExpression = getLoopElement(lexer, false);
		Element endExpression = getLoopElement(lexer, false);
		Element stepExpression = getLoopElement(lexer, true);

		if (stepExpression != null) {
			lexer.nextToken();
		}

		if (lexer.getToken().getType() != TokenType.ENDING_TAG) {
			throw new SmartScriptParserException("To many arguments.");
		}

		ForLoopNode forLoopNode = new ForLoopNode(variable, startExpression, endExpression, stepExpression);

		Node lastPushed;
		try {
			lastPushed = (Node) stack.pop();
		} catch (EmptyStackException e) {
			throw new SmartScriptParserException("Unexpected empty stack while parsing. More ends than for loops.");
		}

		lastPushed.addChildNode(forLoopNode);
		stack.push(lastPushed);
		stack.push(forLoopNode);

	}

	/**
	 * Supplementary method that creates element of ForLoopNode if it is valid.
	 * 
	 * @param lexer
	 *            instance of Lexer that is creating tokens for parsing.
	 * @param stepExpression
	 *            true if checkin 4th optional for loop element, otherwise false
	 * @return element containing loop expression
	 * @throws SmartScriptParserException
	 *             if loop contains invalid tokens.
	 */
	private Element getLoopElement(Lexer lexer, boolean stepExpression) {
		lexer.nextToken();
		TokenType tokenType = lexer.getToken().getType();
		Element newElement = null;

		if (tokenType == TokenType.ENDING_TAG) {
			if (!stepExpression) {
				throw new SmartScriptParserException("too few arguments");
			}
		} else if (tokenType == TokenType.FUNCTION) {
			throw new SmartScriptParserException(lexer.getToken().getValue().toString() + " function name");
		} else if (tokenType == TokenType.VARIABLE) {
			newElement = new ElementVariable(lexer.getToken().getValue().toString());
		} else if (tokenType == TokenType.STRING) {
			newElement = new ElementString(lexer.getToken().getValue().toString());
		} else if (tokenType == TokenType.VARIABLE) {
			newElement = new ElementVariable(lexer.getToken().getValue().toString());
		} else if (tokenType == TokenType.DECIMAL_NUMBER) {
			newElement = new ElementConstantDouble((Double) lexer.getToken().getValue());
		} else if (tokenType == TokenType.INTEGER_NUMBER) {
			newElement = new ElementConstantInteger((Integer) lexer.getToken().getValue());
		}

		return newElement;

	}

	/**
	 * Supplementary method that creates EchoNode.
	 * 
	 * @param tokens
	 *            tokens that conatins values that should be placed in EchoNode;
	 * 
	 * @return created instance of EchoNode
	 */
	private EchoNode createEchoNode(ArrayIndexedCollection tokens) {
		Element[] elements = new Element[tokens.size()];
		for (int i = 0, n = tokens.size(); i < n; i++) {
			Token token = (Token) tokens.get(i);
			TokenType tokenType = token.getType();

			if (tokenType == TokenType.OPERATOR) {
				elements[i] = new ElementOperator(token.getValue().toString());
				continue;
			} else if (tokenType == TokenType.DECIMAL_NUMBER) {
				elements[i] = new ElementConstantDouble((Double) token.getValue());
				continue;
			} else if (tokenType == TokenType.INTEGER_NUMBER) {
				elements[i] = new ElementConstantInteger((Integer) token.getValue());
				continue;
			} else if (tokenType == TokenType.STRING) {
				elements[i] = new ElementString((String) token.getValue());
				continue;
			} else if (tokenType == TokenType.FUNCTION) {
				elements[i] = new ElementFunction((String) token.getValue());
				continue;
			} else if (tokenType == TokenType.VARIABLE) {
				elements[i] = new ElementVariable((String) token.getValue());
				continue;
			}
		}

		return new EchoNode(elements);

	}

}
