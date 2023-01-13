import com.google.gson.Gson;

public class FromJsonMain {

    public static void main(String[] args) {
        String json = "{\"name\": \"Wesley\", \"age\": 20 }";

        Gson gson = new Gson();
        Developer dev = gson.fromJson(json, Developer.class);

        System.out.printf("%s, %d years old, %s%n", dev.getName(), dev.getAge(), dev.getCompany());
    }

}
