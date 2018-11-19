package bg.sofia.uni.fmi.jira.test;

import bg.sofia.uni.fmi.jira.issues.Issue;

public class TestUtils {

	public static int countIssues(Issue[] issues) {
		int counter = 0;
		for (Issue issue : issues) {
			if (issue != null) {
				counter++;
			}
		}
		
		return counter;
	}
}
