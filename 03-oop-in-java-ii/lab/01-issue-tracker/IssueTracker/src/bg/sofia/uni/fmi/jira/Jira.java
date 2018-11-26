package bg.sofia.uni.fmi.jira;

import java.time.LocalDateTime;
import java.util.Arrays;

import bg.sofia.uni.fmi.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.jira.enums.IssueType;
import bg.sofia.uni.fmi.jira.interfaces.IssueTracker;
import bg.sofia.uni.fmi.jira.issues.Bug;
import bg.sofia.uni.fmi.jira.issues.Issue;
import bg.sofia.uni.fmi.jira.issues.NewFeature;
import bg.sofia.uni.fmi.jira.issues.ScheduledIssue;
import bg.sofia.uni.fmi.jira.issues.Task;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidReporterException;

public class Jira implements IssueTracker {

	private Issue[] issues;

	public Jira(Issue[] issues) {
		this.issues = issues;
	}

	@Override
	public Issue[] findAll(Component component, IssueStatus status) {
		Issue[] filteredIssues = new Issue[issues.length];
		int currentIndex = 0;
		for (int i = 0; i < issues.length; i++) {
			Issue issue = issues[i];
			if (issue != null && component.getName().equals(issue.getComponent().getName()) && status == issue.getStatus()) {
				filteredIssues[currentIndex++] = issue;
			}

		}

		return filteredIssues;
	}

	@Override
	public Issue[] findAll(Component component, IssuePriority priority) {
		Issue[] filteredIssues = new Issue[issues.length];
		int currentIndex = 0;
		for (int i = 0; i < issues.length; i++) {
			Issue issue = issues[i];
			if (issue != null && component.getName().equals(issue.getComponent().getName()) && priority == issue.getPriority()) {
				filteredIssues[currentIndex++] = issue;
			}
		}

		return filteredIssues;
	}

	@Override
	public Issue[] findAll(Component component, IssueType type) {
		Issue[] filteredIssues = new Issue[issues.length];
		int currentIndex = 0;
		for (int i = 0; i < issues.length; i++) {
			Issue issue = issues[i];
			if (issue != null && component.getName().equals(issue.getComponent().getName()) && type == issue.getType()) {
				filteredIssues[currentIndex++] = issue;
			}
		}

		return filteredIssues;
	}

	@Override
	public Issue[] findAll(Component component, IssueResolution resolution) {
		Issue[] filteredIssues = new Issue[issues.length];
		int currentIndex = 0;
		for (int i = 0; i < issues.length; i++) {
			Issue issue = issues[i];
			if (issue != null && component.getName().equals(issue.getComponent().getName()) && resolution == issue.getResolution()) {
				filteredIssues[currentIndex++] = issue;
			}
		}

		return filteredIssues;
	}

	@Override
	public Issue[] findAllIssuesCreatedBetween(LocalDateTime startTime, LocalDateTime endTime) {
		Issue[] filteredIssues = new Issue[issues.length];
		int currentIndex = 0;
		for (int i = 0; i < issues.length; i++) {
			Issue issue = issues[i];
			if (issue != null && issue.getCreatedAt().isAfter(startTime) && issue.getCreatedAt().isBefore(endTime)) {
				filteredIssues[currentIndex++] = issue;
			}
		}

		return filteredIssues;
	}

	@Override
	public Issue[] findAllBefore(LocalDateTime dueTime) {
		Issue[] filteredIssues = new Issue[issues.length];
		int currentIndex = 0;
		for (int i = 0; i < issues.length; i++) {
			Issue issue = issues[i];
			if (issue != null && (issue.getType() == IssueType.NEW_FEATURE || issue.getType() == IssueType.TASK)) {
				ScheduledIssue scheduledIssue = (ScheduledIssue) issue;
				if (scheduledIssue.getDueTime().isBefore(dueTime)) {
					filteredIssues[currentIndex++] = scheduledIssue;
				}
			}
		}

		return filteredIssues;
	}
}
