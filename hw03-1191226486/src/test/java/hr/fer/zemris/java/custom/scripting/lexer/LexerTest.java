package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;


public class LexerTest {

	@Test
	public void testNotNull() {
		Lexer lexer = new Lexer("");
		
		assertNotNull("Token was expected but null was returned.", lexer.nextToken());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testNullInput() {
		// must throw!
		new Lexer(null);
	}

	@Test
	public void testEmpty() {
		Lexer lexer = new Lexer("");
		
		assertEquals("Empty input must generate only EOF token.", TokenType.EOF, lexer.nextToken().getType());
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		Lexer lexer = new Lexer("");
		
		Token token = lexer.nextToken();
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
	}

	@Test(expected=LexerException.class)
	public void testRadAfterEOF() {
		Lexer lexer = new Lexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		lexer.nextToken();
	}
	
	
	@Test
	public void testTextOnly() {
		// Lets check for several words...
		Lexer lexer = new Lexer("This is sample text.  ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TEXT, "This is sample text.  "),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test(expected=LexerException.class)
	public void testINvalidEscapeText() {
		// Lets check for several words...
		Lexer lexer = new Lexer("This is sample \\} text.  ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TEXT, "This is sample text.  "),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testValidEscapeText() {
		// Lets check for several words...
		Lexer lexer = new Lexer("This is sample \\{ text.\\\\  ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TEXT, "This is sample { text.\\  "),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testEchoTag() {
		// Lets check for several words...
		Lexer lexer = new Lexer("{$= 123 @func -12.5$}");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.BEGINNIG_TAG, "{$"),
			new Token(TokenType.TAG_ECHO, "ECHO"),
			new Token(TokenType.INTEGER_NUMBER, 123),
			new Token(TokenType.FUNCTION, "@func"),
			new Token(TokenType.DECIMAL_NUMBER, -12.5),
			new Token(TokenType.ENDING_TAG, "$}"),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testEndTag() {
		// Lets check for several words...
		Lexer lexer = new Lexer("{$ eNd$}");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.BEGINNIG_TAG, "{$"),
			new Token(TokenType.TAG_END, "eNd"),
			new Token(TokenType.ENDING_TAG, "$}"),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testForTag() {
		// Lets check for several words...
		Lexer lexer = new Lexer("{$ FOR  i__235 abb @a__8 $}");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.BEGINNIG_TAG, "{$"),
			new Token(TokenType.TAG_FOR, "FOR"),
			new Token(TokenType.VARIABLE, "i__235"),
			new Token(TokenType.VARIABLE, "abb"),
			new Token(TokenType.FUNCTION, "@a__8"),
			new Token(TokenType.ENDING_TAG, "$}"),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test(expected = LexerException.class)
	public void testInvalidTag() {
		// Lets check for several words...
		Lexer lexer = new Lexer("{$ FORR  i$}");

		lexer.nextToken();
		lexer.nextToken();

	}
	
	
	@Test(expected = LexerException.class)
	public void testInvalidString() {
		// Lets check for several words...
		Lexer lexer = new Lexer("{$ FOR \"aaa  $}");

		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
	}
	
	@Test
	public void testValidString() {
		// Lets check for several words...
		Lexer lexer = new Lexer("{$ FOR \"a\\\"a\\\\a \" abb @a__8 $}");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.BEGINNIG_TAG, "{$"),
			new Token(TokenType.TAG_FOR, "FOR"),
			new Token(TokenType.STRING, "a\"a\\a "),
			new Token(TokenType.VARIABLE, "abb"),
			new Token(TokenType.FUNCTION, "@a__8"),
			new Token(TokenType.ENDING_TAG, "$}"),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testValidStringNoSpace() {
		// Lets check for several words...
		Lexer lexer = new Lexer("{$ FOR tmp+@sin\"xyz\" $}");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.BEGINNIG_TAG, "{$"),
			new Token(TokenType.TAG_FOR, "FOR"),
			new Token(TokenType.VARIABLE, "tmp"),
			new Token(TokenType.OPERATOR, '+'),
			new Token(TokenType.FUNCTION, "@sin"),
			new Token(TokenType.STRING, "xyz"),
			new Token(TokenType.ENDING_TAG, "$}"),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testValidStringText() {
		// Lets check for several words...
		Lexer lexer = new Lexer("Example \\{$=1$}. Now actually write one {$=1$}");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TEXT, "Example {$=1$}. Now actually write one "),
			new Token(TokenType.BEGINNIG_TAG, "{$"),
			new Token(TokenType.TAG_ECHO, "ECHO"),
			new Token(TokenType.INTEGER_NUMBER, 1),
			new Token(TokenType.ENDING_TAG, "$}"),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void test1() {
		// Lets check for several words...
		Lexer lexer = new Lexer("Hello. This is {$= i $}");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TEXT, "Hello. This is "),
			new Token(TokenType.BEGINNIG_TAG, "{$"),
			new Token(TokenType.TAG_ECHO, "ECHO"),
			new Token(TokenType.VARIABLE, "i"),
			new Token(TokenType.ENDING_TAG, "$}"),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	
	
	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	private void checkTokenStream(Lexer lexer, Token[] correctData) {
		int counter = 0;
		for(Token expected : correctData) {
			Token actual = lexer.nextToken();
			String msg = "Checking token "+counter + ":";
			assertEquals(msg, expected.getType(), actual.getType());
			assertEquals(msg, expected.getValue(), actual.getValue());
			counter++;
		}
	}

		
	
}
