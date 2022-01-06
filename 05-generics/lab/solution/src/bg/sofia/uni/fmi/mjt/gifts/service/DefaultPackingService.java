package bg.sofia.uni.fmi.mjt.gifts.service;

import bg.sofia.uni.fmi.mjt.gifts.gift.BirthdayGift;
import bg.sofia.uni.fmi.mjt.gifts.gift.Gift;
import bg.sofia.uni.fmi.mjt.gifts.gift.Priceable;
import bg.sofia.uni.fmi.mjt.gifts.person.Person;

import java.util.Collection;
import java.util.Set;

public class DefaultPackingService<T extends Priceable> implements PackingService<T> {

    @Override
    public Gift<T> pack(Person<?> sender, Person<?> receiver, T item) {
        assertNotNull(sender, "sender");
        assertNotNull(receiver, "receiver");
        assertNotNull(item, "item");

        return new BirthdayGift<T>(sender, receiver, Set.of(item));
    }

    @Override
    public Gift<T> pack(Person<?> sender, Person<?> receiver, T... items) {
        assertNotNull(sender, "sender");
        assertNotNull(receiver, "receiver");
        for (T item : items) {
            assertNotNull(item, "item");
        }

        return new BirthdayGift<T>(sender, receiver, Set.of(items));
    }

    @Override
    public Collection<T> unpack(Gift<T> t) {
        assertNotNull(t, "gift");

        return t.getItems();
    }

    private void assertNotNull(Object o, String name) {
        if (o == null) {
            throw new IllegalArgumentException(name + " is null");
        }
    }

}
