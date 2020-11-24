package bg.sofia.uni.fmi.mjt.socialmedia;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import bg.sofia.uni.fmi.mjt.socialmedia.content.Content;

public class EvilSocialInatorSampleTest {

	private static final String DEFAULT_USERNAME = "gabi";
	private static final String USERNAME_1 = "moni";
	private static final String USERNAME_2 = "desi";
	private static final String USERNAME_3 = "zdravko";

	private static final String DEFAULT_DESCRIPTION = "hi, followers. #cat #dog @moni";
	
	private static final String DEFAULT_COMMENT = "great photo!";
	
	private static final int DEFAULT_N = 10;
	
	private SocialMediaInator media;

	@Before
	public void setup() {
		media = new EvilSocialInator();
	}
	
	@Test
	public void testNumberOfCommentsIsCorrect() {
		media.register(DEFAULT_USERNAME);
		media.register(USERNAME_1);
		media.register(USERNAME_2);
		media.register(USERNAME_3);

		String id = media.publishStory(DEFAULT_USERNAME, LocalDateTime.now(), DEFAULT_DESCRIPTION);

		media.comment(USERNAME_1, DEFAULT_COMMENT, id);
		media.comment(USERNAME_2, DEFAULT_COMMENT, id);
		media.comment(USERNAME_3, DEFAULT_COMMENT, id);

		Content content = get(id, media.getNMostRecentContent(DEFAULT_USERNAME, 1));

		int expected = 3;
		int actual = content.getNumberOfComments();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testRegisterUser() {
		media.register(DEFAULT_USERNAME);

		int expectedActivitySize = 0;
		int actualActivitySize = media.getActivityLog(DEFAULT_USERNAME).size();
		assertEquals(expectedActivitySize, actualActivitySize);

		int expectedContentSize = 0;
		int actualContentSize = media.getNMostRecentContent(DEFAULT_USERNAME, DEFAULT_N).size();
		assertEquals(expectedContentSize, actualContentSize);
	}
	
	private Content get(String id, Collection<Content> content) {
		for (Content c : content) {
			if (id.equals(c.getId())) {
				return c;
			}
		}
		return null;
	}
}
