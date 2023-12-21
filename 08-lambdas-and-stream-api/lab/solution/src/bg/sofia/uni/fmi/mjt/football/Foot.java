package bg.sofia.uni.fmi.mjt.football;

public enum Foot {
    LEFT, RIGHT;

    public static Foot of(String foot) {
        return Foot.valueOf(foot.toUpperCase());
    }
}
