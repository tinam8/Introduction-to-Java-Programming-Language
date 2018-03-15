package hr.fer.zemris.java.hw03;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Program accepts a single command-line argument: path to document. Ilustrates
 * parsing.
 * 
 * @author tina
 *
 */
public class SmartScriptTester {

	/**
	 * Method which starts program.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("One command line argument required!");
			System.exit(-1);
		}

		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Can not open file!");
			System.exit(-1);
		}

		SmartScriptParser parser = null;
		
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document! " + e.getMessage());
			System.exit(-1);
		} catch (Exception e) {	
			System.out.println("If this line ever executes, you have failed this class!" + e.getMessage());
			System.exit(-1);
		}

		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody); // should write something like original
		 // content of docBody
		
		SmartScriptParser parser2 = null;
		try {
			parser2 = new SmartScriptParser(originalDocumentBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}

		
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocumentBody2 = createOriginalDocumentBody(document2);
//		System.out.println(document2);
		
		
		if (!originalDocumentBody2.equals(originalDocumentBody)) {
			throw new SmartScriptParserException("Something went wrong");
		}
		
	}

	/**
	 * Method that recreates original documents from parsing tree. Reproduces
	 * something which will after parsing again result with the same document
	 * model!
	 * 
	 * @param document
	 *            root node of parsing tree
	 * @return recreated document; String
	 */
	private static String createOriginalDocumentBody(DocumentNode document) {
		return document.toString();
	}

}
