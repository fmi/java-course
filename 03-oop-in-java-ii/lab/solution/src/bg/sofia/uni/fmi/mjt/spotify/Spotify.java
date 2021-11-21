package bg.sofia.uni.fmi.mjt.spotify;

import bg.sofia.uni.fmi.mjt.spotify.account.Account;
import bg.sofia.uni.fmi.mjt.spotify.exceptions.AccountNotFoundException;
import bg.sofia.uni.fmi.mjt.spotify.exceptions.PlayableNotFoundException;
import bg.sofia.uni.fmi.mjt.spotify.exceptions.StreamingServiceException;
import bg.sofia.uni.fmi.mjt.spotify.playable.Playable;
import bg.sofia.uni.fmi.mjt.spotify.exceptions.PlaylistCapacityExceededException;

public class Spotify implements StreamingService {

    private static final double AD_FEE = 0.1;
    private static final double PREMIUM_FEE = 25.0;

    private final Account[] accounts;
    private final Playable[] playableContent;

    public Spotify(Account[] accounts, Playable[] playableContent) {
        this.accounts = accounts;
        this.playableContent = playableContent;
    }

    @Override
    public void play(Account account, String title) throws AccountNotFoundException, PlayableNotFoundException {
        checkNotNull(account);
        checkNotNullOrEmpty(title);
        if (!accountExists(account)) {
            throw new AccountNotFoundException("Account not found: " + account);
        }

        Playable playable = findByTitle(title);
        playable.play();
        account.listen(playable);
    }

    @Override
    public void like(Account account, String title) throws AccountNotFoundException, PlayableNotFoundException, StreamingServiceException {
        checkNotNull(account);
        checkNotNullOrEmpty(title);
        if (!accountExists(account)) {
            throw new AccountNotFoundException("Account not found: " + account);
        }

        Playable playable = findByTitle(title);
        try {
            account.getLibrary().getLiked().add(playable);
        } catch (PlaylistCapacityExceededException e) {
            throw new StreamingServiceException("Failed to like playable content", e);
        }
    }

    @Override
    public Playable findByTitle(String title) throws PlayableNotFoundException {
        checkNotNullOrEmpty(title);

        for (Playable playable : playableContent) {
            if (title.equals(playable.getTitle())) {
                return playable;
            }
        }

        throw new PlayableNotFoundException("Failed to find playable content with title " + title);
    }

    @Override
    public Playable getMostPlayed() {
        int mostPlays = 0;
        Playable mostPlayed = null;

        for (Playable playable : playableContent) {
            if (playable.getTotalPlays() > mostPlays) {
                mostPlayed = playable;
                mostPlays = playable.getTotalPlays();
            }
        }

        return mostPlays == 0 ? null : mostPlayed;
    }

    @Override
    public double getTotalListenTime() {
        double totalPlayedTime = 0;

        for (Account a : accounts) {
            totalPlayedTime += a.getTotalListenTime();
        }

        return totalPlayedTime;
    }

    @Override
    public double getTotalPlatformRevenue() {
        double totalMoneyEarned = 0.0;

        for (Account account : accounts) {
            switch (account.getType()) {
                case FREE ->
                    totalMoneyEarned += account.getAdsListenedTo() * AD_FEE;
                case PREMIUM ->
                    totalMoneyEarned += PREMIUM_FEE;
            }
        }

        return totalMoneyEarned;
    }

    private void checkNotNull(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account can not be null");
        }
    }

    private void checkNotNullOrEmpty(String string) {
        if (string == null || string.isEmpty()) {
            throw new IllegalArgumentException("Title can not be null or empty");
        }
    }

    private boolean accountExists(Account account) {
        for (Account acc : accounts) {
            if (account.equals(acc)) {
                return true;
            }
        }

        return false;
    }
}
