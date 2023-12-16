# Мрежово програмиране

## T-Shirt Shop :shirt: :department_store:

*Производството по заявка* направи революция в света на персонализираните облекла. Ще си направим бизнес за продажба на тениски в духа на този иновативен бизнес модел.
Нека помогнем на бъдещите ни клиенти, като създадем приложение, с което да правят поръчки за различни видове тениски. Ще го разработим като клиент-сървър приложение, като сървърът ще съхранява и менажира поръчките, а потребителите ще използват клиента, за да комуникират със сървъра и да правят и вземат информация за поръчки.

### T-Shirt Shop Client

- Всеки потребител може да прави поръчки за тениски
- Потребителите също могат да разглеждат поръчки за тениски (всички поръчки, всички успешни поръчки или по id)
- Потребителите могат да прекратят връзката си към сървъра по всяко време

### T-Shirt Shop Server

- Сървърът трябва да може да обслужва множество клиенти едновременно
- Сървърът получава команди от клиентите и връща подходящ резултат

### Описание на клиентските команди

```bash
- request size=<t-shirt size> color=<t-shirt color> shipTo=<t-shirt destination> - изпраща заявка за създаване на нова поръчка на тениска
  с дадените размер, цвят и дестинация за доставка. Опциите се изброяват на командния ред като
  се разделят с whitespace.

- get
  - get all – връща списък с всички поръчки за тениски
  - get all-successful – връща списък с всички успешни поръчки за тениски
  - get my-order id=<id> – връща поръчка на тениска с даденото id

- disconnect – потребителят прекратява връзката си със сървъра
```

### Пример

```bash
# 1. Start a t-shirt request client

# 2 Request T-Shirts
# 2.1. Create a new request for t-shirt with valid options
=> request size=L color=BLACK shipTo=EUROPE
=> {"status":"CREATED", "additionalInfo":"ORDER_ID=1"}

# 2.2. Create a new request for t-shirt with invalid t-shirt size
=> request size=K color=BLACK shipTo=EUROPE
=> {"status":"DECLINED", "additionalInfo":"invalid:size"}

# 2.3. Create a new request for t-shirt with invalid color
=> request size=L color=ORANGE shipTo=EUROPE
=> {"status":"DECLINED", "additionalInfo":"invalid:color"}

# 2.4. Create a new request for t-shirt with invalid destination
=> request size=L color=BLACK shipTo=NARNIA
=> {"status":"DECLINED", "additionalInfo":"invalid:destination"}

# 2.5. Create a new request for a t-shirt with multiple invalid parameters
=> request size=K color=ORANGE shipTo=NARNIA
=> {"status":"DECLINED", "additionalInfo":"invalid:size,color,destination"}

# 3 Get T-Shirts
# 3.1. get all requested t-shirts (valid and invalid)
=> get all
=> {"status":"OK", "orders":[{"id":1, "tShirt":{"size":"L", "color":"BLACK"}, "destination":"EUROPE"},
{"id":-1, "tShirt":{"size":"UNKNOWN", "color":"BLACK"}, "destination":"EUROPE"},
{"id":-1, "tShirt":{"size":"L", "color":"UNKNOWN"}, "destination":"EUROPE"},
{"id":-1, "tShirt":{"size":"L", "color":"BLACK"}, "destination":"UNKNOWN"},
{"id":-1, "tShirt":{"size":"UNKNOWN", "color":"UNKNOWN"}, "destination":"UNKNOWN"}]}
 
# 3.2. Get all successfully executed t-shirt orders
=> get all-successful
=> {"status":"OK", "orders":[{"id":1, "tShirt":{"size":"L", "color":"BLACK"}, "destination":"EUROPE"}]}
 
# 3.3. Get order by id
=> get my-order id=1
=> {"status":"OK", "orders":[{"id":1, "tShirt":{"size":"L", "color":"BLACK"}, "destination":"EUROPE"}]}

# 3.3. Get order by id
=> get my-order id=2
=> {"status":"NOT_FOUND", "additionalInfo":"Order with id = 2 does not exist."}
 
# 4. Random
=> some random commands
=> Unknown command
 
# 5. Disconnect from the server
=> disconnect
=> Disconnected from the server
```

### Дизайн

Един от принципите на чистия дизайн е разделянето на бизнес логиката от входно-изходната логика. Ако разгледаме сървърната част, бизнес логиката е поддържането на базата с поръчки и операциите върху тях, а входно-изходната логика е мрежовата комуникация с клиентите. Подобно разделение прави кода по-четим и лесен за разширение, а също и значително улеснява тестването на бизнес логиката в изолация от мрежовата комуникация.

Реализирайте бизнес логиката на сървъра чрез класа `MJTOrderRepository`, който има публичен конструктор по подразбиране и имплементира следния интерфейс:

```java
public interface OrderRepository {
    /**
     * Creates a new T-Shirt order
     *
     * @param size        - size of the requested T-Shirt
     * @param color       - color of the requested T-Shirt
     * @param destination - destination of the requested T-Shirt
     *
     * @return response which contains status and additional info (orderId or invalid parameters if there are such)
     */
    Response request(String size, String color, String destination);

    /**
     * Retrieves a T-Shirt order with the given id
     *
     * @param id order id
     * @return response which contains status and an order with the given id
     */
    Response getOrderById(int id);

    /**
     * Retrieves all T-Shirt orders
     *
     * @return response which contains status and  all T-Shirt orders from the repository, in undefined order.
     * If there are no orders in the repository, returns an empty collection.
     */
    Response getAllOrders();

    /**
     * Retrieves all successful orders for T-Shirts
     *
     * @return response which contains status and all successful orders for T-Shirts from the repository, in undefined order.
     * If there are no such orders in the repository, returns an empty collection.
     */
    Response getAllSuccessfulOrders();
}
```

## Order & TShirt

Поръчка ще моделираме чрез record-а `Order(int id, TShirt tShirt, Destination destination)`, където `TShirt` е друг record, `TShirt(Size size, Color color)`, моделиращ тениска, която ще бъде поръчана. Двата record-a трябва да имат публични канонични конструктори.

## Color

Цветът на дадена тениска се моделира от следния `enum`:

```java
package bg.sofia.uni.fmi.mjt.order.server.tshirt;

public enum Color {
    BLACK("BLACK"),
    WHITE("WHITE"),
    RED("RED"),
    UNKNOWN("UNKNOWN");

    private final String name;

    Color(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```

## Size

Размерът на дадена тениска се представя чрез следния `enum`:

```java
package bg.sofia.uni.fmi.mjt.order.server.tshirt;

public enum Size {
    S("S"),
    M("M"),
    L("L"),
    XL("XL"),
    UNKNOWN("UNKNOWN");

    private final String name;

    Size(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```

## Destination

Дестинацията за доставка на дадена тениска се моделира от следния `enum`:

```java
package bg.sofia.uni.fmi.mjt.order.server.destination;

public enum Destination {
    EUROPE("EUROPE"),
    NORTH_AMERICA("NORTH_AMERICA"),
    AUSTRALIA("AUSTRALIA"),
    UNKNOWN("UNKNOWN");

    private final String name;

    Destination(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```

## Response

Response от сървъра ще моделираме чрез record-a: 

```java
public record Response(Status status, String additionalInfo, Collection<Order> orders) {
    private enum Status {
        OK, CREATED, DECLINED, NOT_FOUND
    }

    // TODO: Add implementation if needed
}
```

Response-ите имат следния формат:
- При създаване на нова поръчка:
  - ако е валидна: `{"status":"CREATED", "additionalInfo":"ORDER_ID=\<id\>"}`
  - ако е невалидна: `{"status":"DECLINED", "additionalInfo":"invalid:size,color,destination"}`. Редът на проверка за невалидни аргументи е: size, color, destination.
    - Невалидните поръчки се запазват в паметта с id -1
- При вземане на всички поръчки:
  - включително невалидните: `{"status":"OK", "orders":[{"id":1, "tShirt":{size":"L", "color":"BLACK"}, "destination":"EUROPE"},
    {"id":-1, "tShirt":{"size":"L", "color":"UNKNOWN"}, "destination":"EUROPE"}]`
  - само успешните: `{"status":"OK", "orders":[{"id": 1, "tShirt":{size":"L", "color":"BLACK"}, "shipTo":"EUROPE"}]}`
- При вземане на една валидна поръчка:
  - когато съществува: `{"status":"OK", "orders":[{"id": 1, "tShirt":{size":"L", "color":"BLACK"}, "shipTo":"EUROPE"}]}`
  - когато не съществува: `{"status":"NOT_FOUND", "additionalInfo":"Order with id = <id> does not exist."}`
### Тестване

:star: Тествайте ръчно имплементацията, първо с един, а после с няколко паралелно свързани клиента, и се убедете, че работи коректно.

:star: Писането на автоматични тестове за тази задача е по ваш избор, но съветваме всеки да пробва, тъй като ще ви е полезно и за курсовите проекти.

**Подсказка:** Припомнете си имплементацията на Echo Client-Server, която разгледахме. Можем ли да я превърнем в T-Shirt Shop Client/Server приложение?

**Подсказка:** Решението на тази задача ще ви улесни изключително много при разработката на курсовите ви проекти, защото всички те представляват приложения тип клиент-сървър, като сървърът обслужва много потребители едновременно.

### Примерна структура на проекта

Добра практика при създаването на приложения тип клиент-сървър е да отделяте клиента и сървъра в отделни проекти. Това предотвратява грешки от типа, класове/интерфейси от клиента да се ползват от сървъра, или обратно. Също така, в реална ситуация, бихме искали да пакетираме и разпространяваме поотделно клиентската и сървърната част на нашето приложение.

В грейдъра качете общ `zip` архив на папки `src` и `test`, ако имате тестове.

```
src
└─ bg.sofia.uni.fmi.mjt.order.server
    ├── repository
    │    ├─ exception
    │    │    ├── OrderNotFoundException.java
    │    │    └── (...)           
    │    ├─ MJTOrderRepository.java
    │    ├─ OrderRepository.java
    │    └── (...)
    ├── destination
    │    └── Destination.java
    ├── order
    │    └── Order.java
    ├── tshirt
    │    ├── Color.java
    │    ├── Size.java
    │    └── TShirt.java
    └── (...)
```
