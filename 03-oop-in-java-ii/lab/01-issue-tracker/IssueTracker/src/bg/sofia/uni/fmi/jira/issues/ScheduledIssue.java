package bg.sofia.uni.fmi.jira.issues;

import java.time.LocalDateTime;

import bg.sofia.uni.fmi.jira.Component;
import bg.sofia.uni.fmi.jira.User;
import bg.sofia.uni.fmi.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidReporterException;

public abstract class ScheduledIssue extends Issue {

	protected LocalDateTime dueTime;

	public ScheduledIssue(IssuePriority priority, Component component, User reporter, String description,
			LocalDateTime dueTime) throws InvalidReporterException {
		super(priority, component, reporter, description);
		this.dueTime = dueTime;
	}

	public LocalDateTime getDueTime() {
		return dueTime;
	}
}
