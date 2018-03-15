package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

public class QueryParserTest {

	@Test
	public void parseCorrectQuery() {
		QueryParser qp1 = new QueryParser(" jmbag = \"0123456789\" ");
		assertEquals(true, qp1.isDirectQuery());
		assertEquals("0123456789", qp1.getQueriedJMBAG());
		assertEquals(1, qp1.getQuery().size());
	}

	@Test(expected = IllegalStateException.class)
	public void parseIndirectQuery() {
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertEquals(false, qp2.isDirectQuery()); // false
		assertEquals(2, + qp2.getQuery().size()); // 2
		qp2.getQueriedJMBAG(); // would throw!
	}
	
	@Test(expected = QueryParserException.class)
	public void parseIncorrectQuery() {
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\"  lastName>\"J\"");
	}
	
	@Test(expected = QueryParserException.class)
	public void parseIncorrectQuery2() {
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"");
	}

}
