import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SampleDNAAnalyzerTest {

    private static DNAAnalyzer dnaAnalyzer;

    @BeforeClass
    public static void setUp() {
        dnaAnalyzer = new DNAAnalyzer();
    }

    @Test
    public void testDNAAnalyzer_Abracadabra() {
        assertEquals(dnaAnalyzer.longestRepeatingSequence("abracadabra"), "abra");
    }

    @Test
    public void testDNAAnalyzer_DNASequence() {
        assertEquals(dnaAnalyzer.longestRepeatingSequence("ATACTCGGTACTCT"), "TACTC");
    }

}
