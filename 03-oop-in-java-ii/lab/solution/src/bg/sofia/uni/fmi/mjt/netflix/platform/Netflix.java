package bg.sofia.uni.fmi.mjt.netflix.platform;

import bg.sofia.uni.fmi.mjt.netflix.account.Account;
import bg.sofia.uni.fmi.mjt.netflix.content.MeteredStreamableContent;
import bg.sofia.uni.fmi.mjt.netflix.content.Streamable;
import bg.sofia.uni.fmi.mjt.netflix.exceptions.ContentNotFoundException;
import bg.sofia.uni.fmi.mjt.netflix.exceptions.ContentUnavailableException;
import bg.sofia.uni.fmi.mjt.netflix.exceptions.UserNotFoundException;

import java.time.LocalDateTime;

public class Netflix implements StreamingService {

    private final MeteredStreamableContent[] videos;
    private final Account[] accounts;
    private MeteredStreamableContent mostWatched;
    private int totalWatchedTime;

    public Netflix(Account[] accounts, Streamable[] streamableContent) {
        this.accounts = accounts;
        videos = new MeteredStreamableContent[streamableContent.length];
        for (int i = 0; i < videos.length; i++) {
            videos[i] = new MeteredStreamableContent(streamableContent[i]);
        }
    }

    public void watch(Account user, String videoContentName) throws ContentUnavailableException {
        if (!isUserRegistered(user)) {
            throw new UserNotFoundException(String.format("User with username %s is not yet registered in the streaming service", user.username()));
        }

        MeteredStreamableContent video = findMeteredStreamableByName(videoContentName);
        if (video == null) {
            throw new ContentNotFoundException(String.format("Streaming content with name %s not found", videoContentName));
        }

        if (isContentAgeRestricted(video.getContent(), user)) {
            throw new ContentUnavailableException(String.format("Content with title %s is age restricted", videoContentName));
        }

        video.watch();

        totalWatchedTime += video.getContent().getDuration();

        if (mostWatched == null || video.getTotalTimesWatched() > mostWatched.getTotalTimesWatched()) {
            mostWatched = video;
        }
    }

    public Streamable findByName(String videoContentName) {
        MeteredStreamableContent streamable = findMeteredStreamableByName(videoContentName);
        return streamable == null ? null : streamable.getContent();
    }

    public int totalWatchedTimeByUsers() {
        return totalWatchedTime;
    }

    private MeteredStreamableContent findMeteredStreamableByName(String videoContentName) {
        for (MeteredStreamableContent video : videos) {
            if (video.getContent().getTitle().equalsIgnoreCase(videoContentName.strip())) {
                return video;
            }
        }

        return null;
    }

    public Streamable mostViewed() {
        return mostWatched != null ? mostWatched.getContent() : null;
    }

    private boolean isUserRegistered(Account account) {
        for (Account platformAcc : accounts) {
            if (platformAcc.equals(account)) {
                return true;
            }
        }

        return false;
    }

    public boolean isContentAgeRestricted(Streamable content, Account user) {
        LocalDateTime minYearsAgo = null;
        switch (content.getRating()) {
            case PG13 -> minYearsAgo = LocalDateTime.now().minusYears(13);
            case NC17 -> minYearsAgo = LocalDateTime.now().minusYears(17);
        }

        return minYearsAgo != null && user.birthdayDate().isAfter(minYearsAgo);
    }

}
