import com.google.gson.Gson;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class VirusScanner {

    private static final String VIRUS_TOTAL_API_KEY = "983fc8b876df4ac6330886a51706d0785c2227c7fda3490c741ea51304efc112";

    public static void main(String[] args) throws Exception {

        var client = HttpClient.newBuilder().build();

        Path localFile = Paths.get("7z.exe");

        Map<Object, Object> data = new LinkedHashMap<>();
        data.put("apikey", VIRUS_TOTAL_API_KEY);
        data.put("file", localFile);
        String boundary = new BigInteger(256, new Random()).toString();

        var request = HttpRequest.newBuilder()
                .header("Content-Type", "multipart/form-data;boundary=" + boundary)
                .POST(ofMimeMultipartData(data, boundary))
                .uri(URI.create("https://www.virustotal.com/vtapi/v2/file/scan"))
                .build();

        HttpResponse<String> vtResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(vtResponse.body());

        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(vtResponse.body(), Map.class);
        String resource = map.get("resource");
        String link = map.get("permalink");

        System.out.println(link);

        URI uri = new URI("https", "www.virustotal.com", "/vtapi/v2/file/report", "apikey=" + VIRUS_TOTAL_API_KEY + "&resource=" + resource, null);

        HttpResponse<String> status = client.send(HttpRequest.newBuilder(uri).build(),
                HttpResponse.BodyHandlers.ofString());

        System.out.println(status.body());
    }

    public static HttpRequest.BodyPublisher ofMimeMultipartData(Map<Object, Object> data,
                                                                String boundary) throws IOException {
        var byteArrays = new ArrayList<byte[]>();
        byte[] separator = ("--" + boundary + "\r\nContent-Disposition: form-data; name=")
                .getBytes(StandardCharsets.UTF_8);
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            byteArrays.add(separator);

            if (entry.getValue() instanceof Path) {
                var path = (Path) entry.getValue();
                String mimeType = Files.probeContentType(path);
                byteArrays.add(("\"" + entry.getKey() + "\"; file=\"" + path.getFileName()
                        + "\"\r\nContent-Type: " + mimeType + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
                byteArrays.add(Files.readAllBytes(path));
                byteArrays.add("\r\n".getBytes(StandardCharsets.UTF_8));
            } else {
                byteArrays.add(("\"" + entry.getKey() + "\"\r\n\r\n" + entry.getValue() + "\r\n")
                        .getBytes(StandardCharsets.UTF_8));
            }
        }
        byteArrays.add(("--" + boundary + "--").getBytes(StandardCharsets.UTF_8));
        return HttpRequest.BodyPublishers.ofByteArrays(byteArrays);
    }

}
