import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WordAnalyzerTest {

    private static WordAnalyzer wordAnalyzer;

    @BeforeClass
    public static void setUp() {
        wordAnalyzer = new WordAnalyzer();
    }

    @Test
    public void testWordAnalyzer_ThreeCommon() {
        assertEquals(wordAnalyzer.getSharedLetters("house", "home"), "eho");
    }

    @Test
    public void testWordAnalyzer_OneCommon() {
        assertEquals(wordAnalyzer.getSharedLetters("Micky", "mouse"), "m");
    }

    @Test
    public void testWordAnalyzer_NoCommon() {
        assertEquals(wordAnalyzer.getSharedLetters("house", "villa"), "");
    }

    @Test
    public void testWordAnalyzer_AllCommon() {
        assertEquals(wordAnalyzer.getSharedLetters("TuB", "BuT"), "btu");
    }

    @Test
    public void testWordAnalyzer_Same() {
        assertEquals(wordAnalyzer.getSharedLetters("run", "run"), "nru");
    }

    @Test
    public void testWordAnalyzer_SameDiffCap() {
        assertEquals(wordAnalyzer.getSharedLetters("run", "RUN"), "nru");
    }

}
