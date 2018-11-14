package bg.sofia.uni.fmi.mjt.git;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

public class RepositoryTest {

	private Repository repo;

	@Before
	public void setUp() {
		repo = new Repository();
	}

	@Test
	public void testRepo_HasInitialValues() {
		assertNull(repo.getHead());

		assertEquals("master", repo.getBranch());
	}

	@Test
	public void testAdd_SingleFile() {
		Result actual = repo.add("foo.txt");
		assertSuccess("added foo.txt to stage", actual);
	}

	@Test
	public void testAdd_MultipleFiles() {
		Result actual = repo.add("foo.txt", "bar.txt", "baz.txt");
		assertSuccess("added foo.txt, bar.txt, baz.txt to stage", actual);
	}

	@Test
	public void testAdd_WhenSameFileExistsInStage() {
		repo.add("bar.txt", "baz.txt");

		Result actual = repo.add("baz.txt");
		assertFail("'baz.txt' already exists", actual);
	}

	@Test
	public void testAdd_WithMultipleFilesFailsWhenFileExistsFromPreviousCommit() {
		repo.add("bar.txt", "baz.txt");
		repo.commit("Add bar baz");

		Result actual = repo.add("foo.txt", "bar.txt", "baz.txt");
		assertFail("'bar.txt' already exists", actual);
	}

	@Test
	public void testRemove_MissingFileWithoutAnyCommit() {
		Result actual = repo.remove("foo.txt");
		assertFail("'foo.txt' did not match any files", actual);
	}

	@Test
	public void testRemove_DoesNothingWhenAnyFileIsMissing() {
		repo.add("foo.txt", "bar.txt");

		Result actual = repo.remove("foo.txt", "baz.txt");
		assertFail("'baz.txt' did not match any files", actual);

		actual = repo.commit("After removal");
		assertSuccess("2 files changed", actual);
	}

	@Test
	public void testRemove_SingleFileFromStagingArea() {
		repo.add("foo.txt");

		Result actual = repo.remove("foo.txt");
		assertSuccess("added foo.txt for removal", actual);

		actual = repo.commit("I hope, it will pass");
		assertEquals("nothing to commit, working tree clean", actual.getMessage());
	}

	@Test
	public void testRemove_MultipleFilesFromPreviousCommit() {
		repo.add("foo.txt", "bar.txt", "baz.txt");
		repo.commit("Add foo bar baz");

		Result actual = repo.remove("bar.txt", "baz.txt");
		assertSuccess("added bar.txt, baz.txt for removal", actual);

		actual = repo.commit("Remove bar baz");
		assertSuccess("2 files changed", actual);
	}

	@Test
	public void testCommit_CanCommitToEmptyRepo() {
		repo.add("foo.txt");
		repo.add("bar.txt", "baz.txt");

		Result actual = repo.commit("Initial commit");
		assertSuccess("3 files changed", actual);

		Commit head = repo.getHead();
		String now = format(LocalDateTime.now());
		String hash = hexDigest(now + "Initial commit");
		assertEquals(hash, head.getHash());
		assertEquals("Initial commit", head.getMessage());
	}

	@Test
	public void testCommit_WithoutAddedOrRemovedFilesToEmptyRepo() {
		Result actual = repo.commit("I hope, it will pass");
		assertFail("nothing to commit, working tree clean", actual);
	}

	@Test
	public void testCommit_WithoutAddedOrRemovedFilesToNonEmptyRepo() {
		repo.add("foo.txt");
		repo.commit("Initial commit");

		repo.createBranch("feature-x");
		repo.checkoutBranch("feature-x");

		Result actual = repo.commit("I hope, it will pass");
		assertFail("nothing to commit, working tree clean", actual);
	}

	@Test
	public void testCommit_RemovedFiles() {
		repo.add("foo.txt", "bar.txt");
		repo.commit("Awesome!1");
		repo.remove("foo.txt");

		Result actual = repo.commit("Remove foo.txt");
		assertSuccess("1 files changed", actual);

		actual = repo.add("foo.txt");
		assertSuccess("added foo.txt to stage", actual);

		actual = repo.commit("Add foo.txt once again");
		assertSuccess("1 files changed", actual);
	}

	@Test
	public void testLog_ForEmptyRepo() {
		Result actual = repo.log();
		assertFail("branch master does not have any commits yet", actual);
	}

	@Test
	public void testLog_ForSingleCommit() {
		repo.add("foo.txt", "bar.txt");
		repo.commit("Initial commit");

		Result actual = repo.log();
		String now = format(LocalDateTime.now());
		String hash = hexDigest(now + "Initial commit");
		String expected = String.format("commit %s\nDate: %s\n\n\tInitial commit", hash, now);
		assertSuccess(expected, actual);
	}

	@Test
	public void testLog_ForMultipleCommits() {
		repo.add("foo.txt");
		repo.commit("First commit");

		repo.remove("foo.txt");
		repo.commit("Second commit");

		Result actual = repo.log();
		String now = format(LocalDateTime.now());
		String firstHash = hexDigest(now + "First commit");
		String secondHash = hexDigest(now + "Second commit");
		String expected = String.format(
				"commit %s\nDate: %s\n\n\tSecond commit\n\ncommit %s\nDate: %s\n\n\tFirst commit", secondHash, now,
				firstHash, now);
		assertSuccess(expected, actual);
	}

	@Test
	public void testLog_ShowsLogsOnlyForCurrentBranch() {
		repo.add("README.md");
		repo.commit("Add README.md");

		repo.createBranch("dev");
		repo.add("foo.txt");
		repo.commit("Second commit");

		repo.checkoutBranch("dev");
		assertEquals("dev", repo.getBranch());
		assertEquals("Add README.md", repo.getHead().getMessage());

		Result actual = repo.log();
		String now = format(LocalDateTime.now());
		String hash = hexDigest(now + "Add README.md");
		String expected = String.format("commit %s\nDate: %s\n\n\tAdd README.md", hash, now);
		assertSuccess(expected, actual);
	}

	@Test
	public void testLog_ForNewBranch() {
		repo.add("foo.txt");
		repo.commit("Initial commit");

		repo.createBranch("feature-z");
		repo.checkoutBranch("feature-z");

		repo.remove("foo.txt");
		Result actual = repo.commit("Remove foo.txt");
		assertSuccess("1 files changed", actual);

		actual = repo.log();
		String now = format(LocalDateTime.now());
		String firstHash = hexDigest(now + "Initial commit");
		String secondHash = hexDigest(now + "Remove foo.txt");
		String expected = String.format(
				"commit %s\nDate: %s\n\n\tRemove foo.txt\n\ncommit %s\nDate: %s\n\n\tInitial commit", secondHash, now,
				firstHash, now);
		assertSuccess(expected, actual);
	}

	@Test
	public void testCreateBranch_OnEmptyRepo() {
		Result actual = repo.createBranch("foo");
		assertSuccess("created branch foo", actual);

		actual = repo.log();
		assertFail("branch master does not have any commits yet", actual);

		actual = repo.checkoutBranch("foo");
		assertSuccess("switched to branch foo", actual);

		actual = repo.log();
		assertFail("branch foo does not have any commits yet", actual);
	}

	@Test
	public void testCreateBranch_WhenBranchWithTheSameNameAlreadyExists() {
		Result actual = repo.createBranch("master");
		assertFail("branch master already exists", actual);

		actual = repo.createBranch("cool-feature");
		assertSuccess("created branch cool-feature", actual);
		assertNull(repo.getHead());
		assertEquals("master", repo.getBranch());

		actual = repo.createBranch("cool-feature");
		assertFail("branch cool-feature already exists", actual);
	}

	@Test
	public void testCheckoutBranch_CannotSwitchToNonexisting() {
		Result actual = repo.checkoutBranch("refactoring");
		assertFail("branch refactoring does not exist", actual);
		assertEquals("master", repo.getBranch());
	}

	@Test
	public void testCheckoutBranch_CanSwitchBranches() {
		repo.add("src/Main.java");
		repo.commit("Add Main.java");

		repo.createBranch("dev");
		Result actual = repo.checkoutBranch("dev");
		assertSuccess("switched to branch dev", actual);

		repo.remove("src/Main.java");
		repo.commit("Remove Main.java");
		assertEquals("Remove Main.java", repo.getHead().getMessage());

		actual = repo.checkoutBranch("master");
		assertSuccess("switched to branch master", actual);
		assertEquals("Add Main.java", repo.getHead().getMessage());
	}

	@Test
	public void testCheckoutCommit_InEmptyRepo() {
		Result actual = repo.checkoutCommit("хеш");
		assertFail("commit хеш does not exist", actual);
	}

	@Test
	public void testCheckoutCommit_WithNonexistingHash() {
		repo.add("src/Repository.java");
		repo.commit("Latest and greatest");

		Result actual = repo.checkoutCommit("foo");
		assertFail("commit foo does not exist", actual);
	}

	@Test
	public void testCheckoutCommit_CanSwitchCommits() {
		repo.add("main.go");
		repo.commit("Add main.go");
		Commit firstCommit = repo.getHead();

		repo.add("main_test.go");
		repo.commit("Add main_test.go");

		Result actual = repo.checkoutCommit(firstCommit.getHash());
		String expected = String.format("HEAD is now at %s", firstCommit.getHash());
		assertSuccess(expected, actual);
		assertEquals("Add main.go", repo.getHead().getMessage());

		repo.createBranch("клон");
		repo.checkoutBranch("клон");
		assertEquals(firstCommit.getHash(), repo.getHead().getHash());

		actual = repo.remove("main_test.go");
		assertFail("'main_test.go' did not match any files", actual);

		actual = repo.remove("main.go");
		assertSuccess("added main.go for removal", actual);

		actual = repo.commit("Remove main.go");
		assertSuccess("1 files changed", actual);
		assertEquals("клон", repo.getBranch());
		assertEquals("Remove main.go", repo.getHead().getMessage());
	}

	@Test
	public void testMultipleRepos() {
		Repository other = new Repository();

		repo.add("app.rb", "css/style.css");
		repo.commit("Add app.rb, style.css");

		other.add("Main.cpp");
		other.commit("Add Main.cpp");

		assertEquals("Add app.rb, style.css", repo.getHead().getMessage());
		assertEquals("Add Main.cpp", other.getHead().getMessage());

		repo.createBranch("pr-13");
		Result actual = other.checkoutBranch("pr-13");
		assertFail("branch pr-13 does not exist", actual);

		String hash = repo.getHead().getHash();
		actual = other.checkoutCommit(hash);
		String expected = String.format("commit %s does not exist", hash);
		assertFail(expected, actual);
	}

	@Test
	public void testCheckoutCommit_CannotCheckoutNonexistingHash() {
		Result actual = repo.checkoutCommit("foo");
		assertFail("commit foo does not exist", actual);
	}

	private static void assertSuccess(String expected, Result actual) {
		assertTrue(actual.isSuccessful());
		assertEquals(expected, actual.getMessage());
	}

	private static void assertFail(String expected, Result actual) {
		assertFalse(actual.isSuccessful());
		assertEquals(expected, actual.getMessage());
	}

	private static String format(LocalDateTime dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm yyyy");
		return dateTime.format(formatter);
	}

	private String hexDigest(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[] bytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
			return convertBytesToHex(bytes);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private String convertBytesToHex(byte[] bytes) {
		StringBuilder hex = new StringBuilder();
		for (byte current : bytes) {
			hex.append(String.format("%02x", current));
		}

		return hex.toString();
	}
}
