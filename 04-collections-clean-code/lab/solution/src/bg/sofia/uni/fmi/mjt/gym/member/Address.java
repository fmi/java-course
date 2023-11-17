package bg.sofia.uni.fmi.mjt.gym.member;

public record Address(double longitude, double latitude) {
    public double getDistanceTo(Address other) {
        return Math.sqrt(
            Math.pow((other.longitude - this.longitude), 2) + Math.pow((other.latitude - this.latitude), 2));
    }
}
