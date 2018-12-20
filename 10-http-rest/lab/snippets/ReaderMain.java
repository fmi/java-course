import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

public class ReaderMain {

	public static void main(String[] args) throws IOException {
		Gson gson = new Gson();

		FileReader reader = new FileReader(new File("devs.json"));
		List<Developer> devs = gson.fromJson(reader, List.class);
		int ch = reader.read();
		System.out.println(ch);
	}
}
