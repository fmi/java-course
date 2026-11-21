package bg.sofia.uni.fmi.mjt.carwash.plan;

public enum WashStep {
    PREWASH(10, 0, 0),
    SHAMPOO(5, 20, 0),
    RINSE(15, 0, 0),
    WAX(0, 0, 10),
    DRY(0, 0, 0);

    private final int waterLitres;
    private final int shampooMl;
    private final int waxMl;

    WashStep(int waterLitres, int shampooMl, int waxMl) {
        this.waterLitres = waterLitres;
        this.shampooMl = shampooMl;
        this.waxMl = waxMl;
    }

    public int getWaterLitres() {
        return waterLitres;
    }

    public int getShampooMl() {
        return shampooMl;
    }

    public int getWaxMl() {
        return waxMl;
    }
}
