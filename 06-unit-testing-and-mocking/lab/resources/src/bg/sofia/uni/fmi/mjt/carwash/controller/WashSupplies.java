package bg.sofia.uni.fmi.mjt.carwash.controller;

public record WashSupplies(int waterLitres, int shampooMl, int waxMl) {

    public WashSupplies {
        if (waterLitres < 0 || shampooMl < 0 || waxMl < 0) {
            throw new IllegalArgumentException("Supply amounts must not be negative");
        }
    }

}
