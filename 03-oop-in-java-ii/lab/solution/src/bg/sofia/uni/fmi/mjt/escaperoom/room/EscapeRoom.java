package bg.sofia.uni.fmi.mjt.escaperoom.room;

import bg.sofia.uni.fmi.mjt.escaperoom.rating.Ratable;

import java.util.Objects;

public class EscapeRoom implements Ratable {

    private static final int MAX_REVIEWS_COUNT = 20;
    private String name;
    private Theme theme;
    private Difficulty difficulty;
    private int maxTimeToEscape;
    private double priceToPlay;
    private int maxReviewsCount;

    private double ratingsSum;
    private int ratingsCount;

    private int currentReviewIndex;
    private Review[] reviews;

    public EscapeRoom(String name, Theme theme, Difficulty difficulty, int maxTimeToEscape, double priceToPlay,
                      int maxReviewsCount) {
        this.name = name;
        this.theme = theme;
        this.difficulty = difficulty;
        this.maxTimeToEscape = maxTimeToEscape;
        this.priceToPlay = priceToPlay;
        this.maxReviewsCount = maxReviewsCount;
        this.reviews = new Review[maxReviewsCount];
    }

    @Override
    public double getRating() {
        if (ratingsCount != 0) {
            return ratingsSum / ratingsCount;
        }

        return 0.0;
    }

    /**
     * Returns the name of the escape room.
     */
    public String getName() {
        return name;
    }

    public Theme getTheme() {
        return theme;
    }

    /**
     * Returns the difficulty of the escape room.
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Returns the maximum time to escape the room.
     */
    public int getMaxTimeToEscape() {
        return maxTimeToEscape;
    }

    public double getPriceToPlay() {
        return priceToPlay;
    }

    /**
     * Returns all user reviews stored for this escape room, in the order they have been added.
     */
    public Review[] getReviews() {
        int actualReviewsCount = Math.min(ratingsCount, maxReviewsCount);
        Review[] result = new Review[actualReviewsCount];

        int firstReview = (ratingsCount < maxReviewsCount) ? 0 : currentReviewIndex;

        for (int i = 0; i < actualReviewsCount; i++) {
            result[i] = reviews[(firstReview + i) % maxReviewsCount];
        }

        return result;
    }

    /**
     * Adds a user review for this escape room.
     * The platform keeps just the latest {@code maxReviewsCount} reviews and in case the capacity is full,
     * a newly added review would overwrite the oldest added one, so the platform contains
     * {@code maxReviewsCount} at maximum, at any given time. Note that, despite older reviews may have been
     * overwritten, the rating of the room averages all submitted review ratings, regardless of whether all reviews
     * themselves are still stored in the platform.
     *
     * @param review the user review to add.
     */
    public void addReview(Review review) {
        reviews[currentReviewIndex++] = review;
        if (currentReviewIndex == maxReviewsCount) {
            currentReviewIndex = 0;
        }

        ratingsSum += review.rating();
        ratingsCount++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EscapeRoom that = (EscapeRoom) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
