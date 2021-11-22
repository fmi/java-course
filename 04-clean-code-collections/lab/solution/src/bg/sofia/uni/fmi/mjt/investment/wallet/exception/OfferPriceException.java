package bg.sofia.uni.fmi.mjt.investment.wallet.exception;

public class OfferPriceException extends WalletException {

    public OfferPriceException(String msg) {
        super(msg);
    }

    public OfferPriceException(String msg, Throwable t) {
        super(msg, t);
    }
}
