import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CollectionsMain {

	public static void main(String[] args) {
		List<Developer> devs = List.of(
				new Developer("Kelsey", 28, "Google, Inc"),
				new Developer("Wesley", 20, "Google, Inc"));

		Gson gson = new Gson();
		String json = gson.toJson(devs);

		Type type = new TypeToken<List<Developer>>(){}.getType();
		List<Developer> devsAgain = gson.fromJson(json, type);

		System.out.println(devsAgain.size());
	}
}
