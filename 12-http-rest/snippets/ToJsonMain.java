import com.google.gson.Gson;

public class ToJsonMain {

	public static void main(String[] args) {
		Developer dev = new Developer("Kelsey", 28, "Google, Inc");

		Gson gson = new Gson();
		String json = gson.toJson(dev);
		System.out.println(json);
	}
}
