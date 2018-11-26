package bg.sofia.uni.fmi.mjt.git;

import java.util.HashMap;
import java.util.Map;

public class Repository {

	private Stage stage;
	private String active;
	private Map<String, Commit> branches;

	public Repository() {
		stage = new Stage();
		active = "master";
		branches = new HashMap<>();

		branches.put(active, null);
	}

	public String getBranch() {
		return active;
	}

	public Commit getHead() {
		return branches.get(active);
	}

	public Result add(String... files) {
		for (String file : files) {
			if (contains(file)) {
				String message = String.format("'%s' already exists", file);
				return Result.fail(message);
			}
		}

		stage.add(files);

		String message = String.format("added %s to stage", String.join(", ", files));
		return Result.success(message);
	}

	public Result remove(String... files) {
		for (String file : files) {
			if (!contains(file)) {
				String message = String.format("'%s' did not match any files", file);
				return Result.fail(message);
			}
		}

		stage.remove(files);

		String message = String.format("added %s for removal", String.join(", ", files));
		return Result.success(message);
	}

	public Result commit(String message) {
		if (stage.getChangedFilesCount() == 0) {
			return Result.fail("nothing to commit, working tree clean");
		}

		Commit head = new Commit(message, stage, getHead());
		updateHead(head);

		stage = new Stage();

		String resultMessage = String.format("%d files changed", head.getChangedFilesCount());
		return Result.success(resultMessage);
	}

	public Result checkoutCommit(String hash) {
		Commit commit = getCommit(hash);
		if (commit == null) {
			String message = String.format("commit %s does not exist", hash);
			return Result.fail(message);
		}

		branches.put(active, commit);

		String message = String.format("HEAD is now at %s", hash);
		return Result.success(message);
	}

	public Result log() {
		if (getHead() == null) {
			String message = String.format("branch %s does not have any commits yet", active);
			return Result.fail(message);
		}

		String history = buildHistory();
		return Result.success(history);
	}

	public boolean contains(String file) {
		boolean isInStage = stage.contains(file);
		boolean isCommitted = getHead() != null && getHead().contains(file);

		return isInStage || isCommitted;
	}

	public Result createBranch(String name) {
		if (branches.containsKey(name)) {
			String message = String.format("branch %s already exists", name);
			return Result.fail(message);
		}

		branches.put(name, getHead());

		String message = String.format("created branch %s", name);
		return Result.success(message);
	}

	public Result checkoutBranch(String name) {
		if (!branches.containsKey(name)) {
			String message = String.format("branch %s does not exist", name);
			return Result.fail(message);
		}

		active = name;

		String message = String.format("switched to branch %s", name);
		return Result.success(message);
	}

	private void updateHead(Commit head) {
		branches.put(active, head);
	}

	private String buildHistory() {
		StringBuilder message = new StringBuilder();
		message.append(getHead());

		Commit commit = getHead().getPrevious();
		while (commit != null) {
			message.append("\n\n");
			message.append(commit);

			commit = commit.getPrevious();
		}

		return message.toString();
	}

	private Commit getCommit(String hash) {
		Commit commit = getHead();
		while (commit != null && !hash.equals(commit.getHash())) {
			commit = commit.getPrevious();
		}

		return commit;
	}
}
