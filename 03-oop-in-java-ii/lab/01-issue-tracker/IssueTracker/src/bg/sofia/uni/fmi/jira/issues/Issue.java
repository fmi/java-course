package bg.sofia.uni.fmi.jira.issues;

import java.time.LocalDateTime;

import bg.sofia.uni.fmi.jira.Component;
import bg.sofia.uni.fmi.jira.IdGenerator;
import bg.sofia.uni.fmi.jira.User;
import bg.sofia.uni.fmi.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.jira.enums.IssueType;
import bg.sofia.uni.fmi.jira.interfaces.IIssue;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidComponentException;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidDescriptionException;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidPriorityException;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidReporterException;

public abstract class Issue implements IIssue {

	private String id;
	private IssuePriority priority;
	private Component component;
	private IssueStatus status;
	private IssueResolution resolution;
	private User assignee;
	private User reporter;
	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;
	private String description;

	public Issue(IssuePriority priority, Component component, User reporter, String description)
			throws InvalidReporterException {
		validateReporter(reporter);
		validateDescription(description);
		validatePriority(priority);
		validateComponent(component);
		this.priority = priority;
		this.component = component;
		this.reporter = reporter;
		this.id = component.getShortName() + "-" + IdGenerator.generate();
		this.description = description;
		setCreatedAt(LocalDateTime.now());
		this.status = IssueStatus.OPEN;
		this.resolution = IssueResolution.UNRESOLVED;
	}

	private void validateReporter(User user) throws InvalidReporterException {
		if (user == null) {
			throw new InvalidReporterException("Reporter could not be null");
		}
	}

	private void validateDescription(String descrition) {
		if (descrition == null) {
			throw new InvalidDescriptionException("Invalid description");
		}
	}

	private void validatePriority(IssuePriority priority) {
		if (priority == null) {
			throw new InvalidPriorityException("Invalid priority");
		}
	}

	private void validateComponent(Component component) {
		if (component == null) {
			throw new InvalidComponentException("Invalid component");
		}
	}

	public abstract IssueType getType();

	@Override
	public void resolve(IssueResolution resolution) {
		setResolution(resolution);
	}

	public IssuePriority getPriority() {
		return priority;
	}

	public void setPriority(IssuePriority priority) {
		this.priority = priority;
		setLastModifiedAt(LocalDateTime.now());
	}

	@Override
	public String getId() {
		return id;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
		setLastModifiedAt(LocalDateTime.now());
	}

	public IssueStatus getStatus() {
		return status;
	}

	@Override
	public void setStatus(IssueStatus status) {
		this.status = status;
		setLastModifiedAt(LocalDateTime.now());
	}

	public IssueResolution getResolution() {
		return resolution;
	}

	private void setResolution(IssueResolution resolution) {
		this.resolution = resolution;
		setLastModifiedAt(LocalDateTime.now());
	}

	public User getAssignee() {
		return assignee;
	}

	public void setAssignee(User assignee) {
		this.assignee = assignee;
		setLastModifiedAt(LocalDateTime.now());
	}

	public User getReporter() {
		return reporter;
	}

	@Override
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	private void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
		setLastModifiedAt(createdAt);
	}

	@Override
	public LocalDateTime getLastModifiedAt() {
		return lastModifiedAt;
	}

	private void setLastModifiedAt(LocalDateTime lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		setLastModifiedAt(LocalDateTime.now());
	}

	@Override
	public String toString() {
		return "Issue [id=" + id + ", priority=" + priority + ", component=" + component + ", status=" + status
				+ ", resolution=" + resolution + ", assignee=" + assignee + ", reporter=" + reporter + ", createdAt="
				+ createdAt + ", lastModifiedAt=" + lastModifiedAt + ", description=" + description + "]";
	}
}
