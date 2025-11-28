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

    // The list can be one of:
    // List<TropicalFruit>
    // List<Banana>
    // List<PassionFruit>
    public static void getSomeTropicalFruit(List<? extends TropicalFruit> mysteryFruitBasket) {
        // it's safe to get items because they always have TropicalFruit's methods
        mysteryFruitBasket.getFirst().fruit();
        mysteryFruitBasket.getFirst().someTropicalFruitMethod();

        // this won't work if we are in the case of List<PassionFruit>
        // mysteryFruitBasket.add(new Banana()); // compilation error
    }

    // The Java compiler inserts type casts if necessary to preserve type safety:
    // public static void getSomeTropicalFruit(List mysteryFruitBasket) {
    //    ((TropicalFruit) mysteryFruitBasket.getFirst()).fruit();
    //    ((TropicalFruit) mysteryFruitBasket.getFirst()).someTropicalFruitMethod();
    // }

    // The list is one of:
    // List<TropicalFruit> mysteryFruitBasket
    // List<Fruit> mysteryFruitBasket
    public static void putSomeTropicalFruit(List<? super TropicalFruit> mysteryFruitBasket) {
        // safe to add all of these, since they all are Tropical fruits
        // and can be added to a list of fruits or a list of tropical fruits:
        mysteryFruitBasket.add(new Banana());
        mysteryFruitBasket.add(new PassionFruit());
        mysteryFruitBasket.add(new TropicalFruit());

        // returns object, hence no methods from TropicalFruit or Fruit are available
        // mysteryFruitBasket.getFirst().fruit(); // compilation error

        // you cannot put fruits that are not TropicalFruits because
        // you don't know if the list can hold them, i.e. if we have
        // List<TropicalFruit> we cannot put neither of those:
        // mysteryFruitBasket.add(new Fruit()); // compilation error
        // mysteryFruitBasket.add(new Apple()); // compilation error
    }

    // Replace all type parameters in generic types with their bounds or Object if the type parameters are unbounded.
    // The produced bytecode, therefore, contains only ordinary classes, interfaces, and methods.
    // Compiler PoV; Basically a list of Objects, so you can put anything
    // public static void putSomeTropicalFruit(List basket) {
    //     basket.add(new TropicalFruit());
    //     basket.add(new Banana());
    // }

}

