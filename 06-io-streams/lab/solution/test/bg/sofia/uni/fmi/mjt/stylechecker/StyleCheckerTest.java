package bg.sofia.uni.fmi.mjt.stylechecker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class StyleCheckerTest {

	private static final String OPENING_BRACKET_ERROR = "// FIXME Opening brackets "
													 + "should be placed on the same line as the declaration";
	private static final String WILDCARD_IMPORT_ERROR = "// FIXME Wildcards are not allowed in import statements";
	private static final String LINE_LENGTH_ERROR = "// FIXME Length of line should not exceed %s characters";
	private static final String STATEMENTS_ERROR = "// FIXME Only one statement per line is allowed";

	private static final int DEFAULT_LINE_LIMIT = 100;
	
	private static ByteArrayInputStream defaultPropertiesStream;
	private static ByteArrayInputStream customPropertiesStream;
	private static ByteArrayInputStream allPropertiesOffStream;

	private static StyleChecker checker = new StyleChecker();
	private static StyleChecker checkerWithDefaultProperties;
	private static StyleChecker checkerWithAllPropertiesOff;
	private static StyleChecker checkerWithCustomLineLength;

	@BeforeClass
	public static void setUp() throws IOException {

		String defaultProperties = "wildcard.import.check.active=true\n" 
								 + "statements.per.line.check.active=true\n"
								 + "line.length.limit=100\n" 
								 + "length.of.line.check.active=true\n"
								 + "opening.bracket.check.active=true";

		String allPropertiesOff = "wildcard.import.check.active=false\n" 
								+ "statements.per.line.check.active=false\n"
								+ "line.length.limit=100\n" 
								+ "length.of.line.check.active=false\n"
								+ "opening.bracket.check.active=false";

		String customProperties = "line.length.limit=10";

		defaultPropertiesStream = new ByteArrayInputStream(defaultProperties.getBytes());
		allPropertiesOffStream = new ByteArrayInputStream(allPropertiesOff.getBytes());
		customPropertiesStream = new ByteArrayInputStream(customProperties.getBytes());

		checkerWithDefaultProperties = new StyleChecker(defaultPropertiesStream);
		checkerWithAllPropertiesOff = new StyleChecker(allPropertiesOffStream);
		checkerWithCustomLineLength = new StyleChecker(customPropertiesStream);
	}

	private void oneLineCodeCheck(StyleChecker checker, String line, String assertMessage, String expected) {
		ByteArrayInputStream input = new ByteArrayInputStream(line.getBytes());
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		checker.checkStyle(input, output);
		String actual = new String(output.toByteArray());

		assertEquals(assertMessage, expected, actual.trim());
	}

	@Test
	public void testDefaultStyleCheckerIfImportWithWildcardProducesError() {
		String importStatement = "import java.util.*;";
		String assertMessage = "Wildcards are not allowed in import statements";

		oneLineCodeCheck(checker, importStatement, assertMessage,
		        WILDCARD_IMPORT_ERROR + System.lineSeparator() + importStatement);
		oneLineCodeCheck(checkerWithDefaultProperties, importStatement, assertMessage,
		        WILDCARD_IMPORT_ERROR + System.lineSeparator() + importStatement);
	}

	@Test
	public void testDefaultStyleCheckerForImportsWithoutWildcards() {
		String importStatement = "import java.util.List;";
		String assertMessage = "Import statements without wildcards are allowed";

		oneLineCodeCheck(checker, importStatement, assertMessage, importStatement);
		oneLineCodeCheck(checkerWithDefaultProperties, importStatement, assertMessage, importStatement);
	}

	@Test
	public void testDefaultStyleCheckerForLongImports() {
		String longImportStatement = "import java.aa.bb.cc.dd.ee.ff.gg.hh"
									 + ".ll.mm.nn.pp.qq.rr.ss.tt.uu.vv.xx.ww.yy.zz.SomeClassWithSuperLongNameAlso;";
		String assertMessage = "Import statements are not checked for line length";

		oneLineCodeCheck(checker, longImportStatement, assertMessage, longImportStatement);
		oneLineCodeCheck(checkerWithDefaultProperties, longImportStatement, assertMessage, longImportStatement);
	}

	@Test
	public void testDefaultStyleCheckerIfExceededLineLengthProducesError() throws FileNotFoundException, IOException {
		String longStatement = "new StyleChecker(test)."
							 + "testDefaultStyleCheckerIfOneStatementWithMutlipleDelimitersDoesNotProduceError();";
		String assertMessage = "Length of line should not exceed 100 characters";

		oneLineCodeCheck(checker, longStatement, assertMessage,
		        String.format(LINE_LENGTH_ERROR, DEFAULT_LINE_LIMIT) + System.lineSeparator() + longStatement);
		oneLineCodeCheck(checkerWithDefaultProperties, longStatement, assertMessage,
		        String.format(LINE_LENGTH_ERROR, DEFAULT_LINE_LIMIT) + System.lineSeparator() + longStatement);
	}

	@Test
	public void testDefaultStyleCheckerIfShortLineLengthDoesNotProduceError() {
		String shortStatement = "new StyleChecker();";
		String assertMessage = "Length of line < 100 charachers should not produce error";

		oneLineCodeCheck(checker, shortStatement, assertMessage, shortStatement);
		oneLineCodeCheck(checkerWithDefaultProperties, shortStatement, assertMessage, shortStatement);
	}

	@Test
	public void testDefaultStyleCheckerIfMultipleStatementsProduceError() {
		String multipleStatements = "new StyleChecker();new StyleCheker();";
		String assertMessage = "Multiple statements on the same line should produce error";

		oneLineCodeCheck(checker, multipleStatements, assertMessage,
		        STATEMENTS_ERROR + System.lineSeparator() + multipleStatements);
		oneLineCodeCheck(checkerWithDefaultProperties, multipleStatements, assertMessage,
		        STATEMENTS_ERROR + System.lineSeparator() + multipleStatements);
	}

	@Test
	public void testDefaultStyleCheckerIfOneStatementWithMutlipleDelimitersDoesNotProduceError() {
		String oneStatementOnly = "new StyleChecker();;;";
		String assertMessage = "One statement with multiple semicolons should not produce error";

		oneLineCodeCheck(checker, oneStatementOnly, assertMessage, oneStatementOnly);
		oneLineCodeCheck(checkerWithDefaultProperties, oneStatementOnly, assertMessage, oneStatementOnly);
	}

	@Test
	public void testDefaultStyleCheckerIfBracketOnTheSameLineDoesNotProduceError() {
		String oneStatementOnly = "public static void main(String[] args) {";
		String assertMessage = "Opening bracket on the same line as declration should not produce error";

		oneLineCodeCheck(checker, oneStatementOnly, assertMessage, oneStatementOnly);
		oneLineCodeCheck(checkerWithDefaultProperties, oneStatementOnly, assertMessage, oneStatementOnly);

	}

	@Test
	public void testDefautlStyleCheckIfBracketOnSeparateLineProducesError() {
		String oneStatementOnly = "    {";
		String assertMessage = "Opening bracket should not be on a separate line";

		oneLineCodeCheck(checker, oneStatementOnly, assertMessage,
		        OPENING_BRACKET_ERROR + System.lineSeparator() + oneStatementOnly);
		oneLineCodeCheck(checkerWithDefaultProperties, oneStatementOnly, assertMessage,
		        OPENING_BRACKET_ERROR + System.lineSeparator() + oneStatementOnly);
	}

	@Test
	public void testDefaultStyleCheckerForMultipleErrorsOnTheSameLine() {
		StyleChecker checker = new StyleChecker();

		String multipleErrorsOnTheSameLine = "new StyleChecker();new StyleChecker();new StyleChecker();"
											 + "new StyleChecker();new StyleChecker();new StyleChecker();";
		String assertMessage = "Multiple errors on the same line should produce multiple error comments.";

		List<String> expectedMessages = new ArrayList<>();
		expectedMessages.add(String.format(LINE_LENGTH_ERROR, DEFAULT_LINE_LIMIT));
		expectedMessages.add(STATEMENTS_ERROR);

		ByteArrayInputStream source = new ByteArrayInputStream(multipleErrorsOnTheSameLine.getBytes());
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		checker.checkStyle(source, output);

		String actual = new String(output.toByteArray());
		actual = actual.trim();

		String[] actualLines = actual.split("\n");
		assertEquals(multipleErrorsOnTheSameLine, actualLines[actualLines.length - 1]);

		for (int i = actualLines.length - 2; i >= 0; i--) {
			String errorMessage = actualLines[i].trim();
			assertTrue(assertMessage, expectedMessages.contains(errorMessage));

			if (expectedMessages.contains(errorMessage)) {
				expectedMessages.remove(errorMessage);
			}
		}

		assertTrue(expectedMessages.isEmpty());

	}

	@Test
	public void testCustomStyleCheckerWithWildcardImportCheckOff() {
		String importStatement = "import java.util.*;";
		String assertMessage = "Wildcard check is turned off, so no error should be produced";

		oneLineCodeCheck(checkerWithAllPropertiesOff, importStatement, assertMessage, importStatement);
	}

	@Test
	public void testCustomStyleCheckerWithLineLengthCheckOff() {
		String longStatement = "new StyleChecker(test)"
							 + ".testDefaultStyleCheckerIfOneStatementWithMutlipleDelimitersDoesNotProduceError();";
		String assertMessage = "Length of line is turned off, so no error should ne produced";

		oneLineCodeCheck(checkerWithAllPropertiesOff, longStatement, assertMessage, longStatement);
	}

	@Test
	public void testCustomStyleCheckerWithOneStatementPerLineCheckOff() {
		String multipleStatements = "new StyleChecker();new StyleCheker();";
		String assertMessage = "Multiple statements check is turned off, so no error should be produced";

		oneLineCodeCheck(checkerWithAllPropertiesOff, multipleStatements, assertMessage, multipleStatements);
	}

	@Test
	public void testCustomStyleCheckerWithOpeningBracketsCheckOff() {
		String oneStatementOnly = "    {";
		String assertMessage = "Opening brackets check is turned off, so no error should be produced";
		oneLineCodeCheck(checkerWithAllPropertiesOff, oneStatementOnly.trim(), assertMessage, oneStatementOnly.trim());
	}

	@Test
	public void testCustomStyleCheckerWithCustomLineLengthSet() {
		String longStatement = "new StyleChecker(test)"
							 + ".testDefaultStyleCheckerIfOneStatementWithMutlipleDelimitersDoesNotProduceError();";
		String assertMessage = "Length of line should not exceed 10 characters";

		final int smallLimit = 10;
		
		oneLineCodeCheck(checkerWithCustomLineLength, longStatement, assertMessage,
		        String.format(LINE_LENGTH_ERROR, smallLimit) + System.lineSeparator() + longStatement);
	}

	@Test
	public void testIfDefaultPropertiesAreUsedAsFallback() throws IOException {
		ByteArrayInputStream emptyStream = new ByteArrayInputStream("".getBytes());
		StyleChecker sc = new StyleChecker(emptyStream);

		String longImportStatement = "import java.aa.bb.cc.dd.ee.ff.gg.hh.ll.mm."
									 + "nn.pp.qq.rr.ss.tt.uu.vv.xx.ww.yy.zz.SomeClassWithSuperLongNameAlso;";
		String assertMessage = "Import statements are not checked for line length";

		oneLineCodeCheck(sc, longImportStatement, assertMessage, longImportStatement);
	}

	@Test
	public void testEmptySourceFile() throws FileNotFoundException {
		ByteArrayInputStream source = new ByteArrayInputStream("".getBytes());
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		checker.checkStyle(source, output);

		String expected = "";
		String actual = new String(output.toByteArray());

		assertEquals(expected, actual);
	}
}
