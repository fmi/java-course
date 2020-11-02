package bg.sofia.uni.fmi.mjt.revolut;

import bg.sofia.uni.fmi.mjt.revolut.account.Account;
import bg.sofia.uni.fmi.mjt.revolut.card.Card;

public interface RevolutAPI {

    /**
     * Executes a card payment using a POS terminal
     *
     * @param card the card used for the payment. Only physical cards are accepted
     * @param pin 4-digit PIN
     * @param amount the amount paid
     * @param currency the currency of the payment ("BGN" or "EUR")
     * @return true, if the operation is successful and false otherwise.
     *         Payment is successful, if the card is available in Revolut, valid, unblocked,
     *         the specified PIN is correct and an account with sufficient amount in the specified currency exists.
     *         In case of three consecutive incorrect PIN payment attempts, the card should be blocked.
     **/
    boolean pay(Card card, int pin, double amount, String currency);

    /**
     * Executes an online card payment
     *
     * @param card the card used for the payment. Any type of card is accepted
     * @param pin 4-digit PIN
     * @param amount the amount paid
     * @param currency the currency of the payment ("BGN" or "EUR")
     * @param shopURL the shop's domain name. ".biz" top level domains are currently banned and payments should be rejected
     * @return true, if the operation is successful and false otherwise.
     *         Payment is successful, if the card is available in Revolut, valid, unblocked,
     *         the specified PIN is correct and an account with sufficient amount in the specified currency exists.
     *         In case of three consecutive incorrect PIN payment attempts, the card should be blocked.
     **/
    boolean payOnline(Card card, int pin, double amount, String currency, String shopURL);

    /**
     * Adds money to a Revolut account
     *
     * @param account the account to debit
     * @param amount the amount to add to the account, in the @account's currency
     * @return true, if the acount exists in Revolut and false otherwise
     **/
    boolean addMoney(Account account, double amount);

    /**
     * Transfers money between accounts, doing currency conversion, if needed.
     * The official fixed EUR to BGN exchange rate is 1.95583.
     *
     * @param from the account to credit
     * @param to the account to debit
     * @param amount the amount to transfer, in the @from account's currency
     * @return true if both accounts exist and are different (with different IBANs) and false otherwise
     **/
    boolean transferMoney(Account from, Account to, double amount);

    /**
     * Returns the total available amount
     *
     * @return The total available amount (the sum of amounts for all accounts), in BGN
     **/
    double getTotalAmount();

}
