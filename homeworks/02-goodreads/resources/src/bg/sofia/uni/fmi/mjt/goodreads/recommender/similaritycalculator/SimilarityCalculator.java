package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;

public interface SimilarityCalculator {
    /**
     * Calculates the similarity between two books.
     *
     * @param first, second - Books used for similarity calculation
     * @throws IllegalArgumentException if first or second is null
     * @return a double - score of similarity
     */
    double calculateSimilarity(Book first, Book second);
}
