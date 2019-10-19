# Обектно-ориентирано програмиране с Java (част I)

## Virtual Wallet Application :credit_card:
Ще създадем приложение за управление на виртуални карти.
Нашият `виртулен портфейл` ще има следните прости функционалности:
- създаване на нова карта
- зареждане на конкретна карта с пари
- плащане с конкретна карта
- извличане на конкретна карта по име

:point_right: Той ще има възможност за съхранение на до 5 карти.

### Виртуален портфейл
За целта нека имаме интерфейс `VirtualWalletAPI`:
``` java
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

Създайте конкретен клас с име `VirtualWallet`, който да го имплементира:
``` java
package bg.sofia.uni.fmi.mjt.virtualwallet.core;

public class VirtualWallet implements VirtualWalletAPI {

    public VirtualWallet() {

    }
}
```
## Карти
:point_right: `VirtualWallet` ще поддържа два типа карти - `StandardCard` и `GoldenCard`.

:point_right: При плащане с `GoldenCard` ни се възвръщат 15% от цената на покупката. 


Класовете `StandardCard` и `GoldenCard` трябва да наследяват абстрактия клас `Card`:

``` java
package bg.sofia.uni.fmi.mjt.virtualwallet.core.card;

public abstract class Card {

    public Card(String name) {

    }
	
    public abstract boolean executePayment(double cost);

    public String getName() {

    }
	
    public double getAmount() {

    }
```

:point_right: Разликата в поведението между `StandardCard` и `GoldenCard` е отразена
чрез абстрактния метод <br>
   `boolean executePayment(double cost)`
## Плащане
:computer: Информацията за всяко плащане ще се съдържа в класа `PaymentInfo`:
``` java
package bg.sofia.uni.fmi.mjt.virtualwallet.core.payment;

public class PaymentInfo {
	
    public PaymentInfo(String reason, String location, double cost) {

    }
	
    public double getCost() {

    }
	
    public String getReason() {

    }
	
    public String getLocation() {

    }
```

## Валидация
:ballot_box_with_check: Обърнете внимание на методите от `VirtualWallet`-a и помислете кога биха връщали false. Направете подходяща валидация на параметрите, които тези методи приемат.
## Бонус задача
Съхранявайте последните 10 транзакции, извършени през виртуалния портфейл. Включете:
- името на картата, от която е била направена транзакцията (плащането)
- дата на транзакцията
- информация за самото плащане т.е `PaymentInfo`.

:point_right: Когато вече нямате свободно място за съхранение, премахвайте най-старата транзакция.

:point_right: За работа с дата и час може да използвате `java.time API`, обръщайки по-специално внимание на `LocalDateTime` класа и неговите методи.
## Пакети
Спазвайте имената на пакетите на всички по-горе описани класове, тъй като в противен случай решението ви няма да може да бъде тествано от грейдъра.
``` bash
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
