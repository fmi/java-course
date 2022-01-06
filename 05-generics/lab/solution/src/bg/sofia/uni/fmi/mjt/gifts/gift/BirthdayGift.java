package bg.sofia.uni.fmi.mjt.gifts.gift;

import bg.sofia.uni.fmi.mjt.gifts.person.Person;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;

public class BirthdayGift<T extends Priceable> implements Gift<T> {
    private final Person<?> sender;
    private final Person<?> receiver;
    private final Collection<T> items;
    private double price;

    public BirthdayGift(Person<?> sender, Person<?> receiver, Collection<T> items) {
        this.sender = sender;
        this.receiver = receiver;
        this.items = items;
        this.price = calculatePrice(items);
    }

    @Override
    public Person<?> getSender() {
        return sender;
    }

    @Override
    public Person<?> getReceiver() {
        return receiver;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void addItem(T t) {
        assertNotNull(t);

        price += t.getPrice();
        items.add(t);
    }

    @Override
    public boolean removeItem(T t) {
        if (t == null) {
            return false;
        }

        price -= t.getPrice();
        return items.remove(t);
    }

    @Override
    public Collection<T> getItems() {
        return Set.copyOf(items);
    }

    @Override
    public T getMostExpensiveItem() {
        if (items.isEmpty()) {
            return null;
        }

        return Collections.max(items, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return Double.compare(o1.getPrice(), o2.getPrice());
            }
        });
    }

    private double calculatePrice(Collection<T> items) {
        double result = 0;

        for (T item : items) {
            result += item.getPrice();
        }

        return result;
    }

    private void assertNotNull(T t) {
        if (t == null) {
            throw new IllegalArgumentException("");
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, price, receiver, sender);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BirthdayGift other = (BirthdayGift) obj;
        return Objects.equals(items, other.items)
            && Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
            && Objects.equals(receiver, other.receiver) && Objects.equals(sender, other.sender);
    }

}
