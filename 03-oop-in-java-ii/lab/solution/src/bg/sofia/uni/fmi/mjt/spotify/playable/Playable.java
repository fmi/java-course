package bg.sofia.uni.fmi.mjt.spotify.playable;

public interface Playable {

    /**
     * Simulates playing of the content, which results in increment of the total plays count,
     * and returns a message for the user. The message has the following format:
     * "Currently playing <type> content: <content_title>", where <type> is the type (case-insensitive) of the playable content, and
     * <content_title> is its title.
     */
    String play();

    /**
     * Returns the total number of times the content has been played.
     */
    int getTotalPlays();

    /**
     * Returns the title of the playable.
     */
    String getTitle();

    /**
     * Returns the artist of the playable.
     */
    String getArtist();

    /**
     * Returns the publishing year of the playable.
     */
    int getYear();

    /**
     * Returns the duration of the playable.
     */
    double getDuration();

}
