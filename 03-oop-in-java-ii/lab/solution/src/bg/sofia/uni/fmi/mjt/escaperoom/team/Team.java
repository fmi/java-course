package bg.sofia.uni.fmi.mjt.escaperoom.team;

import bg.sofia.uni.fmi.mjt.escaperoom.rating.Ratable;

public class Team implements Ratable {

    private String name;
    private TeamMember[] members;
    private double rating;

    private Team(String name, TeamMember[] members) {
        this.name = name;
        this.members = members;
    }

    public static Team of(String name, TeamMember[] members) {
        return new Team(name, members);
    }

    public void updateRating(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points cannot be negative");
        }
        rating += points;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns the team rating.
     */
    @Override
    public double getRating() {
        return rating;
    }

}
