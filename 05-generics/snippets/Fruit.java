final class Pineapple extends Fruit {}
final class Melon extends Fruit {}

public sealed class Fruit<T> permits Pineapple, Melon {
    private T t;
}
