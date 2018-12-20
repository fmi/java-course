import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;

public class SongLyricsRetriever {

    private static final String API_URL = "https://api.lyrics.ovh/v1/";

    public String getLyricsSync(HttpClient client, HttpRequest request) throws Exception {
        return client.send(request, BodyHandlers.ofString()).body();
    }

    public CompletableFuture<String> getLyricsAsync(HttpClient client, HttpRequest request) throws Exception {
        return client.sendAsync(request, BodyHandlers.ofString())
                     .thenApply(HttpResponse::body);
    }

    public static void main(String... args) throws Exception {        
        HttpClient client = HttpClient.newHttpClient();
        
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(
                API_URL + URLEncoder.encode("Lady Gaga", "UTF-8") + "/" + URLEncoder.encode("Shallow", "UTF-8")))
                .build();

        var retriever = new SongLyricsRetriever();

        String lyrics = retriever.getLyricsSync(client, request);
        System.out.println(lyrics);

        CompletableFuture<String> future = retriever.getLyricsAsync(client, request);
        future.thenAccept(System.out::println);
        System.out.println("The HTTP call is fired. Performing some other work...");

        // wait the async HTTP call which is executed in daemon thread
        future.join();
    }

}
