## Въведение в Java

_09.10.2019_

---

#### Какво е Java?

@ul

- Език за програмиране
- Java компилатор
- Java виртуална машина (JVM)
- Java Development Kit (JDK)
  - Java Runtime Environment (JRE)
- Java платформа

@ulend

---

#### Java Editions

@ul

- Java Edition е вариация на Java платформата (JDK-то), асемблирана за различна цел
  - Java Platform Standard Edition (Java SE)
  - Java Platform Enterprise Edition (Java EE)
  - Java Platform Micro Edition (Java ME)
    - Android SDK
  - Java Card

@ulend

---

#### JDK инструменти

@ul

- Основни
  - javac
  - java
- Допълнителни
  - jshell 
  - javadoc
  - jar
  - ...

@ulend

---

#### Java: най-популярните IDE-та

@ul

- Eclipse
- IntelliJ IDEA
- NetBeans

@ulend

---

#### Езикът Java

- Създаден през 1995 от James Gosling (Sun Microsystems)
- Актуална версия: Java 13 (released 17.09.2019)

![Java logo and mascot](images/01.1-java-logo-mascot.png?raw=true)

---

#### Езикът Java

@ul

- Обектно-ориентиран
- Със C/C++ синтаксис
- "Write once, run anywhere"

@ulend

---

#### Hello world!

<br/>

```java
public class HelloWorldApp {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
```

<br/>

```bash
$ javac HelloWorldApp.java
$ java HelloWorldApp
Hello world!
```

---

#### Стандартно Java приложение

![Java app diagram](images/01.2-java-app.jpg?raw=true)

---

#### Java виртуалната машина (JVM)

@ul

- Интерпретира и изпълнява byte код инструкции
- Компилира по време на изпълнението byte кода до машинен код
- Заделя памет за оперативните данни
- Автоматично изчиства паметта
- Зарежда класове
- Стартира нишки
- Взаимодейства с операционната система

@ulend

---

#### Ще започнем с езика Java от...

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

#### Tипове данни и променливи

@ul

- Тип данни == множество стойности + операции върху тях
- Java е статично типизиран език => всички променливи трябва да бъдат декларирани преди да бъдат използвани и типът на дадена променлива се фиксира в декларацията ѝ и не може да се променя
- Декларацията включва името и типа и може да е съчетана (или не) с инициализация

@ulend

---

#### Tипове данни и променливи

![Variables](images/01.3-variables.jpg?raw=true)

---

#### Tипове данни

@ul

- Примитивни типове
  - Булев (boolean) тип
  - Числени (numeric) типове
    - Целочислени (integral) типове
    - Типове за числа с плаваща запетая (floating-point)
- Reference типове

@ulend

---

#### Примитивни типове данни

<table>
  <tr>
    <th style="font-size:0.9em">Тип данни</th>
    <th style="font-size:0.9em">Размер</th>
    <th style="font-size:0.9em">Минимум</th>
    <th style="font-size:0.9em">Максимум</th>
  </tr>
  <tr style="font-size:0.7em">
    <td>boolean</td>
    <td>-</td>
    <td>-</td>
    <td>-</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>byte</td>
    <td>8 бита</td>
    <td>-128</td>
    <td>+127</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>char</td>
    <td>16 бита</td>
    <td>Unicode 0</td>
    <td>Unicode 2<sup>16</sup> - 1</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>short</td>
    <td>16 бита</td>
    <td>-2<sup>15</sup></td>
    <td>+2<sup>15</sup> - 1</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>int</td>
    <td>32 бита</td>
    <td>-2<sup>31</sup></td>
    <td>+2<sup>31</sup> - 1</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>long</td>
    <td>64 бита</td>
    <td>-2<sup>63</sup></td>
    <td>+2<sup>63</sup> - 1</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>float</td>
    <td>32 бита</td>
    <td>IEEE754</td>
    <td>IEEE754</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>double</td>
    <td>64 бита</td>
    <td>IEEE754</td>
    <td>IEEE754</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>void</td>
    <td>-</td>
    <td>-</td>
    <td>-</td>
  </tr>
</table>

---

#### Минимална и максимална стойност на примитивните типове

```java
Byte.MIN_VALUE // -128
Byte.MAX_VALUE // 127
Short.MIN_VALUE // -32768
Short.MAX_VALUE // 32767
Integer.MIN_VALUE // -2147483648
Integer.MAX_VALUE // 2147483647
Long.MIN_VALUE // -9223372036854775808
Long.MAX_VALUE // 9223372036854775807
(int) Character.MIN_VALUE // 0
(int) Character.MAX_VALUE // 65535
```
---

#### Типът char

@ul

- цяло число без знак
- стойност (*code point*) от 0 до 65535 включително
- представя Unicode символ
- може да участва в аритметични операции (със своя code point)

@ulend

---

#### Типът char

<table>
  <tr>
    <th style="font-size:0.9em">Code point</th>
    <th style="font-size:0.9em">Unicode escape</th>
    <th style="font-size:0.9em">Печатна репрезентация</th>
    <th style="font-size:0.9em">Описание</th>
  </tr>
  <tr style="font-size:0.7em">
    <td>33</td>
    <td>\u0021</td>
    <td>!</td>
    <td>Символ за удивителна</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>50</td>
    <td>\u0032</td>
    <td>2</td>
    <td>Цифра 2</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>65</td>
    <td>\u0041</td>
    <td>A</td>
    <td>Главна латинска буква 'A'</td>
  </tr>
</table>

---

#### Литерали на примитивните типове

```java
int i = 1;           // int by default
long l = 1L;         // L or l
double d = 0.1;      // d or D is optional
double d2 = 1e-1;    // same, in scientific
float f = 0.1;       // will not compile, why?
char c = 'A';
char c2 = '\u0041';  // again, 'A'
```

---

#### Целочислени литерали

```java
// The number 26, in decimal
int decVal = 26;

// The number 26, in hexadecimal
int hexVal = 0x1a;

// The number 26, in binary
int binVal = 0b11010;

// The number 26, in octal
int octVal = 032;
```

---

#### Литерали с подчертавка: syntactic sugar

```java
int thousand = 1_000;
int million = 1_000_000;
long magic = 0xCAFE_BABE;
int one = 0b1;
int mask = 0b1010_1010_1010;
```

---

#### Стойности по подразбиране

- Компилаторът **не** присвоява стойности по подразбиране на неинициализираните локални променливи!

---

<table>
  <tr>
    <th>Тип данни</th>
    <th>Стойност по подразбиране</th>
  </tr>
  <tr style="font-size:0.8em">
    <td>boolean</td>
    <td>false</td>
  </tr>
  <tr style="font-size:0.8em">
    <td>short</td>
    <td>0</td>
  </tr>
  <tr style="font-size:0.8em">
    <td>int</td>
    <td>0</td>
  </tr>
  <tr style="font-size:0.8em">
    <td>long</td>
    <td>0L</td>
  </tr>
  <tr style="font-size:0.8em">
    <td>float</td>
    <td>0.0f</td>
  </tr>
  <tr style="font-size:0.8em">
    <td>double</td>
    <td>0.0d</td>
  </tr>
  <tr style="font-size:0.8em">
    <td>char</td>
    <td>'\u0000'</td>
  </tr>
  <tr style="font-size:0.8em">
    <td>Reference типове</td>
    <td>null</td>
  </tr>
</table>

---

#### Конвертиране на типовете

- Имплицитно - без загуба на точност
- Експлицитно - чрез cast

<table style="width:100%">
  <tr>
    <th style="width:50%">Израз</th>
    <th style="width:25%">Тип на израза</th>
    <th style="width:25%">Стойност на израза</th>
  </tr>
  <tr style="font-size:0.8em">
    <td>"1234" + 99</td>
    <td>String</td>
    <td>"123499"</td>
  </tr>
  <tr style="font-size:0.8em">
    <td>(int) 2.71828</td>
    <td>int</td>
    <td>2</td>
  </tr>
  <tr style="font-size:0.8em">
    <td>11 \* 0.3</td>
    <td>double</td>
    <td>3.3</td>
  </tr>
  <tr style="font-size:0.8em">
    <td>(int) 11 \* 0.3</td>
    <td>double</td>
    <td>3.3</td>
  </tr>
  <tr style="font-size:0.8em">
    <td>11 \* (int) 0.3</td>
    <td>int</td>
    <td>0</td>
  </tr>
  <tr style="font-size:0.8em">
    <td>(int) (11 \* 0.3)</td>
    <td>int</td>
    <td>3</td>
  </tr>
<table>

---

#### Защо ни трябват типове?

- За да ни помага компилаторът в откриването на грешки

<br />

![Java app diagram](images/01.4-rocket.jpg?raw=true)

<p style="font-size:0.5em">През 1996, ракетата Ариана 5 експлодира след излитане поради софтуерна грешка в конвертирането на типове (опит да „набута“ 64-битово число в 16 бита).</p>

---

#### Оператори

<table style="width:100%">
  <tr>
    <th style="width:50%; font-size:0.8em">Operators</th>
    <th style="width:50%; font-size:0.8em">Precedence</th>
  </tr>
  <tr style="font-size:0.5em">
    <td>postfix</td>
    <td>expr++ expr--</td>
  </tr>
  <tr style="font-size:0.5em">
    <td>unary</td>
    <td>++expr --expr +expr -expr ~ !</td>
  </tr>
  <tr style="font-size:0.5em">
    <td>multiplicative</td>
    <td>\* / %</td>
  </tr>
  <tr style="font-size:0.5em">
    <td>additive</td>
    <td>+ -</td>
  </tr>
  <tr style="font-size:0.5em">
    <td>shift</td>
    <td><< >> >>></td>
  </tr>
  <tr style="font-size:0.5em">
    <td>relational</td>
    <td>< > <= >= instanceof</td>
  </tr>
  <tr style="font-size:0.5em">
    <td>equality</td>
    <td>== !=</td>
  </tr>
  <tr style="font-size:0.5em">
    <td>bitwise AND</td>
    <td>&</td>
  </tr>
  <tr style="font-size:0.5em">
    <td>bitwise exclusive OR</td>
    <td>^</td>
  </tr>
  <tr style="font-size:0.5em">
    <td>bitwise inclusive OR</td>
    <td>|</td>
  </tr>
  <tr style="font-size:0.5em">
    <td>logical AND</td>
    <td>&&</td>
  </tr>
  <tr style="font-size:0.5em">
    <td>logical OR</td>
    <td>||</td>
  </tr>
  <tr style="font-size:0.5em">
    <td>ternary</td>
    <td>? :</td>
  </tr>
  <tr style="font-size:0.5em">
    <td>assignment</td>
    <td>= += -= *= /= %= &= ^= |= <<= >>= >>>=</td>
  </tr>
</table>

---

#### Scoping

```java
{
    int x = 12;
    // Only x available
    {
        int q = 96;
        // Both x & q available
    }
    // Only x available
    // q is "out of scope" 
}

```

---

#### Низове в Java

@ul

- Низовете са reference тип, а не примитивен
- Инстанции са на `String` класа
- Immutable са: всяка промяна води до създаване на нов `String` обект, а старият остава непроменен

@ulend

---

#### Низовете в паметта: string pool vs heap

@ul

- String литералите се създават в специална област на паметта на JVM-a, наречена *string pool*
- При опит за добавяне на низ в string pool-a, ако там вече съществува низ със същото съдържание, не се създава нов обект, а се връща референция към съществуващия (-> пести се памет)

@ulend

---

#### Низовете в паметта: string pool vs heap

@ul

- String обектите, създадени с оператора `new` се създават в динамичната памет (heap-a). Всеки низ в heap-a е отделна инстанция, дори съдържанието на два и повече низа да е идентично
- String обект може да се прехвърли от heap-a в string pool-a чрез извикване на метода му `intern()`

@ulend

---

#### Сравняване на низове

@ul

- Сравняването на низове с `==` е сравнение на съответните референции
- Сравняването на низове за идентичност като съдържание се прави с метода `equals()` на String класа

@ulend

---

#### Низовете в паметта: string pool vs heap

```java
String literalOne = "FMI"; // goes to the string pool
String literalTwo = "FMI"; // "FMI" is present in the String pool ->
                           // literalTwo will refer to the same object

System.out.println(literalOne == literalTwo); // true

String newString = new String("FMI"); // new object in the heap

System.out.println(literalOne == newString); // false
System.out.println(literalOne.equals(newString)); // true

String intern = newString.intern(); // adds the string to the pool
                                    // and returns a reference to it
System.out.println(literalOne == newString); // false, newString
                                             // is not reassigned
System.out.println(literalOne == intern); // true
```

---

#### Низове - литерали, конкатениране

```java
String language = "Java";
String tbd = null;
String message = "I <3 " + language;
String year = "The current year is " + 2019;
```

---

#### Многоредови низови литерали

```java
// multi-line string literals before Java 13
String html = "<html>\n" +
              "    <body>\n" +
              "        <p>Hello world!</p>\n" +
              "    <body>\n" +
              "</html>\n";

// new in Java 13: text blocks for multi-line string literals
String html = """
                       <html>
                           <body>
                               <p>Hello world!</p>
                           </body>
                       </html>
              """;
```

---

#### Низове - обхождане

```java
String s = "Firebird";
char ic = s.charAt(i);
char[] ca = s.toCharArray();

for (int i = 0; i < ca.size(); i++) {...}
for (char c : ca) {...} // enhanced for-loop, a.k.a. for-each

Arrays.sort(ca);
String sorted = String.valueOf(ca); // "Fbdeiir"
```

---

#### String.split

```java
String str1 = "Current year is 2019";

String[] tokens = str1.split(" "); // разделител – интервал

// tokens = ["Current", "year", "is", "2019"]

int year = Integer.parseInt(tokens[3]); 
// year == 2019
```

---

Писане на стандартния изход

```java
System.out.println("Something printed on the console");
```

Четене от стандартния вход

```java
import java.util.Scanner;
// [...]

Scanner sc = new Scanner(System.in);
String lineRead = sc.nextLine();

// [...]
```

---

#### Local variable type inference

```java
// since Java 10
var message = "FMI rulez!"; // local variable type inference

message = "Cool"; // variable can be reassigned

message = 1; // will not compile: type is fixed upon declaration
             // and cannot change

var mystery; // will not compile: type cannot be inferred
             // without initializer
```

---

#### Булеви константи

@ul

- `true` или `false`
- За разлика от С/С++, не може да се използва число вместо булев израз

@ulend

---

#### Булеви логически оператори

@ul[squares]

- | - the OR operator
- & - the AND operator
- ^ - the XOR operator
- ! - the NOT operator
- || - the short-circuit OR operator
- && - the short-circuit AND operator
- == - the EQUAL TO operator
- != - the NOT EQUAL TO operator

@ulend

---

#### Булеви логически оператори

<table>
  <tr>
    <th>A</th>
    <th>B</th>
    <th>A|B</th>
    <th>A&B</th>
    <th>A^B</th>
    <th>!A</th>
  </tr>
  <tr>
    <td>false</td>
    <td>false</td>
    <td>false</td>
    <td>false</td>
    <td>false</td>
    <td>true</td>
  </tr>
  <tr>
    <td>true</td>
    <td>false</td>
    <td>true</td>
    <td>false</td>
    <td>true</td>
    <td>false</td>
  </tr>
  <tr>
    <td>false</td>
    <td>true</td>
    <td>true</td>
    <td>false</td>
    <td>true</td>
    <td>true</td>
  </tr>
  <tr>
    <td>true</td>
    <td>true</td>
    <td>true</td>
    <td>true</td>
    <td>false</td>
    <td>false</td>
  </tr>
</table>


---

#### if-else

```java
if (booleanExpression) {
    // statements
}

if (booleanExpression) {
    // statements
} else {
    // statements
}
```

---

#### Операторът ? :

```java
// the only ternary (i.e. three-argument) operator in Java
condition ? statement1 : statement2

// the above is equivalent to
if (condition) {
    statement1
} else {
    statement2
}  
```

---

#### Итерация - while

```java
while (booleanExpression) {
    statement
}
```

---

#### Итерация - do-while

```java
do {
    statement
} while (booleanExpression);
```

---

#### Итерация - for

```java
for (initialization; booleanExpression; step) {
    statement
}
```

---

#### Безусловно разклонение на изпълнението

- `return [value]`
- `break [label]`
- `continue [label]`
- Няма `goto` (ключовата дума е запазена, но не е използвана)

---

#### Switch

```java
switch (selector) {
    case value1 : statement; break; 
    case value2 : statement; break; 
    case value3 : statement; break; 
    // [... ]
    default: statement; 
}
```

---

#### Java 13 preview feature: подобрен `switch`

```java
char ch = 'a';
int t;

switch (ch) {
    case 'a'      : t = 1; break;
    case 'b', 'c' : t = 12; break;
    default       : t = 27;
}

switch (ch) {
    case 'a'      -> t = 1;
    case 'b', 'c' -> t = 12;
    default       -> t = 27;
}
```

---

#### Java 13 preview feature: подобрен `switch` като израз

```java
int z = switch (ch) {
    case 'a'      -> 1;
    case 'b', 'c' -> 12;
    default       -> 27;
};

int r = switch (ch) {
    case 'a' : yield 1;
    case 'b' : yield 12;
    default  : yield 27;
};
```

---

#### Масиви

![Array](images/01.5-array.jpg?raw=true)

---

#### Масиви

```java
int[] a;     // preferred syntax
int a[];     // also valid

// explicit initialization
// can be done only during declaration
int[] a = {1, 2, 3, 4};

int[] b = new int[7];
b.length;
```

---

#### Масиви

@ul

- Декларация – не се заделя памет за елементите на масива
- Инициализация – заделя се памет за елементите на масива
- Масивите от примитивни типове се инициализират автоматично със стойността по подразбиране на съответния тип

@ulend

---

#### Многомерни масиви

```java
int[][] a;
a = new int[3][4];

int[][] b = {
    { 1, 0, 12, -1 },
    { 7, -3, 2, 5 },
    { -5, -2, 2, -9 },
};
```

@snap[east fragment]
![Matrix](images/01.6-matrix.jpg?raw=true)
@snapend

---

#### Многомерни масиви

```java
double[][] matrix = new double[7][];
// rows have not yet been created

for (int i = 0; i < 7; i++) {
    // Create row i with i + 1 elements.
    matrix[i] = new double[i+1];
}
```

---

#### Операции с масиви

```java
System.arraycopy(src, srcPos, dest, destPos, length);

Arrays.equals(arr1, arr2);
Arrays.fill(arr, value);
Arrays.toString(arr);
```

---

#### Операции с масиви

```java
Arrays.sort(arr);
Arrays.sort(a, Collections.reverseOrder());
```

---

#### Функции

![Functions](images/01.7-funcs.jpg?raw=true)

---

## Въпроси?

@snap[south span-100]
@fab[github] [fmi/java-course](https://github.com/fmi/java-course)
@snapend

---

![Thinking in Java](images/01.8-thinking-in-java.jpg)

---

![Effective Java](images/01.9-effective-java.jpg)
