package bg.sofia.uni.fmi.mjt.meetup.dto;

public class Venue {

	private String name;
	private String city;

	public Venue() {
		// Empty body
	}

	public Venue(String name, String city) {
		this.name = name;
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Venue [name=" + name + ", city=" + city + "]";
	}

}
