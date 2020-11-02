package bg.sofia.uni.fmi.mjt.revolut.account;

public class BGNAccount extends Account {
    private static final String CURRENCY = "BGN";

    public BGNAccount(String IBAN, double amount) {
        super(IBAN, amount);
    }

    @Override
    public String getCurrency() {
        return CURRENCY;
    }

}
