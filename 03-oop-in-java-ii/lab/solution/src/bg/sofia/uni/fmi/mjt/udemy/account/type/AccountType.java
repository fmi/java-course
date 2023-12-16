package bg.sofia.uni.fmi.mjt.udemy.account.type;

public enum AccountType {
    STANDARD(0),
    EDUCATION(0.15),
    BUSINESS(0.20);

    private final double discount;

    AccountType(double discount) {
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }
}
