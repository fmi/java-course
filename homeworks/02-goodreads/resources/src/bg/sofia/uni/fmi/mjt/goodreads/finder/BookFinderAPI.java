package bg.sofia.uni.fmi.mjt.goodreads.finder;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;

import java.util.List;
import java.util.Set;

public interface BookFinderAPI {

    /**
     * Retrieves all books
     *
     * @return a Set of all books.
     */
    Set<Book> allBooks();

    /**
     * Retrieves all genres
     *
     * @return a Set of all genres.
     */
    Set<String> allGenres();

    /**
     * Searches for books by the specified author name.
     *
     * @param authorName the name of the author to search for.
     * @throws IllegalArgumentException if the author name if null or empty
     * @return a List of books written by the specified author.
     *         Returns an empty list if no books are found.
     */
    List<Book> searchByAuthor(String authorName);

    /**
     * Searches for books that belong to the specified genres.
     * The search can be based on different match options (all or any genres).
     *
     * @param genres a Set of genres to search for.
     * @throws IllegalArgumentException if {@param genres} is null
     * @return a List of books that match the given genres according to the MatchOption
     *         Returns an empty list if no books are found.
     */
    List<Book> searchByGenres(Set<String> genres, MatchOption option);

    /**
     * Searches for books that match the specified keywords.
     * The search can be based on different match options (all or any keywords).
     *
     * @param keywords a {@code Set} of keywords to search for.
     * @param option the {@code MatchOption} that defines the search criteria
     *               (either {@link MatchOption#MATCH_ALL} or {@link MatchOption#MATCH_ANY}).
     * @return a List of books in which the title or description match the given keywords according to the MatchOption
     *         Returns an empty list if no books are found.
     */
    List<Book> searchByKeywords(Set<String> keywords, MatchOption option);
    
}
