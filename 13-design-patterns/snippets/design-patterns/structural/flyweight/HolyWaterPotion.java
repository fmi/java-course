package structural.flyweight;

public class HolyWaterPotion implements Potion {

    @Override
    public void drink() {
        System.out.printf("You feel blessed. (Potion=%s)\n", System.identityHashCode(this));
    }

}
