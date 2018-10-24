# Обектно-ориентирано програмиране с Java (част II)

### Interfaces

```java
public interface Likeable {
	public default void saySomething() {
		System.out.println("I like you");
	}
}
```

```java
public interface Loveable {
	public default void saySomething() {
		System.out.println("I love you");
	}
}
```

```java
public class Person implements Loveable, Likeable {

	@Override
	public void saySomething() {
		Likeable.super.saySomething();
	}
}

```


### Wrapper classes

В Java всяка променлива си има свой Wrapper Class: int -> Integer, char -> CHARACTER ....
Wrapper класовете се използват за да конвертират всеки примитив в Обект. Както всеки клас в Java,
така и Wrapper класовете наследяват неявно java.lang.Object класа. В практиката често се използват
при колекциите, защото те приемат само обекти(Повече за това на следващата лекция).
Java компилаторът може автоматично да конвертира от примитив в Wrapepr Class и обратно(autoboxing и autounboxing). Поради тази възможнот, Java ни позволява да правим неща като: Integer number = 5;


```java
public class Main {
	public static void main(String[] args) {
		Integer firstNum = 1;
		Integer secondNum = 1;
		
		System.out.println(firstNum.compareTo(secondNum)); //???
		System.out.println(firstNum.equals(secondNum));    //???
		System.out.println(firstNum == secondNum);         //???
	}
}
```

### Enums

Enum-ите в Java имат функционалността да дефинират изборими множества, както Enum-ите във всеки един език за програмиране + още нещо. В Java всеки един Enum е наследник на java.lang.Enum, който от своя страна ни предоставя методи като values() и valueOf() на готово. Java Enum-ите също като всеки друг клас могат да си имат 
член данни и конструктур. Важно е да отбележим, че всеки Enum очаква в началото, след scope-a на класа да види първо изборимите си множества.


```java
public enum VirtualMachineSize {
	
	X_SMALL(1, 256),
	SMALL(1, 512),
	MEDIUM(2, 1024), 
	LARGE(3, 2048),
	X_LARGE(4, 4096);
	
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
		VirtualMachineSize.LARGE.getCpu(); //3
		VirtualMachineSize.values()[0]; // X_SMALL
		VirtualMachineSize.values()[2]; // MEDIUM
		VirtualMachineSize size = VirtualMachineSize.valueOf("SMALL");
		size.getCpu(); // 1	
	}
}
```

###Static 

Static в Java може да стои общо взето при декларацията на член данни и методи. Static член данните(Методите) се асоциират със самия клас, а не с неговите инстанции(Много различни окръжности можем на начертаем, но всички ползват едно и също PI). Static метод може да приема само static член данни и да вика само static методи от външния свят(извън своя scope).

Къде се пазят всички static член данни?

Не са нито в stack-a, нито в heap-a.

Преди Java 8 са се пазили в PermGen.
След Java 8 се пазят в Metaspace.

Това представляват късчета памет, различни от познатите ни stack и heap, които пазят всички данни асоциирани с класовете. Нуждата от промяна след Java 8 възниква, защото PermGem се е заделял автоматично преди старт на JVM-a. По този начин друдно може да се прецени нужните ресурси, които трябва да му се заделят. Metaspace представлява resizing структура, която автоматично се resize-ва при достигане на нейния капацитет.


```java
public class HealthPotion implements Treasure {
	private static final String HEALTH_POTION_FOUND_MESSAGE = "Health potion found! %d health points added to your hero!";
	private static final String HERO_IS_NOT_ALIVE_MESSAGE = "Hero is not alive";

	---------------------------------------------

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

###Exceptions

Всеки Exception в Java е наследник на класа Throwable. Съществуват 3 групи Exception-и.

1) Checked Exceptions(Compile time Exceptions): Компилаторът ни задължава да ги обработим или прехвърлим нагоре по веригата(IOException).
2) Unchecked Exceptions(Runtime Exceptions): Невидими за компилаторът и доста често подсказват, че кодът ни не работи правилно в определени сценарии(IndexOutOfBoundsException).
3) Errors: При тях нищо не може да се направи и програмата ни се терминира(OutOfMemoryError Exception).

При улавянето на Exception-и е важно в catch блока да ги редуваме от по- конкретна към по- обща.

```java
public class CustomException extends Exception{
	
	public CustomException(String message) {
		super(message);
	}
	
}
```

```java
public void throwException() throws CustomException {
		throw new CustomException("Bad Exception");
	}
	
	public void iDoNotKnowWhatToDoWithThisException() throws CustomException {
		throwException();
	}
	
	public void iWillHandleTheException() {
		try {
			iDoNotKnowWhatToDoWithThisException();
		} catch (CustomException e) {
			System.out.println("The exception finally is gone");
		}
	}
```
