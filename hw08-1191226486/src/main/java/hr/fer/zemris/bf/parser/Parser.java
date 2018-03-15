package hr.fer.zemris.bf.parser;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.bf.lexer.Lexer;
import hr.fer.zemris.bf.lexer.LexerException;
import hr.fer.zemris.bf.lexer.TokenType;
import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * Implements parser for boolean expression. <br>
 * Implements following grammar: <br>
 * S -> E1 <br>
 * E1 -> E2 (OR E2)* <br>
 * E2 -> E3 (XOR E3)* <br>
 * E3 -> E4 (AND E4)* <br>
 * E4 -> NOT E4 | E5 <br>
 * E5 -> VAR | KONST | '(' E1 ')' <br>
 * It creates a tree that represents parsed expression.
 * 
 * @author tina
 *
 */
public class Parser {
	/** Root node of expression */
	private Node root;

	/**
	 * Getter for root node
	 * 
	 * @return root
	 */
	public Node getExpression() {
		return root;
	}

	/**
	 * Constructor which accepts a string that contains expression.
	 * 
	 * @param expression
	 *            string that contains expression
	 * 
	 * @throws ParserException
	 *             if error occurs while parsing.
	 */
	public Parser(String expression) {
		try {
			Lexer lexer = new Lexer(expression);
			parseExpression(lexer);
		} catch (LexerException e) {
			throw new ParserException("Lexer has thrown exception: " + e.getMessage());
		} catch (Exception e) {
			throw new ParserException(e.getMessage());
		}
	}

	/**
	 * Supplementary method that parses string that contains expression. Parsers
	 * grammar: <br>
	 * S -> E1 <br>
	 * E1 -> E2 (OR E2)* <br>
	 * E2 -> E3 (XOR E3)* <br>
	 * E3 -> E4 (AND E4)* <br>
	 * E4 -> NOT E4 | E5 <br>
	 * E5 -> VAR | KONST | '(' E1 ')'
	 * 
	 * @param lexer
	 *            instance of Lexer that provides tokens for expression.
	 * @throws ParserException
	 *             if any error occurs during parsing.
	 */
	private void parseExpression(Lexer lexer) {
		lexer.nextToken();
		root = getE1(lexer);

	}

	/**
	 * Analyzes rule E1 -> E2 (OR E2)* <br>
	 * 
	 * @param lexer
	 *            lexer
	 * @return node of appropriate elements and operator
	 */
	private Node getE1(Lexer lexer) {
		Node member = getE2(lexer);
		List<Node> children = new ArrayList<>();
		children.add(member);

		if (isInvalidToken(lexer)) {
			throw new ParserException("Unexpected token: 1 " + lexer.getToken());
		}

		while (lexer.getToken().getTokenType() == TokenType.OPERATOR && lexer.getToken().getTokenValue().equals("or")) {
			lexer.nextToken();
			children.add(getE2(lexer));
		}

		if (children.size() > 1) {
			member = new BinaryOperatorNode("or", children, (v1, v2) -> v1 || v2);
		}

		return member;
	}

	/**
	 * Analyzes rule E2 -> E3 (XOR E3)* <br>
	 * 
	 * @param lexer
	 *            lexer
	 * @return node of appropriate elements and operator
	 */
	private Node getE2(Lexer lexer) {
		Node member = getE3(lexer);
		List<Node> children = new ArrayList<>();
		children.add(member);

		if (isInvalidToken(lexer)) {
			throw new ParserException("Unexpected token: 2" + lexer.getToken());
		}

		while (lexer.getToken().getTokenType() == TokenType.OPERATOR
				&& lexer.getToken().getTokenValue().equals("xor")) {
			lexer.nextToken();
			children.add(getE3(lexer));
		}

		if (children.size() > 1) {
			member = new BinaryOperatorNode("xor", children, (v1, v2) -> !v1 && v2 || v1 && !v2);
		}

		return member;
	}

	/**
	 * Analyzes rule E3 -> E4 (AND E4)* <br>
	 * 
	 * @param lexer
	 *            lexer
	 * @return node of appropriate elements and operator
	 */
	private Node getE3(Lexer lexer) {
		Node member = getE4(lexer);
		List<Node> children = new ArrayList<>();
		children.add(member);

		if (isInvalidToken(lexer)) {
			throw new ParserException("Unexpected token: 3 " + lexer.getToken());
		}

		while (lexer.getToken().getTokenType() == TokenType.OPERATOR
				&& lexer.getToken().getTokenValue().equals("and")) {
			lexer.nextToken();
			children.add(getE4(lexer));
		}

		if (children.size() > 1) {
			member = new BinaryOperatorNode("and", children, (v1, v2) -> v1 && v2);
		}

		return member;
	}

	/**
	 * Analyzes rules: <br>
	 * E4 -> NOT E4 | E5 <br>
	 * s
	 * 
	 * @param lexer
	 *            lexer
	 * @return node of appropriate elements and operator
	 */
	private Node getE4(Lexer lexer) {
		Node member = null;

		if (lexer.getToken().getTokenType() == TokenType.OPERATOR && lexer.getToken().getTokenValue().equals("not")) {
			lexer.nextToken();
			member = new UnaryOperatorNode("not", getE4(lexer), v -> !v);

		} else {
			member = getE5(lexer);
		}

		return member;
	}

	/**
	 * Analyzes rule: <br>
	 * E5 -> VAR | KONST | '(' E1 ')'
	 * 
	 * @param lexer
	 *            lexer
	 * @return node of appropriate elements and operator
	 */
	private Node getE5(Lexer lexer) {
		Node member = null;

		if (lexer.getToken().getTokenType() == TokenType.VARIABLE) {
			member = new VariableNode(lexer.getToken().getTokenValue().toString());
		} else if (lexer.getToken().getTokenType() == TokenType.CONSTANT) {
			member = new ConstantNode((boolean) lexer.getToken().getTokenValue());
		} else if (lexer.getToken().getTokenType() == TokenType.OPEN_BRACKET) {

			lexer.nextToken();
			member = getE1(lexer);

			if (lexer.getToken().getTokenType() != TokenType.CLOSED_BRACKET) {
				throw new ParserException("Exprected closed bracket ')', found " + lexer.getToken().getTokenType());
			}

		} else {
			throw new ParserException(
					"Expected variable, constant or (expression), read " + lexer.getToken().getTokenType());
		}

		if (lexer.getToken().getTokenType() != TokenType.EOF)
			lexer.nextToken();

		return member;
	}

	/**
	 * Method that check if given token is invalid. (Doesn't match grammar
	 * rules.)
	 * 
	 * @param lexer
	 *            lexer for boolean expression
	 * @return false if token is invalid, otherwise false
	 */
	private boolean isInvalidToken(Lexer lexer) {
		if (lexer.getToken().getTokenType() == TokenType.CONSTANT
				|| lexer.getToken().getTokenType() == TokenType.VARIABLE
				|| lexer.getToken().getTokenType() == TokenType.OPEN_BRACKET) {
			return true;
		}

		if (lexer.getToken().getTokenType() == TokenType.OPERATOR && lexer.getToken().getTokenValue().equals("not")) {
			return true;
		}

		return false;
	}

}
