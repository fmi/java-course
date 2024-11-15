package bg.sofia.uni.fmi.mjt.olympics.competitor;

import java.util.Collection;

public interface Competitor {

	/**
	 * Returns the unique identifier of the competitor.
	 **/
	String getIdentifier();

	/**
	 * Returns the name of the competitor.
	 **/
	String getName();

	/**
	 * Returns the nationality of the competitor.
	 **/
	String getNationality();

	/**
	 * Returns unmodifiable collection of medals won by the competitor.
	 **/
	Collection<Medal> getMedals();

	/**
	 * Adds a medal to the competitor's collection of medals.
	 *
	 * @throws IllegalArgumentException if the medal is null.
	 **/
	void addMedal(Medal medal);
}
