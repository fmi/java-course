package bg.sofia.uni.fmi.mjt.investment.wallet.exception;

public class UnknownAssetException extends WalletException {

    public UnknownAssetException(String msg) {
        super(msg);
    }

    public UnknownAssetException(String msg, Throwable t) {
        super(msg, t);
    }
}
