package bg.sofia.uni.fmi.mjt.spotify.account;

import bg.sofia.uni.fmi.mjt.spotify.library.Library;
import bg.sofia.uni.fmi.mjt.spotify.playable.Playable;

import java.util.Objects;

public class Account {

    private final String email;
    private final Library library;

    private int listenedSongs;
    private double totalListenTime;

    public Account(String email, Library library) {
        this.email = email;
        this.library = library;
        this.listenedSongs = 0;
    }

    public void listen(Playable playable) {
        listenedSongs++;
        totalListenTime += playable.getDuration();
    }

    public int getAdsListenedTo() {
        return listenedSongs / 5;
    }

    public Library getLibrary() {
        return library;
    }

    public double getTotalListenTime() {
        return totalListenTime;
    }

    public AccountType getType() {
        return AccountType.FREE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(email, account.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "Account{" +
            "email='" + email + '\'' +
            '}';
    }
}
