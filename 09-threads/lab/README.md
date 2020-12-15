# Многонишково програмиране

## MJT Dining Place :stew: :pizza: :spaghetti:

След lockdown-a, по празниците, всички ще се отпуснат и втурнат по ресторанти, барове и клубове. Очаква се, софиянци и гости на столицата да заложат на заведения с вкусна храна и качествено обслужване, като реномирания ресторант *MJT Dining Place*.

За да помогнем на персонала в подготовката за дългоочакваното откриване, ще се опитаме да симулираме с помощта на Java нишки едновременното обслужване на много клиенти и да тестваме лимитите на кухнята на ресторанта, в която работят множество готвачи.

За разлика от street food местата, където клиентите чакат на опашка и поръчват един по един, в ресторантите клиентите биват обслужвани паралелно, като всеки си избира и поръчва ястие от менюто. MJT Dining Place е стъпка напред в бъдещето - като концептуален ресторант, собственост на програмисти, и придържайки се към "Modern" в името си, ресторантът е оборудван с електронна система и клиентите избират и поръчват директно от таблети, вградени в масите, като поръчките пристигат в кухнята и се приготвят паралелно от множество готвачи.

## Условие

Задачата е разделена на 7 стъпки, като препоръката ни е да започнете с проста частична имплементация, която да допълните и подобрите впоследствие.

**1. Създайте клас `MJTDiningPlace`**

Класът трябва да има публичен конструктор, който приема цяло число (броя на готвачите в ресторанта) и имплементира интерфейса `Restaurant`:

```java
package bg.sofia.uni.fmi.mjt.restaurant;

public interface Restaurant {

    /**
     * Adds an order.
     **/
    void submitOrder(Order order);

    /**
     * Returns the next order to be cooked
     * and removes it from the pending orders
     **/
    Order nextOrder();

    /**
     * Returns the total number of submitted orders.
     **/
    int getOrdersCount();

    /**
     * Returns the restaurant's chefs.
     **/
    Chef[] getChefs();

    /**
     * Prepares the restaurant for closing. When this method is called,
     * the chefs complete any pending orders and then finish work.
     **/
    void close();

}

```

:star: Забележкa:

- Използвайте подходяща структура от данни, в която да съхранявате подадените, но все още неизпълнени поръчки.

**2. Създайте `record Order(Meal meal, AbstractCustomer customer)`**

Той описва поръчка от клиент, като приемаме, че една поръчка съдържа точно едно ястие.

**3. Създайте абстрактен клас `Customer`, който е нишка, и има следния вид:**

```java
package bg.sofia.uni.fmi.mjt.restaurant.customer;

public abstract class AbstractCustomer extends Thread {

    public AbstractCustomer(Restaurant restaurant) {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    public abstract boolean hasVipCard();

}

```

Има два вида клиенти, моделирани от класовете `Customer` и `VipCustomer`, наследяващи `AbstractCustomer` и имащи публичен конструктор, приемащ параметър тип `Restaurant`. Поръчките на VIP клиентите (притежатели на VIP карта) се обслужват с приоритет спрямо тези на обикновените клиенти, като отделно ресторантът подрежда VIP и обикновените поръчки като ред на изпълнение спрямо времето (в низходящ ред), необходимо за приготвяне на даденото ястие.

  Например, при следните поръчки:

  - Поръчка 1. VIP, време за приготвяне 30 минути
  - Поръчка 2. време за приготвяне 20 минути
  - Поръчка 3. време за приготвяне 40 минути
  - Поръчка 4. VIP, време за приготвяне 10 минути

редът на приготвяне следва да бъде 1, 4, 3 и 2.

Създаването и стартирането на клиентите може да направите във ваш клас, в който да реализирате симулацията и да я тествате ръчно, а също и в автоматичните тестове.
Всеки клиент избира ястие от менюто. Това отнема известно време, което можем да моделираме с паузиране на нишката - клиент за случаен отрязък от 1 до 5 секунди, след което клиентът поръчва избраното ястие.

Ястията от менюто на ресторанта са описани в `enum`-a `Meal`:

```java
package bg.sofia.uni.fmi.mjt.restaurant;

import java.util.Random;

public enum Meal {

    PIZZA("Pizza", 20), MUSAKA("Musaka", 30), SALAD("Salad", 5), SPAGHETTI("Spaghetti", 25);

    private static final Meal[] ALL_MEALS = Meal.values();
    private static final Random MEAL_RANDOM = new Random();

    private final String name;
    private final int cookingTime;

    private Meal(String type, int cookingTime) {
        this.name = type;
        this.cookingTime = cookingTime;
    }

    public String getName() {
        return name;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public static Meal chooseFromMenu() {
        return ALL_MEALS[MEAL_RANDOM.nextInt(ALL_MEALS.length)];
    }

}
```

**4. Създайте клас `Chef`, който е нишка, и има следния вид:**

```java
package bg.sofia.uni.fmi.mjt.restaurant;

public class Chef extends Thread {

    private final int id;
    private final Restaurant restaurant;

    public Chef(int id, Restaurant restaurant) {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    /**
     * Returns the total number of meals that this chef has cooked.
     **/
    public int getTotalCookedMeals() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

}
```

  Задачата на готвачите е да приготвят ястията, поръчани от клиентите:

  1. Взимане на поръчка за приготвяне (в реда, определен от правилата на ресторанта и описан по-горе)
  2. Приготвяне на ястието. Времето за приготвяне зависи от типа на ястието (т.е. `getCookingTime()` от `Meal`) - ще го симулираме отново с паузиране на нишката - готвач за съответния брой милисекунди.

:star: Забележка:

- В даден момент (докато ресторантът не е затворен), може да няма непоети поръчки за приговяне. В такъв случай, готвачите без работа изчакват да се появи нова поръчка.

**5. Създайте инстанция на `MJTDiningPlace` и пуснете клиенти**

- Готвачите се създават и започват работа при създаването на ресторанта.
- Готвачите получават уникални `auto-increment` ID-та, започващи от 0.
- След това, създайте и пуснете едновременно N клиента.
- Изчакайте всички клиенти да приключат с поръчването на ястие.

:star: Локално тестване:

- Уверете се, че броят на всички поръчки, получени в кухнята на ресторанта, е коректен (т.е. равен на N)

**6. Тествайте локално решението ви**

- В основната нишка, изчакайте даден брой секунди за настъпване на края на работното време на ресторанта.
- След това, извикайте метода `close()` на ресторанта.
- Всички ястия, поръчани преди извикването на `close()`, трябва да бъдат приготвени.
- След като всеки готвач приключи работа (т.е дошъл е краят на работния ден и всички поръчки са изпълнени), изведете на конзолата ID-то на готвача и броя на приготвените от него ястия (за ваши тестови цели).

- Уверете се, че броят на всички поръчки в ресторанта е коректен
- Уверете се, че всички ястия, които са поръчани, са и приготвени (т.е. всички поръчки са изпълнени)

**7. Изполвайте насоките от горната точка за създаване на unit тестове**

## Структура на проекта

```bash
src
╷
└─ bg/sofia/uni/fmi/mjt/restaurant/
   ├─ Chef.java
   ├─ Meal.java
   ├─ MJTDiningPlace.java
   ├─ Order.java
   ├─ Restaurant.java
   └─ (...)
└─ bg/sofia/uni/fmi/mjt/restaurant/customer
   ├─ AbstractCustomer.java
   ├─ Customer.java
   ├─ VipCustomer.java
   └─ (...)
test
╷
└─ bg/sofia/uni/fmi/mjt/restaurant/
   ├─ MJTDiningPlaceTest.java
   └─ (...)
```

В [sapera.org](http://grader.sapera.org/) качете `zip` архив на `src` и `test` директориите.
