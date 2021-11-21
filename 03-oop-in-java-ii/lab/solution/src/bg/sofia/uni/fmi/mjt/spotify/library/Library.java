package bg.sofia.uni.fmi.mjt.spotify.library;

import bg.sofia.uni.fmi.mjt.spotify.exceptions.EmptyLibraryException;
import bg.sofia.uni.fmi.mjt.spotify.exceptions.LibraryCapacityExceededException;
import bg.sofia.uni.fmi.mjt.spotify.playlist.Playlist;
import bg.sofia.uni.fmi.mjt.spotify.exceptions.PlaylistNotFoundException;

public interface Library {

    /**
     * Adds the provided playlist to the library.
     *
     * @throws LibraryCapacityExceededException when the library capacity (fixed to 21) is exceeded.
     */
    void add(Playlist playlist) throws LibraryCapacityExceededException;

    /**
     * Removes the playlist with the given name from the library.
     *
     * @throws IllegalArgumentException if the given playlist name matches the default "Liked Content" playlist name.
     * @throws EmptyLibraryException if the library is empty.
     * @throws PlaylistNotFoundException if the playlist with the given name is not present in the library.
     */
    void remove(String name) throws EmptyLibraryException, PlaylistNotFoundException;

    /**
     * Returns the default "Liked Content" playlist from the library.
     */
    Playlist getLiked();

}
