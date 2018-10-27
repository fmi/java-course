# Обектно-ориентирано програмиране с Java (част II)

### Interfaces

Когато имаме 2 интерфейса с един и същ default-ен метод, то в имплементационния клас трябва задължително да предоставим имплементация на въпросния метод. Може да извикаме конкретна default-на имплементация от даден интерфейс, използвайки синтаксиса `{Interface}.super.{method}` (например `Likeable.super.saySomething()`).


```java
public interface Likeable {
	public default void saySomething() {
		System.out.println("I like you");
	}
}
```

```java
public interface Lovable {
	public default void saySomething() {
		System.out.println("I love you");
	}
}
```

```java
public class Person implements Lovable, Likeable {

	@Override
	public void saySomething() {
		Likeable.super.saySomething();
	}
}
```

### Wrapper classes

В Java всеки примитивен тип си има свой Wrapper Class: int -> Integer, char -> Character ....
Wrapper класовете се използват за да конвертират всеки примитив в Обект. Както всеки клас в Java,
така и Wrapper класовете наследяват неявно java.lang.Object класа. В практиката често се използват
при колекциите, защото те приемат само обекти (повече за това на следващата лекция).
Java компилаторът може автоматично да конвертира от примитив в Wrappеr Class (autoboxing) и обратно (autounboxing). Поради тази възможнот, Java ни позволява да правим неща като: `Integer number = 5;`

| Примитивен тип | Wrapper клас | Пример за използване |
| -------------- |:------------:| :-------------------:|
| boolean        | Boolean      | new Boolean(true)    |
| byte           | Byte         | new Byte((byte) 1)   |
| short          | Short        | new Short((short) 1) |
| int            | Integer      | new Integer(1)       |
| long           | Long         | new Long(1)          |
| float          | Float        | new Float(1.0)       |
| double         | Double       | new Double(1.0)      |
| char           | Character    | new Character('a')   |
| void           | Void         | -                    |

```java
public class Main {
	public static void main(String[] args) {
		Integer firstNum = 1;
		Integer secondNum = 1;
		
		System.out.println(firstNum.compareTo(secondNum)); // 
		System.out.println(firstNum.equals(secondNum));    // 
		System.out.println(firstNum == secondNum);         // 
	}
}
```

### Йерархия на Wrapper класовете

![hierarchy](http://tinyimg.io/i/a6BPbvk.png)

### Enums

Enum-ите в Java имат функционалността да дефинират изборими множества, както Enum-ите във всеки един език за програмиране + още нещо. В Java всеки един Enum е наследник на java.lang.Enum, който от своя страна ни предоставя методи като values() и valueOf() на готово. Java Enum-ите също като всеки друг клас могат да си имат 
член данни и конструктор (може да бъде само package-private или private). Важно е да отбележим, че всеки Enum очаква в началото, след декларацията да види първо изброимите си множества.

```java
public enum VirtualMachineSize {

	X_SMALL(1, 2048),
	SMALL(2, 4096),
	MEDIUM(4, 8192), 
	LARGE(8, 16384),
	X_LARGE(16, 32768);

	public final int cpu;
	public final int memory;

	VirtualMachineSize(int cpu, int memory) {
		this.cpu = cpu;
		this.memory = memory;
	}

	public int getCpu() {
		return cpu;
	}

	public int getMemory() {
		return memory;
	}
}
```

```java
public class Main {
	public static void main(String[] args) {
		VirtualMachineSize.LARGE.getCpu(); // 8
		VirtualMachineSize.values()[0]; // X_SMALL
		VirtualMachineSize.values()[2]; // MEDIUM
		VirtualMachineSize size = VirtualMachineSize.valueOf("SMALL");
		size.getCpu(); // 2
	}
}
```

### Static

Ключовата дума `static` в Java може да се използва при декларацията на член данни и методи. Static член данните/методите се асоциират със самия клас, а не с неговите инстанции (много различни окръжности можем на начертаем, но всички ползват едно и също PI, т.е PI не се обвързва с конкретна инстанция, а със самия клас Circle). Static метод може да реферира само static член данни и да вика само static методи.

Къде се пазят всички static член данни?

Не са нито в stack-a, нито в heap-a.

Преди Java 8 се пазят в PermGen. След Java 8 се пазят в Metaspace. Може да намерите повече информация [тук](https://dzone.com/articles/java-8-permgen-metaspace).

```java
public class HealthPotion implements Treasure {
	private static final String HEALTH_POTION_FOUND_MESSAGE = "Health potion found! %d health points added to your hero!";
	private static final String HERO_IS_NOT_ALIVE_MESSAGE = "Hero is not alive";

	// omitted

	public String collect(Hero hero) {
		if (hero.isAlive()) {
			hero.takeHealing(heal());
			return String.format(HEALTH_POTION_FOUND_MESSAGE, heal());
		} else {
			return HERO_IS_NOT_ALIVE_MESSAGE;
		}
	}
}
```

### Exceptions

Всеки Exception в Java е наследник на класа Throwable. Съществуват 2 групи изключения:

1) Exceptions
  - Checked Exceptions (Compile time Exceptions) - компилаторът ни задължава да ги обработим или прехвърлим нагоре по веригата (например java.io.IOException).
  - Unchecked Exceptions (Runtime Exceptions) - невидими за компилатора и доста често подсказват, че в кода ни има бъг (например java.lang.IndexOutOfBoundsException). Не е добра практика runtime exception-ите да бъдат прихващани. Хубаво е да си debug-нем кода и да си отстраним бъговете.
2) Errors - при тях нищо не може да се направи и програмата ни се терминира (например java.lang.OutOfMemoryError).

При прихващането на Exception-и е задължително в catch блока да ги изредим от по-конкретния към по-общия.
При наличието на finally блок, без значение развоя на събитията, кодът в него \*винаги се изпълнява след try-catch секцията.

```java
public class CustomException extends Exception {

	public CustomException(String message) {
		super(message);
	}
}
```

```java
public void iWillHandleTheException() {
	try {
		iDoNotKnowWhatToDoWithThisException();
	} catch (CustomException e) {
		System.out.println("The exception finally is gone");
	}
}

public void iDoNotKnowWhatToDoWithThisException() throws CustomException {
	throwException();
}

public void throwException() throws CustomException {
	throw new CustomException("Bad Exception");
}
```

```java
public static int getNumber() {	
	try {
		throw new Exception();
		//return 1;  -> Unreachable
	} catch (Exception e) {
		return 2;
	} finally {
		return 3;
	}
}

public static void main(String[] args) {
	getNumber(); 
}
```

```java
public static void terminate() {
	try {
		throw new Exception();
	} catch (Exception e) {
		System.out.println("I am in catch block");
		System.exit(0);
	} finally {
		System.out.println("I am in finally block");
	}
}

public static void main(String[] args) {
	terminate(); 
}
```
