# Обектно-ориентирано програмиране с Java (част I)

## Virtual Wallet Application :credit_card:

Ще създадем приложение за управление на виртуални карти.
Нашият `виртуален портфейл` ще има следните прости функционалности:
- създаване на нова карта
- съхраняване на до 5 карти
- зареждане на конкретна карта с пари
- плащане с конкретна карта
- извличане на конкретна карта по име

### Виртуален портфейл

За целта ще създадем интерфейс `VirtualWalletAPI`:

```java
package bg.sofia.uni.fmi.mjt.virtualwallet.core;

public interface VirtualWalletAPI {
    /**
    * Registers a new card in the wallet.
    * Returns true if the operation is successful and false
    * if there is already a card with the same name.
    **/
    boolean registerCard(Card card);

    /**
    * Executes a payment from the given card. Returns true
    * if the operation is successful and false otherwise.
    **/
    boolean executePayment(Card card, PaymentInfo paymentInfo);

    /**
    * Feeds the given card with @amount money. Returns true
    * if the operation is successful and false otherwise.
    **/
    boolean feed(Card card, double amount);

     /**
    * Returns a card from the wallet with the given name.
    * If there is no such card, returns null.
    **/
    Card getCardByName(String name);

    /**
    * Returns the current number of registered cards in the wallet.
    **/
    int getTotalNumberOfCards();

}
```

- Помислете, кога операциите `executePayment`, `feed` и `registerCard` могат да са неуспешни, т.е да връщат `false`.<br>
- С какви подадени параметри можем да 'счупим' нашия `VirtualWallet`?

След като сме дефинирали 'спецификацията' на нашия виртуален портфейл, ще създадем конкретен клас `VirtualWallet`, който да го имплементира:

```java

package bg.sofia.uni.fmi.mjt.virtualwallet.core;

public class VirtualWallet implements VirtualWalletAPI {

    public VirtualWallet() {
        // Implement logic
    }

}
```

## Карти

Създаденият `VirtualWallet` ще поддържа два типа карти - стандартни и златни, съответно имплементирани от класовете `StandardCard` и `GoldenCard`. 
- При всяко плащане със златна карта, тя ще ни връща 15% от цената на покупката. За да изпълним дадено плащане трябва първо да имаме първоначалната сума на покупката (преди да приложим 'бонуса' от 15%)

Двата класа `StandardCard` и `GoldenCard` ще наследяват абстрактния клас `Card`:

```java
package bg.sofia.uni.fmi.mjt.virtualwallet.core.card;

public abstract class Card {

    public Card(String name) {
        // Implement logic
    }

    public abstract boolean executePayment(double cost);

    public String getName() {
        // Implement logic
    }

    public double getAmount() {
        // Implement logic
    }

}
```

Разликата в поведението между двете карти, отразяваме чрез абстрактния метод `executePayment`

## Плащане

Информацията за всяко плащане ще съхраняваме в класа `PaymentInfo`:

```java
package bg.sofia.uni.fmi.mjt.virtualwallet.core.payment;

public class PaymentInfo {

    public PaymentInfo(String reason, String location, double cost) {
        // Implement logic
    }

    public double getCost() {
        // Implement logic
    }

    public String getReason() {
        // Implement logic
    }

    public String getLocation() {
        // Implement logic
    }

}
```

## Бонус задача

Съхранявайте последните 10 транзакции, извършени през виртуалния портфейл. Всяка транзакция трябва да съдържа:
- името на картата, от която е била направена транзакцията (плащането)
- дата на транзакцията
- информация за самото плащане т.е. `PaymentInfo`.

- Когато вече нямате свободно място за съхранение, премахвайте най-старата транзакция от масива.
- За работа с дата и час, може да използвате `java.time API`, обръщайки по-специално внимание на `LocalDateTime` класа и неговите методи.

## Пакети

Спазвайте имената на пакетите на всички по-горе описани класове, тъй като в противен случай, решението ви няма да може да бъде тествано от грейдъра.

```bash
src
╷
└─ bg/sofia/uni/fmi/mjt/virtualwallet/core
   └─ VirtualWalletAPI.java
   ├─ VirtualWallet.java
   └─ (...)
   └─ card/
      └─ Card.java
      └─ StandardCard.java
      └─ GoldenCard.java
      └─ (...)
   └─ payment/
      └─ PaymentInfo.java
      └─ (...)
```

## Забележки

За да тествате решението си в grader.sapera.org, архивирайте в `zip` цялата `src` папка на проекта.
