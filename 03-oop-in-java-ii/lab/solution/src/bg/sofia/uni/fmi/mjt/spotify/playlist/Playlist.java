package bg.sofia.uni.fmi.mjt.spotify.playlist;

import bg.sofia.uni.fmi.mjt.spotify.exceptions.PlaylistCapacityExceededException;
import bg.sofia.uni.fmi.mjt.spotify.playable.Playable;

public interface Playlist {

    /**
     * Adds the given playable content to the playlist.
     *
     * @throws PlaylistCapacityExceededException when the playlist has exceeded its predefined capacity of 20 items.
     */
    void add(Playable playable) throws PlaylistCapacityExceededException;

    /**
     * Returns the name of the playlist.
     */
    String getName();

}