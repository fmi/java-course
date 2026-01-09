package http;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;

public class VirusScanner {

    // signup here to get your API key: https://developers.virustotal.com/v3.0/reference#getting-started
    private static final String API_KEY = "YOUR_API_KEY_HERE";

    private static final String API_KEY_HEADER = "x-apikey";

    private static final Gson GSON = new Gson();

    public static void main(String[] args) throws Exception {

        Path file = Path.of("7z.exe");
        if (!Files.exists(file)) {
            System.err.println("File not found: " + file.toAbsolutePath());
            return;
        }

        HttpClient client = HttpClient.newHttpClient();

        String boundary = "JavaBoundary-" + Instant.now().toEpochMilli();

        HttpRequest uploadRequest = HttpRequest.newBuilder()
            .uri(URI.create("https://www.virustotal.com/api/v3/files"))
            .header("Content-Type", "multipart/form-data; boundary=" + boundary)
            .header(API_KEY_HEADER, API_KEY)
            .POST(multipartFile(file, boundary))
            .build();

        System.out.println("Uploading file...");
        HttpResponse<String> uploadResponse = client.send(uploadRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(uploadResponse.body());

        // Parse the response to extract the analysis ID
        UploadResponse parsed = GSON.fromJson(uploadResponse.body(), UploadResponse.class);

        String analysisId = parsed.data().id();

        HttpRequest statusRequest = HttpRequest.newBuilder()
            .uri(URI.create(
                "https://www.virustotal.com/api/v3/analyses/" + analysisId))
            .header(API_KEY_HEADER, API_KEY)
            .build();

        System.out.println("\nChecking analysis status...");
        HttpResponse<String> statusResponse = client.send(statusRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(statusResponse.body());
    }

    /**
     * Creates a multipart/form-data body with exactly ONE file.
     * This is intentionally simple for teaching purposes.
     */
    private static HttpRequest.BodyPublisher multipartFile(Path file, String boundary) throws IOException {

        String mimeType = Files.probeContentType(file);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        String header =
            "--" + boundary + "\r\n" +
                "Content-Disposition: form-data; name=\"file\"; filename=\"" +
                file.getFileName() + "\"\r\n" +
                "Content-Type: " + mimeType + "\r\n\r\n";

        String footer =
            "\r\n--" + boundary + "--\r\n";

        return HttpRequest.BodyPublishers.ofByteArrays(
            List.of(
                header.getBytes(StandardCharsets.UTF_8),
                Files.readAllBytes(file),
                footer.getBytes(StandardCharsets.UTF_8)
            )
        );
    }

    record UploadResponse(Data data) {
        record Data(String id) {
        }
    }

}
