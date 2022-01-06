package bg.sofia.uni.fmi.mjt.gifts.person;

import bg.sofia.uni.fmi.mjt.gifts.exception.WrongReceiverException;
import bg.sofia.uni.fmi.mjt.gifts.gift.Gift;

import java.util.Collection;

public interface Person<I> {

    /**
     * @param n the max number of most expensive gifts to return. If there is a tie
     *          of two or more gifts with the same lowest price in the top @n, which
     *          of those to include in the result is undefined. If @n exceeds the
     *          total number of gifts received, return all of them. If @n = 0,
     *          return an empty collection.
     * @return an unmodifiable copy of the @n most expensive gifts received by this
     * person. The order of the gifts in the returned collection is
     * undefined.
     * @throws IllegalArgumentException if @n < 0
     */
    Collection<Gift<?>> getNMostExpensiveReceivedGifts(int n);

    /**
     * @param person the sender of the gifts. If there are no gifts sent by @person
     *               to this person, return an empty collection.
     * @return an unmodifiable copy of the gifts sent by @person, in undefined order
     * @throws IllegalArgumentException if @person is null
     */
    Collection<Gift<?>> getGiftsBy(Person<?> person);

    /**
     * @return the unique id of this person.
     */
    I getId();

    /**
     * @param gift the @gift to be received
     * @throws IllegalArgumentException if @gift is null
     * @throws WrongReceiverException   if recipient of the @gift is not this person
     */
    void receiveGift(Gift<?> gift);

}
