# Count Triples

### Условие

Три поредни еднакви символа в даден низ наричаме `тройка`. Например в низа `aBBBcd` имаме тройката от символи `BBB`. Създайте публичен клас `Counter` с метод

```java
public int countTriples(String str)
```
който по даден низ от символи `str` намира броя на тройките в него. Тройките могат да се застъпват. Сравнението между символите трябва да бъде case sensitive (т.е `'a' != 'A'`).

### Примери

| Вход            | Изход |
| --------------- |:-----:|
| "aBBBcdee"      | 1     |
| "AAAbCCCCdef"   | 3     |
| "a"             | 0     |

### The hard way

Както може би сте забелязали, в тази директория освен условието се намира и още един файл - `SampleCounterTest.java`. Към всяка задача ще получите тестове, които покриват сценариите от примерите в условието. След крайния срок на задачата ще публикуваме и пълния набор от тестове и авторско решение. Преди да submit-нете вашето решение в [grader.sapera.org](http://grader.sapera.org), препоръчваме ви да изпълните примерните тестове локално при вас (през IDE/command line) и да се уверите, че вашето решение покрива базовите случаи. Чрез стъпките долу може да изпълните тестовете в `SampleCounterTest.java`.

```bash
# Създаваме си директория за конкретната задача
$ mkdir 01-count-triples
$ cd 01-count-triples

# Създаваме си следните поддиректории
$ mkdir bin                # Ще съдържа компилираните binaries (.class files)
$ mkdir src                # Ще съдържа source файлове (.java files)
$ mkdir test               # Ще съдържа тестови файлове
$ mkdir lib                # Ще съдържа външни библиотеки (напр. junit)

$ touch src/Counter.java   # Ще съдържа нашето решение
```

Отваряме `Counter.java` с любим текстов редактор и добавяме следния код фрагмент в него:
```java
public class Counter {
	
	public int countTriples(String str) {
		return 0;
	}
}

```

[JUnit](https://junit.org/junit5/) е библиотека за unit тестване. Ще я използваме доста често по време на курса. За нашите цели е нужно да си свалим binary-тата на тази библиотека, за да я използваме. Сваляме последна версия на junit от [тук](https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.3.1/junit-platform-console-standalone-1.3.1.jar).

```bash
# Сваления jar файл го местим в lib директорията
$ mv junit-platform-console-standalone-1.3.1.jar lib/

# Сваляме също SampleCounterTest.java и го местим в test директорията
$ mv SampleCounterTest.java test/

# Компилираме си решението и тестовете
$ javac -cp lib/*.jar -d bin/ src/Counter.java ./test/SampleCounterTest.java

# Получените .class файлове отиват в bin/ директорията
```

```bash
# Да изпълним sample тестовете с нашето решение
$ java -jar lib/*.jar --class-path bin/ --scan-class-path

╷
├─ JUnit Jupiter ✔
└─ JUnit Vintage ✔
   └─ SampleCounterTest ✔
      ├─ testCountTriples_WithoutTriples ✔
      ├─ testCountTriples_WithThreeTriples ✘ expected:<3> but was:<0>
      └─ testCountTriples_WithSingleTriple ✘ expected:<1> but was:<0>
[...]
```
Разбира се, 2 от трите ни теста не минават, понеже предоставихме dummy имплементация. Разбира се, че може да ползвате IDE - идеята ни просто беше да покажем какви стъпки скрива IDE-то и какво се случва под капака.
