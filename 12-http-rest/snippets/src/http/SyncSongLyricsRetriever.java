package http;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

record LyricsResponse(String lyrics) {
}

public class SyncSongLyricsRetriever {

    private static final Gson GSON = new Gson();

    public String getLyricsSync(String artist, String song) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        URI uri = new URI("https", "api.lyrics.ovh", "/v1/" + artist + "/" + song, null);
        HttpRequest request = HttpRequest.newBuilder(uri).build();

        String json = client.send(request, BodyHandlers.ofString()).body();

        LyricsResponse response = GSON.fromJson(json, LyricsResponse.class);
        return response.lyrics();
    }

    static void main() throws Exception {
        System.out.println(
            new SyncSongLyricsRetriever().getLyricsSync("Adele", "Hello")
        );
    }
}