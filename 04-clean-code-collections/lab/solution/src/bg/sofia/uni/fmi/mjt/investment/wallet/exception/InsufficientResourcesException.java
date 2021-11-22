package bg.sofia.uni.fmi.mjt.investment.wallet.exception;

public class InsufficientResourcesException extends WalletException {

    public InsufficientResourcesException(String msg) {
        super(msg);
    }

    public InsufficientResourcesException(String msg, Throwable t) {
        super(msg, t);
    }
}
