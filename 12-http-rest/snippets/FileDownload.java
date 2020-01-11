import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownload {

    public static void main(String... args) {
        var url = "https://www.7-zip.org/a/7z1806-x64.exe";

        var client = HttpClient.newBuilder().build();
        var request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        Path localFile = Paths.get("7z.exe");
        try {
            HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandlers.ofFile(localFile));
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }

}
