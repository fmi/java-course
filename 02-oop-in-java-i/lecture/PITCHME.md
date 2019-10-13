## Обектно-ориентирано програмиране с Java

#### Част I

_16.10.2019_

---

#### Предната лекция говорихме за:

@ul

- Tиповете данни
- Оператори, изрази, statements
- Условия и разклонения
- Итерация / Цикли
- Низове и операции с тях
- Масиви
- Функции

@ulend

---

#### Днес ще разгледаме:

@ul

- Класове и обекти
- Абстрактни класове и интерфейси
- Фундаменталните ООП принципи
  - Енкапсулация
  - Наследяване
  - Полиморфизъм
  - Абстракция

@ulend

---

#### Обектно-ориентирано програмиране

@ul

- Родено е от необходимостта за по-добър контрол над конкурентната модификация на споделени данни
- Основната идея е, да няма директен достъп до данните, а те да се достъпват през нарочен за целта слой код
- Понеже данните трябва да се предават и модифицират, се ражда концепцията за обект
- Обект в най-общ смисъл е множество данни, които могат да се предават и достъпват само чрез множество методи, които се подават заедно с данните
- Данните съставляват *състоянието на обекта*, а методите съставляват *поведението на обекта*
- Състоянието на обекта е скрито (*енкапсулирано*) от директен достъп
- Възможно е даден клас да няма състояние или да няма поведение

@ulend

---

#### Клас

@ul

- Клас – дефиниция на (клас от) обекти
  - описва състояние чрез член-данни (член-променливи)
  - описва поведение чрез методи 
- Конструктор(и)
- Метод(и)
- Всеки клас има определен *интерфейс* - формално описание на начина, по който други обекти могат да взаимодействат с него

@ulend

---

#### Методи на клас

@ul

– функция(и) за манипулиране на член-променливите на класа
  - име и списък от параметри (*сигнатура*)
  - броят на параметрите се нарича *арност*
  - два метода имат еднаква сигнатура, ако имат еднакви имена, еднаква арност и еднаква последователност от типове с списъка от параметри
  - тяло, оградено с {} и тип на връщаната стойност сигнатура и тяло
  - тялото може да съдържа декларации на локални променливи и statements
  - може да има странични ефекти

@ulend

---

#### Varargs

@ul

- Специален вид параметър, който се декларира от тип, следван от три точки (многоточие)
- Нарича се *varargs* от *variable arguments*
- Ако метод има varargs параметър, той трябва да е един и да е последен в списъка
- При достигане до varargs параметър, компилаторът създава масив от останалите аргументии и ги подава на метода като масив

@ulend

---

#### Ключовата дума this

@ul

- Референция към конкретния обект
- Неявно се подава като параметър на всеки конструктор и метод на класа
- Употребява се за:
  - достъпване на член-променливи, "скрити" от едноименни параметри на метод или локални променливи
  - извикване от конструктор на друг overloaded конструктор в същия клас
  - извикване на произволен метод на класа

@ulend

---

#### Програма на Java

@ul

- Състои се от един или повече класове
- За да е изпълнима, поне един от класовете трябва да има `main` метод - метод с конкретна сигнатура и тип
- Този клас се подава като параметър на java командата при стартиране на JVM
- От командният ред може също да се подават аргументи на `main` метода, който ги получава като масив от низове
- JVM-ът зарежда класа в паметта, намира `main` метода му и го изпълнява
- Ако JVM-ът се натъкне на statement, който изисква изпълнението на метод на друг клас, този клас (неговият .class файл) също се зарежда в паметта и се изпълнява съответният метод

@ulend

---

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

#### Обект

Обектът е конкретна инстанция на даден клас.

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

#### Пакет

@ul

- Именувани групи от семантично свързани класове
- Служат за йерархично организиране на кода
- Съответстват на директорно дърво на файловата система
- Конвенция за именуване:
  - само малки букви, точка за разделител
  - компаниите използват обърнат домейн адрес
    - mail.google.com -> com.google.mail

@ulend

---

#### Достъп до клас от друг пакет

@ul

- Всеки клас има достъп по подразбиране (имплицитно) до:
  - класовете от собствения си пакет
  - класовете в пакета `java.lang`
- Ако искаме клас да има достъп до клас в някой друг пакет, трябва експлицитно да го заявим с `import` декларация, която поставяме над декларацията на класа.

@ulend

---

```java
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

#### Достъп до клас от друг пакет (2)

@ul

- Прието е да `import`-ите да се подреждат в сортиран ред по `<package>.<class>`
- По-чисто е да се изброят конкретните класове от пакета вместо `import java.util.*;`

@ulend

---

#### Модификатори за достъп

За клас*

<table style="border: 1px solid #ddd">
  <tr style="font-size:0.8em">
    <td style="border: 1px solid #ddd">public</td>
    <td style="border: 1px solid #ddd">Достъпен за всеки клас във всеки пакет</td>
  </tr>
  <tr style="font-size:0.8em">
    <td style="border: 1px solid #ddd">без модификатор ("package-private", "default")</td>
    <td style="border: 1px solid #ddd">Достъпен само за класовете в собствения си пакет</td>
  </tr>
</table>

<p style="font-size:0.5em">* top-level клас, т.е. невложен в друг</p>

---

#### Модификатори за достъп (2)

За член-променливи и методи на клас

| Modifier    | Class             | Package                  | Subclass                 | World                    |
| ----------- |:-----------------:|:------------------------:|:------------------------:|:------------------------:|
| public      | yes               | yes                      | yes                      | yes                      |
| protected   | yes               | yes                      | yes                      | no                       |
| no modifier | yes               | yes                      | no                       | no                       |
| private     | yes               | no                       | no                       | no                       |

---

#### Енкапсулация

@ul

- Множеството текущи стойности на член-данните на даден обект се наричат негово *състояние*
- Само вътрешните методи на даден обект имат достъп до неговото състояние, като правят невъзможни неочакваните промени на състоянието от външния свят.
- Енкапсулацията адресира основният проблем, мотивирал създаването на ООП: до-добро менажиране на конкурентния достъп до споделени данни
- В Java се постига чрез модификаторите за достъп.

@ulend

---

#### Енкапсулация – пример за нарушаване

```java
public class Human {

    public String name;

    public Human(String name) {
        this.name = name;
    }
}

public class Main {

    public static void main(String[] args) {
        Human ivan = new Human("Ivan");
        ivan.name = "Faked Ivan";
    }
}
```

@snap[south span-100]
@[3-4](No encapsulation)
@[14-15](Hmm..)
@snapend

---

#### Енкапсулация – пример за спазване

```java
public class Human {
    
    private String name;

    public Human(String name) {
        this.name = name;
    }    
}

public class Main {

    public static void main(String[] args) {
        Human ivan = new Human("Ivan");
        ivan.name = "Faked Ivan";
    }
}
```

@snap[south span-100]
@[3-4](Stays hidden.)
@[14-15](Won't compile)
@snapend

---

#### Наследяване

@ul

- Позволява преизползване и разширяване на състояние и поведение на вече съществуващи класове
- Когато клас наследява друг клас, за него казваме, че е *наследник*, или *дете* или *подклас*, а вторият клас наричаме *родител*, или *базов клас* или *суперклас*
- В Java се реализира с ключовата дума `extends`
- Класът-наследник получава достъп до всички public и protected член-променливи и методи на родителския клас
- Класът-наследник може да предостави собствена дефиниция на (т.е. да предефинира) методи на родителския клас (*method overriding*).

@ulend

---

#### Наследяване (2)

@ul

- Модификаторът за достъп на метод в класа–наследник трябва да съвпада или да разширява модификатора за достъп на метода в родителския клас (но не може да я свива/ограничава)
- Java не поддържа множествено наследяване

@ulend

---

#### Ключовата дума super

@ul

- Референция към прекия родител на обекта
- Употребява се за:
  - достъпване на член-променливи на родителя
  - извикване от конструктор в текущия клас на конструктор в родителския клас
  - извикване на произволен метод на родителския клас

@ulend

---

#### Ключовата дума super (2)

@ul

- Неявно се подава като параметър на всеки конструктор и метод на класа
- Не нарушава енкапсулацията - през `super` може да достъпим само `public` и `protected` членове на родителския клас

@ulend

---


```java
public class Student extends Human {

    private int facultyNumber;

    public Student(String name, int facultyNumber) {
        super(name);
        this.facultyNumber = facultyNumber;
    }

    public static void main(String[] args) {
        Student ivan = new Student("Ivan", 61786);
        ivan.whoAmI();
    }
}
```

@snap[south span-100]
@[6-6](Извикване на родителски конструктор)
@[12-12](Наследен от родителя метод)
@snapend

---

#### Йерархията от класове

@ul

- Всички класове в Java са (преки или косвени) наследници на класа `java.lang.Object`
- Липсата на множествено наследяване означава, че всеки клас има точно един родител (с изключение на един-единствен клас,  `java.lang.Object`, който няма родител).
- Йерархията от класове е дърво, с `java.lang.Object` в корена

@ulend

---

#### Операторът instanceof

@ul

- Използва се за type checking - дали даден обект е инстанция на даден клас

@ulend

---

```java
Student ivan = new Student("Ivan", 61786);
Human petar = new Human("Petar");
        
System.out.println(ivan instanceof Student);   // true
System.out.println(ivan instanceof Human);     // true
System.out.println(petar instanceof Student);  // false
System.out.println(petar instanceof Human);    // true

System.out.println(null instanceof AnyClass);
System.out.println(ref instanceof Object);
```

@snap[south span-100]
@[9-9](false for any class: null is not an instance of anything)
@[10-10](true for any non-null ref, because any class extends java.lang.Object)
@snapend

---

#### Ключовата дума final

- в декларация на променлива -> прави я константа
- в декларация на метод -> методът не може да се override-ва
- в декларация на клас -> класът не може да се наследява

---

#### Полиморфизъм

- От гръцки poly (много) + morphe (форма)
- Дефиниция от биологията - съществуване на морфологично различни индивиди в границите на един вид
- В контекста на ООП, полиморфизъм е способността на даден обект да се държи като инстанция на друг клас или като имплементация на друг интерфейс

---

#### Полиморфизъм (2)

- ООП - наследниците на даден клас споделят поведение от родителския клас, но могат да дефинират и собствено поведение
- Всички Java обекти са полиморфични, понеже всеки обект наследява `Object` класа

---

#### Method overriding vs method overloading

@ul

- Overriding - класът-наследник предефинира поведението на класа-родител
- Overloading - класът декларира методи с едно и също име и различен брой и/или тип параметри 

@ulend

---

#### Runtime полиморфизъм чрез method overriding

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

#### Compile-time полиморфизъм чрез method overloading

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

#### Method overriding vs method overloading

<table>
  <tr>
    <th></th>
    <th style="font-size:0.7em">Overloading</th>
    <th style="font-size:0.7em">Overriding</th>
  </tr>
  <tr style="font-size:0.7em">
    <td>Кога</td>
    <td>Compile-time</td>
    <td>Runtime</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>Къде</td>
    <td>В същия клас</td>
    <td>В класовете - наследници</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>Runtime performance</td>
    <td>Better</td>
    <td></td>
  </tr>
  <tr style="font-size:0.7em">
    <td>Return type</td>
    <td>Може да бъде различен</td>
    <td>Запазва се</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>static, private & final methods</td>
    <td>Да</td>
    <td>Не</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>Binding</td>
    <td>Статично</td>
    <td>Динамично</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>Списък от аргументи</td>
    <td>Различен</td>
    <td>Запазва се</td>
  </tr>
</table>

---

#### Non-polymorphic code


```java
Student ivan = new Student("Ivan", 61786);
Human petar = new Student("Petar", 74451);

Object[] objs = { ivan, petar };

for (Object obj : objs) {
    if (obj instanceof Student) {
        ((Student) obj).whoAmI();
    } else if (obj instanceof Human) {
        ((Human) obj).whoAmI();
    }
}
```

@snap[south span-100]
@[7-11](instanceof and explicit casts are the "red lights")
@snapend

---

#### Polymorphic code

```java
Human[] humans = { ivan, petar };

for (Human human : humans) {
    human.whoAmI();
}
```

---

#### Абстрактни класове

@ul

- Дефинират се с модификатора `abstract`
- Могат да имат методи без имплементация, които се декларират с модификатора abstract
- Не са напълно дефинирани (оставят на наследниците си да ги конкретизират/допълнят) 
  - -> не могат да се създават обекти от тях

@ulend

---

#### Абстрактни класове - пример

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

@snap[south span-100]
@[1-11](abstract class Cat)
@[13-17](class DomesticCat)
@[19-23](class Leopard)
@snapend

---

#### Интерфейси

@ul

- Съвкупност от декларации на методи без имплементация
- Описва формално поведение без да го имплементира
- Може да съдържа static final член-променливи == константи
- Java 8 - може да съдържат също `default` и `static` методи с имплементация
- от Java 9 – и private методи

@ulend

---

#### Интерфейси - пример

```java
public interface Animal {
    public void move();
    public void communicate();
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

@snap[south span-100]
@[1-4](interface Animal)
@[6-18](class Human)
@[20-27](class Leopard)
@snapend

---

#### `java.lang.Object`

```java
boolean equals(Object obj)
int hashCode()
String toString()
Object clone()
```

---

#### `equals()`

@ul

- Трябва да го предефинираме, ако сравняваме два обекта за семантична (т.е. смислова) еднаквост, а не по референциите им (т.е. адреса им в паметта).
- Например, две инстанции на клас Student смислово са еднакви (отговарят на един и същи студент), ако факултетните им номера са еднакви – без значение дали референциите са еднакви или не.

@ulend

---

#### `hashCode()`

@ul

- Трябва да го предефинираме, ако сме предефинирали equals()
- При предефинирането на `hashCode()`, ако `equals()` връща `true`, `hashCode`-ът на съответните обекти трябва да е равен
- Ако `hashCode`-ът на два обекта е равен, не е задължително `equals()` да връща `true`

@ulend

---

#### Видове памет на JVM-a - stack и heap

<table style="width:100%">
  <tr>
    <th style="width:50%; font-size:0.7em">Stack</th>
    <th style="width:50%; font-size:0.7em">Heap</th>
  </tr>
  <tr style="font-size:0.7em">
    <td>
        <ul>
            <li>литерали и променливи от примитивните типове</li>
            <li>параметри и локални променливи в методи</li>
            <li>референции към обекти</li>
        </ul>
    </td>
    <td>
        <ul>
            <li>обекти (инстанции на класове)</li>
            <li>масиви</li>
        </ul>
    </td>
  </tr>
  <tr style="font-size:0.7em">
    <td>
        <ul>
            <li>заделяне: при извикване на метод</li>
            <li>освобождаване: при приключване на изпълнението на метод</li>
        </ul>
    </td>
    <td>
        <ul>
            <li>заделяне: с оператора new</li>
            <li>освобождаване: от JVM Garbage Collector-a</li>
        </ul>
    </td>
  </tr>
  <tr style="font-size:0.7em">
    <td>
        <ul>
            <li>с кратък живот (method invocation)</li>
            <li>бърз достъп</li>
            <li>не се фрагментира</li>
        </ul>
    </td>
    <td>
        <ul>
            <li>обикновено по-дълъг живот</li>
            <li>по-бавен достъп</li>
            <li>може да се фрагментира</li>
        </ul>
    </td>
  </tr>
</table>

---

## Въпроси

@snap[south span-100]
@fab[github] [fmi/java-course](https://github.com/fmi/java-course)
@snapend
