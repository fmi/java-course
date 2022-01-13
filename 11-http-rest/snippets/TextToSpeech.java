import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Path;

public class TextToSpeech {

    // signup here to get your API key: https://account.cloudmersive.com/signup
    private static final String API_KEY = "YOUR_API_KEY_HERE";

    private static final String TEXT =
            "\"I was skeptical about Java 17 in the beginning but I am starting to appreciate it. \"";

    public static void main(String... args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        URI uri = new URI("https", "api.cloudmersive.com", "/speech/speak/text/basicVoice/wav", null);
        HttpRequest request = HttpRequest.newBuilder()
                .header("Apikey", API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(TEXT))
                .uri(uri)
                .build();

        client.send(request, BodyHandlers.ofFile(Path.of("speech.wav")));
    }

}
