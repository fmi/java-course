import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CollectionsMain {

    public static void main(String[] args) {
        // Lists example
        List<Developer> devs = List.of(
            new Developer("Kelsey", 28, "Google, Inc"),
            new Developer("Wesley", 20, "Google, Inc"));

        Gson gson = new Gson();
        String json = gson.toJson(devs);

        System.out.println(json);

        Type type = new TypeToken<List<Developer>>() {
        }.getType();
        List<Developer> devsAgain = gson.fromJson(json, type);

        System.out.println(devsAgain.size()); // 2

        // Maps example
        json = "{\"apple\":1,\"banana\":2,\"cherry\":3}";

        Map<String, Integer> map = gson.fromJson(json, new TypeToken<Map<String, Integer>>() {
        }.getType());

        System.out.println(map);  // {apple=1, banana=2, cherry=3}
    }
}
