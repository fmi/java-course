package bg.sofia.uni.fmi.mjt.gifts.service;

import bg.sofia.uni.fmi.mjt.gifts.exception.WrongReceiverException;
import bg.sofia.uni.fmi.mjt.gifts.gift.Gift;
import bg.sofia.uni.fmi.mjt.gifts.person.Person;

public interface DeliveryService {

    /**
     * @param receiver the receiver of the @gift
     * @param gift     the gift to be sent
     * @throws IllegalArgumentException if @receiver or @gift is null
     * @throws WrongReceiverException   if @receiver is different from the receiver
     *                                  of the gift
     */
    void send(Person<?> receiver, Gift<?> gift);
}
