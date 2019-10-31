package bg.sofia.uni.fmi.mjt.virtualwallet.core;

import bg.sofia.uni.fmi.mjt.virtualwallet.core.card.Card;
import bg.sofia.uni.fmi.mjt.virtualwallet.core.payment.PaymentInfo;

public interface VirtualWalletAPI {

    boolean registerCard(Card card);
    boolean executePayment(Card card, PaymentInfo paymentInfo);
    boolean feed(Card card, double amount);
    Card getCardByName(String name);
    int getTotalNumberOfCards();

}
