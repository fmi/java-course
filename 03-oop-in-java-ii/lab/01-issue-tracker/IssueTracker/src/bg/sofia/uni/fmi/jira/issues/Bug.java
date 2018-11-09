package bg.sofia.uni.fmi.jira.issues;

import bg.sofia.uni.fmi.jira.Component;
import bg.sofia.uni.fmi.jira.User;
import bg.sofia.uni.fmi.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.jira.enums.IssueType;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidReporterException;

public class Bug extends Issue {

	public Bug(IssuePriority priority, Component component, User reporter, String description)
			throws InvalidReporterException {
		super(priority, component, reporter, description);
	}

	@Override
	public IssueType getType() {
		return IssueType.BUG;
	}
}