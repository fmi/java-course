package bg.sofia.uni.fmi.mjt.virtualwallet.core.card;

public class GoldenCard extends Card {

    private static final double PAYBACK_PERCENT = 0.15;

    public GoldenCard(String name) {
        super(name);
    }

    @Override
    public boolean executePayment(double cost) {
        if (cost > getAmount()) {
            return false;
        }

        return withdraw(cost - cost * PAYBACK_PERCENT);

    }

}
