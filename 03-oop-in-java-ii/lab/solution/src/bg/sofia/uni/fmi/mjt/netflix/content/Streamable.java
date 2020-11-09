package bg.sofia.uni.fmi.mjt.netflix.content;

import bg.sofia.uni.fmi.mjt.netflix.content.enums.PgRating;

public interface Streamable {
    /**
     * @return the title of the streamable content.
     */
    String getTitle();

    /**
     * @return the content duration in minutes.
     */
    int getDuration();

    /**
     * @return the PG rating of the streamable content.
     */
    PgRating getRating();
}
