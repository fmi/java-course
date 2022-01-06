package bg.sofia.uni.fmi.mjt.gifts.person;

import bg.sofia.uni.fmi.mjt.gifts.exception.WrongReceiverException;
import bg.sofia.uni.fmi.mjt.gifts.gift.Gift;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class DefaultPerson<I> implements Person<I> {
    private final I id;
    private final Collection<Gift<?>> gifts;

    public DefaultPerson(I id) {
        this.id = id;
        this.gifts = new TreeSet<>(new Comparator<Gift<?>>() {
            @Override
            public int compare(Gift<?> o1, Gift<?> o2) {
                int compare = Double.compare(o2.getPrice(), o1.getPrice());
                return compare != 0 ? compare : -1;
            }
        });
    }

    @Override
    public Collection<Gift<?>> getNMostExpensiveReceivedGifts(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n should be non-negative");
        }

        Set<Gift<?>> resultSet = new HashSet<>();
        Iterator<Gift<?>> it = gifts.iterator();

        while (it.hasNext() && n > 0) {
            n--;
            resultSet.add(it.next());
        }

        return Set.copyOf(resultSet);
    }

    @Override
    public Collection<Gift<?>> getGiftsBy(Person<?> person) {
        assertNotNull(person, "person");

        Set<Gift<?>> resultSet = new HashSet<>();

        for (Gift<?> gift : gifts) {
            if (gift.getSender().equals(person)) {
                resultSet.add(gift);
            }
        }

        return Set.copyOf(resultSet);
    }

    @Override
    public I getId() {
        return id;
    }

    @Override
    public void receiveGift(Gift<?> gift) {
        assertNotNull(gift, "gift");

        if (!gift.getReceiver().equals(this)) {
            throw new WrongReceiverException("gift is not for me");
        }

        gifts.add(gift);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DefaultPerson<?> that = (DefaultPerson<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private void assertNotNull(Object object, String name) {
        if (object == null) {
            throw new IllegalArgumentException(name + " should not be null");
        }
    }
}
