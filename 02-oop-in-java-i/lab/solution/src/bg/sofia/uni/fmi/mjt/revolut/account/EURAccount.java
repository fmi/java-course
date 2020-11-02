package bg.sofia.uni.fmi.mjt.revolut.account;

public class EURAccount extends Account {
    private static final String CURRENCY = "EUR";

    public EURAccount(String IBAN, double amount) {
        super(IBAN, amount);
    }

    @Override
    public String getCurrency() {
        return CURRENCY;
    }

}
