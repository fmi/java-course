package bg.sofia.uni.fmi.mjt.investment.wallet.exception;

public class WalletException extends Exception {

    public WalletException(String msg) {
        super(msg);
    }

    public WalletException(String msg, Throwable t) {
        super(msg, t);
    }
}
