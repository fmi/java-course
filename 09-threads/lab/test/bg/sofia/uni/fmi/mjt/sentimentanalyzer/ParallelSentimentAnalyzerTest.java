package bg.sofia.uni.fmi.mjt.sentimentanalyzer;

import bg.sofia.uni.fmi.mjt.sentimentnalyzer.AnalyzerInput;
import bg.sofia.uni.fmi.mjt.sentimentnalyzer.ParallelSentimentAnalyzer;
import bg.sofia.uni.fmi.mjt.sentimentnalyzer.SentimentScore;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParallelSentimentAnalyzerTest {

    private Map<String, SentimentScore> getMockSentimentLexicon() {
        return Map.of(
                "love", SentimentScore.fromScore(3),
                "hate", SentimentScore.fromScore(-3),
                "happy", SentimentScore.fromScore(2),
                "sad", SentimentScore.fromScore(-2),
                "good", SentimentScore.fromScore(1),
                "fun", SentimentScore.fromScore(2),
                "bad", SentimentScore.fromScore(-1)
        );
    }

    private Set<String> getMockStopWords() {
        return Set.of(
                "sometimes",
                "work",
                "I"
        );
    }

    @Test
    public void testAnalyzeWithMultipleInputs() {
        ParallelSentimentAnalyzer analyzer = new ParallelSentimentAnalyzer(2, getMockStopWords(), getMockSentimentLexicon());

        String[] inputs = {
                "I love programming, it's so fun. But sometimes I hate when the code doesn't work.",
                "Today was a good day. I felt happy and accomplished, though I had some challenges.",
                "I feel so sad today. Everything seems bad and nothing is going right.",
                "I love working on new projects. However, I hate the pressure of deadlines.",
                "Life is good. I am happy with my work and personal life.",
                "The weather is nice today, which makes me feel good. I love sunny days.",
                "I feel bad about the mistakes I made yesterday. It's tough to fix things.",
                "Hate is such a strong word. It's better to focus on good things.",
                "Good things come to those who wait. I am confident about the future.",
                "Sad to see my friends leave, but I know they will be successful in their new journey."
        };

        AnalyzerInput[] analyzerInputs = new AnalyzerInput[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            analyzerInputs[i] = new AnalyzerInput("ID-" + i, new StringReader(inputs[i]));
        }

        Map<String, SentimentScore> results = analyzer.analyze(analyzerInputs);

        assertEquals(10, results.size(), "Expected 10 results");

        assertTrue(results.get("ID-0").getScore() >= 0, "Expected a positive sentiment for input 0");
        assertTrue(results.get("ID-1").getScore() >= 0, "Expected a positive sentiment for input 1");
        assertTrue(results.get("ID-2").getScore() <= 0, "Expected a negative sentiment for input 2");
        assertTrue(results.get("ID-3").getScore() <= 0, "Expected a negative sentiment for input 3");
        assertTrue(results.get("ID-4").getScore() >= 0, "Expected a positive sentiment for input 4");
        assertTrue(results.get("ID-5").getScore() >= 0, "Expected a positive sentiment for input 5");
        assertTrue(results.get("ID-6").getScore() <= 0, "Expected a negative sentiment for input 6");
        assertTrue(results.get("ID-7").getScore() <= 0, "Expected a positive sentiment for input 7");
        assertTrue(results.get("ID-8").getScore() >= 0, "Expected a positive sentiment for input 8");
        assertTrue(results.get("ID-9").getScore() <= 0, "Expected a negative sentiment for input 9");
    }
}
