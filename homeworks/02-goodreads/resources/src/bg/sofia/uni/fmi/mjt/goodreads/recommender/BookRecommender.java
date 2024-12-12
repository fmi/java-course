package bg.sofia.uni.fmi.mjt.goodreads.recommender;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;

import java.util.Map;
import java.util.Set;

public class BookRecommender implements BookRecommenderAPI {

    public BookRecommender(Set<Book> initialBooks, SimilarityCalculator calculator) {}


    @Override
    public SortedMap<Book, Double> recommendBooks(Book origin, int maxN) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
}
