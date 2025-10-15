class: center, middle

# Обектно-ориентирано програмиране с Java

![Java OOP](images/02.1-oop.jpg)

15.10.2025

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

- Родено е от необходимостта за по-добър контрол над едновременната модификация на споделени данни
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

    public static double sumRec(int n) {
        if (n == 0) {
            return 1.0;
        } else {
            return 1.0 / Math.pow(2, n) + sumRec(n - 1);
        }
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
- Ако искаме клас да има достъп до клас в някой друг пакет, трябва или експлицитно да го заявим с `import` декларация, която поставяме над декларацията на класа, или да се позоваваме до него с пълното му име, което включва и пакета му (не се препоръчва).
- `import` декларацията може да е за конкретен клас с пълното му име: име на пакет + `.` + име на клас, или на всички класове в даден пакет (т.нар. *wildcard import*: име на пакет + `.*`, което обаче е лоша практика откъм clean code)

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
        // java.util.Arrays.sort(tokens); // the ugly alternative
        System.out.println(tokens);
    }

}
```

---

### Достъп до клас от друг пакет

- Статичните член-данни и методи се достъпват с префикс: името на класа, който ги съдържа и точка
<br/>
`double r = Math.cos(Math.PI * 10.0);`
- `import static` конструкцията позволява статичните членове да се ползват директно по името си

<br/>

```java
import static java.lang.Math.cos;
import static java.lang.Math.PI;

...

double r = cos(PI * 10.0);

```

---

### Достъп до клас от друг пакет

- Прието е `import`-ите да се подреждат в сортиран лексикографски ред по `<package>.<class>`
- По-чисто е да се изброят конкретните класове от пакета, вместо wildcard import (тип `import java.util.*;`)
    - IDE-тата обаче автоматично "collapse"-ват `import`-ите от един и същи пакет над някаква бройка - изключете тази "екстра" 

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

Енкапсулацията адресира основния проблем, мотивирал създаването на ООП: по-добро управление на достъпа до споделени данни.

---

### Енкапсулация

- Множеството текущи стойности на член-данните на даден обект се наричат негово *състояние*
- Само вътрешните методи на даден обект имат достъп до неговото състояние, като правят невъзможни неочакваните промени на състоянието от външния свят
- В Java се постига чрез модификаторите за достъп

---

### Енкапсулация – пример за нарушаване

```java
public class BankAccount {
    public double balance; // нарушена енкапсулация!

    public BankAccount(double balance) {
        this.balance = balance;
    }
}

public class Main {
    public static void main(String[] args) {
        BankAccount acc = new BankAccount(1000);
        acc.balance -= 500; // всеки може да променя баланса директно!
        System.out.println("Баланс: " + acc.balance);
    }
}
```

---

### Енкапсулация – пример за спазване

```java
public class BankAccount {
    private double balance; // скрито състояние

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount > 0) balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public double getBalance() {
        return balance;
    }
}

public class Main {
    public static void main(String[] args) {
        BankAccount acc = new BankAccount(1000);
        acc.withdraw(500); // само чрез метод!
        System.out.println("Баланс: " + acc.getBalance());
    }
}
``
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

- Употребява се за:
  - достъпване на член-променливи на родителя
  - извикване от конструктор в текущия клас на конструктор в родителския клас
  - извикване на произволен метод на родителския клас
- Не нарушава енкапсулацията - през `super` може да достъпим само `public` и `protected` членове на родителския клас
- За разлика от `this`, не се предава като имплицитен параметър на нестатичните методи и конструкторите. JVM-ът знае йерархията от класове по време на изпълнение и може да достъпи елементите на родителския клас директно, без да му трябва референция

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

### Flexible Constructor Bodies (since Java 25, preview in Java 22 & 23)

- Преди Java 22, ако в конструктор има извикване на друг конструктор с `this(...)` или `super(...)`, това извикване трябва да е първият statement в тялото на конструктора
- От Java 22 това ограничение отпада
- От Java 23 е възможно и да *инициализираме* полета на конструиращия се в момента обект
- Кодът преди `this(...)` или `super(...)` се нарича *пролог*
- Кодът след `this(...)` или `super(...)` или в тяло на конструктор без тях се нарича *епилог*
- Кодът в пролога може да инициализира полета, но не трябва да достъпва/чете полета на класа и не трябва да вика нестатични методи на класа

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

### Primitive types in pattern matching (since Java 23)

```java
int value = ...
if (value instanceof byte b && b > 0) {
  System.out.println("b = " + b);
}
```

<br/>

Семантиката е: "ако стойността на value може да се съхрани в byte променлива и е положителна, то я присвои на b и я изведи"

---

### Ключовата дума `final`

- в декларация на променлива → прави я константа
- в декларация на метод → методът не може да се override-ва
- в декларация на клас → класът не може да се наследява

---

### Наследяване vs. Композиция

- **Наследяване** (Inheritance) и **композиция** (Composition) са два основни подхода за изграждане на сложни обекти и преизползване на код в ООП
- И двата имат своите предимства и недостатъци.

---

### Наследяване (Inheritance)

- Позволява на един клас (наследник) да наследи състояние и поведение от друг клас (родител)
- Използва се, когато има естествена йерархия: връзка тип „е вид“ (*is-a*)
- Пример: `public class Student extends Human`
- **Предимства:**
  - Лесно повторно използване на код
  - Позволява полиморфизъм
- **Недостатъци:**
  - Води до силно свързване между класовете
  - Промени в родителския клас могат да повлияят на всички наследници
  - Не е подходящо, ако връзката не е от тип „е вид“.

---

### Композиция (Composition)

- Класът съдържа други обекти като свои членове (*has-a* връзка)
- Използва се, когато искаме да изградим сложни обекти чрез комбиниране на по-прости
- Пример:
  ```java
  public class Car {
      private Engine engine;
      private Wheel[] wheels;
  }
  ```

---

### Композиция (Composition)

- **Предимства:**
  - По-слабо свързване между класовете
  - По-голяма гъвкавост – може да сменяме компоненти
  - Улеснява преизползването на код
- **Недостатъци:**
  - Може да изисква повече код за делегиране на методи.

---

### Наследяване vs. Композиция: кога кое да избереш?

- **Използвай наследяване**, когато има ясно изразена „е вид“ (*is-a*) връзка и класът-наследник наистина е специализация на родителя
- **Използвай композиция**, когато искаш да комбинираш функционалности, да избегнеш твърдо фиксирана йерархия или да имаш по-гъвкав и лесен за поддръжка код
- Принципът **„предпочитай композиция пред наследяване“** (*"Favor composition over inheritance"*) е широко препоръчван в съвременното ООП
- Наследяването е мощен инструмент, но може да доведе до твърде сложни йерархии и проблеми при промени
- Композицията дава повече гъвкавост и улеснява поддръжката на кода
- Винаги се питай: *„Това наистина ли е специален случай на ... или просто използва ...?“*.

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
Полиморфният код е не само по-кратък и четим. Помислете, как трябва да се променят двата фрагмента код, ако в бъдеще се появят нови класове – наследници на `Human`

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
- В предефинирания метод може експлицитно да се укаже, default-ната имплементация от кой родителски интерфейс да се ползва. В този случай, синтаксисът е, `<interfaceName>.super.<defaultMethodName>()`

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
    //        OptimisticLockable and PessimisticLockable"
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

.font-xl[.ri-youtube-fill.icon-inline[[MJT2026](https://www.youtube.com/playlist?list=PLew34f6r0Pxx6LmzYcc9-8-_-T3ZPZTXg)]]
