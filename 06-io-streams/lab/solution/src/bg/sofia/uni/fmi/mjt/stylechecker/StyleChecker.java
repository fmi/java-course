package bg.sofia.uni.fmi.mjt.stylechecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import bg.sofia.uni.fmi.mjt.stylechecker.check.BracketsCheck;
import bg.sofia.uni.fmi.mjt.stylechecker.check.CodeCheck;
import bg.sofia.uni.fmi.mjt.stylechecker.check.LineLengthCheck;
import bg.sofia.uni.fmi.mjt.stylechecker.check.SingleStatementCheck;
import bg.sofia.uni.fmi.mjt.stylechecker.check.WildcardImportCheck;

/**
 * Used for static code checks of Java files.
 *
 * Depending on a stream of user-defined configuration or default values, it
 * checks if the following rules are applied:
 * <li>there is only one statement per line;
 * <li>the line lengths do not exceed 100 (or user-defined number of) characters;
 * <li>the import statements do not use a wildcard;
 * <li>each opening block bracket is on the same line as the declaration.
 */
public class StyleChecker {

	private static final String WILDCARD_IMPORT_CHECK = "wildcard.import.check.active";
	private static final String STATEMENTS_PER_LINE_CHECK = "statements.per.line.check.active";
	private static final String LENGTH_OF_LINE_CHECK = "length.of.line.check.active";
	private static final String OPENING_BRACKET_CHECK = "opening.bracket.check.active";
	private static final String LINE_LENGTH_LIMIT = "line.length.limit";

	private static final String BRACKET_ERROR = "// FIXME Opening brackets "
												 + "should be placed on the same line as the declaration";
	private static final String WILDCARD_IMPORT_ERROR = "// FIXME Wildcards are not allowed in import statements";
	private static final String LINE_LENGTH_ERROR = "// FIXME Length of line should not exceed %s characters";
	private static final String STATEMENTS_ERROR = "// FIXME Only one statement per line is allowed";

	private static final String DEFAULT_LINE_LENGTH_LIMIT = "100";
	private static final String DEFAULT_CHECK_MODE = "true";

	private Properties properties;
	private Set<CodeCheck> checks;

	/**
	 * Creates a StyleChecker with properties having the following default values:
	 * <li>{@code wildcard.import.check.active=true}
	 * <li>{@code statements.per.line.check.active=true}
	 * <li>{@code opening.bracket.check.active=true}
	 * <li>{@code length.of.line.check.active=true}
	 * <li>{@code line.length.limit=100}
	 **/
	public StyleChecker() {
		this(null);
	}

	/**
	 * Creates a StyleChecker with custom configuration, based on the content from
	 * the given {@code inputStream}. If the stream does not contain any of the
	 * properties, the missing ones get their default values.
	 **/
	public StyleChecker(InputStream input) {
		initDefaultProperties();

		if (input != null) {
			try {
				properties.load(input);
			} catch (IOException e) {
				properties = new Properties();
				initDefaultProperties();
			}
		}

		initCheckers();
	}

	/**
	 * For each line from the given {@code source} InputStream, writes FixMe comment
	 * for the violated rules (if any) with an explanation of the style error
	 * followed by the line itself in the {@code output}.
	 **/
	public void checkStyle(InputStream source, OutputStream output) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(source));
		        PrintWriter writer = new PrintWriter(output)) {

			String line;
			Set<String> lineErrors;

			while ((line = reader.readLine()) != null) {
				if (line.trim().equals("")) {
					writer.println(line);
					continue;
				}

				lineErrors = getErrors(line);

				if (!lineErrors.isEmpty()) {
					for (String error : lineErrors) {
						// add spaces or tabulation to "FixMe" comments
						String whitespaces = line.substring(0, line.indexOf(line.trim()));
						writer.println(whitespaces + error);
					}
				}

				writer.println(line);
			}

		} catch (IOException ioExc) {
			throw new RuntimeException("Error occurred while checking style", ioExc);
		}
	}

	/**
	 * Initializes the boolean properties with their default values
	 */
	private void initDefaultProperties() {
		properties = new Properties();
		String[] booleanProps = { WILDCARD_IMPORT_CHECK, STATEMENTS_PER_LINE_CHECK,
								 LENGTH_OF_LINE_CHECK, OPENING_BRACKET_CHECK };

		for (String property : booleanProps) {
			properties.setProperty(property, DEFAULT_CHECK_MODE);
		}

		properties.setProperty(LINE_LENGTH_LIMIT, DEFAULT_LINE_LENGTH_LIMIT);
	}

	/**
	 * Initializes the {@code CodeCheck} according to the properties
	 */
	private void initCheckers() {
		checks = new HashSet<>();

		if (isPropertySet(OPENING_BRACKET_CHECK)) {
			checks.add(new BracketsCheck(BRACKET_ERROR));
		}

		if (isPropertySet(STATEMENTS_PER_LINE_CHECK)) {
			checks.add(new SingleStatementCheck(STATEMENTS_ERROR));
		}

		if (isPropertySet(LENGTH_OF_LINE_CHECK)) {
			int lineLimit = Integer.parseInt(properties.getProperty(LINE_LENGTH_LIMIT));
			checks.add(new LineLengthCheck(LINE_LENGTH_ERROR, lineLimit));
		}

		if (isPropertySet(WILDCARD_IMPORT_CHECK)) {
			checks.add(new WildcardImportCheck(WILDCARD_IMPORT_ERROR));
		}

	}

	private boolean isPropertySet(String propertyName) {
		String property = properties.getProperty(propertyName);
		return Boolean.parseBoolean(property);
	}

	private Set<String> getErrors(String line) {
		Set<String> errors = new HashSet<>();

		for (CodeCheck codeCheck : checks) {
			if (codeCheck.checkForError(line)) {
				errors.add(codeCheck.getErrorMessage());
			}
		}

		return errors;
	}
}
