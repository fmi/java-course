import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ParallelHttpRequestsSender {

    private static final String API_URL = "http://www.google.com";

    public long executeRequestsSync(HttpClient client, HttpRequest request) throws Exception {
        // send 100 synchronous requests and measure time
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            client.send(request, BodyHandlers.ofString()).body();
        }

        return System.currentTimeMillis() - startTime;
    }

    public long executeRequestsAsync(HttpClient client, HttpRequest request) {
        List<CompletableFuture<String>> futures = new ArrayList<>();

        // send 100 asynchronous requests and measure time
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            futures.add(client.sendAsync(request, BodyHandlers.ofString()).thenApply(HttpResponse::body));
        }

        // uncomment to dump responses to console
        // futures.stream().map(f -> f.join()).forEach(System.out::println);

        // wait for all futures to complete
        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();

        return System.currentTimeMillis() - startTime;
    }

    public static void main(String... args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        // build a request
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_URL)).build();

        var sender = new ParallelHttpRequestsSender();

        long syncExecutionTime = sender.executeRequestsSync(client, request);
        long asyncExecutionTime = sender.executeRequestsAsync(client, request);

        System.out.println("Async: " + asyncExecutionTime + " Sync: " + syncExecutionTime);
    }

}
