package bg.sofia.uni.fmi.mjt.trading;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class DoubleRounder {

    private DoubleRounder() {
        // Prevent initialization
    }

    public static double round(double price) {
        return BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
