package bg.sofia.uni.fmi.mjt.virtualwallet.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bg.sofia.uni.fmi.mjt.virtualwallet.core.card.Card;
import bg.sofia.uni.fmi.mjt.virtualwallet.core.card.GoldenCard;
import bg.sofia.uni.fmi.mjt.virtualwallet.core.card.StandardCard;
import bg.sofia.uni.fmi.mjt.virtualwallet.core.payment.PaymentInfo;

public class VirtualWalletTest {
    private static final double DELTA = 1e-15;

    private static final String STANDARD_CARD = "standard";
    private static final String GOLDEN_CARD = "golden";
    private static final String UNKNOWN_CARD = "unknown";
    private static final String TEST_CARD = "test";

    private static final double LARGE_FEED_AMOUNT = 100.0;
    private static final double SMALL_FEED_AMOUNT = 10.0;
    private static final double NEGATIVE_FEED_AMOUNT = -100.0;

    private static final double PAYBACK_PERCENT = 0.15;


    private VirtualWalletAPI wallet;

    private Card standardCard;
    private Card goldenCard;

    private static Card nullNameCard;
    private static Card unknownCard;

    private static PaymentInfo info;
    private static PaymentInfo negativeCostInfo;

    @BeforeClass
    public static void setUpBeforeClass() {
        info = new PaymentInfo("Happy Bar & Grill", "Sofia", 50.0);
        negativeCostInfo = new PaymentInfo("Happy Bar & Grill", "Sofia", -50.0);

        unknownCard = new StandardCard(UNKNOWN_CARD);
        nullNameCard = new StandardCard(null);
    }

    @Before
    public void setUp() {
        wallet = new VirtualWallet();

        standardCard = new StandardCard(STANDARD_CARD);
        goldenCard = new GoldenCard(GOLDEN_CARD);
    }

    @Test
    public void testIfCardIsCreatedSuccessfully() {
        boolean isCreateSuccessful = wallet.registerCard(standardCard);
        assertTrue(isCreateSuccessful);

        int expected = 1;
        int actual = wallet.getTotalNumberOfCards();
        assertEquals(expected, actual);

        Card card = wallet.getCardByName(STANDARD_CARD);
        assertEquals(standardCard, card);
    }

    @Test
    public void testIfCreateFailsIfMoreCards() {
        String names[] = {"test", "test1", "test2", "test3"};

        assertTrue(wallet.registerCard(standardCard));
        assertTrue(wallet.registerCard(goldenCard));

        assertTrue(wallet.registerCard(new StandardCard(names[0])));
        assertTrue(wallet.registerCard(new StandardCard(names[1])));
        assertTrue(wallet.registerCard(new StandardCard(names[2])));
        assertFalse(wallet.registerCard(new StandardCard(names[3])));
    }

    @Test
    public void testIfCreateFailsIfExistingStandardCard() {
        assertTrue(wallet.registerCard(standardCard));
        assertFalse(wallet.registerCard(standardCard));

        int expected = 1;
        int actual = wallet.getTotalNumberOfCards();
        assertEquals(expected, actual);
    }

    @Test
    public void testIfCreateFailsIfExistingGoldenCard() {
        assertTrue(wallet.registerCard(goldenCard));
        assertFalse(wallet.registerCard(goldenCard));

        assertEquals(1, wallet.getTotalNumberOfCards());
    }

    @Test
    public void testIfCreateFailsIfExistingCardNameAndDifferentCardImpl() {
        assertTrue(wallet.registerCard(new StandardCard(TEST_CARD)));
        assertFalse(wallet.registerCard(new GoldenCard(TEST_CARD)));

        int expected = 1;
        int actual = wallet.getTotalNumberOfCards();
        assertEquals(expected, actual);
    }

    @Test
    public void testIfCreateFailsIfNullCard() {
        assertFalse(wallet.registerCard(null));

        int expected = 0;
        int actual = wallet.getTotalNumberOfCards();
        assertEquals(expected, actual);
    }

    @Test
    public void testIfCreateFailsIfNullCardName() {
        assertFalse(wallet.registerCard(nullNameCard));

        int expected = 0;
        int actual = wallet.getTotalNumberOfCards();
        assertEquals(expected, actual);
    }

    @Test
    public void testIfPaymentFailsIfNullCardIsPassed() {
        assertFalse(wallet.executePayment(null, info));
    }

    @Test
    public void testIfPaymentFailsIfNullCardNameIsPassed() {
        assertFalse(wallet.executePayment(nullNameCard, info));
    }

    @Test
    public void testIfPaymentFailsIfNullPaymentIsPassed() {
        assertTrue(wallet.registerCard(standardCard));
        assertFalse(wallet.executePayment(standardCard, null));
    }

    @Test
    public void testIfPaymentFailsIfNonExistingCardIsPassed() {
        assertFalse(wallet.executePayment(unknownCard, info));
    }

    @Test
    public void testIfPaymentFailsWithStandardCardIfThereIsNotEnoughBalance() {
        assertTrue(wallet.registerCard(standardCard));
        assertTrue(wallet.feed(standardCard, SMALL_FEED_AMOUNT));
        assertFalse(wallet.executePayment(standardCard, info));

        Card card  = wallet.getCardByName(STANDARD_CARD);

        double expected = SMALL_FEED_AMOUNT;
        double actual = card.getAmount();
        assertEquals(expected, actual, DELTA);
    }

    @Test
    public void testIfPaymentFailsWithGoldenCardIfThereIsNotEnoughBalance() {
        assertTrue(wallet.registerCard(goldenCard));
        assertTrue(wallet.feed(goldenCard, SMALL_FEED_AMOUNT));
        assertFalse(wallet.executePayment(goldenCard, info));

        Card card  = wallet.getCardByName(GOLDEN_CARD);

        double expected = SMALL_FEED_AMOUNT;
        double actual = card.getAmount();
        assertEquals(expected, actual, DELTA);
    }

    @Test
    public void testIfPaymentSucceedsWithStandardCardIfThereIsEnoughBalance() {
        assertTrue(wallet.registerCard(standardCard));
        assertTrue(wallet.feed(standardCard, LARGE_FEED_AMOUNT));
        assertTrue(wallet.executePayment(standardCard, info));

        Card card  = wallet.getCardByName(STANDARD_CARD);

        double expected = LARGE_FEED_AMOUNT - info.getCost();
        double actual = card.getAmount();
        assertEquals(expected, actual, DELTA);
    }

    @Test
    public void testIfPaymentSucceedsWithGoldenCardIfThereIsEnoughBalance() {
        assertTrue(wallet.registerCard(goldenCard));
        assertTrue(wallet.feed(goldenCard, LARGE_FEED_AMOUNT));
        assertTrue(wallet.executePayment(goldenCard, info));

        Card card  = wallet.getCardByName(GOLDEN_CARD);

        double expected = LARGE_FEED_AMOUNT - (info.getCost() - info.getCost() * PAYBACK_PERCENT);
        double actual = card.getAmount();
        assertEquals(expected, actual, DELTA);
    }

    @Test
    public void testIfPaymentFailsIfNegativeCost() {
        assertTrue(wallet.registerCard(goldenCard));
        assertTrue(wallet.feed(goldenCard, LARGE_FEED_AMOUNT));
        assertFalse(wallet.executePayment(goldenCard, negativeCostInfo));

        Card card  = wallet.getCardByName(GOLDEN_CARD);

        double expected = LARGE_FEED_AMOUNT;
        double actual = card.getAmount();
        assertEquals(expected, actual, DELTA);
    }

    @Test
    public void testIfPaymentFailsIfCardWithExistingNameButDifferentImpl() {
        assertTrue(wallet.registerCard(goldenCard));
        assertTrue(wallet.feed(goldenCard, LARGE_FEED_AMOUNT));
        assertFalse(wallet.executePayment(new StandardCard(GOLDEN_CARD), negativeCostInfo));

        Card card  = wallet.getCardByName(GOLDEN_CARD);

        double expected = LARGE_FEED_AMOUNT;
        double actual = card.getAmount();
        assertEquals(expected, actual, DELTA);
    }

    @Test
    public void testIfFeedFailsIfNullCardIsPassed() {
        assertFalse(wallet.feed(null, LARGE_FEED_AMOUNT));
    }

    @Test
    public void testIfFeedFailsIfNullCardNameIsPassed() {
        assertFalse(wallet.feed(nullNameCard, LARGE_FEED_AMOUNT));
    }

    @Test
    public void testIfFeedFailsIfUnknownCardIsPassed() {
        assertFalse(wallet.feed(unknownCard, LARGE_FEED_AMOUNT));
    }

    @Test
    public void testIfFeedFailsIfCardWithExistingNameButDifferentImpl() {
        assertTrue(wallet.registerCard(standardCard));
        assertFalse(wallet.feed(new GoldenCard(STANDARD_CARD), NEGATIVE_FEED_AMOUNT));
    }

    @Test
    public void testIfFeedSucceedsIfExistingCardIsPassed() {
        assertTrue(wallet.registerCard(standardCard));
        assertTrue(wallet.feed(standardCard, LARGE_FEED_AMOUNT));

        Card card = wallet.getCardByName(STANDARD_CARD);

        double expected = LARGE_FEED_AMOUNT;
        double actual = card.getAmount();
        assertEquals(expected, actual, DELTA);
    }

    @Test
    public void testIfFeedFailsIfNegativeAmount() {
        assertTrue(wallet.registerCard(standardCard));
        assertFalse(wallet.feed(standardCard, NEGATIVE_FEED_AMOUNT));
    }

    @ Test
    public void testIfGetCardByNameReturnsExistingCard() {
        assertTrue(wallet.registerCard(standardCard));
        assertEquals(wallet.getCardByName(STANDARD_CARD), standardCard);
    }

    @ Test
    public void testIfGetCardByNameReturnsNullIfNonExistingCard() {
        assertTrue(wallet.registerCard(standardCard));
        assertNull(wallet.getCardByName(UNKNOWN_CARD));
    }

}
