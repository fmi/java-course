# Многонишково програмиране

## Коледната работилница на Дядо Коледа :christmas_tree:

Коледното настроение вече витае във въздуха около нас и започва да завладява всички. Най-щастливи около празниците традиционно са децата, които нямат търпение да напишат своето писмо до Дядо Коледа.

Потопени в коледния дух, ще се опитаме да симулираме изпращането на писма до Дядо Коледа с желания за подаръци и изработването на подаръци от елфите, използвайки Java нишки.

Децата ще могат паралелно да изпращат писма с желания до Дядо Коледа, а елфите, също паралелно - да създават пожеланите играчки. Логистиката оставяме в ръцете на добрия старец.

## Условие

Задачата е разделена на 5 стъпки (+ 1 допълнителна), като препоръката ни е да започнете с проста имплементация, която, с леки модификации, да подобрите впоследствие.

**1. Създайте клас `Workshop`, който ще представлява работилницата на Дядо Коледа:**


```java
package bg.sofia.uni.fmi.mjt.christmas;

public class Workshop {

    public Workshop() {

    }

    /**
    * Adds a gift to the elves' backlog.
    **/
    public void postWish(Gift gift) {
        throw new UnsupportedOperationException();
    }

    /**
    * Returns an array of the elves working in Santa's workshop.
    **/
    public Elf[] getElves() {
        throw new UnsupportedOperationException();
    }

    /**
    * Returns the next gift from the elves' backlog that has to be manufactured.
    **/
    public Gift nextGift() {
        throw new UnsupportedOperationException();
    }

    /**
    * Returns the total number of wishes sent to Santa's workshop by the kids.
    **/
    public int getWishCount() {
         throw new UnsupportedOperationException(); 
    }
}
```

:star: Забележки:
- Използвайте подходяща структура от данни, в която да съхранявате подаръците, които децата са си пожелали, но все още не са изработени от помощниците на Дядо Коледа - добрите елфи.

<hr>

**2. Създайте клас `Kid`, който е нишка и има следния вид:**

```java
    package bg.sofia.uni.fmi.mjt.christmas;
    
    public class Kid {

        public Kid(Workshop workshop) {
            throw new UnsupportedOperationException();
        }
        
    }
```

Задачите, които всяко дете трябва да свърши преди Коледа, са:

1. Да си избере подарък - отнема известно време, което можем да симулираме с `Thread.sleep([reasonable random time])`.

2. Да изпрати писмо с желание за подаръка към работилницата на Дядо Коледа.

Децата могат да си пожелават различни видове подаръци, които са описани чрез `enum`-a `Gift`. 

Използвайте метода `getGift()`, за да вземете произволен подарък.

```java
package bg.sofia.uni.fmi.mjt.christmas;

public enum Gift {

    BIKE("Bicycle", 50), CAR("Car", 10),
    DOLL("Barbie doll", 6), PUZZLE("Puzzle", 15);

    private final String type;
    private final int craftTime;

    private static Gift[] gifts = Gift.values();

    private static Random giftRand = new Random();

    private Gift(String type, int craftTime) {
        this.type = type;
        this.craftTime = craftTime;
    }

    public String getType() {
        return type;
    }

    public int getCraftTime() {
        return craftTime;
    }

    public static Gift getGift() {
        return gifts[giftRand.nextInt(gifts.length)];
    }

}
```

<hr>

**3. Създайте работилницата на Дядо Коледа и пуснете конкурентно N на брой деца**

Изчакайте всички деца да приключат с изпращането на писмата.

:star: Локално тестване:

- Уверете се, че броят на всички желания в работилницата е коректен

<hr>

**4. Създайте клас `Elf`, който е нишка и има следния вид:**

```java
package bg.sofia.uni.fmi.mjt.christmas;

public class Elf {

    public Elf(int id, Workshop workshop) {
        throw new UnsupportedOperationException();
    }

    /**
    * Gets a wish from the backlog and creates the wanted gift.
    **/
    public void craftGift() {
        throw new UnsupportedOperationException();
    }

    /**
    * Returns the total number of gifts that the given elf has crafted.
    **/
    public int getTotalGiftsCrafted() {
         throw new UnsupportedOperationException();
    }
}
```

  Задачата на елфите ще бъде да създават играчки:

  1. Взимане на желание за подарък от `backlog`-a.
  2. Изработване на подаръка. Времето, необходимо за изработване на една играчка, зависи от нейния тип (т.е. `getCraftTime()` от `Gift`) - ще го симулираме отново с `Thread.sleep()`.

:star: Забележки:
- По време на изпълнението си, всеки елф взима една по една задача от `backlog`-a с подаръци от работилницата.
- Списъкът с желания може да бъде временно празен. В такъв случай, елфите без работа изчакват да се появи нова задача.

<hr>

**5. Създайте работилницата на Дядо Коледа - този път с работещи елфи, които да правят играчките**

:star: Забележки:
- В работилницата на Дядо Коледа работят **20** елфи.
- При създаването на работилницата, елфите започват да работят.
- Те се създават с уникални `auto-increment` ID-та, започващи от 0.

:exclamation: Изчакайте известно време и вижте дали елфите приключват работа. Ако не, защо?

<hr>

**6. Традицията повелява, че елфите приключват работа при настъпването на Коледа (Допълнително)**

- В основната нишка, изчакайте даден брой (мили)секунди (настъпването на Коледа).
- След това, вдигнете флаг в `Workshop`, например `isChristmasTime`, който да даде знак на елфите да спрат да работят.
- Всички желания, които са останали в `backlog`-a на елфите след настъпване на Коледа, също трябва да бъдат изпълнени.
- След като всеки елф приключи работа (т.е Коледа е настъпила и вече няма подаръци за изработване), изведете на конзолата ID-то на елфа и броя на изработените от него подаръци (за ваши тестови цели).

:star:  Локално тестване:
- Уверете се, че броят на всички желания в работилницата е коректен
- Уверете се, че броят на подаръците, които са изработени, е равен на броя на всички желания

## Структура на проекта

```bash
src
╷
└─ bg/sofia/uni/fmi/mjt/christmas/
   ├─ Workshop.java
   ├─ Kid.java
   ├─ Gift.java
   └─ Elf.java
```

В [sapera.org](http://grader.sapera.org/) качете `zip` архив на `src` директорията.
