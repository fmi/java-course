package bg.sofia.uni.fmi.mjt.meetup;
import java.net.http.HttpClient;
import java.nio.file.Paths;
import java.util.Set;

import bg.sofia.uni.fmi.mjt.meetup.MeetupClient;

public class Main {

	public static void main(String... args) throws Exception {
		final String API_KEY = "23918e316b316040716536e7e3011";

		HttpClient client = HttpClient.newHttpClient();

		MeetupClient meetupClient = new MeetupClient(client, API_KEY);

		System.out.println(meetupClient.getEventsNearby());
		System.out.println(meetupClient.getEventsWithVenueName("Cosmos Co-Working Camp").size());
		System.out.println(meetupClient.getEventsWithKeywords(Set.of("pizza", "beer")));
		System.out.println(meetupClient.getEventWithMaxDuration());
		System.out.println(meetupClient.getEvent("EOS-Developers-EOS-Bulgaria", "257367821"));
		System.out.println(meetupClient.getEvent("foo", "257367821"));
		System.out.println(meetupClient.getAlbumPhotos("Cloud-Native-Computing-Bulgaria", "29473987"));
		meetupClient.downloadPhotoAlbum("Cloud-Native-Computing-Bulgaria", "29473987", Paths.get("photos"));
	}
}
