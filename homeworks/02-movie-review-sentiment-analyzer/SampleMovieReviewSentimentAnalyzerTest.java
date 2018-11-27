package bg.uni.sofia.fmi.mjt.sentiment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;

public class MovieReviewSentimentAnalyzerTest {

	private MovieReviewSentimentAnalyzer analyzer;

	private InputStream reviewsStream;
	private InputStream stopwordsStream;
	private OutputStream resultStream;

	@Before
	public void init() throws FileNotFoundException {
		stopwordsStream = new FileInputStream("resources/stopwords.txt");
		reviewsStream = new FileInputStream("resources/reviews.txt");
		resultStream = new FileOutputStream("resources/reviews.txt", true);
		analyzer = new MovieReviewSentimentAnalyzer(stopwordsStream, reviewsStream, resultStream);
	}

	@Test
	public void testIsStopWordNegativeFromDictionary() {
		String assertMessage = "A word should not be incorrectly identified as a stopword, if it is not part of the stopwords list";
		assertFalse(assertMessage, analyzer.isStopWord("effects"));
	}

	@Test
	public void testIsStopWordNegativeNotFromDictionary() {
		String assertMessage = "A word should not be incorrectly identified as a stopword, if it is not part of the stopwords list";
		assertFalse(assertMessage, analyzer.isStopWord("stoyo"));
	}

	@Test
	public void testIsStopWordPositive() {
		assertTrue("Stop word not counted as stop word", analyzer.isStopWord("a"));
	}
}
