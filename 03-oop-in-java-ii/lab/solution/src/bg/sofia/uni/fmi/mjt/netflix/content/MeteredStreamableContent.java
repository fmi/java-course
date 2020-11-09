package bg.sofia.uni.fmi.mjt.netflix.content;

public class MeteredStreamableContent {
    private final Streamable content;
    private int totalTimesWatched;

    public MeteredStreamableContent(Streamable content) {
        this.content = content;
    }

    public int getTotalTimesWatched() {
        return totalTimesWatched;
    }

    public Streamable getContent() {
        return content;
    }

    public void watch() {
        totalTimesWatched++;
    }
}
