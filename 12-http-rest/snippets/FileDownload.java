import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

public class FileDownload {

    public static void main(String... args) throws Exception {
        var url = "https://www.7-zip.org/a/7z1806-x64.exe";

        var client = HttpClient.newBuilder().build();
        var request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        Path localFile = Path.of("7z.exe");

        HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandlers.ofFile(localFile));
    }

}
