package bg.sofia.uni.fmi.mjt.escaperoom.room;

public enum Difficulty {

    EASY(1),
    MEDIUM(2),
    HARD(3),
    EXTREME(4);

    private int rank;

    Difficulty(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

}
