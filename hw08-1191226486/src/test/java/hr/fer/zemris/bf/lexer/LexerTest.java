package hr.fer.zemris.bf.lexer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class LexerTest {

	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	private void checkTokenStream(Lexer lexer, Token[] correctData) {
		int counter = 0;
		for (Token expected : correctData) {
			Token actual = lexer.nextToken();
			String msg = "Checking token " + counter + ":";
			assertEquals(msg, expected.getTokenType(), actual.getTokenType());
			assertEquals(msg, expected.getTokenValue(), actual.getTokenValue());
			counter++;
		}
	}

	@Test
	public void testNotNull() {
		Lexer lexer = new Lexer("");

		assertNotNull("Token was expected but null was returned.", lexer.nextToken());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullInput() {
		// must throw!
		new Lexer(null);
	}

	@Test
	public void testEmpty() {
		Lexer lexer = new Lexer("");

		assertEquals("Empty input must generate only EOF token.", TokenType.EOF, lexer.nextToken().getTokenType());
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must
		// return each time what nextToken returned...
		Lexer lexer = new Lexer("");

		Token token = lexer.nextToken();
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
	}

	@Test(expected = LexerException.class)
	public void testRadAfterEOF() {
		Lexer lexer = new Lexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		lexer.nextToken();
	}

	@Test
	public void testIdentifiers() {
		Lexer lexer = new Lexer("000 and false xOr");

		// We expect the following stream of tokens
		Token correctData[] = { new Token(TokenType.CONSTANT, false), new Token(TokenType.OPERATOR, "and"),
				new Token(TokenType.CONSTANT, false), new Token(TokenType.OPERATOR, "xor") };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testOperatorAndVariable() {
		Lexer lexer = new Lexer(" an_d  :+:");

		// We expect the following stream of tokens
		Token correctData[] = { new Token(TokenType.VARIABLE, "AN_D"), new Token(TokenType.OPERATOR, "xor") };

		checkTokenStream(lexer, correctData);
	}

	@Test(expected = LexerException.class)
	public void testInvalidVariable() {
		Lexer lexer = new Lexer("var@var");

		lexer.nextToken();
	}

	@Test(expected = LexerException.class)
	public void testWrongSequence() {
		Lexer lexer = new Lexer(" var  0000100 ");

		// We expect the following stream of tokens
		Token correctData[] = { new Token(TokenType.VARIABLE, "VAR"), new Token(TokenType.OPERATOR, "xor") };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testBrackets() {
		Lexer lexer = new Lexer(" (var + var2 ) ");

		// We expect the following stream of tokens
		Token correctData[] = { new Token(TokenType.OPEN_BRACKET, '('), new Token(TokenType.VARIABLE, "VAR"),
				new Token(TokenType.OPERATOR, "or"), new Token(TokenType.VARIABLE, "VAR2"),
				new Token(TokenType.CLOSED_BRACKET, ')'), };

		checkTokenStream(lexer, correctData);
	}

}
