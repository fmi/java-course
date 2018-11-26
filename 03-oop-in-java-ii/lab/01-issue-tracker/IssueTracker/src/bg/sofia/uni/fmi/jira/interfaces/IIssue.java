package bg.sofia.uni.fmi.jira.interfaces;

import java.time.LocalDateTime;

import bg.sofia.uni.fmi.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.jira.enums.IssueStatus;

public interface IIssue {

	public void resolve(IssueResolution resolution);

	public void setStatus(IssueStatus status);

	public String getId();

	public LocalDateTime getCreatedAt();

	public LocalDateTime getLastModifiedAt();
}
