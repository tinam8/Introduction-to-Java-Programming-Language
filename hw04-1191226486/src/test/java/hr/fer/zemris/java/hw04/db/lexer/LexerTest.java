package hr.fer.zemris.java.hw04.db.lexer;

import static org.junit.Assert.*;

import org.junit.Test;

public class LexerTest {

	@Test
	public void tokens() {
		Lexer lexer = new Lexer("query jmbag = \"0123456789\"");
		assertEquals(TokenType.FIELD_NAME, lexer.nextToken().getType());
		assertEquals(TokenType.OPERATOR, lexer.nextToken().getType());
		assertEquals(TokenType.VALUE, lexer.nextToken().getType());
		assertEquals(TokenType.EOF, lexer.nextToken().getType());	
	}
	
	@Test
	public void queryWithAnd() {
		Lexer lexer = new Lexer(" query jmbag<=\"0123456789\" and firstName LIKE \"a*aaa\"");
		assertEquals(TokenType.FIELD_NAME, lexer.nextToken().getType());
		assertEquals(TokenType.OPERATOR, lexer.nextToken().getType());
		assertEquals(TokenType.VALUE, lexer.nextToken().getType());
		assertEquals(TokenType.FIELD_NAME, lexer.nextToken().getType());
		assertEquals(TokenType.OPERATOR, lexer.nextToken().getType());
		assertEquals("a*aaa", lexer.nextToken().getValue());
		assertEquals(TokenType.EOF, lexer.nextToken().getType());	
	}
	
	@Test(expected=LexerException.class)
	public void queryMissingAnd() {
		Lexer lexer = new Lexer(" query jmbag<=\"0123456789\" firstName LIKE \"a*aaa\"");
		assertEquals(TokenType.FIELD_NAME, lexer.nextToken().getType());
		assertEquals(TokenType.OPERATOR, lexer.nextToken().getType());
		assertEquals(TokenType.VALUE, lexer.nextToken().getType());
		assertEquals(TokenType.FIELD_NAME, lexer.nextToken().getType());
		assertEquals(TokenType.OPERATOR, lexer.nextToken().getType());
		assertEquals("a*aaa", lexer.nextToken().getValue());
		assertEquals(TokenType.EOF, lexer.nextToken().getType());	
	}

}
