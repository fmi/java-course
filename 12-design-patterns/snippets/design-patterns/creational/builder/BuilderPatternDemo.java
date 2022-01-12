package creational.builder;

public class BuilderPatternDemo {

    public static void main(String[] args) {
        // Using builder to get the object in a single line of code and
        // without any inconsistent state or arguments management issues
        Computer comp = Computer.builder("5 TB", "32 GB")
                .setBluetoothEnabled(true)
                .setGraphicsCardEnabled(true)
                .build();

        Computer comp2 = Computer.builder("1 TB", "16 GB").build();
    }

}
