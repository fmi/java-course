import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleDNAAnalyzerTest {

    private static DNAAnalyzer dnaAnalyzer;

    @BeforeAll
    public static void setUp() {
        dnaAnalyzer = new DNAAnalyzer();
    }

    @Test
    public void testDNAAnalyzer_Abracadabra() {
        assertEquals("abra", dnaAnalyzer.longestRepeatingSequence("abracadabra"));
    }

    @Test
    public void testDNAAnalyzer_DNASequence() {
        assertEquals("TACTC", dnaAnalyzer.longestRepeatingSequence("ATACTCGGTACTCT"));
    }
}
