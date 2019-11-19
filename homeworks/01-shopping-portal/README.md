# Домашно 1

## **Shopping Portal** :department_store:

`Краен срок: 27.11.2019, 23:45`

Ще създадем справочник за online пазаруване, който ще позволява сравняване на цени на различни оферти за продукти, намиране на най-добрите оферти за даден продукт, преглед на пълната информация за цената на даден продукт във времето и добавяне на нова оферта за продукт. 

Създайте клас `ShoppingDirectoryImpl`, който имплементира следния интерфейс:

``` java
package bg.sofia.uni.fmi.mjt.shopping.portal;

public interface ShoppingDirectory {

    /**
     * Returns a collection of offers submitted in the last 30 days
     * for the product with name @productName sorted by total price 
     * in ascending order. 
     * 
     * @throws ProductNotFoundException if there is no product with name @productName
     * @throws IllegalArgumentException if @productName is null
     */
    Collection<Offer> findAllOffers(String productName) throws ProductNotFoundException;
    
    /**
     * Returns the most favorable offer for the product with name @productName 
     * submitted in the last 30 days - the one with lowest total price. 
     * 
     * @throws ProductNotFoundException if there is no product with name @productName
     * @throws NoOfferFoundException if there is no offer submitted in the last 30 
     *     days for the product with name @productName
     * @throws IllegalArgumentException if @productName is null
     */
    Offer findBestOffer(String productName) throws ProductNotFoundException, NoOfferFoundException;
    
    /**
     * Returns a collection of price statistics for the product with name @productName 
     * sorted by date in descending order.
     * 
     * @throws ProductNotFoundException if there is no product with name @productName
     * @throws IllegalArgumentException if @productName is null
     */
    Collection<PriceStatistic> collectProductStatistics(String productName) throws ProductNotFoundException;
    
    /**
     * Submits a new offer.
     * 
     * @throws OfferAlreadySubmittedException if an identical @offer has already been submitted
     * @throws IllegalArgumentException if @offer is null
     */
    void submitOffer(Offer offer) throws OfferAlreadySubmittedException;
    
}
```
:exclamation: Обърнете внимание, че текущият ден също е част от последните 30 дни.

### **Оферти**

Офертите в нашия справочник трябва да имплементират интерфейса `Offer`. Ще имаме два вида оферти - `RegularOffer` и `PremiumOffer`.
* `RegularOffer` ще съдържа дата, на която е направена офертата, име, описание, цена и цена за доставка за продукт. `RegularOffer` трябва да има публичен конструктор с параметри:

```java
public RegularOffer(String productName, LocalDate date, String description, double price, double shippingPrice)
```

* `PremiumOffer`, освен атрибутите на обикновената, ще съдържа в себе си и процент на отстъпка. Той трябва да е закръглен до втория знак след десетичната запетая и да е в интервала _[0.00, 100.00]_. Отстъпката се прилага само върху общата цена на продукта. `PremiumOffer` трябва да има публичен конструктор с параметри:

```java
public PremiumOffer(String productName, LocalDate date, String description, double price, double shippingPrice, double discount)
```

* _Обща цена_ на продукт наричаме сумата от цената на продукта и цената за неговата доставка.

* Две оферти се различват по _име на продукт, дата и обща цена на продукта_.

* Името на продукт е _case insensitive_, с други думи, при сравнение на две имена, разликата в капитализацията на буквите се пренебрегва.

* За представяне на датата на оферта може да използвате класа [LocalDate](https://docs.oracle.com/en/java/javase/13/docs/api/java.base/java/time/LocalDate.html) от пакета [java.time](https://docs.oracle.com/en/java/javase/13/docs/api/java.base/java/time/package-summary.html).


```java
package bg.sofia.uni.fmi.mjt.shopping.portal.offer;

public interface Offer {

    /**
     * Returns the name of the product for which 
     * the offer is made.
     */
    String getProductName();
    
    /**
     * Returns the date of the offer.
     */
    LocalDate getDate();
    
    /**
     * Returns a short description of the offer.
     */
    String getDescription();
    
    /**
     * Returns the offer's price for the product.
     */
    double getPrice();
    
     /**
     * Returns the offer's shipping price for the product.
     */
    double getShippingPrice();
    
    /**
     * Returns the offer's total price (equal to the price plus shipping price).
     */
    double getTotalPrice();

}
```

### **Статистики**

Тъй като за всеки потребител е важно да се информира за това, как цените от офертите за даден продукт са се изменяли във времето, нашият справочник ще съхранява статистики за офертите, направени за даден продукт. Статистиките са дневни и се изчисляват на база на всички оферти, направени за продукта, за конкретен ден.

```java
package bg.sofia.uni.fmi.mjt.shopping.portal;

public class PriceStatistic {
    
    /**
     * Returns the date for which the statistic is
     * collected.
     */
    public LocalDate getDate() {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Returns the lowest total price from the offers 
     * for this product for the specific date.
     */
    public double getLowestPrice() {
        throw new UnsupportedOperationException();
    }
    
   /**
     * Return the average total price from the offers 
     * for this product for the specific date.
     */
    public double getAveragePrice() {
        throw new UnsupportedOperationException();
    }

}
```

### **Бързодействие**

:exclamation: Обърнете внимание, че в нашия справочник може да има хиляди и дори милиони продукти, както и че за всеки продукт може да има направени също толкова много оферти. Вашето решение трябва ефективно да се справя с обработката на голямо количество данни.

:exclamation: Решения, използващи [Java Stream API](https://docs.oracle.com/en/java/javase/13/docs/api/java.base/java/util/stream/package-summary.html), няма да се приемат за това домашно. Имайте търпение, скоро ще се запознаем и с това API, а дотогава, старайте се да научите API-тата на Collections framework-а.


### **Пакети**

Спазвайте имената на пакетите на всички по-горе описани класове, тъй като в противен случай решението ви няма да може да бъде тествано от грейдъра.

```bash
src
╷
└─ bg/sofia/uni/fmi/mjt/shopping/portal/
   └─ PriceStatistic.java
   └─ ShoppingDirectory.java
   └─ ShoppingDirectoryImpl.java
   └─ (...)
   bg/sofia/uni/fmi/mjt/shopping/portal/offer/
   └─ Offer.java
   └─ RegularOffer.java
   └─ PremiumOffer.java
   bg/sofia/uni/fmi/mjt/shopping/portal/exceptions/
      └─ OfferAlreadySubmittedException.java
      └─ NoOfferFoundException.java
      └─ ProductNotFoundException.java
      └─ (...)
```

### **Предаване**

За да предадете решението си, архивирайте в **zip** цялата **src** папка на проекта и я качете в [grader.sapera.org](http://grader.sapera.org/WebObjects/Web-CAT.woa).

### **Оценяване**

Решението може да ви донесе до 100 точки, като ще бъде оценявано за:

* функционална пълнота и коректност: чрез автоматични тестове (80% от оценката)
* добър обектно-ориентиран дизайн и спазване на правилата за чист код (20% от оценката: 10% от инструментите за статичен код анализ на грейдъра и 10% от code review от асистент)

### **Желаем ви успех!**
