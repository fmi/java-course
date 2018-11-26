package bg.sofia.uni.fmi.mjt.git;

import java.util.HashSet;
import java.util.Set;

public class Stage {

	private Set<String> added;
	private Set<String> removed;

	public Stage() {
		added = new HashSet<>();
		removed = new HashSet<>();
	}

	public Set<String> getAdded() {
		return added;
	}

	public Set<String> getRemoved() {
		return removed;
	}

	public int getChangedFilesCount() {
		return added.size() + removed.size();
	}

	public boolean contains(String file) {
		return added.contains(file);
	}

	public void add(String... files) {
		for (String file : files) {
			added.add(file);
		}
	}

	public void remove(String... files) {
		for (String file : files) {
			if (added.contains(file)) {
				added.remove(file);
				continue;
			}

			removed.add(file);
		}
	}
}
