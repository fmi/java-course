package bg.sofia.uni.fmi.mjt.escaperoom;

import bg.sofia.uni.fmi.mjt.escaperoom.exception.RoomNotFoundException;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.PlatformCapacityExceededException;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.RoomAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.TeamNotFoundException;
import bg.sofia.uni.fmi.mjt.escaperoom.room.EscapeRoom;
import bg.sofia.uni.fmi.mjt.escaperoom.room.Review;
import bg.sofia.uni.fmi.mjt.escaperoom.team.Team;

public class EscapeRoomPlatform implements EscapeRoomAdminAPI, EscapeRoomPortalAPI {

    private int maxCapacity;
    private int escapeRoomsCount;
    private EscapeRoom[] escapeRooms;

    private Team[] teams;

    public EscapeRoomPlatform(Team[] teams, int maxCapacity) {
        this.teams = teams;
        this.escapeRooms = new EscapeRoom[maxCapacity];
        this.maxCapacity = maxCapacity;
    }

    @Override
    public void addEscapeRoom(EscapeRoom room) throws RoomAlreadyExistsException {
        if (room == null) {
            throw new IllegalArgumentException("room should not be null");
        }

        if (escapeRoomsCount == maxCapacity) {
            throw new PlatformCapacityExceededException("Platform capacity of " + escapeRoomsCount + " exhausted");
        }

        for (EscapeRoom currRoom : escapeRooms) {
            if (currRoom != null && currRoom.equals(room)) {
                throw new RoomAlreadyExistsException("Room with name " + currRoom.getName() + " already exists");
            }
        }

        for (int i = 0; i < maxCapacity; i++) {
            if (escapeRooms[i] == null) {
                escapeRooms[i] = room;
                escapeRoomsCount++;
                break;
            }
        }

    }

    @Override
    public void removeEscapeRoom(String roomName) throws RoomNotFoundException {
        if (roomName == null || roomName.isEmpty() || roomName.isBlank()) {
            throw new IllegalArgumentException("room name should not be null, empty or blank");
        }

        for (int i = 0; i < maxCapacity; i++) {
            if (escapeRooms[i] != null && escapeRooms[i].getName().equals(roomName)) {
                escapeRooms[i] = null;
                escapeRoomsCount--;
                return;
            }
        }

        throw new RoomNotFoundException("Room name: " + roomName);
    }

    @Override
    public EscapeRoom[] getAllEscapeRooms() {
        EscapeRoom[] rooms = new EscapeRoom[escapeRoomsCount];

        int currIndex = 0;
        for (int i = 0; i < maxCapacity; i++) {
            if (escapeRooms[i] != null) {
                rooms[currIndex++] = escapeRooms[i];
            }
        }

        return rooms;
    }

    @Override
    public void registerAchievement(String roomName, String teamName, int escapeTime)
        throws RoomNotFoundException, TeamNotFoundException {

        EscapeRoom room = getEscapeRoomByName(roomName);
        Team team = getTeamByName(teamName);

        if (escapeTime <= 0 || escapeTime > room.getMaxTimeToEscape()) {
            throw new IllegalArgumentException("Escape time should be in the range (0, maxTimeToEscape) for this room");
        }

        int bonus = 0;
        double escapeSpeed = escapeTime / (double) room.getMaxTimeToEscape();
        if (escapeSpeed <= 0.5) {
            bonus = 2;
        } else if (escapeSpeed <= 0.75) {
            bonus = 1;
        }

        team.updateRating(room.getDifficulty().getRank() + bonus);

    }

    private Team getTeamByName(String name) throws TeamNotFoundException {
        if (name == null || name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("Team name cannot be null, empty or blank");
        }

        for (Team t : teams) {
            if (t != null && t.getName().equals(name)) {
                return t;
            }
        }

        throw new TeamNotFoundException("Team name: " + name);
    }

    @Override
    public EscapeRoom getEscapeRoomByName(String roomName) throws RoomNotFoundException {
        if (roomName == null || roomName.isEmpty() || roomName.isBlank()) {
            throw new IllegalArgumentException("Room name cannot be null, empty or blank");
        }

        for (int i = 0; i < maxCapacity; i++) {
            if (escapeRooms[i] != null && escapeRooms[i].getName().equals(roomName)) {
                return escapeRooms[i];
            }
        }

        throw new RoomNotFoundException("Room name: " + roomName);
    }

    @Override
    public void reviewEscapeRoom(String roomName, Review review) throws RoomNotFoundException {
        if (review == null) {
            throw new IllegalArgumentException("Review cannot be null");
        }
        EscapeRoom room = getEscapeRoomByName(roomName);
        room.addReview(review);
    }

    @Override
    public Review[] getReviews(String roomName) throws RoomNotFoundException {
        EscapeRoom room = getEscapeRoomByName(roomName);
        return room.getReviews();
    }

    @Override
    public Team getTopTeamByRating() {
        Team topTeam = null;
        double topTeamRating = 0.0;

        for (Team t : teams) {
            if (t != null && t.getRating() > topTeamRating) {
                topTeam = t;
                topTeamRating = t.getRating();
            }
        }

        return topTeam;
    }

}
