package bg.sofia.uni.fmi.mjt.git;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class Commit {

	private String hash;
	private LocalDateTime dateTime;
	private String message;
	private Stage stage;
	private Commit previous;

	public Commit(String message, Stage stage, Commit previous) {
		this.dateTime = LocalDateTime.now();
		this.message = message;
		this.stage = stage;
		this.previous = previous;

		this.hash = hexDigest(getFormattedDateTime() + message);
	}

	public String getHash() {
		return hash;
	}

	public String getMessage() {
		return message;
	}

	public String getFormattedDateTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm yyyy");
		return dateTime.format(formatter);
	}

	public Commit getPrevious() {
		return previous;
	}

	public int getChangedFilesCount() {
		return stage.getChangedFilesCount();
	}

	public boolean contains(String file) {
		return getAllFiles().contains(file);
	}

	private Set<String> getAllFiles() {
		if (previous == null) {
			return stage.getAdded();
		}

		Set<String> all = new HashSet<>(previous.getAllFiles());
		all.addAll(stage.getAdded());
		all.removeAll(stage.getRemoved());
		return all;
	}

	private String hexDigest(String input) {
		return new SHA1Digest().hexDigest(input);
	}

	@Override
	public String toString() {
		return String.format("commit %s\nDate: %s\n\n\t%s", hash, getFormattedDateTime(), message);
	}
}
