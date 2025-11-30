# Amazon Trends Analyzer :chart_with_upwards_trend: :package:

"Черният петък" мина, но работата за анализаторите на данни едва сега започва. Amazon са генерирали огромно количество поръчки и имат нужда от система, която да извлече статистики за потребителското поведение, най-продаваните продукти и ефективността на разплащателните методи.

Тази седмица ще приложим знанията си за работа с файлове, входно-изходни потоци, ламбда изрази и [Java Stream API-то](https://docs.oracle.com/en/java/javase/25/docs/api//java.base/java/util/stream/package-summary.html), за да създадем **Amazon Trends Analyzer**, който ще анализира потребителското поведение и продажбите в платформата. Анализът ще се базира на реален *dataset*, съдържащ данни за поръчки от Amazon, който е взет от [kaggle](https://www.kaggle.com/datasets/zahidmughal2343/amazon-sales-2025), онлайн платформата за machine learning и data science на Google.

Всеки ред от файла съдържа информация за една поръчка, като полетата са разделени със запетая (CSV формат):

`Order ID,Date,Product,Category,Price,Quantity,Total Sales,Customer Name,Customer Location,Payment Method,Status`   

## Зареждане и обработка на данните

Преди да започнем със същинската част на анализа на *dataset*-a, ще заредим данните в паметта.

Една от първите стъпки в задачите за анализ на данни, винаги е "изчистване/подготовка" на данните и превръщането им в обектен модел.

Имплементирайте record-a `Order`, който има следните компоненти:
`(String id, LocalDate date, String product, Category category, double price, int quantity, double totalSales, String customerName, String customerLocation, PaymentMethod paymentMethod, Status status)` и има публичен статичен factory метод със сигнатура

`public static Order of(String line)`

Методът приема низ, представляващ един ред от dataset-а (в CSV формат) и връща съответния `Order` обект. Всеки `Order` се идентифицира еднозначно от своя идентификатор (`id`).

👉 Подсказка: Датите в dataset-a са във формат dd-MM-yy (напр. 14-03-25). За парсването им ще ви влезе в употреба [`DateTimeFormatter.ofPattern("dd-MM-yy")`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/time/format/DateTimeFormatter.html), заедно с [`LocalDate.parse(...)`](https://docs.oracle.com/en/java/javase/25/docs/api//java.base/java/time/LocalDate.html#parse(java.lang.CharSequence)).


### Категории продукти

Продуктите в поръчките спадат към една от няколко категории, описани от следния enum в пакета `bg.sofia.uni.fmi.mjt.order.domain`:

```java
public enum Category {
    ELECTRONICS, CLOTHING, FOOTWEAR, HOME_APPLIANCES, BOOKS
}
```

### Методи за плащане

Начините на плащане се моделират чрез следния enum в пакета `bg.sofia.uni.fmi.mjt.order.domain`:

```java
public enum PaymentMethod {
    CREDIT_CARD, DEBIT_CARD, PAYPAL, AMAZON_PAY, GIFT_CARD
}
```

### Статус на поръчка

Текущото състояние на поръчката се описва от следния enum в пакета `bg.sofia.uni.fmi.mjt.order.domain`:

```java
public enum Status {
    COMPLETED, PENDING, CANCELLED
}
```

## OrderLoader

В пакета `bg.sofia.uni.fmi.mjt.order.loader` създайте клас `OrderLoader`, който имплементира логиката за зареждане на данните. Вашата задача е да довършите имплементацията на метода `load`:

```java
package bg.sofia.uni.fmi.mjt.order.loader;

import bg.sofia.uni.fmi.mjt.order.domain.Order;

import java.io.Reader;
import java.util.List;

public class OrderLoader {

    /**
     * Returns a list of orders read from the source Reader.
     *
     * @param reader the Reader with orders
     * @throws IllegalArgumentException if the reader is null
     */
    public static List<Order> load(Reader reader) {
        throw new UnsupportedOperationException("Please implement this method");
    }
}
```

## Amazon Trends Analyzer

В пакета `bg.sofia.uni.fmi.mjt.order.analyzer` създайте клас `OrderAnalyzerImpl`, който има публичен конструктор `OrderAnalyzerImpl(List<Order> orders)` и имплементира интерфейса `OrderAnalyzer`:

```java
package bg.sofia.uni.fmi.mjt.order.analyzer;

import bg.sofia.uni.fmi.mjt.order.domain.Category;
import bg.sofia.uni.fmi.mjt.order.domain.Order;
import bg.sofia.uni.fmi.mjt.order.domain.PaymentMethod;
import bg.sofia.uni.fmi.mjt.order.domain.Status;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Provides analytical operations over a collection of {@link Order} objects.
 * <p>
 * This interface exposes various utility methods for querying, grouping,
 * and extracting insights from order data.
 */
public interface OrderAnalyzer {

    /**
     * Returns an immutable copy of all orders.
     *
     * @return the list of all orders
     */
    List<Order> allOrders();

    /**
     * Returns an immutable list of all orders placed by the given customer.
     *
     * @param customer the customer name (case-sensitive) to filter by
     * @return a list of orders made by the specified customer,
     * or an empty list if none exist
     * @throws IllegalArgumentException if customer is null or blank
     */
    List<Order> ordersByCustomer(String customer);

    /**
     * Returns the date on which the most orders were placed and their count.
     * In case of a tie, return the earliest of the dates with equal number of orders.
     *
     * @return the date with the highest number of orders and the count of the orders,
     * or null if there are no orders
     */
    Map.Entry<LocalDate, Long> dateWithMostOrders();

    /**
     * Returns the top N most frequently ordered products, where frequency is
     * the number of orders containing the product (not the sum of ordered quantities).
     * If two products have the same number of orders, sort them alphabetically.
     *
     * @param n the number of products to return. If n is 0, returns an empty list.
     *          If n exceeds the number of distinct products, returns all of them.
     * @return a list of product names ordered by frequency
     * @throws IllegalArgumentException if n < 0
     */
    List<String> topNMostOrderedProducts(int n);

    /**
     * Computes the total revenue for each {@link Category} present in the dataset.
     * Revenue is defined as the sum of {@link Order#totalSales()} for all orders
     * in that category.
     *
     * @return a map from category to total revenue
     */
    Map<Category, Double> revenueByCategory();

    /**
     * Identifies customers whose ordering behavior is suspicious.
     * A customer is suspicious if they have more than 3 orders that are both:
     * <ul>
     *   <li>with status {@link Status#CANCELLED}</li>
     *   <li>with total sales value < 100.0</li>
     * </ul>
     *
     * @return a set of suspicious customer names
     */
    Set<String> suspiciousCustomers();

    /**
     * Determines the most frequently used {@link PaymentMethod}
     * for each product category in the dataset. In case the dataset is empty,
     * returns an empty Map.
     * In case of a tie for a category, return the {@link PaymentMethod}
     * whose name comes first alphabetically.
     *
     * @return a map from category to its most frequently used payment method
     */
    Map<Category, PaymentMethod> mostUsedPaymentMethodForCategory();

    /**
     * Determines the customer location that has the most orders.
     * If multiple locations tie, return the one that is alphabetically smallest.
     *
     * @return the location with the most orders, or null if there are no orders
     */
    String locationWithMostOrders();

    /**
     * Groups orders first by {@link Category}, and within each category
     * by {@link Status}, counting how many orders fall into each group.
     * In case the dataset is empty, returns an empty Map.
     *
     * @return a map where each category maps to another map
     * from status to the count of orders with that status
     */
    Map<Category, Map<Status, Long>> groupByCategoryAndStatus();
}
```

Вашата задача е да имплементирате методите на интерфейса, използвайки Java Stream API-то.

👉 Обърнете внимание, че dataset-ът съдържа заглавен ред, който трябва да пропуснете при изчитането му.

👉 Напомняне: когато сравнявате числа с плаваща запетая, не разчитайте на точно равенство, а работете с равенство с определена точност (делта).

### Примерна употреба

Ето един прост пример, как може да се използва Order Analyzer-a:

```java
import bg.sofia.uni.fmi.mjt.order.analyzer.OrderAnalyzerImpl;
import bg.sofia.uni.fmi.mjt.order.loader.OrderLoader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String filePath = "amazon_sales_2025.csv";

        Reader reader = new FileReader(filePath);

        var orders = OrderLoader.load(reader);
        System.out.println(orders.size());

        var analyzer = new OrderAnalyzerImpl(orders);

        System.out.println(analyzer.suspiciousCustomers());
    }
}
```

### Тестване

Най-добре първо тествайте реализацията си локално с пример като горния. После създайте и unit тестове.

### Пакети

Спазвайте имената на пакетите на всички по-горе описани класове, тъй като в противен случай решението ви няма да може да бъде тествано от грейдъра.

```java
src
└─ bg.sofia.uni.fmi.mjt.order
    ├── analyzer
    │     ├── OrderAnalyzer.java
    │     ├── OrderAnalyzerImpl.java
    │     └── (...)
    ├── domain
    │     ├── Category.java
    │     ├── Order.java
    │     ├── PaymentMethod.java
    │     ├── Status.java
    │     └── (...)
    ├── loader
    │     ├── OrderLoader.java
    │     └── (...)
    └── (...)
test
└─ bg.sofia.uni.fmi.mjt.order
    └── (...)
```

### :warning: Забележки 
- В грейдъра качете директориите src и test като ги селектирате и двете. Друг вариант е да ги сложите в общ .zip архив и да качите него
- Не качвайте jar-ките на JUnit и Mockito библиотеките. На грейдъра ги има, няма смисъл решението ви да набъбва излишно.

Успех!
