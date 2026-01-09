package http;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

public class TextToSpeech {

    // signup here to get your API key: https://console.deepgram.com/signup
    private static final String API_KEY = "YOUR_API_KEY_HERE";
    private static final String TEXT =
        "Alright crew, that’s a wrap for today’s Lab! Not gonna lie, I was kinda sus about this Modern Java Technologies course at first... but now? Big vibes. Can’t wait to see what the final homework is about. Let's keep it chill and ship something awesome!";

    static void main() throws Exception {

        HttpClient client = HttpClient.newHttpClient();

        String url = "https://api.deepgram.com/v1/speak?model=aura-2-aries-en";

        String jsonBody = """
            {
              "text": "%s"
            }
            """.formatted(TEXT);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .header("Authorization", "Token " + API_KEY)
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();

        client.send(request, HttpResponse.BodyHandlers.ofFile(Path.of("speech.wav")));

    }
}
