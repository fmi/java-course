package bg.sofia.uni.fmi.mjt.spotify.playable;

public class Audio extends AbstractPlayable {

    public Audio(String title, String artist, int year, double duration) {
        super(title, artist, year, duration);
    }

    @Override
    public String play() {
        increaseTotalPlays();
        return String.format("Currently playing audio content: %s", this.getTitle());
    }
}
