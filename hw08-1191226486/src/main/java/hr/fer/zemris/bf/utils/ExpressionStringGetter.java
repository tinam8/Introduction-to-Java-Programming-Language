package hr.fer.zemris.bf.utils;

import java.util.List;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.NodeVisitor;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * Implements visitor that creates string of an expression that represents
 * minimal from of boolean function. <br>
 * Form DNF.
 * 
 * @author tina
 *
 */
public class ExpressionStringGetter implements NodeVisitor {
	/** String builder used for creating expression*/
	StringBuilder sb = new StringBuilder();

	/**
	 * Returns created string of an expression that is accepting this visitor.
	 * @return expression string
	 */
	public String getExpressionString() {
		return sb.toString();
	}
	
	/**
	 * Method that clears string builder for another use of {@link ExpressionStringGetter} instance.
	 */
	public void start() {
		sb = new StringBuilder();
	}
	
	@Override
	public void visit(ConstantNode node) {
		sb.append(" "+node.getValue() +" ");
	}

	@Override
	public void visit(VariableNode node) {
		sb.append(" " + node.getName() + " ");
	}

	@Override
	public void visit(UnaryOperatorNode node) {
		sb.append(" " + node.getName());
		node.getChild().accept(this);
	}

	@Override
	public void visit(BinaryOperatorNode node) {
		List<Node> childern = node.getChildren();

		for (int i = 0; i < childern.size(); i++) {
			childern.get(i).accept(this);
			
			if (i != childern.size()-1) {
				sb.append(node.getName());
			}
		}
	}

}
