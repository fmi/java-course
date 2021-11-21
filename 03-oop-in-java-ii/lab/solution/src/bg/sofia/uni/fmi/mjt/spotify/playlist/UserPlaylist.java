package bg.sofia.uni.fmi.mjt.spotify.playlist;

import bg.sofia.uni.fmi.mjt.spotify.exceptions.PlaylistCapacityExceededException;
import bg.sofia.uni.fmi.mjt.spotify.playable.Playable;

public class UserPlaylist implements Playlist {
    private static final int MAX_SIZE = 20;

    private final String name;
    private final Playable[] playableContent;

    private int currentSize = 0;

    public UserPlaylist(String name) {
        this.name = name;
        playableContent = new Playable[MAX_SIZE];
    }

    @Override
    public void add(Playable playable) throws PlaylistCapacityExceededException {
        if (currentSize == MAX_SIZE) {
            throw new PlaylistCapacityExceededException("Could not add playable " + playable.getTitle());
        }
        for (int i = 0; i < playableContent.length; i++) {
            if (playableContent[i] == null) {
                playableContent[i] = playable;
                currentSize++;
                return;
            }
        }
    }

    @Override
    public  String getName() {
        return name;
    }

}
