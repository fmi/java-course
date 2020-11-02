package bg.sofia.uni.fmi.mjt.revolut.card;

import java.time.LocalDate;
import java.util.Objects;

public abstract class BaseCard implements Card {
    private static final int MAX_PIN_ATTEMPTS = 3;

    private String number;
    private int pin;

    private LocalDate expirationDate;

    private boolean isBlocked;
    private int incorrectPinAttempts;

    public BaseCard(String number, int pin, LocalDate expirationDate) {
        this.number = number;
        this.pin = pin;
        this.expirationDate = expirationDate;
    }

    public abstract String getType();

    public boolean checkPin(int pin) {
        if (this.pin != pin) {
            if(++incorrectPinAttempts >= MAX_PIN_ATTEMPTS) {
                isBlocked = true;
            }
            return false;
        }

        incorrectPinAttempts = 0;
        return true;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void block() {
        isBlocked = true;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseCard)) return false;
        BaseCard card = (BaseCard) o;
        return this.number.equals(card.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.number);
    }

}
