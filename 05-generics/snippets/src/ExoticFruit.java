final class Pineapple extends ExoticFruit {}
final class Melon extends ExoticFruit {}

public sealed class ExoticFruit<T> permits Pineapple, Melon {
    private T t;
}
