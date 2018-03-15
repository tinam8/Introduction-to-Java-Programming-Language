package hr.fer.zemris.java.gui.prim;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PrimListModelTest {

	@Test
	public void testNexy() {
		PrimListModel model = new PrimListModel();
		assertEquals(1,model.getSize());
		model.next();
		assertEquals(2,model.getSize());
		assertEquals(new Integer(1),model.getElementAt(0));
		assertEquals(new Integer(2),model.getElementAt(1));
	}

}
