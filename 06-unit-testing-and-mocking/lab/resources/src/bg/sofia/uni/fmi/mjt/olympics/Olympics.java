package bg.sofia.uni.fmi.mjt.olympics;

import bg.sofia.uni.fmi.mjt.olympics.competition.Competition;
import bg.sofia.uni.fmi.mjt.olympics.competitor.Competitor;
import bg.sofia.uni.fmi.mjt.olympics.competitor.Medal;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public interface Olympics {

	/**
	 * The method updates the competitors' medal statistics based on the competition result.
	 *
	 * @param competition the competition to update the statistics with
	 * @throws IllegalArgumentException if the competition is null.
	 * @throws IllegalArgumentException if a competitor is not registered in the Olympics.
	 */
	void updateMedalStatistics(Competition competition);

	/**
	 * Returns the nations sorted in Descending order based on the total medal count.
	 * If two nations have an equal number of medals, they are sorted alphabetically.
	 */
	TreeSet<String> getNationsRankList();

	/**
	 * Returns the total number of medals won by competitors from the specified nationality.
	 *
	 * @param nationality the nationality of the competitors
	 * @throws IllegalArgumentException when nationality is null
	 * @throws IllegalArgumentException when nationality is not registered in the olympics
	 *
	 * @return the total number of medals
	 */
	int getTotalMedals(String nationality);

	/**
	 * Returns a map of nations and their respective medal amount won from each competition.
	 *
	 * @return the nations' medal table
	 */
	Map<String, EnumMap<Medal, Integer>> getNationsMedalTable();

	/**
	 * Returns the set of competitors registered for the Olympics.
	 *
	 * @return the set of registered competitors
	 */
	Set<Competitor> getRegisteredCompetitors();
}
