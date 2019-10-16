import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SampleWordAnalyzerTest {

    private static WordAnalyzer wordAnalyzer;

    @BeforeClass
    public static void setUp() {
        wordAnalyzer = new WordAnalyzer();
    }

    @Test
    public void testWordAnalyzer_ThreeCommon() {
        assertEquals("eho", wordAnalyzer.getSharedLetters("house", "home"));
    }

    @Test
    public void testWordAnalyzer_OneCommon() {
        assertEquals("m", wordAnalyzer.getSharedLetters("Micky", "mouse"));
    }

    @Test
    public void testWordAnalyzer_NoCommon() {
        assertEquals("", wordAnalyzer.getSharedLetters("house", "villa"));
    }

}
