package bg.sofia.uni.fmi.mjt.virtualwallet.core;

import bg.sofia.uni.fmi.mjt.virtualwallet.core.card.Card;
import bg.sofia.uni.fmi.mjt.virtualwallet.core.payment.PaymentInfo;

public class VirtualWallet implements VirtualWalletAPI {

    private static final int MAX_CARDS = 5;

    private Card[] cards;
    private int numberOfCards;

    public VirtualWallet() {
        this.cards = new Card[MAX_CARDS];
    }

    @Override
    public boolean registerCard(Card card) {
        if (numberOfCards >= MAX_CARDS) {
            return false;
        }

        if (card == null || card.getName() == null) {
            return false;
        }

        if (retrieveCardByName(card.getName()) != null) {
            return false;
        }

        cards[numberOfCards++] = card;

        return true;
    }

    @Override
    public boolean executePayment(Card card, PaymentInfo payment) {
        if (card == null || card.getName() == null || payment == null) {
            return false;
        }

        if (payment.getCost() <= 0) {
            return false;
        }

        Card walletCard = findCard(card);

        if (walletCard == null) {
            return false;
        }

        return walletCard.executePayment(payment.getCost());
    }

    @Override
    public boolean feed(Card card, double amount) {
        if (card == null || card.getName() == null) {
            return false;
        }

        if (amount <= 0) {
            return false;
        }

        Card walletCard = findCard(card);

        if (walletCard == null) {
            return false;
        }

        walletCard.deposit(amount);
        return true;
    }

    @Override
    public Card getCardByName(String name) {
        return retrieveCardByName(name);
    }

    @Override
    public int getTotalNumberOfCards() {
        return numberOfCards;
    }

    private Card retrieveCardByName(String name) {
        for (int i = 0; i < numberOfCards; i++) {
            if ((cards[i].getName()).equals(name)) {
                return cards[i];
            }
        }

        return null;
    }

    private Card findCard(Card card) {
        for (int i = 0; i < numberOfCards; i++) {
            if (cards[i].equals(card)) {
                return cards[i];
            }
        }

        return null;
    }

}
