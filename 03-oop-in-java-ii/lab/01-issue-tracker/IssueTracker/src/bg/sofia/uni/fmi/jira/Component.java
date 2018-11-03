package bg.sofia.uni.fmi.jira;

import java.time.LocalDateTime;

public class Component {

	private String name;
	private String shortName;
	private User creator;
	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;

	public Component(String name, String shortName, User creator) {
		this.name = name;
		this.shortName = shortName;
		this.creator = creator;
		setCreatedAt(LocalDateTime.now());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		setLastModifiedAt(LocalDateTime.now());
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
		setLastModifiedAt(LocalDateTime.now());
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
		setLastModifiedAt(LocalDateTime.now());
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	private void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
		setLastModifiedAt(createdAt);
	}

	public LocalDateTime getLastModifiedAt() {
		return lastModifiedAt;
	}

	private void setLastModifiedAt(LocalDateTime lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	@Override
	public String toString() {
		return "Component [name=" + name + ", shortName=" + shortName + ", creator=" + creator + ", createdAt="
				+ createdAt + ", lastModifiedAt=" + lastModifiedAt + "]";
	}

}
