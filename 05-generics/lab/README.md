# Event Bus :bell:

Нека упражним знанията си за Java Generics като същевременно се запознаем с някои важни концепции и архитектурни design patterns в съвременната софтуерна разработка като [Event Driven Architecture (EDA)](https://en.wikipedia.org/wiki/Event-driven_architecture).

*Event Bus* (или *Event Broker*) е архитектурен design pattern, използван в софтуерните системи за управление на комуникацията между различните компоненти на дадено приложение. Той позволява на тези компоненти да публикуват *събития* и да се *абонират* да получават (да бъдат уведомявани за) тези събития, без да има нужда да знаят един за друг и да имат директна връзка помежду си. По същество, може да разглеждаме Event Bus като по-сложна и по-гъвкава реализация на [Observer Design Pattern-a](https://refactoring.guru/design-patterns/observer), подходяща за по-големи и по-сложни системи.

Този шаблон се използва широко в съвременната софтуерна разработка, защото подобрява модулна архитектура на приложението и намалява взаимозависимостта между отделните му части, повишава производителността и *responsiveness*-a на приложението, благодарение на асинхронната комуникация между компонентите му и се среща често там, където е важна обработката на събития в реално време, като игри, чат приложения или финансови системи.

Основните "действащи лица" в един Event Bus са *събитията* (Events) и *абонатите* (Subscribers).

### Събития

Събитията са съобщения (нотификации) за нещо, случило се в системата. Ще моделираме събитията чрез класове, които имплементират следния generic интерфейс:

```java
package bg.sofia.uni.fmi.mjt.eventbus.events;

import java.time.Instant;

public interface Event<T extends Payload<?>> {

    /**
     * @return the time when the event was created.
     */
    Instant getTimestamp();

    /**
     * @return the priority of the event. Lower number denotes higher priority.
     */
    int getPriority();

    /**
     * @return the source of the event.
     */
    String getSource();

    /**
     * @return the payload of the event.
     */
    T getPayload();

}
```

Освен метаданни като timestamp, приоритет и източник, всяко събитие съдържа и пренася същинското си съдържание в имплементация на generic интерфейса `Payload`:

```java
package bg.sofia.uni.fmi.mjt.eventbus.events;

public interface Payload<T> {

    /**
     * @return the size of the payload
     */
    int getSize();

    /**
     * @return the payload
     */
    T getPayload();

}
```

Различните видове събития имат различен по тип payload.

### Абонати

Абонатите са компоненти, които регистрират своя интерес към определени типове събития. Когато абонат бъде уведомен (нотифициран) за дадено събитие, той може да извърши подходящи действия според съдържанието на събитието.
Абонатите имплементират следния прост интерфейс:

```java
package bg.sofia.uni.fmi.mjt.eventbus.subscribers;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;

public interface Subscriber<T extends Event<?>> {

    /**
     * This method will be called by the EventBus when a new event for the type this subscriber is
     * subscribed to is published.
     *
     * @param event the event that was published
     * @throws IllegalArgumentException if the event is null
     */
    void onEvent(T event);

}
```

`DeferredEventSubscriber` е специален вид абонат, който съхранява всички събития, които е получил, за обработка в по-късен момент. Ето негов скелет, като вашата задача е да довършите имплементацията му:

```java
package bg.sofia.uni.fmi.mjt.eventbus.subscribers;

import java.util.Iterator;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;

public class DeferredEventSubscriber<T extends Event<?>> implements Subscriber<T>, Iterable<T> {

    /**
     * Store an event for processing at a later time.
     *
     * @param event the event to be processed
     * @throws IllegalArgumentException if the event is null
     */
    @Override
    public void onEvent(T event) {
        throw new UnsupportedOperationException("Still not implemented");
    }

    /**
     * Get an iterator for the unprocessed events. The iterator should provide the events sorted
     * by priority, with higher-priority events first (lower priority number = higher priority).
     * For events with equal priority, earlier events (by timestamp) come first.
     *
     * @return an iterator for the unprocessed events
     */
    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Still not implemented");
    }

    /**
     * Check if there are unprocessed events.
     *
     * @return true if there are unprocessed events, false otherwise
     */
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Still not implemented");
    }

}
```

Класът `DeferredEventSubscriber` трябва да има публичен конструктор по подразбиране.

### Event Bus

Централният компонент се моделира от класа `EventBusImpl`, който имплементира интерфейса `EventBus`:

```java
package bg.sofia.uni.fmi.mjt.eventbus;

import java.time.Instant;
import java.util.Collection;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;
import bg.sofia.uni.fmi.mjt.eventbus.exception.MissingSubscriptionException;
import bg.sofia.uni.fmi.mjt.eventbus.subscribers.Subscriber;

public interface EventBus {

    /**
     * Subscribes the given subscriber to the given event type.
     * If the same subscriber is already subscribed to the given event type, the method
     * should do nothing (no duplicate subscriptions).
     *
     * @param eventType  the type of event to subscribe to
     * @param subscriber the subscriber to subscribe
     * @throws IllegalArgumentException if the event type is null
     * @throws IllegalArgumentException if the subscriber is null
     */
    <T extends Event<?>> void subscribe(Class<T> eventType, Subscriber<? super T> subscriber);

    /**
     * Unsubscribes the given subscriber from the given event type.
     *
     * @param eventType  the type of event to unsubscribe from
     * @param subscriber the subscriber to unsubscribe
     * @throws IllegalArgumentException     if the event type is null
     * @throws IllegalArgumentException     if the subscriber is null
     * @throws MissingSubscriptionException if the subscriber is not subscribed to the event type
     */
    <T extends Event<?>> void unsubscribe(Class<T> eventType, Subscriber<? super T> subscriber)
        throws MissingSubscriptionException;

    /**
     * Publishes the given event to all subscribers of the event type.
     *
     * @param event the event to publish
     * @throws IllegalArgumentException if the event is null
     */
    <T extends Event<?>> void publish(T event);

    /**
     * Clears all subscribers and event logs.
     */
    void clear();

    /**
     * Returns all events of the given event type that occurred between the given timestamps. If
     * {@code from} and {@code to} are equal the returned collection is empty.
     * <p> {@code from} - inclusive, {@code to} - exclusive. </p>
     *
     * @param eventType the type of event to get
     * @param from      the start timestamp (inclusive)
     * @param to        the end timestamp (exclusive)
     * @return an unmodifiable collection of events of the given event type that occurred between
     * the given timestamps
     * @throws IllegalArgumentException if the event type is null
     * @throws IllegalArgumentException if the start timestamp is null
     * @throws IllegalArgumentException if the end timestamp is null
     */
    Collection<? extends Event<?>> getEventLogs(Class<? extends Event<?>> eventType, Instant from,
                                                Instant to);
    
    /**
     * Returns all subscribers for the given event type in an unmodifiable collection. If there are
     * no subscribers for the event type, the method returns an empty unmodifiable collection.
     *
     * @param eventType the type of event to get subscribers for
     * @return an unmodifiable collection of subscribers for the given event type
     * @throws IllegalArgumentException if the event type is null
     */
    <T extends Event<?>> Collection<Subscriber<?>> getSubscribersForEvent(Class<T> eventType);

}
```

Класът `EventBusImpl` трябва да има публичен конструктор по подразбиране.

## Подсказки

:point_right: За смислена реализация и локално тестване, ще трябва да си създадете имплементации на някои от описаните интерфейси, но имате свобода да прецените, с колко и какви класове.

:point_right: За да разберем какво представлява класът [`Class<T>`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Class.html), добре е да знаем, че ако `MyClass` е име на клас, то `MyClass.class` е референция към обекта, който представлява този клас, и тази референция е от тип `Class<MyClass>`.  Да речем, ако класът е `String`, може да напишем

```java
Class<String> stringClassRef = String.class;
```

---

### 💬 Рефлексия

При проектирането на системи, които използват Generics и динамично свързване на обекти по време на изпълнение, понякога се появяват ситуации, в които езикът не позволява пълна типова безопасност.

В тази задача е важно не само да реализирате функционално коректно решение, но и да обосновете избора си, когато става въпрос за типовата безопасност на кода.

Ако в хода на разработката ви се наложи да използвате type cast или raw type, защото Java Generics не позволява друго решение, добавете ясен коментар в кода, който обяснява защо този подход е оправдан и безопасен в конкретния контекст.

Целта е да развиете усет кога езиковите механизми на Generics могат да се прилагат директно и кога е важно да бъдете критични и осъзнати към начина, по който ги използвате.

---

### Пакети

Спазвайте имената на пакетите на всички по-горе описани типове, тъй като в противен случай, решението ви няма да може да бъде автоматично тествано.

```
src
└── bg.sofia.uni.fmi.mjt.eventbus
    ├── events
    │      ├── Event.java 
    │      ├── Payload.java 
    │      └── (...)
    ├── exception
    │      └── MissingSubscriptionException.java
    ├── subscribers
    │      ├── DeferredEventSubscriber.java
    │      ├── Subscriber.java
    │      └── (...)
    ├── EventBus.java
    ├── EventBusImpl.java
    └── (...)
```

### :warning: Забележки

- Не променяйте по никакъв начин интерфейсите, дадени в условието.
- Използването на [Java Stream API](https://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/util/stream/package-summary.html) и/или [lambdas](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html) **не е разрешено**. Задачата трябва да се реши с помощта на знанията от курса до момента.
