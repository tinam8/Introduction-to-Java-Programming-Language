package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.elems.IElementVisitor;
import hr.fer.zemris.java.custom.scripting.elems.functions.Functions;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class that executes the document whose parsed tree it obtains.
 * 
 * @author tina
 *
 */
public class SmartScriptEngine {
	/** Root node of parsed tree */
	private DocumentNode documentNode;
	/** Context of the request */
	private RequestContext requestContext;
	/** Multistack used for execution */
	private ObjectMultistack multistack = new ObjectMultistack();
	/**  */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				System.out.println("Error while executing text node.");
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String variable = node.getVariable().asText();
			String initialValue = node.getStartExpression().asText();
			String step = node.getStepExpression().asText();
			step = step == null ? "1" : step;
			String end = node.getEndExpression().asText();

			multistack.push(variable, new ValueWrapper(initialValue));

			while (multistack.peek(variable).numCompare(end) < 0) {
				for (int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(this);
				}

				multistack.peek(variable).add(step);
			}

			multistack.pop(variable);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> stackTemp = new Stack<Object>();
			ElementResolver elementRes = new ElementResolver(stackTemp);

			for (Element element : node.getElements()) {
				element.accept(elementRes);
			}

			if (!stackTemp.isEmpty()) {
				try {
					writeFromStack(stackTemp);
				} catch (IOException ignorable) {
				}
			}
		}

		/**
		 * Method that writes element from stack to output stream
		 * @param stackTemp stack that holds values
		 * @throws IOException if error occurs while writing
		 */
		private void writeFromStack(Stack<Object> stackTemp) throws IOException {
			List<Object> objectList = new ArrayList<>(stackTemp);
			for (Object object : objectList) {
				requestContext.write(object.toString());
			}
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
		}
	};

	/**
	 * Class that implements {@link IElementVisitor} for visiting different
	 * instances of {@link Element} and performing computation for each of them.
	 * 
	 * @author tina
	 *
	 */
	private class ElementResolver implements IElementVisitor {
		/** Stack used for execution of elements */
		Stack<Object> stack;

		/**
		 * Constructor that sets stack 
		 * @param stack stack that holds elements used for execution.
		 */
		public ElementResolver(Stack<Object> stack) {
			this.stack = stack;
		}

		@Override
		public void visitElementConstantDouble(ElementConstantDouble element) {
			stack.push(element.getValue());
		}

		@Override
		public void visitElementConstantInteger(ElementConstantInteger element) {
			stack.push(element.getValue());
		}

		@Override
		public void visitElementFuntion(ElementFunction element) {
			Functions functions = new Functions();
			functions.getFunction(element.getName()).calculate(stack, requestContext);
		}

		@Override
		public void visitElementOperator(ElementOperator element) {
			if (stack.size() < 2) {
				System.out.println("Error while executing operation.");
				System.exit(-1);
			}

			ValueWrapper value1 = new ValueWrapper(stack.pop());
			ValueWrapper value2 = new ValueWrapper(stack.pop());

			switch (element.getSymbol()) {
			case "+":
				value1.add(value2.getValue());
				break;
			case "-":
				value1.subtract(value2.getValue());
				break;
			case "*":
				value1.multiply(value2.getValue());
				break;
			case "/":
				value1.divide(value2.getValue());
				break;
			default:
				System.out.println("Unsupported operation.");
				System.exit(-1);
			}

			stack.push(value1.getValue());
		}

		@Override
		public void visitElementString(ElementString element) {
			stack.push(element.getValue());
		}

		@Override
		public void visitElementVariable(ElementVariable element) {
			ValueWrapper name = multistack.peek(element.getName());
			stack.push(name.getValue());
		}

	}

	/**
	 * Constructor that sets context and root of parsed tree of the script.
	 * 
	 * @param documentNode
	 *            root of parsed tree
	 * @param requestContext
	 *            instance of {@link RequestContext}
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Method that executes the script.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

}
