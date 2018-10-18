# Обектно-ориентирано програмиране с Java (част I)

### packages && imports

Пакетите служат за групиране на логически свързани типове. Чрез тях решаваме naming конфликти, като индентифицираме по уникален начин даден тип (клас, интерфейс, enum или анотация) - `org.slf4j.Logger` vs `org.apache.log4j.Logger`. Пакетите също така служат за контролиране на достъпа - може да дефинираме класове с `package-private` visibility, като това ще направи видими въпросните класове само в пакета. Конвенцията за именуване на пакети е използване на малки букви и точка за разделител (`java.lang`, за компаниите е прието да се ползва reversed domain name - `mail.google.com` -> `com.google.mail`, `com.github.myusername.mylibrary`). Класовете от първото упражнение бяха без package declaration и се намираха в така наречения `default package` (подходящ е само за малки тестове).

В даден наш пакет може да използваме клас от друг пакет, като се обърнем към чрез пълното му име. За да. използваме даден клас от друг паке по `simple clss name`, то трябва да го import-нем. Типовете, които се намират в един и същ пакет, могат да се реферират без `import`. Типовете от `java.lang` се import-ват по подразбиране. Пример:

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

### #equals vs ==

### instanceof

### abstract

### java.lang.Object
