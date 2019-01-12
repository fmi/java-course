package bg.sofia.uni.fmi.mjt.meetup;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import bg.sofia.uni.fmi.mjt.meetup.dto.Event;
import bg.sofia.uni.fmi.mjt.meetup.dto.Photo;

public class MeetupClient {

	private static final String API_URL = "https://api.meetup.com";

	private HttpClient client;
	private String apiKey;

	public MeetupClient(HttpClient client, String apiKey) {
		this.client = client;
		this.apiKey = apiKey;
	}

	/**
	 * Fetches the nearby events.
	 * 
	 * @return
	 */
	public List<Event> getEventsNearby() {
		String URL = API_URL + "/find/events?key=" + apiKey;
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
		try {
			String jsonResponse = client.send(request, BodyHandlers.ofString()).body();
			Gson gson = new Gson();
			return gson.fromJson(jsonResponse, new TypeToken<List<Event>>() {
			}.getType());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Fetches the nearby events with the given venue name. The comparison is case
	 * insensitive.
	 * 
	 * @param venueName
	 *            - the event venue name
	 * @return
	 */
	public List<Event> getEventsWithVenueName(String venueName) {
		return getEventsNearby().stream()
				.filter(e -> e.getVenue() != null && venueName.equalsIgnoreCase(e.getVenue().getName()))
				.collect(Collectors.toList());
	}

	/**
	 * Fetches the nearby events which descriptions contains all of the given
	 * keywords. The comparison is case insensitive.
	 * 
	 * @param keywords
	 *            - the keywords to search in the event description
	 * @return
	 */
	public List<Event> getEventsWithKeywords(Collection<String> keywords) {
		return getEventsNearby().stream().filter(e -> e.getDescription() != null
				&& keywords.stream().map(String::toLowerCase).allMatch(e.getDescription().toLowerCase()::contains))
				.collect(Collectors.toList());
	}

	/**
	 * Fetches the nearby event with max duration. Returns null when no events are
	 * found.
	 * 
	 * @return
	 */
	public Event getEventWithMaxDuration() {
		return getEventsNearby().stream().max(Comparator.comparing(Event::getDuration)).orElse(null);
	}

	/**
	 * Fetches an event by group urlname and event id. Returns null in case of a
	 * missing event.
	 * 
	 * @param urlname
	 *            - the event group urlname
	 * @param id
	 *            - the event id
	 * @return
	 */
	public Event getEvent(String urlname, String id) {
		String URL = API_URL + "/" + urlname + "/events/" + id + "?key=" + apiKey;
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
		try {
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			if (response.statusCode() == 404) {
				return null;
			}

			String jsonResponse = client.send(request, BodyHandlers.ofString()).body();
			Gson gson = new Gson();
			return gson.fromJson(jsonResponse, Event.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Fetches photos for the photo album with the given id. Returns empty list in case of
	 * a missing photo album.
	 * 
	 * @param urlname
	 *            - the photo album group urlname
	 * @param id
	 *            - the photo album id
	 * @return
	 */
	public List<Photo> getAlbumPhotos(String urlname, String id) {
		String URL = API_URL + "/" + urlname + "/photo_albums/" + id + "/photos?key=" + apiKey;
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
		try {
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			if (response.statusCode() == 404) {
				return Collections.emptyList();
			}

			String jsonResponse = client.send(request, BodyHandlers.ofString()).body();
			Gson gson = new Gson();
			return gson.fromJson(jsonResponse, new TypeToken<List<Photo>>() {
			}.getType());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Performs an async download of the photos from the given photo album to the
	 * given target directory. A folder with the album id is being created in the
	 * target directory. The photos are downloaded in the newly created album
	 * directory. The file name of each photo is its id.
	 * 
	 * @param urlname
	 *            - the photo album group urlname
	 * @param albumId
	 *            - the photo album id
	 * @param target
	 *            - the target directory to save the photo album
	 * @throws IOException
	 */
	public void downloadPhotoAlbum(String urlname, String albumId, Path target) throws IOException {
		List<Photo> photos = getAlbumPhotos(urlname, albumId);
		List<CompletableFuture<HttpResponse<Path>>> futures = new ArrayList<>();
		for (Photo photo : photos) {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(photo.getPhotoLink())).build();
			Path path = Paths.get(target.toString(), albumId, photo.getId() + ".jpeg");
			File photoFile = path.toFile();
			photoFile.getParentFile().mkdirs();
			futures.add(client.sendAsync(request, BodyHandlers.ofFile(path)));
		}

		CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();
	}

}
