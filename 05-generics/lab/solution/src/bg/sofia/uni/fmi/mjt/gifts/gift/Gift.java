package bg.sofia.uni.fmi.mjt.gifts.gift;

import bg.sofia.uni.fmi.mjt.gifts.person.Person;

import java.util.Collection;

public interface Gift<T extends Priceable> {

    /**
     * @return the sender of this gift.
     */
    Person<?> getSender();

    /**
     * @return the receiver of this gift.
     */
    Person<?> getReceiver();

    /**
     * @return the total price of the items in the gift.
     */
    double getPrice();

    /**
     * @param t the item to add to this gift
     * @throws IllegalArgumentException if @t is null
     */
    void addItem(T t);

    /**
     * @param t the item to remove from this gift
     * @return true if the @t was contained in this gift, false otherwise. In
     * particular, if @t is null, return false.
     */
    boolean removeItem(T t);

    /**
     * @return an unmodifiable copy of the items in this gift.
     */
    Collection<T> getItems();

    /**
     * @return the most expensive item in this gift. If there is a tie, return any
     * of them. If the gift contains no items, return null.
     */
    T getMostExpensiveItem();

}
