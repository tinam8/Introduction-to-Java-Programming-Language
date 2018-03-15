package hr.fer.zemris.java.hw01;

import static org.junit.Assert.*;

import org.junit.Test;


public class UniqueNumbersTest {

	
	
	@Test
	public void dodavanjeKorijena() {
		UniqueNumbers.TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 5);
		assertEquals(glava.value, 5);
		assertEquals(glava.left, null);
		assertEquals(glava.right, null);
	}

	@Test
	public void dodavanjeIsteVrijednosti() {
		UniqueNumbers.TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 42);
		glava = UniqueNumbers.addNode(glava, 42);
		assertEquals(glava.value, 42);
		assertEquals(glava.left, null);
		assertEquals(glava.right, null);
	}
	
	@Test
	public void dodavanjeViseVrijednosti() {
		UniqueNumbers.TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 42);
		glava = UniqueNumbers.addNode(glava, 76);
		glava = UniqueNumbers.addNode(glava, 21);
		glava = UniqueNumbers.addNode(glava, 76);
		glava = UniqueNumbers.addNode(glava, 35);
		assertEquals(glava.value, 42);
		assertEquals(glava.left.value, 21);
		assertEquals(glava.left.right.value, 35);
		assertEquals(glava.right.value, 76);
	}
		
	@Test
	public void velicinaPraznogStabla() {
		UniqueNumbers.TreeNode glava = null;
		assertEquals(UniqueNumbers.treeSize(glava), 0);
	}
	

	@Test
	public void velicinaPraznogStablaJedanCvor() {
		UniqueNumbers.TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 42);
		assertEquals(UniqueNumbers.treeSize(glava), 1);
	}
	
	@Test
	public void velicinaStablaViseCvorova() {
		UniqueNumbers.TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 42);
		glava = UniqueNumbers.addNode(glava, 76);
		glava = UniqueNumbers.addNode(glava, 21);
		glava = UniqueNumbers.addNode(glava, 76);
		glava = UniqueNumbers.addNode(glava, 35);
		assertEquals(UniqueNumbers.treeSize(glava), 4);
	}
	
	@Test
	public void praznoStabloSadrzi() {
		UniqueNumbers.TreeNode glava = null;
		assertEquals(UniqueNumbers.containsValue(glava, 5), false);
	}

	@Test
	public void jedanCvorSadrzi() {
		UniqueNumbers.TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 42);
		assertEquals(UniqueNumbers.containsValue(glava, 42), true);
	}
	
	@Test
	public void jedanCvorNeSadrzi() {
		UniqueNumbers.TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 42);
		assertEquals(UniqueNumbers.containsValue(glava, 43), false);
	}
	
	@Test
	public void viseCvorovaSadrzi() {
		UniqueNumbers.TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 42);
		glava = UniqueNumbers.addNode(glava, 76);
		glava = UniqueNumbers.addNode(glava, 21);
		glava = UniqueNumbers.addNode(glava, 76);
		glava = UniqueNumbers.addNode(glava, 35);
		assertEquals(UniqueNumbers.containsValue(glava, 21), true);
	}
	
	@Test
	public void viseCvorovaNeSadrzi() {
		UniqueNumbers.TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 42);
		glava = UniqueNumbers.addNode(glava, 76);
		glava = UniqueNumbers.addNode(glava, 21);
		glava = UniqueNumbers.addNode(glava, 76);
		glava = UniqueNumbers.addNode(glava, 35);
		assertEquals(UniqueNumbers.containsValue(glava, 51), false);
	}
	
}
