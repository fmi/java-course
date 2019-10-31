package bg.sofia.uni.fmi.mjt.virtualwallet.core.card;

public abstract class Card {

    private String name;
    private double amount;

    public Card(String name) {
        this.name = name;
    }

    public abstract boolean executePayment(double cost);

    protected boolean withdraw(double amount) {
        if (amount <= 0) {
            return false;
        }

        double moneyLeft = this.amount - amount;

        if (moneyLeft < 0) {
            return false;
        }

        this.amount = moneyLeft;
        return true;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }

        this.amount += amount;
        return true;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Card other = (Card) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Card [name=" + name + ", amount=" + amount + "]";
    }

}
