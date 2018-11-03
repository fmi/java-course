package bg.sofia.uni.fmi.jira;

import java.time.LocalDateTime;

public class User {

	private String userName;
	private LocalDateTime createdAt;

	public User(String userName) {
		this.userName = userName;
		setCreatedAt(LocalDateTime.now());
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", createdAt=" + createdAt + "]";
	}

}
