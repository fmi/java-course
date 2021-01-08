import com.google.gson.annotations.SerializedName;

public class Developer {
    private String name;
    private int age;
    @SerializedName("employer") // this sets custom name to the serialized field
    private transient String company; // remove transient keyword to serialize

    public Developer(String name, int age, String company) {
        this.name = name;
        this.age = age;
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
