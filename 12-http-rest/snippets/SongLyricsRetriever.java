import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;

public class SongLyricsRetriever {

    public String getLyricsSync(HttpClient client, HttpRequest request) throws Exception {
        return client.send(request, BodyHandlers.ofString()).body();
    }

    public CompletableFuture<String> getLyricsAsync(HttpClient client, HttpRequest request) throws Exception {
        return client.sendAsync(request, BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }

    public static void main(String... args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        URI uri = new URI("https", "api.lyrics.ovh", "/v1/" + "Lady Gaga/Shallow", null);

        System.out.println(uri);

        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();

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
