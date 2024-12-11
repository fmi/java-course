package bg.sofia.uni.fmi.mjt.goodreads.recommender;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BookRecommender implements BookRecommenderAPI {

    public BookRecommender(Set<Book> initialBooks, SimilarityCalculator calculator) {}


    @Override
    public Map<Book, Double> recommendBooks(Book origin, int maxN) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
}
