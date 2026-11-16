/*
 * ============================================================================
 *  GENERICS UNDER THE HOOD · A BRIDGE WITH NO GENERICS AT ALL
 * ============================================================================
 *
 *  Bridges are often introduced as "a generics thing". They are really about
 *  JVM signature compatibility, and erasure is only one cause. Covariant
 *  return types (a Java 5 feature, no generics involved) trigger them too.
 *
 *  Cat.reproduce() returns Cat. It overrides Animal.reproduce() which returns
 *  Animal (a cat gives birth to a cat). The JVM dispatches on the exact
 *  descriptor, so it still needs an Animal-returning method -> bridge on Cat.
 *
 *      javac CovariantBridge.java
 *      javap -p Cat.class
 *
 *  Two methods, zero generics:
 *      public Cat    reproduce();       // yours
 *      public Animal reproduce();       // bridge
 *
 *      javap -p -v Cat.class
 *      the Animal-returning one: ACC_PUBLIC, ACC_BRIDGE, ACC_SYNTHETIC
 *
 *  Takeaway: "bridge method" == the compiler re-adds a signature that erasure
 *  OR covariance would otherwise hide. Generics are the common cause, not the
 *  only one.
 * ============================================================================
 */
class Animal {
    Animal reproduce() {
        return new Animal();
    }
}

class Cat extends Animal {
    @Override
    public Cat reproduce() {
        return new Cat();    // covariant return: Cat, not Animal
    }
}

public class CovariantBridge {
    public static void main(String... args) {
        Animal a = new Cat();
        Animal baby = a.reproduce();   // dynamic dispatch goes through the Animal-returning bridge on Cat
        System.out.println(baby.getClass().getSimpleName());   // Cat
    }
}
