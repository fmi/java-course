package bg.sofia.uni.fmi.mjt.spotify;

import bg.sofia.uni.fmi.mjt.spotify.account.Account;
import bg.sofia.uni.fmi.mjt.spotify.exceptions.AccountNotFoundException;
import bg.sofia.uni.fmi.mjt.spotify.exceptions.PlayableNotFoundException;
import bg.sofia.uni.fmi.mjt.spotify.exceptions.StreamingServiceException;
import bg.sofia.uni.fmi.mjt.spotify.playable.Playable;

public interface StreamingService {

    /**
     * Simulates playing content with the specified title by a user with the given account.
     *
     * @param account the account of the user that will play the content. The account must be registered in the platform
     *                in order to access its contents.
     * @param title   the exact name of the content.
     * @throws IllegalArgumentException  if the account is null or if the title is null or empty.
     * @throws AccountNotFoundException  if the account is not registered in the platform.
     * @throws PlayableNotFoundException if content with the specified title is not present in the platform.
     */
    void play(Account account, String title) throws AccountNotFoundException, PlayableNotFoundException;

    /**
     * Adds the content with the given title to the "Liked Content" default playlist in the library of the specified account.
     *
     * @param account the account of the user. The account must be registered in the platform
     *                in order to access its contents.
     * @param title   the exact name of the content.
     * @throws IllegalArgumentException  if the account is null or if the title is null or empty.
     * @throws AccountNotFoundException  if the account is not registered in the platform.
     * @throws PlayableNotFoundException if content with the specified title is not present in the platform.
     * @throws StreamingServiceException if the content could not be added to the "Liked Content" default playlist.
     */
    void like(Account account, String title)
        throws AccountNotFoundException, PlayableNotFoundException, StreamingServiceException;

    /**
     * Returns the playable content with the given title.
     *
     * @param title the exact name of the content.
     * @throws IllegalArgumentException  if the title is null or empty.
     * @throws PlayableNotFoundException if content with the specified title is not present in the platform.
     */
    Playable findByTitle(String title) throws PlayableNotFoundException;

    /**
     * Returns the content which has been played the most. If there is no content in the platform, returns null.
     * If there is a tie, returns any of the most played content.
     */
    Playable getMostPlayed();

    /**
     * Returns the sum of total listen times for all accounts in the platform.
     */
    double getTotalListenTime();

    /**
     * Returns the total revenue of the platform.
     * The platform makes money from each registered account:
     * - for FREE accounts, the revenue is 0.10 for every ad that has been played
     * - for PREMIUM accounts, the revenue is a one-time subscription of 25.00
     */
    double getTotalPlatformRevenue();

}
