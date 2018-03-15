package hr.fer.zemris.bf.utils;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.NodeVisitor;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * Impements visitor that prints parsed tree.
 * 
 * @author tina
 *
 */
public class ExpressionTreePrinter implements NodeVisitor {
	/** Detects level of tree node */
	private int indentation = 0;

	@Override
	public void visit(ConstantNode node) {
		if (node == null) {
			throw new IllegalArgumentException("Node can not be null.");
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indentation; i++) {
			sb.append("  ");
		}
		
		if (node.getValue()) {
			sb.append("1");
		} else {
			sb.append("0");
		}
		
		System.out.println(sb.toString());
	}

	@Override
	public void visit(VariableNode node) {
		if (node == null) {
			throw new IllegalArgumentException("Node can not be null.");
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indentation; i++) {
			sb.append("  ");
		}
		
		sb.append(node.getName());
		System.out.println(sb.toString());
	}

	@Override
	public void visit(UnaryOperatorNode node) {
		if (node == null) {
			throw new IllegalArgumentException("Node can not be null.");
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indentation; i++) {
			sb.append("  ");
		}
		
		sb.append(node.getName());
		System.out.println(sb.toString());
		
		indentation++;
		node.getChild().accept(this);
		indentation--;
	}

	@Override
	public void visit(BinaryOperatorNode node) {
		if (node == null) {
			throw new IllegalArgumentException("Node can not be null.");
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indentation; i++) {
			sb.append("  ");
		}
		
		sb.append(node.getName());
		System.out.println(sb.toString());
		
		indentation++;;
		node.getChildren().forEach(n -> n.accept(this));
		indentation--;
	}

}
