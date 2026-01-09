import com.google.gson.Gson;
import gson.Developer;

import java.nio.file.Files;
import java.nio.file.Path;

void main() throws IOException {
    Gson gson = new Gson();

    try (Reader reader = Files.newBufferedReader(
        Path.of("src", "gson", "devs.json"))) {

        List<Developer> devs =
            gson.fromJson(reader, List.class);

        IO.println(devs);
    }
}
