import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelHttpRequestsSender {

    private static final String WEB_SITE = "http://www.google.com";
    private static final int PARALLEL_REQUESTS = 100;

    public long executeRequestsSync(HttpClient client, HttpRequest request) throws Exception {
        // send some synchronous requests and measure time
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < PARALLEL_REQUESTS; i++) {
            client.send(request, BodyHandlers.ofString()).body();
        }

        return System.currentTimeMillis() - startTime;
    }

    public long executeRequestsAsync(HttpClient client, HttpRequest request) {
        List<CompletableFuture<String>> futures = new ArrayList<>();

        // send some asynchronous requests and measure time
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            futures.add(client.sendAsync(request, BodyHandlers.ofString()).thenApply(x -> {
                System.out.println("thenApply() thread: " + Thread.currentThread().getName());
                return x.body();
            }));
        }

        // uncomment to dump responses to console
        // futures.stream().map(f -> f.join()).forEach(System.out::println);

        // wait for all futures to complete
        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();

        return System.currentTimeMillis() - startTime;
    }

    public static void main(String... args) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        HttpClient client =
                HttpClient.newBuilder().executor(executor).build(); // configure custom executor or use the default

        // build a request
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(WEB_SITE)).build();

        var sender = new ParallelHttpRequestsSender();

        long syncExecutionTime = sender.executeRequestsSync(client, request);
        long asyncExecutionTime = sender.executeRequestsAsync(client, request);

        System.out.println("Async: " + asyncExecutionTime + " Sync: " + syncExecutionTime);

        executor.shutdown();
    }

}
