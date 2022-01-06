package bg.sofia.uni.fmi.mjt.gifts.service;

import bg.sofia.uni.fmi.mjt.gifts.gift.Gift;
import bg.sofia.uni.fmi.mjt.gifts.person.Person;

public class DefaultDeliveryService implements DeliveryService {

    @Override
    public void send(Person<?> receiver, Gift<?> gift) {
        assertNotNull(receiver, "receiver");
        assertNotNull(gift, "gift");

        receiver.receiveGift(gift);
    }

    private void assertNotNull(Object object, String name) {
        if (object == null) {
            throw new IllegalArgumentException(name + " should not be null");
        }
    }
}
