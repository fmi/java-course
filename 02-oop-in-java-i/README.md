# Обектно-ориентирано програмиране с Java (част I)

### packages && imports

Пакетите служат за групиране на логически свързани типове. Чрез тях решаваме naming конфликти, като индентифицираме по уникален начин даден тип (клас, интерфейс, enum или анотация) - `org.slf4j.Logger` vs `org.apache.log4j.Logger`. Пакетите също така служат за контролиране на достъпа - може да дефинираме класове с `package-private` visibility, като това ще направи видими въпросните класове само в пакета. Конвенцията за именуване на пакети е използване на малки букви и точка за разделител (`java.lang`, за компаниите е прието да се ползва reversed domain name - `mail.google.com` -> `com.google.mail`, `com.github.myusername.mylibrary`). Класовете от първото упражнение бяха без package declaration и се намираха в така наречения `default package` (подходящ е само за малки тестове).

В даден наш пакет може да използваме клас от друг пакет, като се обърнем към него чрез пълното му име. За да използваме даден клас от друг пакет по така наречения `simple class name`, то трябва да го import-нем. Типовете, които се намират в един и същ пакет, могат да се реферират без `import`. Типовете от `java.lang` се import-ват по подразбиране. Пример:

```java
package playground.main;

public class Developer {

	private String name; // String is from java.lang, no need to import

	public Developer(String name) {
		this.name = name;
	}

	public void sayHi() {
		System.out.printf("Hi, I am %s!%n", name);
	}
}
```

```java
package playground.company;

public class Company {
	
	@Override
	public String toString() {
		return "We hire !!1!";
	}
}
```

```java
package playground.main;

import playground.company.Company;

public class Main {

	public static void main(String[] args) {
		java.time.LocalDateTime now = java.time.LocalDateTime.now(); // using fully qualified name
		System.out.println(now);

		Developer dev = new Developer("John"); // Developer is from the same package, no need to import
		dev.sayHi();

		Company company = new Company(); // with import
		System.out.println(company);
	}
}
```

Чрез `static import` може да import-нем статични член променливи или методи:

```java
package playground.imports;

import static java.lang.Math.PI;
import static java.lang.Math.pow;

public class Circle {

	private double radius;

	public Circle(double radius) {
		this.radius = radius;
	}

	private double calculateArea() {
		return PI * pow(radius, 2);
	}

	public static void main(String[] args) {
		double area = new Circle(4).calculateArea();
		System.out.printf("%.2f%n", area); // 50.27
	}
}

```

### Принципи на ООП

- Енкапсулация - изразява се в скриване на имплементационните детайли на дaден клас от външния свят и предоставяне на изчистен API. Consumer, който извиква метод на наш клас, не би се интересувал от вътрешното представяне на данните в нашия клас, а по-скоро би приел, че извиканият метод ще свърши дадена работа или ще съобщи за грешка/изключение. Private fields, public methods.

- Наследяване - описва взаимовръзка от тип `is-a` (например Student is-a Person). Няма multiple inheritence. Object - коренът на типовата йерархия.

- Полиморфизъм - способността на метод да върши различна работа въз основа на обекта, от който е извикан. Пример:

```java
package playground.shape;

public interface Shape {

	double calculateArea();
}
```

```java
package playground.shape;

public class Rectangle implements Shape {

	private double width;
	private double height;

	public Rectangle(double width, double height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public double calculateArea() {
		return width * height;
	}
}
```

```java
package playground.shape;

public class Triangle implements Shape {

	private double base;
	private double height;

	public Triangle(double base, double height) {
		this.base = base;
		this.height = height;
	}

	@Override
	public double calculateArea() {
		return (base * height) / 2;
	}
}
```

```java
package playground.shape;

public class Main {

	public static void main(String[] args) {
		Shape[] shapes = { new Rectangle(10, 2.4), new Triangle(5, 4) };
		for (Shape shape : shapes) {
			System.out.println(shape.calculateArea());
		}
	}
}
```

[@Override](https://docs.oracle.com/javase/8/docs/api/java/lang/Override.html) - чрез тази анотация указваме на компилатора, че искаме да override-нем метод от superclass или interface. Ако случайно сме объркали сигнатурата на метода, `@Override` ще генерира компилационна грешка, казвайки ни, че методът, който искаме да override-нем, не съществува.

- Абстракция - представлява моделиране на класове от реалния свят спрямо характеристиките им, които са релевантни за домейна на проблема, който решаваме. Например ако моделираме система за управление на студенти, бихме се интересували от факултетния номер и оценките на даден студент, а не от музикалните му предпочитания.

### Access Modifiers

| Modifier    | Class             | Package                  | Subclass                 | World                    |
| ----------- |:-----------------:|:------------------------:|:------------------------:|:------------------------:|
| public      | :heavy_plus_sign: | :heavy_plus_sign:        | :heavy_plus_sign:        | :heavy_plus_sign:        |
| protected   | :heavy_plus_sign: | :heavy_plus_sign:        | :heavy_plus_sign:        | :heavy_multiplication_x: |
| no modifier | :heavy_plus_sign: | :heavy_plus_sign:        | :heavy_multiplication_x: | :heavy_multiplication_x: |
| private     | :heavy_plus_sign: | :heavy_multiplication_x: | :heavy_multiplication_x: | :heavy_multiplication_x: |

### The final keyword

- final class - не може да бъде наследен
- final method - не може да бъде override-нат
- final variable/field - не може да бъде присвоен повече от веднъж

### Overloading vs Overriding

```java
package playground.overloading;

public class Developer {

	private final String name;

	public Developer(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void sayHi() {
		System.out.println("Hi!1!");
	}

	public void sayHi(String to) {
		System.out.printf("Hi %s!%n", to);
	}

	public void sayHi(Developer to) {
		System.out.printf("Hi %s!%n", to.getName());
	}

	public static void main(String[] args) {
		Developer dev = new Developer("John");
		dev.sayHi(); // Hi!1!
		dev.sayHi("Aaron"); // Hi Aaron!
		dev.sayHi(new Developer("Jane")); // Hi Jane!
	}
}
```

```java
package playground.overriding;

public class Actor {

	public double getMana() {
		return 42;
	}
}
```

```java
package playground.overriding;

public class Hero extends Actor {

	@Override
	public double getMana() {
		return 100;
	}

	public static void main(String[] args) {
		Actor actor = new Actor();
		Actor hero = new Hero();

		actor.getMana(); // 42.0
		hero.getMana();  // 100.0
	}
}
```

### java.lang.Object

Всички класове са (преки или косвени) наследници на класа java.lang.Object. Object класът няма родител/superclass. Йерархията от класове е дърво, с `Object` в корена.

- [#equals](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#equals-java.lang.Object-) - посочва дали даден обект е равен на текущия. Предефинираме го, когато искаме да сравним два обекта по смислова еднаквост, а не по референциите им (адресите им в паметта). Когато предефинираме `#equals`, трябва да се уверим, че имплементацията ни е рефлексивна (`x.equals(x)` връща true), симетрична (ако `x.equals(y)` връща true, то и `y.equals(x)` трябва да върне true), транзитивна (ако `x.equals(y)` && `y.equals(z)`, то и `x.equals(z)`) и консистентна (при множество извиквания на `x.equals(y)`, върнатия резултат трябва да бъде един и същ).

- [#hashCode](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#hashCode--) - при предефинирането на #hashCode, ако #equals връща true, hashCode-ът на съответните обекти трябва да е равен. Ако hashCode-ът на два обекта е равен, не е задължително #equals да връща true.

```java
String first = new String("Aa");
String second = new String("BB");
first.hashCode() //65*31^1 + 97*31^0 = 2112
second.hashCode(); // 66*31^1 + 66*31^0 = 2112

first.hashCode() == second.hashCode(); //true
first.equals(second); //false
```

- [#toString](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#toString--)

- [#clone](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#clone--) - прави shallow copy.

```java
package playground.clone;

public class Person implements Cloneable {

	public static void main(String[] args) throws CloneNotSupportedException {
		Person first = new Person();
		Person second = new Person();

		System.out.println(first == second); // false

		Person referenceCopy = first;
		System.out.println(first == referenceCopy); // true, they point the the same address

		Person  cloned = (Person) first.clone();
		System.out.println(first == cloned); // false
	}
}
```

### instanceof

```java
Actor actor = new Actor();
Actor hero = new Hero();

System.out.println(actor instanceof Actor);  // true
System.out.println(actor instanceof Hero);   // false
System.out.println(actor instanceof Object); // true

System.out.println(hero instanceof Actor);   // true
System.out.println(hero instanceof Hero);    // true
System.out.println(hero instanceof Object);  // true

```

### Interfaces

Интерфейсите описват формално поведение. От java 8 се поддържат default и static методи с имплементация. От java 9 - и private методи.

```java
package playground.interfaces;

public interface Printable {

	default void println(String input) {
		System.out.println(input);
	}

	static void printf(String format, Object... args) {
		System.out.printf(format, args);
	}

	private void doInternalWork() {
		System.out.println("Can't see me");
	}

	private static void doInternalStaticWork() {
		System.out.println("Can't see me, right?");
	}
}
```
