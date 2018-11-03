package bg.sofia.uni.fmi.jira.interfaces;

import java.time.LocalDateTime;

import bg.sofia.uni.fmi.jira.Component;
import bg.sofia.uni.fmi.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.jira.enums.IssueType;
import bg.sofia.uni.fmi.jira.issues.Issue;

public interface IssueTracker {

	public Issue[] findAll(Component component, IssueStatus status);

	public Issue[] findAll(Component component, IssuePriority priority);

	public Issue[] findAll(Component component, IssueType type);

	public Issue[] findAll(Component component, IssueResolution resolution);

	public Issue[] findAllIssuesCreatedBetween(LocalDateTime startTime, LocalDateTime endTime);

	public Issue[] findAllBefore(LocalDateTime dueTime);
}
