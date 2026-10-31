# Stoyo's Tavern | При бай Стойо 🍻

Всеки петък в кръчмата "При бай Стойо" се играе белот. Правилата на играта днес не ни интересуват. Интересува ни какво става
**покрай** нея:

- "Ти ли ще ми казваш на мен какво е терца?!"
- "Тоя има козове в ръкава. И в другия ръкав. И в чорапа!"
- "Като те гледам как държиш картите, се чудя дали не ги пазиш от някого."
- "А този не си е платил бирата от март!"

Досега споровете се решаваха с чинии във въздуха, а чиниите са скъпи. А най-лошото е, че никога не е ясно **кой е
започнал първи**: бай Митко чува обиди и от чашата си, Пешо Хаймана не чака втора обида, за да се хвърли в бой, Гошо
Късметлията печели подозрително често, а дядо Мариян се опитва да успокои всички.

Затова собственикът бай Стойо реши да не гадае повече. Иска програма, която **симулира цяла вечер** в кръчмата му: кой какво пие (в единствената валута, която кръчмата признава – ракии 🥃), кой кого е обидил, кой е излъган, кой е излязъл, без да плати и най-важното - в кой точно момент настроението се скапва и започва боят.

Задачата ни е да създадем софтуер, който помага на бай Стойо със симулацията на вечерта.

> Всички съвпадения с реални роднини, съседи и колеги са напълно случайни.

## TavernAPI

Основната функционалност на системата е представена чрез следния интерфейс `TavernAPI`:

```java
package bg.sofia.uni.fmi.mjt.tavern;

import bg.sofia.uni.fmi.mjt.tavern.event.Event;
import bg.sofia.uni.fmi.mjt.tavern.exception.BannedGuestException;
import bg.sofia.uni.fmi.mjt.tavern.exception.BrawlException;
import bg.sofia.uni.fmi.mjt.tavern.exception.GuestAlreadyInsideException;
import bg.sofia.uni.fmi.mjt.tavern.exception.NoSuchGuestException;
import bg.sofia.uni.fmi.mjt.tavern.exception.UnpaidTabException;
import bg.sofia.uni.fmi.mjt.tavern.guest.Guest;
import bg.sofia.uni.fmi.mjt.tavern.guest.Mood;

public interface TavernAPI {

    /**
     * Lets a guest into the tavern. The guest starts the night with mood CALM,
     * no rakias drunk and an empty tab.
     * For simplicity, if a guest leaves and comes back, he is treated as a new guest,
     * with mood CALM, no rakias drunk and an empty tab.
     *
     * @param guest the guest who wants to come in
     * @throws IllegalArgumentException if the guest is null
     * @throws BannedGuestException if the name of the guest is in the list of the banned guests
     * @throws GuestAlreadyInsideException if there is already a guest with the same name inside the tavern
     */
    void admit(Guest guest);

    /**
     * Handles one event of the night. The rules for every kind of event are described below.
     * NoSuchGuestException and IllegalArgumentException leave the tavern unchanged.
     * BrawlException and UnpaidTabException are thrown after the tavern is updated.
     *
     * @param event the event that happens
     * @throws IllegalArgumentException if the event is null
     * @throws NoSuchGuestException if the event mentions a guest who is not inside the tavern
     * @throws BrawlException if the event ends with a brawl. The cause of the exception
     * is the exception that describes the provocation that started the brawl.
     * @throws UnpaidTabException if a guest leaves without paying the whole tab
     */
    void handle(Event event);

    /**
     * @param name the name of a guest who is inside the tavern
     * @return the current mood of the guest
     * @throws IllegalArgumentException if the name is null or blank
     * @throws NoSuchGuestException if there is no such guest inside the tavern
     */
    Mood moodOf(String name);

    /**
     * @param name the name of a guest who is inside the tavern
     * @return the current tab of the guest in euro cents (so we do not fight about the change)
     * @throws IllegalArgumentException if the name is null or blank
     * @throws NoSuchGuestException if there is no such guest inside the tavern
     */
    int tabOf(String name);

    /**
     * @return the names of the guests inside the tavern, in the order in which they were admitted.
     */
    String[] guestsInside();

    /**
     * @return the names of the banned guests, in the order in which they were banned.
     */
    String[] bannedGuests();

}
```

В пакета `bg.sofia.uni.fmi.mjt.tavern` създайте клас `TavernAPIImpl`, който имплементира интерфейса и има публичен
конструктор:

```java
/**
 * @param rakiaPrice the price of one rakia in euro cents
 *
 * @throws IllegalArgumentException if the rakiaPrice is not positive
 */
public TavernAPIImpl(int rakiaPrice);
```

## Събития

Всичко, което се случва през вечерта, е събитие. Видовете събития са фиксирани и не могат да се разширяват отвън:

```java
package bg.sofia.uni.fmi.mjt.tavern.event;

public sealed interface Event permits Drink, Insult, Cheat, Pay, Leave {
}
```

Можете да добавяте методи в `Event`, но видовете събития трябва да останат точно тези пет.

Създайте петте типа като всеки се създава чрез следните конструктори:

```java
/**
 * A guest orders rakias.
 *
 * @throws IllegalArgumentException if the guest is null or blank
 * @throws IllegalArgumentException if the rakias are not positive
 */
Drink(String guest, int rakias);

/**
 * One guest insults another.
 *
 * @throws IllegalArgumentException if any of the names is null or blank
 * @throws IllegalArgumentException if from and to are the same name (names are compared exactly, case-sensitive)
 */
Insult(String from, String to);

/**
 * One guest cheats another one at the card table.
 *
 * @throws IllegalArgumentException if any of the names is null or blank
 * @throws IllegalArgumentException if cheater and victim are the same name (names are compared exactly, case-sensitive)
 */
Cheat(String cheater, String victim);

/**
 * A guest pays a part of the tab.
 *
 * @throws IllegalArgumentException if the guest is null or blank
 * @throws IllegalArgumentException if the amount is not positive
 */
Pay(String guest, int amount);

/**
 * A guest leaves the tavern.
 *
 * @throws IllegalArgumentException if the guest is null or blank
 */
Leave(String guest);
```

Данните са достъпни чрез методи със същите имена като параметрите (например `from()` и `to()`).

## Настроение

Настроението показва колко е напрегната обстановката около посетителя. То е `enum Mood` в пакета
`bg.sofia.uni.fmi.mjt.tavern.guest`, а константите му са в реда от таблицата долу. Когато настроението се влоши, то
става по-високо в таблицата, а когато се успокои - по-ниско. Това се случва чрез методите `escalate()` и `calmDown()`,
които връщат новото настроение:

| `Mood`     | `escalate()` | `calmDown()` |
|:-----------|:------------:|:------------:|
| `CALM`     |   `TENSE`    |    `CALM`    |
| `TENSE`    |   `ANGRY`    |    `CALM`    |
| `ANGRY`    |  `BRAWLING`  |   `TENSE`    |
| `BRAWLING` |  `BRAWLING`  |   `ANGRY`    |

## Посетители

В същия пакет създайте абстрактния клас `Guest`. Всеки посетител има име и при влизане настроението му е `CALM`. Имената се сравняват точно (с главни и малки букви) - "Пешо" и "пешо" са различни хора. Когато посетител излезе и по-късно влезе пак (дори като същия обект `Guest`), той започва начисто: настроение `CALM`, нула изпити ракии и празна сметка.

```java
/**
 * @param name the name of the guest
 *
 * @throws IllegalArgumentException if the name is null or blank
 */
public Guest(String name);

/**
 * @return the name of the guest
 */
public String getName();
```

Създайте четири вида посетители, които наследяват `Guest`. Всеки има публичен конструктор `(String name)` със същото
условие като в `Guest`.

`Drunkard`: бай Митко, мирен човек, докато не си поръча

- Става "пийнал", когато изпие поне **2 ракии**.
- Когато го обидят, настроението му се влошава с едно ниво.
- Когато го излъжат, настроението му не се променя, защото не помни да е играл.
- Когато други хора се карат, не се намесва.

`Hothead`: Пешо Хаймана, търпението му е колкото малка ракия

- Става "пийнал", когато изпие поне **4 ракии**.
- Когато го обидят, настроението му веднага става `BRAWLING`, без значение какво е било преди това.
- Когато го излъжат, настроението му се влошава с две нива.
- Когато други хора се карат, се намесва: подкокоросва ги и настроението на обидения/излъгания се влошава с едно ниво.

`Peacemaker`: дядо Мариян, носи си мир в джоба, до джибрата

- Става "пийнал", когато изпие поне **5 ракии**.
- Когато го обидят или го излъжат, настроението му не се променя.
- Когато други хора се карат, се намесва: настроението на обидения/излъгания се успокоява с едно ниво.

`Cheater`: Гошо Късметлията, вади карти от места, където картите не растат

- Става "пийнал", когато изпие поне **3 ракии**.
- Когато го обидят, настроението му се влошава с едно ниво.
- Когато го излъжат, настроението му се успокоява с едно ниво, защото уважава майсторлъка.
- Когато други хора се карат, не се намесва.

## Как протича една вечер

Преди всичко друго: ако събитие споменава име на посетител, който не е в кръчмата, се хвърля `NoSuchGuestException` и
нищо не се променя.

### Drink

- Изпитите ракии на посетителя се увеличават с `rakias`, а сметката му с `rakias * rakiaPrice`.
- Настроението на посетителя **не се променя**. Пийнал посетител се влошава с още едно допълнително ниво при обида или мамене (виж по-долу).

### Insult и Cheat

За `Insult` засегнатият е `to`, а за `Cheat` засегнатият е `victim`. Настроението на `from` и `cheater` не се
променя. Двете събития се обработват по един и същи начин:

1. Засегнатият реагира според вида си и спрямо текущото си настроение получава ново.
2. Ако засегнатият е **пийнал** и реакцията му е влошила настроението (новото е по-лошо от това преди реакцията), то се влошава с още едно ниво.
3. След това всеки от **останалите** посетители в кръчмата (всички освен двамата замесени), по реда на влизането им,
   може да се намеси.
4. Полученото настроение е новото настроение на засегнатия. Ако е `BRAWLING`, започва бой:
    - и двамата замесени напускат кръчмата и са вписани в списъка с изгонените (първо този, който е предизвикал
      събитието - `from` или `cheater`, после другият);
    - хвърля се `BrawlException`, чиято причина (`cause`) е `InsultException` при `Insult` или `CheatingException` при `Cheat`.
    - при бой **не** се хвърля `UnpaidTabException`, дори замесените да имат неплатена сметка, и всеки от тях се вписва в списъка с изгонените само веднъж.

### Pay

Сметката на посетителя намалява с `amount`, но не става по-малка от 0 (ако платиш повече, бакшишът е за бай Стойо).

### Leave

Посетителят напуска кръчмата. Ако сметката му е по-голяма от 0, той се вписва в списъка с изгонените и се хвърля
`UnpaidTabException`. Ако не дължи нищо, си излиза тихо и спокойно.

## Изключения

В пакета `bg.sofia.uni.fmi.mjt.tavern.exception` създайте следните изключения.

- `ProvocationException` общата основа на причините за бой, т.е. това, което предизвиква боя.
- `InsultException` се хвърля, когато боят е започнал от обида. Наследява `ProvocationException`.
- `CheatingException` се хвърля, когато боят е започнал от мамене. Наследява `ProvocationException`.
- `BrawlException` се хвърля, когато събитие завърши с бой.
- `UnpaidTabException` се хвърля, когато посетител напусне кръчмата с неплатена сметка.
- `BannedGuestException` се хвърля, когато изгонен посетител се опита да влезе.
- `GuestAlreadyInsideException` се хвърля, когато в кръчмата се опита да влезе посетител, чието име вече е вътре.
- `NoSuchGuestException` се хвърля, когато събитие споменава посетител, който не е в кръчмата.

Всички проблеми с аргументи, които са `null` или невалидни, се обработват с `IllegalArgumentException`, както е описано в javadoc-а.

## Примери

```
TavernAPI tavern = new TavernAPIImpl(2);

tavern.admit(new Drunkard("Митко"));
tavern.admit(new Hothead("Пешо"));
tavern.admit(new Peacemaker("Мариян"));
tavern.admit(new Cheater("Гошо"));

tavern.handle(new Drink("Митко", 3));
tavern.moodOf("Митко"); // CALM (пиенето не променя настроението)
tavern.tabOf("Митко");  // 6
// 3 ракии са над прага 2 за Drunkard, така че Митко вече е пийнал

tavern.handle(new Drink("Пешо", 1));
tavern.moodOf("Пешо");  // CALM (1 ракия е под прага 4, Пешо е трезвен)

tavern.handle(new Insult("Пешо", "Митко"));
tavern.moodOf("Митко"); // TENSE
// Drunkard реагира на обида с escalate(): CALM -> TENSE.
// Митко е пийнал и реакцията го е влошила, затова още една стъпка: TENSE -> ANGRY.
// Мариян се намесва: calmDown() -> TENSE. Гошо не прави нищо.

tavern.handle(new Insult("Митко", "Пешо"));
tavern.moodOf("Пешо");  // ANGRY
// Пешо е Hothead и от обидата веднага става BRAWLING
// Мариян го успокоява: calmDown() -> ANGRY. 
// Боят е предотвратен.

tavern.handle(new Leave("Мариян")); // няма сметка, излиза тихо
tavern.guestsInside(); // [Митко, Пешо, Гошо]

try {
    tavern.handle(new Cheat("Гошо", "Пешо"));
    // Пешо е ANGRY и от мамене escalate() два пъти го прави BRAWLING.
    // Този път Мариян го няма и никой не го успокоява.
} catch (BrawlException e) {
    System.out.println(e.getCause().getClass().getSimpleName()); // CheatingException
}

tavern.guestsInside(); // [Митко]
tavern.bannedGuests(); // [Гошо, Пешо]

tavern.admit(new Hothead("Пешо")); // BannedGuestException
tavern.admit(new Drunkard("Митко")); // GuestAlreadyInsideException

tavern.handle(new Pay("Митко", 4));
tavern.tabOf("Митко"); // 2

tavern.handle(new Leave("Митко")); // UnpaidTabException
tavern.bannedGuests();             // [Гошо, Пешо, Митко]
tavern.guestsInside();             // []
```

## Структура на пакетите

Спазвайте имената на пакетите на всички по-горе описани класове, интерфейси и конструктори, тъй като в противен случай решението ви няма да може да бъде автоматично тествано. Може да добавяте собствени класове и абстракции в съответните пакети, но не променяйте вече съществуващите, освен ако не е изрично споменато в условието.

```
src
└─ bg/sofia/uni/fmi/mjt/tavern
    ├─ event/
    │  ├─ Cheat.java
    │  ├─ Drink.java
    │  ├─ Event.java
    │  ├─ Insult.java
    │  ├─ Leave.java
    │  ├─ Pay.java
    │  └...
    ├─ exception/
    │  ├─ BannedGuestException.java
    │  ├─ BrawlException.java
    │  ├─ CheatingException.java
    │  ├─ GuestAlreadyInsideException.java
    │  ├─ InsultException.java
    │  ├─ NoSuchGuestException.java
    │  ├─ ProvocationException.java
    │  ├─ UnpaidTabException.java
    │  └...
    ├─ guest/
    │  ├─ Cheater.java
    │  ├─ Drunkard.java
    │  ├─ Guest.java
    │  ├─ Hothead.java
    │  ├─ Mood.java
    │  ├─ Peacemaker.java
    │  └...
    ├─ TavernAPI.java
    ├─ TavernAPIImpl.java
    └...
```

Можете и е желателно да добавите собствени класове и абстракции в съответните пакети.

## ⚠️ Забележки

- Публичният интерфейс е описан подробно, но условието нарочно е оскъдно откъм имплементационни детайли. Имате свобода сами да решите проблеми като:
    - Кой клас трябва да знае, че Hothead избухва от обида? `Tavern` или самият `Hothead`?
    - Кой трябва да пази колко е изпил един посетител?
    - Може ли събитието само да знае какво прави?
    - ...
- Сами решете кои типове да са обикновени класове и кои `record`, и кои изключения да са checked и кои unchecked. Ако
  направите изключение checked, добавете съответното `throws` в интерфейса: grader тестовете се пишат така, че да
  работят и в двата случая.
- Използването на структури от данни, различни от масив, **не е позволено**.
