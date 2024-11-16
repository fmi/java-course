package bg.sofia.uni.fmi.mjt.olympics.competitor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Athlete implements Competitor {

	private final String identifier;
	private final String name;
	private final String nationality;

	private final Set<Medal> medals;

	public Athlete(String identifier, String name, String nationality) {
		this.identifier = identifier;
		this.name = name;
		this.nationality = nationality;
		this.medals = new HashSet<>();
	}

	public void addMedal(Medal medal) {
		validateMedal(medal);
		medals.add(medal);
	}

	private void validateMedal(Medal medal) {
		if (medal == null) {
			throw new IllegalArgumentException("Medal cannot be null");
		}
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getNationality() {
		return nationality;
	}

	@Override
	public Set<Medal> getMedals() {
		return medals;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Athlete athlete = (Athlete) o;
		return Objects.equals(name, athlete.name) && Objects.equals(nationality, athlete.nationality) && Objects.equals(medals, athlete.medals);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name);
	}
}
