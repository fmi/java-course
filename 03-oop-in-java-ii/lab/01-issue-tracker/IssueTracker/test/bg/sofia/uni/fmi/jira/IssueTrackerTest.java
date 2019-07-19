package bg.sofia.uni.fmi.jira;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import bg.sofia.uni.fmi.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.jira.enums.IssueType;
import bg.sofia.uni.fmi.jira.interfaces.IssueTracker;
import bg.sofia.uni.fmi.jira.issues.Bug;
import bg.sofia.uni.fmi.jira.issues.Issue;
import bg.sofia.uni.fmi.jira.issues.NewFeature;
import bg.sofia.uni.fmi.jira.issues.Task;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidReporterException;

public class IssueTrackerTest {

	private static final LocalDateTime DUE_DATE = LocalDateTime.of(2018, 10, 30, 12, 00);

	private static Issue[] issues;

	private static IssueTracker issueTracker;

	private static User pesho;

	private static User ivan;

	private static Component peshosComponent;

	private static Component ivansComponent;

	@Rule
	public ExpectedException expected = ExpectedException.none();

	@BeforeClass
	public static void setUp() throws InvalidReporterException {
		pesho = new User("Pesho");
		ivan = new User("Ivan");
		peshosComponent = new Component("Pesho", "pc", pesho);
		ivansComponent = new Component("Ivan", "ic", ivan);
		issues = new Issue[10];
		issues[1] = new Bug(IssuePriority.MAJOR, peshosComponent, ivan, "Big Bug");
		issues[3] = new Bug(IssuePriority.CRITICAL, ivansComponent, pesho, "Bigger Bug");
		issues[4] = new NewFeature(IssuePriority.MINOR, ivansComponent, pesho, "New Button", DUE_DATE);
		issues[6] = new NewFeature(IssuePriority.TRIVIAL, peshosComponent, ivan, "New Feature", DUE_DATE);
		issues[9] = new Task(IssuePriority.CRITICAL, ivansComponent, pesho, "Task", DUE_DATE);
		issueTracker = new Jira(issues);
	}

	@Test
	public void testDefaultIssueStatus() {
		Issue[] ivanComponentIssues = issueTracker.findAll(ivansComponent, IssueStatus.OPEN);
		Issue[] peshoComponentIssues = issueTracker.findAll(peshosComponent, IssueStatus.OPEN);

		int allIssuesCount = TestUtils.countIssues(ivanComponentIssues) + TestUtils.countIssues(peshoComponentIssues);
		assertEquals(5, allIssuesCount);
	}

	@Test
	public void testFindAllByPriorityShouldReturnTwo() {
		Issue[] result = issueTracker.findAll(ivansComponent, IssuePriority.CRITICAL);

		assertEquals(2, TestUtils.countIssues(result));
	}

	@Test
	public void testFindAllByIssueTypeShouldReturnOne() {
		Issue[] result = issueTracker.findAll(ivansComponent, IssueType.TASK);

		assertEquals(1, TestUtils.countIssues(result));
	}

	@Test
	public void testFindAllByIssueResolutionShouldReturnThree() {
		issues[3].resolve(IssueResolution.FIXED);
		issues[4].resolve(IssueResolution.FIXED);
		issues[9].resolve(IssueResolution.FIXED);

		Issue[] result = issueTracker.findAll(ivansComponent, IssueResolution.FIXED);

		assertEquals(3, TestUtils.countIssues(result));
	}

	@Test
	public void testFindAllCreatedBeforeShouldReturnZero() {
		Issue[] result = issueTracker.findAllBefore(DUE_DATE.minusHours(1));

		assertEquals(0, TestUtils.countIssues(result));
	}

	@Test
	public void testFindAllCreatedBetweenShouldReturnFive() {
		Issue[] result = issueTracker.findAllIssuesCreatedBetween(issues[1].getCreatedAt().minusHours(1),
				LocalDateTime.now());

		assertEquals(5, TestUtils.countIssues(result));
	}

	@Test
	public void testPassNullAsComponentToIssueShouldThrowException() throws InvalidReporterException {
		expected.expect(RuntimeException.class);

		new Bug(IssuePriority.MAJOR, null, ivan, "Bug");
	}

	@Test
	public void testPassNullAsReporterToIssueShouldThrowException() throws InvalidReporterException {
		expected.expect(InvalidReporterException.class);

		new Bug(IssuePriority.MAJOR, ivansComponent, null, "Bug");
	}

	@Test
	public void testPassNullAsDescriptionToIssueShouldThrowException() throws InvalidReporterException {
		expected.expect(RuntimeException.class);

		new Bug(IssuePriority.MAJOR, peshosComponent, ivan, null);
	}

	@Test
	public void testPassNullAsPriorityToIssueShouldThrowException() throws InvalidReporterException {
		expected.expect(RuntimeException.class);

		new Bug(null, peshosComponent, ivan, "Bug");
	}
}
