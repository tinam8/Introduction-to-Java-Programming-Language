package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;

public class SmartScriptParserTest {

	@Test
	public void testDocument1() {
		String docBody = loader("document1.txt");
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		
		// parsing recreated document
		parser = new SmartScriptParser(document.toString());
		DocumentNode document2 = parser.getDocumentNode();
		
		assertEquals(document.toString(), document2.toString());
		assertEquals(4, document.numberOfChildren());
		assertEquals(3, document.getChild(3).numberOfChildren());
	}
	
	@Test
	public void testDocument2EscapeInText() {
		String docBody = loader("document2.txt");
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		
		parser = new SmartScriptParser(document.toString());
		DocumentNode document2 = parser.getDocumentNode();
		
		assertEquals(document.toString(), document2.toString());
	}
	
	@Test(expected=SmartScriptParserException.class) 
	public void testDocument3EscapeInText() {
		String docBody = loader("document3.txt");
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		
	}
	
	@Test(expected=SmartScriptParserException.class) 
	public void testDocument3EndError() {
		String docBody = loader("document4.txt");
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		
	}
	
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}

}
