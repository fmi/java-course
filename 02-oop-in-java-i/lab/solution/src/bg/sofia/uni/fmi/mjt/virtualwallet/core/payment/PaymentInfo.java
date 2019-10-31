package bg.sofia.uni.fmi.mjt.virtualwallet.core.payment;

public class PaymentInfo {

    private String reason;
    private String location;
    private double cost;

    public PaymentInfo(String reason, String location, double cost) {
        this.reason = reason;
        this.location = location;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public String getReason() {
        return reason;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "PaymentInfo [reason=" + reason + ", location=" + location + ", cost=" + cost + "]";
    }

}
