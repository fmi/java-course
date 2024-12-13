package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.descriptions;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.util.Collection;
import java.util.Map;
import java.util.Set;


public class TFIDFSimilarityCalculator implements SimilarityCalculator {

    public TFIDFSimilarityCalculator(Set<Book> books, TextTokenizer tokenizer) {}

    /*
     * Do not modify!
     */
    @Override
    public double calculateSimilarity(Book first, Book second) {
        Map<String, Double> tfIdfScoresFirst = computeTFIDF(first);
        Map<String, Double> tfIdfScoresSecond = computeTFIDF(second);

        return cosineSimilarity(tfIdfScoresFirst, tfIdfScoresSecond);
    }

    public Map<String, Double> computeTFIDF(Book book) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Map<String, Double> computeTF(Book book) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Map<String, Double> computeIDF(Book book) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private double cosineSimilarity(Map<String, Double> first, Map<String, Double> second) {
        double magnitudeFirst = magnitude(first.values());
        double magnitudeSecond = magnitude(second.values());

        return dotProduct(first, second) / (magnitudeFirst * magnitudeSecond);
    }

    private double dotProduct(Map<String, Double> first, Map<String, Double> second) {
        Set<String> commonKeys = new HashSet<>(first.keySet());
        commonKeys.retainAll(second.keySet());

        return keySet.stream()
                .mapToDouble(word -> first.get(word) * second.get(word))
                .sum();
    }

    private double magnitude(Collection<Double> input) {
        double squaredMagnitude = input.stream()
                .map(v -> v * v)
                .reduce(0.0, Double::sum);

        return Math.sqrt(squaredMagnitude);
    }
    
}
