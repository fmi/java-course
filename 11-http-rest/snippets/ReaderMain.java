import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ReaderMain {

    public static void main(String[] args) throws IOException {
        Gson gson = new Gson();

        FileReader reader = new FileReader(new File("devs.json"));
        List<Developer> devs = gson.fromJson(reader, List.class);
        System.out.println(devs);
    }
}
