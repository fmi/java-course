package bg.sofia.uni.fmi.mjt.spotify.library;

import bg.sofia.uni.fmi.mjt.spotify.exceptions.EmptyLibraryException;
import bg.sofia.uni.fmi.mjt.spotify.exceptions.LibraryCapacityExceededException;
import bg.sofia.uni.fmi.mjt.spotify.exceptions.PlaylistNotFoundException;
import bg.sofia.uni.fmi.mjt.spotify.playlist.Playlist;
import bg.sofia.uni.fmi.mjt.spotify.playlist.UserPlaylist;

public class UserLibrary implements Library {

    private static final int MAX_PLAYLISTS = 21;
    private static final String LIKED_SONGS_PLAYLIST_NAME = "Liked Content";

    private final Playlist[] playlists;

    private int currentPlaylists = 1;

    public UserLibrary() {
        playlists = new UserPlaylist[MAX_PLAYLISTS];
        playlists[0] = new UserPlaylist(LIKED_SONGS_PLAYLIST_NAME);
    }

    @Override
    public void add(Playlist playlist) throws LibraryCapacityExceededException {
        if (currentPlaylists == MAX_PLAYLISTS) {
            throw new LibraryCapacityExceededException("User library is full");
        }
        for (int i = 0; i < playlists.length; i++) {
            if (playlists[i] == null) {
                playlists[i] = playlist;
                currentPlaylists++;
                return;
            }
        }
    }

    @Override
    public Playlist getLiked() {
        return playlists[0];
    }

    @Override
    public void remove(String name) throws EmptyLibraryException, PlaylistNotFoundException {
        if (playlists[0].getName().equals(name)) {
            throw new IllegalArgumentException(LIKED_SONGS_PLAYLIST_NAME + " playlist can not be deleted");
        }
        if (currentPlaylists == 1) {
            throw new EmptyLibraryException("Library is empty, could not remove " + name);
        }
        for (int i = 0; i < playlists.length; i++) {
            if (playlists[i] != null && name.equals(playlists[i].getName())) {
                playlists[i] = null;
                currentPlaylists--;
                return;
            }
        }
        throw new PlaylistNotFoundException("Could not find playlist " + name);
    }
}
