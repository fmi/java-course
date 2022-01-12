package behavioral.strategy;

public class StrategyPatternDemo {

    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        Item item1 = new Item("1234", 10);
        Item item2 = new Item("5678", 40);

        cart.addItem(item1);
        cart.addItem(item2);

        // pay by paypal
        cart.pay(new PaypalStrategy("jeff.bezos@amazon.com", "mypwd"));

        // pay by credit card
        cart.pay(new CreditCardStrategy("Jeff Bezos", "1234567890123456", "786", "12/22"));
    }

}
