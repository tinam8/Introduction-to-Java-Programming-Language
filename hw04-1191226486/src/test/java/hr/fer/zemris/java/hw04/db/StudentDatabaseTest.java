package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class StudentDatabaseTest {

	@Test
	public void testfilterAndforJMBAG() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("database.txt"), StandardCharsets.UTF_8);
		StudentDatabase database = new StudentDatabase(lines);
		StudentRecord record1 = database.forJMBAG("0000000016");

		assertEquals("Glumac", record1.getLastName());
		assertEquals(null, database.forJMBAG("3300000016"));

		List<StudentRecord> filteredList = database.filter(new AlwaysTrue());
		assertEquals(63, filteredList.size());

		filteredList = database.filter(record -> {
			return false;
		});
		assertEquals(0, filteredList.size());

	}

	@Test
	public void comparison() throws IOException {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertEquals(true, oper.satisfied("Ana", "Jasna")); // true, since Ana <
															// Jasna

		oper = ComparisonOperators.LESS_OR_EQUALS;
		assertEquals(true, oper.satisfied("Ana", "Jasna")); // true, since Ana <
															// Jasna

		oper = ComparisonOperators.GREATER;
		assertEquals(false, oper.satisfied("Ana", "Jasna"));

		oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertEquals(false, oper.satisfied("Ana", "Jasna"));

		oper = ComparisonOperators.EQUALS;
		assertEquals(false, oper.satisfied("Ana", "Anaa"));
		assertEquals(true, oper.satisfied("Ana", "Ana"));

		oper = ComparisonOperators.NOT_EQUALS;
		assertEquals(true, oper.satisfied("Ana", "Anaa"));
		assertEquals(false, oper.satisfied("Ana", "Ana"));

		oper = ComparisonOperators.LIKE;
		assertEquals(false, oper.satisfied("Zagreb", "Aba*"));

		oper = ComparisonOperators.LIKE;
		assertEquals(false, oper.satisfied("AAA", "AA*AA"));
		assertEquals(false, oper.satisfied("AAA", "AA*AA"));
		assertEquals(true, oper.satisfied("AAAA", "AA*AA"));
		assertEquals(true, oper.satisfied("00000", "000*"));
		assertEquals(false, oper.satisfied("0044", "000*"));
		assertEquals(true, oper.satisfied("00000", "*000"));
	}

	@Test
	public void fieldValueGetters() throws IOException {
		IFieldValueGetter oper = FieldValueGetters.FIRST_NAME;
		StudentRecord record = new StudentRecord("0123456789", "Prezime", "Ime", "5");

		assertEquals("Prezime", FieldValueGetters.LAST_NAME.get(record));
		assertEquals("Ime", FieldValueGetters.FIRST_NAME.get(record));
		assertEquals("0123456789", FieldValueGetters.JMBAG.get(record));
	}

	@Test
	public void conditionalExpression() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Bos*",
				ComparisonOperators.LIKE);
		
		StudentRecord record = new StudentRecord("0123456789", "Prezime", "Ime", "5");
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record), expr.getStringLiteral()); // returns "Bos*"
		assertEquals(false, recordSatisfies);
		
		record = new StudentRecord("0123456789", "Bosna", "Ime", "5");
		recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record), expr.getStringLiteral());
		assertEquals(true, recordSatisfies);
		
	}

	private class AlwaysTrue implements IFilter {

		@Override
		public boolean accepts(StudentRecord record) {
			return true;
		}

	}
}
