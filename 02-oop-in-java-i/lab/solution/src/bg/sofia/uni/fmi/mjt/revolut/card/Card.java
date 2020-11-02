package bg.sofia.uni.fmi.mjt.revolut.card;

import java.time.LocalDate;

public interface Card {

    /**
     * @return the type of the card: "PHYSICAL", "VIRTUALPERMANENT" or "VIRTUALONETIME"
     **/
    String getType();

    /**
     * @return the card's expiration date
     **/
    LocalDate getExpirationDate();

    /**
     * @return true if the PIN is correct and false otherwise
     **/
    boolean checkPin(int pin);

    /**
     * @return true if the card is blocked and false otherwise
     **/
    boolean isBlocked();

    /**
     * Blocks the card
     **/
    void block();

}
