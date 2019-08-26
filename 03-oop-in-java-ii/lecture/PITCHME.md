## Обектно-ориентирано програмиране с Java

#### Част II

_23.10.2018_

---

#### Предната лекция говорихме за:

@ul

- Класове и обекти
- Абстрактни класове и интерфейси
- Фундаменталните ООП принципи
- java.lang.Object
- Stack vs. heap

@ulend

---

#### Днес ще разгледаме:

@ul

- Wrapper types
- Статични член-променливи и статични методи
- Enums
- Изключения

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

### Wrapper types

---

#### Примитивни типове и wrapper типове

<table>
  <tr>
    <th style="font-size:0.8em">Тип данни</th>
    <th style="font-size:0.8em">Размер</th>
    <th style="font-size:0.8em">Минимум</th>
    <th style="font-size:0.8em">Максимум</th>
    <th style="font-size:0.8em">Wrapper</th>
  </tr>
  <tr style="font-size:0.6em">
    <td>boolean</td>
    <td>-</td>
    <td>-</td>
    <td>-</td>
    <td>Boolean</td>
  </tr>
  <tr style="font-size:0.6em">
    <td>char</td>
    <td>16 бита</td>
    <td>Unicode 0</td>
    <td>Unicode 2<sup>16</sup> - 1</td>
    <td>Character</td>
  </tr>
  <tr style="font-size:0.6em">
    <td>byte</td>
    <td>8 бита</td>
    <td>-128</td>
    <td>+127</td>
    <td>Byte</td>
  </tr>
  <tr style="font-size:0.6em">
    <td>short</td>
    <td>16 бита</td>
    <td>-2<sup>15</sup></td>
    <td>+2<sup>15</sup> - 1</td>
    <td>Short</td>
  </tr>
  <tr style="font-size:0.6em">
    <td>int</td>
    <td>32 бита</td>
    <td>-2<sup>31</sup></td>
    <td>+2<sup>31</sup> - 1</td>
    <td>Integer</td>
  </tr>
  <tr style="font-size:0.6em">
    <td>long</td>
    <td>64 бита</td>
    <td>-2<sup>63</sup></td>
    <td>+2<sup>63</sup> - 1</td>
    <td>Long</td>
  </tr>
  <tr style="font-size:0.6em">
    <td>float</td>
    <td>32 бита</td>
    <td>IEEE754</td>
    <td>IEEE754</td>
    <td>Float</td>
  </tr>
  <tr style="font-size:0.6em">
    <td>double</td>
    <td>64 бита</td>
    <td>IEEE754</td>
    <td>IEEE754</td>
    <td>Double</td>
  </tr>
  <tr style="font-size:0.6em">
    <td>void</td>
    <td>-</td>
    <td>-</td>
    <td>-</td>
    <td>Void</td>
  </tr>
</table>

---

#### Къде се ползват wrapper типовете?

- Където синтаксисът на езика изисква обект, а не примитивен тип.
- Когато ви трябват константи или помощни функции, които са имплементирани в съответния wrapper клас.

<br />

```java
Integer.MAX_VALUE
Integer.MIN_VALUE
new Integer(25).intValue()
Integer.parseInt(String)
```

@snap[south span-100]
@[1-1](максималната стойност на типа)
@[2-2](минималната стойност на типа)
@[3-3](връща int стойността, "опакована" в дадената инстанция)
@[4-4](конвертира низ с текстово представяне на цяло число към int)
@snapend

---

#### Autoboxing

```java
// primitive type
char c = 'a';

// wrapper class
Character ch1 = new Character('a');

// autoboxing: char implicitly converted to Character
Character ch2 = 'x';

// autounboxing: Character instance implicitly converted to char
char c2 = ch1;
```

@snap[south span-100]
@[1-2](инициализиране на променлива от тип char с литерал)
@[4-5](инициализирне на Character обект с литерал)
@[7-8](autoboxing)
@[10-11](autounboxing)
@snapend

---

#### Числа с голяма/произволна* точност

- java.math.BigInteger -> цели числа
- java.math.BigDecimal -> реални числа с десетична точка
    - add(), multiply(), subtract(), divide(), ...
- Приличат на Wrapper класове, но нямат "примитивен" аналог

<br />

<p style="font-size:0.7em">*произволна точност/брой цифри не значи неограничена: ограничава ни на практика наличната памет.</p>

---

### Статични член-променливи и статични методи

---

#### Статични член-променливи и статични методи

- Те са част от класа, а не от конкретна негова инстанция (обект).
- Могат да се достъпват без да е създаден обект: само с името на класа, точка, името на статичната член-променлива или метод.

<br />

```java
System.out.println(Math.PI);
System.out.println(Math.pow(3, 2));
```

@snap[south span-100]
@[1-1](3.141592653589793)
@[2-2](9.0)
@snapend

---

#### Статични член-променливи

@ul

- Статичните член-променливи имат едно-единствено копие, което се споделя от всички инстанции на класа
  - ако са константи, пестим памет (няма смисъл да се мултиплицират във всяка инстанция)
  - ако са променливи, всяка инстанция "вижда" и променя една и съща стойност, което е механизъм за комуникация между всички инстанции на дадения клас

@ulend

---

#### Статични методи 

@ul

- Статичните методи имат достъп само до статичните член-променливи и други статични методи на класа
- Нестатичните методи имат достъп както до статичните, така и до нестатичните членове на класа.

@ulend

---

```java
public class Utils {
    public static final double PI = 3.14;
    private static int radius = 10;
    private String fact5 = "5!";

    public static long fact(int n) {
        if (n == 1) { return 1; } else { return n * fact(n - 1); }
    }
    public String getFact() {
        return fact5;
    }

    public static void main(String[] args) {
        System.out.println("Perimeter is " + 2 * Utils.PI * radius);
        System.out.println(new Utils().getFact() + "=" + Utils.fact(5));
        Utils.getFact();
    }
}
```

@snap[south span-100]
@[2](constant)
@[3](static member)
@[4](non-static member)
@[6-8](static method)
@[9-11](non-static method)
@[16](won't compile)
@snapend

---

### Enums

---

#### Enum

@ul

- Специален тип (клас), представящ фиксирано множество от инстанции-константи
- Нарича се enum(eration), защото инстанциите се дефинират чрез изброяване
- Всеки enum неявно наследява абстрактния клас `java.lang.Enum`
  - не може да наследява явно друг клас, защото би било множествено наследяване
  - може да имплементира интерфейси

@ulend

---

#### Enum (2)

@ul

- Тялото на enum класа може да съдържа член-променливи и методи
- Ако има конструктор, той трябва да е package-private или private
- Той автоматично създава константите в дефиницията на enum-a. Не може да се извиква явно.

@ulend

---

#### Enum (3)

@ul

- Компилаторът добавя автоматично и няколко специални статични методи:
  - `values()` - връща масив, съдържащ всички стойности в enum, в реда, в който са изброени в декларацията
  - `valueОf(String name)` - връща enum константата по даденото име

@ulend

---

#### Enum - пример

```java
public enum Day {
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY
}
```

---

#### Enum - пример

```java
public class EnumExample {
    private Day day;
    public EnumExample(Day day) {
        this.day = day;
    }
    public void tellItLikeItIs() {
        switch (day) {
            case MONDAY: System.out.println("Mondays are bad."); break;
            case FRIDAY: System.out.println("Fridays are better."); break;
            case SATURDAY: case SUNDAY: System.out.println("Weekends are best."); break;
            default: System.out.println("Midweek days are so-so."); break;
        }
    }

    public static void main(String[] args) {
        EnumExample example = new EnumExample(Day.TUESDAY);
        example.tellItLikeItIs();
    }
}
```

---

### Изключения

---

#### Изключения (Exceptions)

@ul

- Събитие (проблем), което се случва по време на изпълнение на дадена програма и нарушава нормалната последователност на изпълнение на инструкциите ѝ.
- Още един начин за комуникация на метод с извикващите го: връщана стойност при нормално изпълнение и изключение при проблем.

@ulend

---

#### Например...

@ul

- Подали сме невалидни входни данни
- Опитваме се да отворим несъществуващ файл
- Мрежата се е разкачила по време на комуникация
- Свършила е паметта на виртуалната машина
- ...

@ulend

---

#### Как се генерира ("хвърля") изключение?

```java
public Object pop() {
    if (size == 0) {
        throw new EmptyStackException();
    }

    // [...]
}
```

---

#### Как се обработва ("лови") изключение?

```java
try {
    // код, който може да хвърли изключение
} catch (Exception e) {
    // обработваме изключението ("exception handler")
    // мсоже да има повече от един catch блок
} finally {
    // при нужда, някакви заключителни операции
    // finally блокът е optional, но ако го има,
    // се изпълнява задължително щом влезем в try-a)
}
```

---

#### Catch block chain

```java
try { 
    // [...]
} catch (MostSpecificException mse) {
    // [...]
} catch (MoreGeneralException mge) {
    // [...]
} ... {
    // [...]
} catch (LeastSpecificException lse) {
    // [...]
}
```

---

#### Multi catch block

```java
try {
    // [...]
} catch (IOException | SQLException e) {
    // [...]
}
```

---

#### Стек на извикванията (call stack)

![Call stack](images/03.1-call-stack.jpg?raw=true)

---

#### Finally – не само за обработка на изключения

```java
try { 
    // тук може да се хвърлят изключения
    // или да има return/continue/break
} finally {
    // някакъв важен cleanup code -
    // ще се изпълни винаги*, независимо какво се случи в try блока
}
```

---

#### Видове изключения

@ul

- Изключителните събития могат да се дължат на грешка на потребителя, бъг в кода или физически ресурс, който не е достъпен.
- Делят се на три вида:
  - Checked exceptions
  - Unchecked (Runtime) exceptions
  - Errors

@ulend

---

#### Видове изключения (2)

![Exceptions](images/03.2-exceptions.jpg?raw=true)

---

#### Checked vs. unchecked exceptions

![Exceptions](images/03.3-exceptions.jpg?raw=true)

---

#### Checked exceptions

@ul

- Наричат се още compile-time exceptions, защото компилаторът ни задължава да ги обработим
- Едно добре написано приложение би трябвало да ги очаква и да се възстановява от тях

@ulend

---

#### Checked exceptions - примери

@ul

- `FileNotFoundException` при опит да отворим файл по име, какъвто не съществува
- `IOException` при проблем с четене или писане във файл

@ulend

---

#### Unchecked (Runtime) exceptions

@ul

- Възникват по време на изпълнение на приложението, затова се наричат още runtime exceptions
- Приложението обикновено не може да ги очаква или да се възстанови от тях
- Най-често са резултат от бъгове (логически грешки) в кода, неправилно извикване на API-та и т.н.

@ulend

---

####  Unchecked (Runtime) exceptions - примери

@ul

- `ArithmeticException` при опит за деление на нула
- `ArrayIndexOutOfBoundsException` при опит да достъпим елемент на масив по индекс извън размера на масива
- `NullPointerException` при подаване на null reference, където се очаква обект
- `ClassCastException` при опит да се cast-не обект към клас, на който обектът не е инстанция

@ulend

---

#### Errors

@ul

- Проблеми, които възникват извън приложението, и приложението обикновено не може да ги очаква или да се възстанови от тях.
- Обикновено се генерират от самата виртуална машина.

@ulend

---

#### Errors - примери

- `OutOfMemoryError` при опит да заделим памет, когато свободната памет не е достатъчна (и не може да освободи с garbage collection)
- `StackOverflowError` когато метод извиква свое копие твърде много пъти (напр. при безкрайна рекурсия)

---

#### Деклариране на хвърляни изключения

Ако метод не прехваща/обработва даден checked exception, който може да се хвърли в тялото му, той трябва да го декларира в прототипа си, за да "предупреди"с тези, които го викат:

<br />

```java
public void writeList() throws IOException, FileNotFoundException {
    // [...]
}
```

---

#### Chained exceptions

```java
try {
    // [...]
} catch (IOException e) {
    // прехващаме изключение, обработваме го 
    // и хвърляме ново, към което го "закачаме"
    throw new SampleException("Uppss..", e); 
}
```

---

#### Защо да ползваме изключения?

@ul

- Отделяме кода за обработка на грешки от останалия -> става по-четим
- "Препредаване" на грешки по стека на извикванията
- Групиране и диференциране на различните типове грешки

@ulend

---

## Въпроси

@snap[south span-100]
@fab[github] [fmi/java-course](https://github.com/fmi/java-course)
@snapend