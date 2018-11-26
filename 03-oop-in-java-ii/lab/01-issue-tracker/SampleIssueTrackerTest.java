package bg.sofia.uni.fmi.jira.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import bg.sofia.uni.fmi.jira.Component;
import bg.sofia.uni.fmi.jira.Jira;
import bg.sofia.uni.fmi.jira.User;
import bg.sofia.uni.fmi.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.jira.enums.IssueType;
import bg.sofia.uni.fmi.jira.interfaces.IssueTracker;
import bg.sofia.uni.fmi.jira.issues.Bug;
import bg.sofia.uni.fmi.jira.issues.Issue;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidReporterException;

public class SampleIssueTrackerTest {

	private static Issue[] issues;

	private static IssueTracker issueTracker;

	private static User ivan;

	private static User pesho;

	private static Component peshosComponent;

	@BeforeClass
	public static void setUp() throws InvalidReporterException {
		ivan = new User("Ivan");
		pesho = new User("Pesho");
		peshosComponent = new Component("Pesho", "pc", pesho);
		issues = new Issue[2];
		issues[0] = new Bug(IssuePriority.MAJOR, peshosComponent, ivan, "Big Bug");
		issues[1] = new Bug(IssuePriority.CRITICAL, peshosComponent, ivan, "Bigger Bug");
		issueTracker = new Jira(issues);
	}

	@Test
	public void testFindAllByStatus() {
		Issue[] result = issueTracker.findAll(peshosComponent, IssueStatus.OPEN);

		assertEquals(2, TestUtils.countIssues(result));
	}

	@Test
	public void testFindAllByResolution() {
		Issue[] result = issueTracker.findAll(peshosComponent, IssueResolution.UNRESOLVED);

		assertEquals(2, TestUtils.countIssues(result));
	}

	@Test
	public void testFindAllByType() {
		Issue[] result = issueTracker.findAll(peshosComponent, IssueType.TASK);

		assertEquals(0, TestUtils.countIssues(result));
	}
}
