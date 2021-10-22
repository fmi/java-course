package bg.sofia.uni.fmi.mjt.adorable;

public class CoolPerson implements Adorable {
    // If we do not implement all Adorable methods, we will get a compile-time error:
    //    "CoolPerson is not abstract and does not override abstract method like() in Likeable"

    @Override
    public void like() {
        System.out.println("I am liked!");
    }

    @Override
    public void love() {
        System.out.println("I am loved!");
    }

}
