package bg.sofia.uni.fmi.jira.issues;

import java.time.LocalDateTime;

import bg.sofia.uni.fmi.jira.Component;
import bg.sofia.uni.fmi.jira.User;
import bg.sofia.uni.fmi.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.jira.enums.IssueType;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidReporterException;

public class Task extends ScheduledIssue {

	public Task(IssuePriority priority, Component component, User reporter, String description, LocalDateTime dueTime)
			throws InvalidReporterException {
		super(priority, component, reporter, description, dueTime);
	}

	@Override
	public IssueType getType() {
		return IssueType.TASK;
	}
}
