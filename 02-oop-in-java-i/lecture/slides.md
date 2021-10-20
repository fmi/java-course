class: center, middle

# Обектно-ориентирано програмиране с Java

![Java OOP](images/02.1-oop.jpg)

20.10.2021

---

### Предната лекция говорихме за:

- Tиповете данни
- Оператори, изрази, statements
- Условия и разклонения
- Итерация / Цикли
- Низове и операции с тях
- Масиви
- Функции

---

### Днес ще разгледаме:

- Класове и обекти
- Абстрактни класове и интерфейси
- Фундаменталните ООП принципи
  - Енкапсулация
  - Наследяване
  - Полиморфизъм
  - Абстракция

---

### Обектно-ориентирано програмиране

- Родено е от необходимостта за по-добър контрол над конкурентната модификация на споделени данни
- Основната идея е, да няма директен достъп до данните, а те да се достъпват през нарочен за целта слой код
- Понеже данните трябва да се предават и модифицират, се ражда концепцията за обект

---

### Обект

- Обект в най-общ смисъл е множество данни, които могат да се предават и достъпват само чрез множество методи, които се подават заедно с данните
- Данните съставляват *състоянието* на обекта, а методите съставляват *поведението* на обекта
- Състоянието на обекта е скрито (*енкапсулирано*) от директен достъп
- Обектът е инстанция (представител) на даден клас. Обекти се създават явно чрез оператора `new` или неявно, и живеят в heap паметта

---

### Създаване на обекти

```java
String message = new String("Operation completed."); // explicit
Integer studentsCount = Integer.valueOf(60); // implicit
int[] intArr = new int[100]; // explicit
String[] stringArr = {"Some", "example"}; // implicit
stringArr = new String[]{"Changed", "my", "mind"}; // explicit
```

---

### Клас

- Клас – дефиниция на (клас от) обекти
  - описва състояние чрез член-данни (член-променливи)
  - описва поведение чрез методи 
- Конструктор(и)
- Метод(и)
- Възможно е даден клас да няма състояние или да няма поведение
- Всеки клас има определен *интерфейс* - формално описание на начина, по който други обекти могат да взаимодействат с него

---

### Метод на клас

- Функция за манипулиране на състоянието на класа
  - име и списък от параметри (*сигнатура*)
      - броят на параметрите се нарича *арност*
      - два метода имат еднаква сигнатура, ако имат еднакви имена, еднаква арност и еднаква последователност от типове в списъка от параметри
  - модификатори, тип на връщаната стойност, сигнатура и тяло, оградено с {}
  - тялото може да съдържа декларации на локални променливи и statements
  - може да има странични ефекти

---

### Метод на клас

```java
public class AdmissionTestProblems {

    public static int minSwaps(String s1, String s2) {

        int differences = 0;
        for (int i = 0; i < s1.length(); ++i) {
            differences += (s1.charAt(i) == s2.charAt(i)) ? 0 : 1;
        }

        return differences / 2;

    }

}
```

---

### Методи с променлив брой аргументи

- специален вид параметър на метод: редица от нула или повече аргументи от един и същи тип
- Нарича се *varargs*, от *variable arguments*
- Синтаксис: име на тип, следван от три точки (многоточие)
- Ако метод има varargs параметър, той трябва да е един и да е последен в списъка
- При достигане до varargs параметър, компилаторът създава масив от останалите аргументи и ги подава на метода като масив
- В тялото на метода се достъпва като масив

---

### Методи с променлив брой аргументи

```java
// A method that takes variable number of integer arguments
static void funWithVarargs(int... a) {
    System.out.println("Number of arguments: " + a.length); 

    // using for-each loop to display contents of a
    for (int i : a) {
        System.out.print(i + " "); 
    } 
}

public static void main(String[] args) {
    // standard syntax
}

public static void main(String... args) {
    // varargs syntax - equivalent to the above
}
```

---

### Статични член-променливи и статични методи

- Те са част от класа, а не от конкретна негова инстанция (обект)
- Могат да се достъпват, без да е създаден обект: само с името на класа, точка, името на статичната член-променлива или метод

<br>

```java
System.out.println(Math.PI);        // 3.141592653589793
System.out.println(Math.pow(3, 2)); // 9.0
```

---

### Статични член-променливи

- Статичните член-променливи имат едно-единствено копие, което се споделя от всички инстанции на класа
  - ако са константи, пестим памет (няма смисъл да се мултиплицират във всяка инстанция)
  - ако са променливи, всяка инстанция "вижда" и променя една и съща стойност, което е механизъм за комуникация между всички инстанции на дадения клас

---

### Статични методи

- Статичните методи имат достъп само до статичните член-променливи и други статични методи на класа
- Нестатичните методи имат достъп както до статичните, така и до нестатичните членове на класа

---

### Статични методи

```java
public class Utils {
    public static final double PI = 3.14; // constant
    private static int radius = 10; // static member
    private String fact5 = "5!"; // non-static member

    // static method
    public static long fact(int n) {
        if (n == 1) { return 1; } else { return n * fact(n - 1); }
    }

    // non-static method
    public String getFact() {
        return fact5;
    }

    public static void main(String[] args) {
        System.out.println("Perimeter is " + 2 * Utils.PI * radius);
        System.out.println(new Utils().getFact() + "=" + Utils.fact(5));
        Utils.getFact(); // won't compile
    }
}
```

---

### Ключовата дума `this`

- Референция към конкретния обект
- Неявно се подава като параметър на всеки конструктор и нестатичен метод на класа
- Употребява се за:
  - достъпване на член-променливи, "скрити" от едноименни параметри на метод или локални променливи
  - извикване от конструктор на друг overloaded конструктор в същия клас
  - извикване на произволен метод на класа

---

### Ключовата дума `this`

```java
public class Human {

    private String name;

    public Human() {
        // Извикване на overload-натия конструктор със String параметър
        this("Unknown");
    }

    public Human(String name) {
        // Достъпване на член-променлива, скрита от едноименен параметър
        this.name = name;
    }

    public void whoAmI() {
        System.out.println("My name is " + name);
    }

}
```

---

### Обект - конкретна инстанция на даден клас

```java
public class MainApp {
    
    public static void main(String[] args) {
        Human ivan = new Human("Ivan");
        ivan.whoAmI();
        Human petar = new Human("Petar");
        petar.whoAmI();
    }

}
```

---

### Пакети

- Именувани групи от семантично свързани класове
- Служат за йерархично организиране на кода
- Съответстват на директорно дърво на файловата система
- Конвенция за именуване:
  - само малки букви, точка за разделител
  - компаниите използват обърнат домейн адрес
      - `mail.google.com` → `com.google.mail`

---

### Пакети

- всеки Java клас се намира в някакъв пакет
- пакетът се указва в началото на сорс файла (преди дефиницията на класа) с ключовата дума `package`
- ако липсва `package` декларация, класът е в пакета по подразбиране (който няма име) - което е лоша практика

<br>

```java
package bg.sofia.uni.fmi.mjt.example;

public class Example {
    // class Example is in package bg.sofia.uni.fmi.mjt.example
}
```

---

### Достъп до клас от друг пакет

- Всеки клас има достъп по подразбиране (имплицитно) до:
  - класовете от собствения си пакет
  - класовете в пакета `java.lang`
- Ако искаме клас да има достъп до клас в някой друг пакет, трябва експлицитно да го заявим с `import` декларация, която поставяме над декларацията на класа
- `import` декларацията може да е за конкретен клас с пълното му име: име на пакет + `.` + име на клас, или на всички класове в даден пакет (т.нар. *wildcard import*: име на пакет + `.*`)

---

### Достъп до клас от друг пакет

```java
package bg.sofia.uni.fmi.mjt.example;

import java.util.Arrays;
import java.util.Scanner;

public class StringUtils {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[] tokens = scanner.nextLine().toCharArray();
        Arrays.sort(tokens);
        System.out.println(tokens);
    }

}
```

---

### Достъп до клас от друг пакет

- Прието е `import`-ите да се подреждат в сортиран лексикографски ред по `<package>.<class>`
- По-чисто е да се изброят конкретните класове от пакета, вместо `import java.util.*;`

---

### Модификатори за достъп

за top-level клас, т.е. невложен в друг

<br>

| Модификатор      | Видимост                                         |
| :--------------- |:------------------------------------------------ |
| `public`         | Достъпен за всеки клас във всеки пакет           |
| без модификатор* | Достъпен само за класовете в собствения си пакет |

<br>

<small>
\* казваме също "package-private", "default"
</small>

---

### Модификатори за достъп

За член-променливи и методи на клас

<br>

| Модификатор   | Клас | Пакет | Подклас | Всички други |
| :------------ |:----:|:-----:|:-------:|:------------:|
| `public`      | да   | да    | да      | да           |
| `protected`   | да   | да    | да      | не           |
| no modifier   | да   | да    | не      | не           |
| `private`     | да   | не    | не      | не           |

---

### Енкапсулация

Енкапсулацията адресира основния проблем, мотивирал създаването на ООП: по-добро управление на конкурентния достъп до споделени данни

---

### Енкапсулация

- Множеството текущи стойности на член-данните на даден обект се наричат негово *състояние*
- Само вътрешните методи на даден обект имат достъп до неговото състояние, като правят невъзможни неочакваните промени на състоянието от външния свят
- В Java се постига чрез модификаторите за достъп

---

### Енкапсулация – пример за нарушаване

```java
public class Human {

    public String name; // no encapsulation!

    public Human(String name) {
        this.name = name;
    }

}

public class Main {

    public static void main(String[] args) {
        Human ivan = new Human("Ivan");
        ivan.name = "Faked Ivan"; // Hm...
    }

}
```

---

### Енкапсулация – пример за спазване

```java
public class Human {

    private String name; // stays hidden

    public Human(String name) {
        this.name = name;
    }

}

public class Main {

    public static void main(String[] args) {
        Human ivan = new Human("Ivan");
        ivan.name = "Faked Ivan"; // won't compile
    }

}
```

---

### Наследяване

- Позволява преизползване и разширяване на състояние и поведение на вече съществуващи класове
- Когато клас наследява друг клас, за него казваме, че е *наследник*, или *дете*, или *подклас*, а втория клас наричаме *родител*, или *базов клас*, или *суперклас*
- В Java се реализира с ключовата дума `extends`
- Класът-наследник получава достъп до всички `public` и `protected` член-променливи и методи на родителския клас
- Java не поддържа множествено наследяване

---

### Наследяване и method overriding

- Класът-наследник може да предостави собствена дефиниция на (т.е. да предефинира) методи на родителския клас (*method overriding*)

---

### Наследяване и method overriding

  - Сигнатурата на метода в класа-родител и класа-наследник трябва да е идентична
  - Модификаторът за достъп на метод в класа–наследник трябва да съвпада или да разширява модификатора за достъп на метода в родителския клас (но не може да го свива/ограничава)
  - Типът на връщаната стойност трябва да е *съвместим* с този на override-вания метод. Съвместим означава идентичен или наследник (за референтните типове) - *return type covariance*
  - Методът в класа-наследник е препоръчително да се анотира с опционалната анотация `@Override`. Така компилаторът ще ни помага да не нарушаваме горните правила

---

### Ключовата дума `super`

- Референция към прекия родител на обекта
- Употребява се за:
  - достъпване на член-променливи на родителя
  - извикване от конструктор в текущия клас на конструктор в родителския клас
  - извикване на произволен метод на родителския клас

---

### Ключовата дума `super`

- Неявно се подава като параметър на всеки конструктор и нестатичен метод на класа
- Не нарушава енкапсулацията - през `super` може да достъпим само `public` и `protected` членове на родителския клас

---

### Ключовата дума `super`

```java
public class Student extends Human {

    private int facultyNumber;

    public Student(String name, int facultyNumber) {
        super(name); // извикване на родителския конструктор
        this.facultyNumber = facultyNumber;
    }

    public static void main(String[] args) {
        Student ivan = new Student("Ivan", 61786);
        ivan.whoAmI(); // наследен от родителя метод
    }

}
```

---

### Йерархията от класове в Java

- Всички класове в Java са (преки или косвени) наследници на класа `java.lang.Object`
- Липсата на множествено наследяване означава, че всеки клас има точно един родител (с изключение на един-единствен клас,  `java.lang.Object`, който няма родител).
- Следователно, йерархията от класове е дърво, с `java.lang.Object` в корена

---

### `java.lang.Object`

```java
boolean equals(Object obj)
int hashCode()
String toString()
Object clone()
```

---

### `equals()`

- Трябва да го предефинираме, ако сравняваме два обекта за семантична (т.е. смислова) еднаквост, а не по референциите им (т.е. адреса им в паметта)
- Например, две инстанции на клас `Student` смислово са еднакви (отговарят на един и същи студент), ако факултетните им номера са еднакви – без значение дали референциите са еднакви или не

---

### `hashCode()`

- Трябва да го предефинираме, ако сме предефинирали `equals()`
- При предефинирането на `hashCode()`, ако `equals()` връща `true`, `hashCode`-ът на съответните обекти трябва да е равен
- Ако `hashCode`-ът на два обекта е равен, не е задължително `equals()` да връща `true`

---

### Операторът `instanceof`

- Използва се за type checking на референтните типове - дали даден обект е инстанция на даден клас

---

### Операторът `instanceof`

```java
Student ivan = new Student("Ivan", 61786);
Human petar = new Human("Petar");
        
System.out.println(ivan instanceof Student);   // true
System.out.println(ivan instanceof Human);     // true
System.out.println(petar instanceof Student);  // false
System.out.println(petar instanceof Human);    // true

// arrays are reference types
int[] intArr = new int[2];
System.out.println(intArr instanceof int[]);   // true

// null is not an instance of anything: false for any class
System.out.println(null instanceof AnyClass);

// true for any non-null ref, because any class extends java.lang.Object
System.out.println(ref instanceof Object);
```

---

### Pattern matching for `instanceof` (since Java 16)

```java
// until now
if (obj instanceof String) {
    int stringLength = ((String) obj).length();
}

// since Java 16 (preview feature in Java 14 & 15)
if (obj instanceof String s) {
    int stringLength = s.length();
}
```

---

### Ключовата дума `final`

- в декларация на променлива → прави я константа
- в декларация на метод → методът не може да се override-ва
- в декларация на клас → класът не може да се наследява

---

### Полиморфизъм

- От гръцки: poly (много) + morphe (форма)
- Дефиниция от биологията - съществуване на морфологично различни индивиди в границите на един вид
- В контекста на ООП, *полиморфизъм* е способността на даден обект да се държи като инстанция на друг клас или като имплементация на друг интерфейс

---

### Полиморфизъм

- ООП - наследниците на даден клас споделят поведение от родителския клас, но могат да дефинират и собствено поведение
- Всички Java обекти са полиморфични, понеже всеки обект наследява `java.lang.Object` класа

---

### Method overriding vs method overloading

- *Overriding* - класът-наследник предефинира поведението на класа-родител
- *Overloading* - класът декларира методи с едно и също име и различен брой и/или тип параметри

---

### Runtime полиморфизъм чрез method overriding

```java
public class Human {

    private String name;

    public Human(String name) {
        this.name = name;
    }

    public void whoAmI() {
        System.out.println("My name is " + name);
    }

}
```

---

### Runtime полиморфизъм чрез method overriding

```java
public class Student extends Human {

    private int facultyNumber;

    public Student(String name, int facultyNumber) {
        super(name);
        this.facultyNumber = facultyNumber;
    }

    @Override
    public void whoAmI() {
        super.whoAmI();
        System.out.println("My faculty number is " 
            + this.facultyNumber); 
    }

}
```

---

### Compile-time полиморфизъм чрез method overloading

```java
public class Human {

    public void move() {
        System.out.println("I am walking using two legs.");
    }
        
    public void move(String vehicle) {
        System.out.println("I move using a " + vehicle);
    }

}

public class Main {

    public static void main(String[] args) {
        Human ivan = new Human();
        ivan.move();
        ivan.move("Car");
    }

}
```

---

### Method overriding vs method overloading

<br>

|                                       | __Overloading__       | __Overriding__           |
| :------------------------------------ | :-------------------- | :----------------------- |
| Кога                                  | Compile-time          | Runtime                  |
| Къде                                  | В същия клас          | В класовете - наследници |
| Списък от аргументи                   | Различен              | Идентичен                |
| Return type                           | Може да бъде различен | Съвместим                |
| `static`, `private` и `final` методи  | Да                    | Не                       |
| Свързване (binding)                   | Статично              | Динамично                |
| Runtime performance                   | Better                |                          |

---

### Non-polymorphic code

```java
Student ivan = new Student("Ivan", 61786);
Human petar = new Student("Petar", 74451);

Object[] objs = {ivan, petar};

for (Object obj : objs) {

    if (obj instanceof Student) {
        ((Student) obj).whoAmI();
    } else if (obj instanceof Human) {
        ((Human) obj).whoAmI();
    }

}
```

---

### Non-polymorphic code

```java
Student ivan = new Student("Ivan", 61786);
Human petar = new Student("Petar", 74451);

Object[] objs = {ivan, petar};

for (Object obj : objs) {
    // instanceof and explicit casts are the "red lights"
*   if (obj instanceof Student) {
*       ((Student) obj).whoAmI();
*   } else if (obj instanceof Human) {
*       ((Human) obj).whoAmI();
*   }

}
```

---

### Polymorphic code

```java
Human[] humans = {ivan, petar};

for (Human human : humans) {
    human.whoAmI();
}
```

---

### Polymorphic code

```java
Human[] humans = {ivan, petar};

for (Human human : humans) {
    human.whoAmI();
}
```
<br>
Полиморфният код е не само по-кратък и четим. Помислете как трябва да се променят двата фрагмента код, ако в бъдеще се появят нови класове – наследници на `Human`

---

### Абстрактни класове

- Дефинират се с модификатора `abstract`
- Могат да имат методи без имплементация, които се декларират с модификатора `abstract`
- Не са напълно дефинирани (оставят на наследниците си да ги конкретизират/допълнят) 
    - не могат да се създават обекти от тях
- Един клас не може да бъде едновременно `abstract` и `final` – защо?

---

### Абстрактни класове - пример

```java
public abstract class Cat {
    public void move() {
        System.out.println("I am walking on 4 toes.");
    }
    public void communicate() {
        System.out.println("I mew.");
    }
    public abstract void eat();
}

public class DomesticCat extends Cat {
    public void eat() {
        System.out.println("I eat Whiskas.");
    }
}

public class Leopard extends Cat {
    public void eat() {
        System.out.println("I eat any prey.");   
    }
}
```

---

### Интерфейси

- Съвкупност от декларации на методи без имплементация
- Описват формално поведение, без да го имплементират
- Може да съдържат `static` `final` член-променливи == константи

---

```java
public interface Animal {
    void move();
    void communicate();
}
public class Human implements Animal {
    private String name;
    public Human(String name) {
        this.name = name;
    }
    public void move() {
        System.out.println("I am walking using two legs");
    }
    public void communicate() {
        System.out.println("I speak");
    }
}
public class Cat implements Animal {
    public void move() {
        System.out.println("I am walking using 4 toes");
    }
    public void communicate() {
        System.out.println("I mew");
    }
}
```

---

### Методи на интерфейсите

- Методите на интерфейсите са `public` и `abstract` по подразбиране
- Модификаторите `public` и `abstract` (заедно или поотделно) могат да бъдат указани и експлицитно
    - дали да бъдат експлицитно указани, е въпрос на стил - но е добре да сме консистентни
- Тъй като методите са абстрактни, не могат да бъдат декларирани като `final`

---

### Интерфейси и наследяване

- Интерфейсите могат да се наследяват
- Един интерфейс може да наследява множество интерфейси

---

### Интерфейсите и имплементаците им

- Интерфейсите не могат да се инстанцират
- Можем да инстанцираме (конкретни) класове, които ги имплементират
- Можем да присвояваме инстанция на клас на променлива от тип интерфейс, който класът имплементира
- Можем да проверяваме с `instanceof` дали клас имплементира даден интерфейс
- Един клас може да имплементира множество интерфейси

---

### Интерфейси и имплементациите им

- Ако даден клас декларира, че имплементира интерфейс, той трябва или
  - да даде дефиниции на *всичките* му методи, или
  - да бъде деклариран като абстрактен
- Като следствие, ако променим сигнатурата на метод(и) на интерфейса, или ако добавим нов(и) метод(и), трябва да променим и всички имплементиращи интерфейса класове
- Това често е проблем, а понякога е и невъзможно (нямаме контрол върху всички имплементации)

---

### Default методи в интерфейсите (от Java 8)

- Default-ен метод в интерфейс е метод, който
  - има имплементация
  - има модификатора `default` в декларацията си
- Имплементиращите класове имплицитно ползват default-ната имплементация на методите, но могат и да я предефинират

---

### Default методи в интерфейсите (от Java 8)

- Клас може да имплементира произволен брой интерфейси
- Ако два или повече от тях съдържат `default` метод с еднаква сигнатура, класът трябва задължително да предефинира този метод
- В предефинирания метод може експлицитно да се укаже, default-ната имплементация от кой родителски интерфейс да се ползва. В този случай, синтаксисът е, `<имеНаИнтерфейс>.super.<имеНаDefaultМетод>()`

---

### Default методи в интерфейсите (от Java 8)


```java
public interface OptimisticLockable {
    default boolean isLocked() {
        return false;
    }
}
public interface PessimisticLockable {
    default boolean isLocked() {
        return true;
    }
}
public class Door implements OptimisticLockable, PessimisticLockable {

    // We will get a compile-time error, if we don't override the isLocked() method here:
    // - "Door inherits unrelated defaults for isLocked() from types
    //        OptimisticLockable and PessimisticOldLockable"
    @Override
    public boolean isLocked() {
        return OptimisticLockable.super.isLocked();
    }
}
```

---

### Статични методи в интерфейсите (от Java 8)

- От Java 8, интерфейсите могат да съдържат и статични методи с имплементация
- Една класическа употреба е, за *factory* методи (ще говорим за тях в лекцията за design patterns)

---

### Private методи в интерфейсите (от Java 9)

- От Java 9, интерфейсите могат да съдържат и `private` методи с имплементация
- Изполват се, когато в интерфейса има два ли повече default-ни или статични метода, чиято имплементация частично се дублира
    - тогава изнасяме повтарящия се код в `private` метод, за да предотвратим code duplication

---

### Интерфейсите - обобщение

- Интерфейсите могат да съдържат
  - Публични, абстрактни методи без имплементация
  - `static` `final` член-променливи == константи
  - `default` и `static` методи с имплементация (от Java 8)
  - `private` методи (от Java 9)

---

### Интересни частни случаи на интерфейси

- Интерфейс, който
    - не съдържа нито един метод, се нарича *маркерен*
    - има точно един публичен абстрактен метод, се нарича *функционален*

---

### Абстракция

- Абстракция означава, моделирайки в обектно-ориентиран език за програмиране обекти от реалния или виртуалния свят, да се ограничим само до съществените им за конкретната задача характеристики и да се абстрахираме (пропуснем) в модела несъществените или нерелевантни за задачата
    - Пример: моделирайки студент, да го характеризираме само с име и факултетен номер, абстрахирайки се от всички други характеристики на студента в реалния свят (напр. цвят на очите)

---

### Абстракция

- Абстракция също означава да работим с нещо, което знаем как да използваме, без да знаем как работи вътрешно. Всяка конкретна имплементация на поведение е скрита в своя обект, за външния свят е видимо само поведението (т.е. интерфейсът)
- Принципът за абстракция се постига в Java чрез интерфейси и абстрактни класове

---

## Въпроси?

.font-xl[.ri-github-fill.icon-inline[[fmi/java-course](https://github.com/fmi/java-course)]]

.font-xl[.ri-youtube-fill.icon-inline[[MJT2022](https://www.youtube.com/playlist?list=PLew34f6r0PxyUcIg370lv9jHDwFeMDa7e)]]
