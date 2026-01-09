import com.google.gson.Gson;
import gson.Developer;

void main() {
    String json = "{\"name\": \"Wesley\", \"age\": 20 }";

    Gson gson = new Gson();
    Developer dev = gson.fromJson(json, Developer.class);

    System.out.printf("%s, %d years old, %s%n", dev.getName(), dev.getAge(), dev.getCompany());
}
