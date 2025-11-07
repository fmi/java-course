import java.util.ArrayList;
import java.util.List;

class Fruit {
    public void fruit() {
        System.out.println("I'm a just a fruit");
    }
}

class Apple extends Fruit {
    public void apple() {
        System.out.println("not invented by Steve Jobs btw");
    }
}

class TropicalFruit extends Fruit {
    public void someTropicalFruitMethod() {
        System.out.println("I'm a tropical fruit!");
    }
}

class Banana extends TropicalFruit {
    public void twoBananas() {
        System.out.println("monkey never cramps");
    }
}

class PassionFruit extends TropicalFruit {
    public void exotic() {
        System.out.println("very Exotic!");
    }
}

public class FruitBasket {
    public static void main(String[] args) {
        // can contain all types of fruits
        List<Fruit> fruitBasket = new ArrayList<>();
        // new fruits can be put into:
        fruitBasket.add(new Apple());
        fruitBasket.add(new Banana());
        System.out.println("Basket (List<Fruit>): " + fruitBasket);
        // fruits can also be taken out
        // and the reference can only be Fruit
        Fruit myFruit = fruitBasket.get(0);

        List<Banana> bananas = new ArrayList<>(List.of(new Banana(), new Banana()));
        List<TropicalFruit> tropicalFruits = new ArrayList<>(List.of(new Banana(), new TropicalFruit(), new PassionFruit()));
        List<Fruit> genericFruits = new ArrayList<>(List.of(new Banana(), new TropicalFruit(), new Apple()));

        // bananas are tropical fruits, so safe to get them:
        getSomeTropicalFruit(bananas);
        // obviously safe:
        getSomeTropicalFruit(tropicalFruits);
        // you can only put tropical fruit lists or lists of their child classes
        // getSomeTropicalFruit(genericFruits); // compilation error

        // you cannot pass a list of bananas, you might want to put passion fruit there
        // putSomeTropicalFruit(bananas); // compilation error

        // totally safe to put passion fruit here:
        putSomeTropicalFruit(tropicalFruits);
        putSomeTropicalFruit(genericFruits);
    }

    public static void getSomeTropicalFruit(List<? extends TropicalFruit> mysteryFruitBasket) {
        mysteryFruitBasket.getFirst().fruit();
        mysteryFruitBasket.getFirst().someTropicalFruitMethod();
    }

    public static void putSomeTropicalFruit(List<? super TropicalFruit> mysteryFruitBasket) {
        // returns object, hence no methods from TropicalFruit or Fruit are available
        // mysteryFruitBasket.getFirst().fruit(); // compilation error
        mysteryFruitBasket.add(new Banana());
        mysteryFruitBasket.add(new TropicalFruit());

        // you cannot put fruits, that's a TropicalFruit basket at the end
        //  mysteryFruitBasket.add(new Fruit()); // compilation error
    }

}

