package http;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncSongLyricsRetriever {

    private static final Gson GSON = new Gson();
    private static final ExecutorService IO_EXECUTOR = Executors.newCachedThreadPool();
    private static final ExecutorService APP_EXECUTOR = Executors.newFixedThreadPool(2);
    private static final HttpClient CLIENT = HttpClient.newBuilder().executor(IO_EXECUTOR).build();

    public static CompletableFuture<String> getLyricsAsync(String artist, String song) {

        URI uri = URI.create("https://api.lyrics.ovh/v1/" + artist + "/" + song);

        HttpRequest request = HttpRequest.newBuilder(uri).build();

        System.out.println("Main thread: " + Thread.currentThread().getName());

        return CLIENT
            .sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(response -> {
                System.out.println(
                    "HTTP completed on thread: "
                        + Thread.currentThread().getName()
                );
                return response.body();
            })
            .thenApplyAsync(json -> {
                System.out.println(
                    "Parsing JSON on thread: "
                        + Thread.currentThread().getName()
                );

                if (!json.strip().startsWith("{")) {
                    return new LyricsResponse(json);
                }

                return GSON.fromJson(json, LyricsResponse.class);
            }, APP_EXECUTOR)
            .thenApply(LyricsResponse::lyrics);
    }

    static void main() {
        getLyricsAsync("Adele", "Hello")
            .thenAcceptAsync(lyrics -> {
                System.out.println(
                    "Printing result on thread: "
                        + Thread.currentThread().getName()
                );
                System.out.println();
                System.out.println(lyrics);
            }, APP_EXECUTOR)
            .join();

        IO_EXECUTOR.shutdown();
        APP_EXECUTOR.shutdown();
    }
}
