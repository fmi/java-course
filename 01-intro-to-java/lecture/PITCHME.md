## Въведение в Java

_09.10.2018_

---

#### Езикът Java

@ul

- Създаден през 1995г. от James Gosling от Sun Microsystems
- Обектно-ориентиран
- Статично компилируем
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

![Java app diagram](images/01.1-java-app.jpg?raw=true)

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

#### Формат на .class файла

```
ClassFile {
    u4             	magic;
    u2             	minor_version;
    u2             	major_version;
    u2             	constant_pool_count;
    cp_info        	constant_pool[constant_pool_count-1];
    u2             	access_flags;
    u2             	this_class;
    u2             	super_class;
    u2             	interfaces_count;
    u2             	interfaces[interfaces_count];
    u2             	fields_count;
    field_info     	fields[fields_count];
    u2             	methods_count;
    method_info    	methods[methods_count];
    u2             	attributes_count;
    attribute_info 	attributes[attributes_count];
}
```

---

#### Ще започнем с Java от…

- Вградените типове данни
- Условия и разклонения
- Итерация / Цикли
- Низове (по-подробно)
- Масиви
- Функции

---

#### Вградени типове данни

- Java е статично типизиран език => всички променливи трябва да бъдат декларирани преди да бъдат използвани.
- Декларацията включва името и типа.
```java
int gear = 1;
```

- Тип данни == множество стойности + операции върху тях

---

#### Променливи

![Variables](images/01.2-variables.jpg?raw=true)

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
    <td>char</td>
    <td>16 бита</td>
    <td>Unicode 0</td>
    <td>Unicode 2<sup>16</sup> - 1</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>byte</td>
    <td>8 бита</td>
    <td>-128</td>
    <td>+127</td>
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

#### Литерали

```java
int i = 1;           // int by default
long l = 1L;         // L or l
double d = 0.1;      // d or D is optional
double d2 = 1e-1;    // same, in scientific
float f = 0.1;       // will not compile, why?
char c = 'a';
String s = "cool";
```

@[1]
@[2]
@[3]
@[4]
@[5]
@[6]
@[7]

---

#### Литерали (2)

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

@[1-2]
@[4-5]
@[7-8]
@[10-11]

---

#### Литералите – какво (сравнително) ново?

- Числови литерали с подчертавка
```java
int thousand = 1_000;
int million = 1_000_000;
long magic = 0xCAFE_BABE;
```

- Числови литерали в двоична бройна система
```java
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
    <td>String (or any object)</td>
    <td>null</td>
  </tr>
</table>

---

#### Конвертиране на типовете

- Имплицитно - без загуба на точност; с низ
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

- За да ни помага компилаторът

<br />

![Java app diagram](images/01.3-rocket.jpg?raw=true)

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
    <td>expr++ expr--</td>
  </tr>
  <tr style="font-size:0.5em">
    <td>unary</td>
    <td>++expr --expr +expr -expr ~ !</td>
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

#### Низове

```java
String          // immutable 
StringBuilder   // mutable, fast, single-threaded 
StringBuffer    // mutable, slower, thread-safe
```

---

#### Низове - обхождане

```java
String s = "Firebird";
char ic = s.charAt(i);
char[] ca = s.toCharArray();

for (int i = 0; i < ca.size(); i++) {...}
for (char c : ca) {...} // enhanced for-loop, a.k.a. for-each

String sorted = Arrays.sort(ca).toString(); // "Fbdeiir"
```

---

#### Низове - конкатенация

- Може да конкатенираме низове с оператора `+`
- Ако аргумент на `+` е нещо различно от низ, той се конвертира към низ.

<br />

```java
String str1 = "Current";
String str2 = str1 + " year is " + 2017;
```

---

#### String.split

```java
String str1 = "Current year is 2017";

String[] tokens = str1.split(" "); // разделител – интервал

// tokens = ["Current", "year", "is", "2017"]

int year = Integer.parseInt(tokens[3]); 
// year == 2017
```

---

#### Вход / изход

- Писане на стандартния изход (stdout)
```java
System.out.println("Something printed on the console");
```

- Четене от стандартния вход (stdin)
```java
import java.util.Scanner;
// [...]

Scanner sc = new Scanner(System.in);
String lineRead = sc.nextLine();

// [...]
```

---

#### Булеви изрази

@ul

- `true` или `false`
- За разлика от С/С++, не може да се използва число вместо булев израз.

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

#### Булеви логически оператори (2)

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

- `condition ? statement1 : statement2`
- Eдинственият тернарен оператор в Java.
- Еквивалентен е на:

<br />

```java
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

#### Масиви

![Array](images/01.4-array.jpg?raw=true)

---

#### Масиви (2)

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

#### Масиви (3)

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
![Matrix](images/01.5-matrix.jpg?raw=true)
@snapend

---

#### Многомерни масиви (2)

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

#### Операции с масиви (2)

```java
Arrays.sort(arr);
Arrays.sort(a, Collections.reverseOrder());
```

---

#### Функции

![Functions](images/01.6-funcs.jpg?raw=true)

---

#### Използвана (и препоръчвана) литература

- [Thinking in Java](https://en.wikipedia.org/wiki/Thinking_in_Java)
- Effective Java
- Oracle OCA Java SE 8 Programmer I Study Guide
- [What’s new in JDK 9](https://docs.oracle.com/javase/9/whatsnew/)
- [Learning the Java Language](https://docs.oracle.com/javase/tutorial/java/index.html)

---

## Въпроси

@snap[south span-100]
@fab[github] [fmi/java-course](https://github.com/fmi/java-course)
@snapend
