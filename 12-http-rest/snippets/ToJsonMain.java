import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ToJsonMain {

    public static void main(String[] args) {
        Developer dev = new Developer("Kelsey", 28, "Google, Inc");
        DevManager devManager = new DevManager("Mike", "Google AI R&D", 7);

        Gson gson = new Gson();
        String devJson = gson.toJson(dev);
        System.out.println(devJson);

        // Gson version 2.10 and later also supports records
        // Note that the & character in the department value would be replaced by default with its unicode escape, \u0026
        Gson gsonNonEscaping = new GsonBuilder().disableHtmlEscaping().create();
        String devManagerJson = gsonNonEscaping.toJson(devManager);
        System.out.println(devManagerJson);
    }

}
