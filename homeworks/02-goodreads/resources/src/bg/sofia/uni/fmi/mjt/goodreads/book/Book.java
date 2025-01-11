package bg.sofia.uni.fmi.mjt.goodreads.book;

import java.util.List;

public record Book(
        String ID,
        String title,
        String author,
        String description,
        List<String> genres,
        double rating,
        int ratingCount,
        String URL
) {

    public static Book of(String[] tokens) {
        throw new UnsupportedOperationException("Not yet implemented");
    
    }
}
