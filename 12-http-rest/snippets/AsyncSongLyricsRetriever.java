import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncSongLyricsRetriever {

    public static void getLyricsAsync(String artist, String song) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        HttpClient client =
                HttpClient.newBuilder().executor(executor).build(); // configure custom executor or use the default

        URI uri = new URI("https", "api.lyrics.ovh", "/v1/" + artist + "/" + song, null);
        System.out.println(uri);

        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
        System.out.println("Thread calling sendAsync(): " + Thread.currentThread().getName());

        CompletableFuture<String> future = client.sendAsync(request, BodyHandlers.ofString())
                .thenApply(x -> {
                    System.out.println("Thread executing thenApply(): " + Thread.currentThread().getName());
                    return x.body();
                });
        future.thenAcceptAsync(x -> {
            System.out.println("Thread executing thenAccept(): " + Thread.currentThread().getName());
            System.out.println(x);
        }, executor);
        System.out.println("The HTTP call is fired. Performing some other work...");

        // wait the async HTTP call which is executed in daemon thread
        future.join();

        executor.shutdown();
    }

    public static void main(String... args) throws Exception {
        AsyncSongLyricsRetriever.getLyricsAsync("Billie Eilish", "Lovely");
    }

}
