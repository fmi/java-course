package bg.sofia.uni.fmi.mjt.escaperoom;

import bg.sofia.uni.fmi.mjt.escaperoom.exception.RoomNotFoundException;
import bg.sofia.uni.fmi.mjt.escaperoom.room.EscapeRoom;
import bg.sofia.uni.fmi.mjt.escaperoom.room.Review;
import bg.sofia.uni.fmi.mjt.escaperoom.team.Team;

public interface EscapeRoomPortalAPI {

    /**
     * Returns the escape room with the specified name.
     *
     * @param roomName the name of the escape room.
     * @return the escape room with the specified name.
     * @throws IllegalArgumentException if the room name is null, empty or blank
     * @throws RoomNotFoundException    if the platform does not contain an escape room with the specified name.
     */
    EscapeRoom getEscapeRoomByName(String roomName) throws RoomNotFoundException;

    /**
     * Adds a review for the escape room with the specified name.
     *
     * @param roomName the name of the escape room.
     * @throws IllegalArgumentException if the room name is null, empty or blank, or if the review is null
     * @throws RoomNotFoundException    if the platform does not contain an escape room with the specified name.
     */
    void reviewEscapeRoom(String roomName, Review review) throws RoomNotFoundException;

    /**
     * Returns all reviews for the escape room with the specified name, in the order they have been added.
     * If there are no reviews, returns an empty array.
     *
     * @param roomName the name of the escape room.
     * @return the reviews for the escape room with the specified name
     * @throws IllegalArgumentException if the room name is null, empty or blank, or if the review is null
     * @throws RoomNotFoundException    if the platform does not contain an escape room with the specified name.
     */
    Review[] getReviews(String roomName) throws RoomNotFoundException;

    /**
     * Returns the team with the highest rating. For each room successfully escaped (within the maximum
     * escape time), a team gets points equal to the room difficulty rank (1-4), plus bonus for fast escape:
     * +2 points for escape time less than or equal to 50% of the maximum escape time, or
     * +1 points for escape time less than or equal to 75% (and more than 50%) of the maximum escape time
     * The rating of a team is equal to the sum of all points collected.
     *
     * @return the top team by rating. If there are two or more teams with same highest rating, return any of them.
     * If there are no teams in the platform, returns null.
     */
    Team getTopTeamByRating();

}
