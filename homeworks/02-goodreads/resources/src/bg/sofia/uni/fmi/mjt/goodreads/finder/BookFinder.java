package bg.sofia.uni.fmi.mjt.goodreads.finder;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.util.List;
import java.util.Set;

public class BookFinder implements BookFinderAPI {

    public BookFinder(Set<Book> books, TextTokenizer tokenizer) {}

    public Set<Book> allBooks() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public List<Book> searchByAuthor(String authorName) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Set<String> allGenres() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public List<Book> searchByGenres(Set<String> genres, MatchOption option) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public List<Book> searchByKeywords(Set<String> keywords, MatchOption option) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
}
