import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

public class SyncSongLyricsRetriever {

    public String getLyricsSync(String artist, String song) throws Exception {
        HttpClient client = HttpClient.newBuilder().build();

        URI uri = new URI("https", "api.lyrics.ovh", "/v1/" + artist + "/" + song, null);
        System.out.println(uri);

        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
        return client.send(request, BodyHandlers.ofString()).body();
    }

    public static void main(String... args) throws Exception {
        System.out.println(new SyncSongLyricsRetriever().getLyricsSync("Billie Eilish", "Lovely"));
    }

}
