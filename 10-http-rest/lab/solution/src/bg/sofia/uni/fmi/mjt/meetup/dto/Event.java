package bg.sofia.uni.fmi.mjt.meetup.dto;

public class Event {

	private String id;
	private String name;
	private long duration;
	private Status status;
	private long time;
	private Venue venue;
	private String description;

	public Event() {
		// Empty body
	}

	public Event(String name, Venue venue) {
		this.name = name;
		this.venue = venue;
	}

	public String getId() {
		return id;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", duration=" + duration + ", status=" + status + ", name=" + name + ", time=" + time
				+ ", venue=" + venue + ", description=" + description + "]";
	}

}
