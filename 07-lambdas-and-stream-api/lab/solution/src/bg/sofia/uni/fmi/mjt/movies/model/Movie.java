package bg.sofia.uni.fmi.mjt.movies.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import java.util.Set;

public final class Movie {

    private String title;
    private int year;
    private Set<Actor> actors;

    private Movie(String title, int year, Set<Actor> actors) {
        this.title = title;
        this.year = year;

        this.actors = actors;
    }

    /**
     * Returns a Movie instance, based on the given @{line} from the dataset.
     **/
    public static Movie createMovie(String line) {
        
        String[] actorsArr = line.split("/");
        
        int posOpeningBracket = actorsArr[0].lastIndexOf("(");
        int posClosingBracket = actorsArr[0].lastIndexOf(")");
        
        Set<Actor> actors = new HashSet<>();
        int year = -1;

        String movieTitle = line.substring(0, posOpeningBracket).strip();

        if (posOpeningBracket != -1 && posClosingBracket != -1 && posOpeningBracket < posClosingBracket) {
            String s = line.substring(posOpeningBracket + 1, posClosingBracket);
            int pos = s.indexOf(",");
            if (pos != -1) {
                s = s.substring(0, pos);
            }
            year = Integer.parseInt(s);
        }
        
        for (int i = 1; i < actorsArr.length; i++) {
            // System.out.println(actorsArr[i]);
            String[] names = actorsArr[i].split(",");
            
            if (names.length == 2) {
            actors.add(new Actor(names[1].strip(), names[0].strip()));         
            } else {
                actors.add(new Actor(names[0].strip(), null));
            }
        }

        return new Movie(movieTitle, year, actors);
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public Set<Actor> getActors() {
        return Collections.unmodifiableSet(actors);
    }

    @Override
    public String toString() {
        return "Movie [title=" + title + ", year=" + year + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((actors == null) ? 0 : actors.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + year;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Movie other = (Movie) obj;
        if (actors == null) {
            if (other.actors != null)
                return false;
        } else if (!actors.equals(other.actors))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (year != other.year)
            return false;
        return true;
    }

}
