# Обектно-ориентирано програмиране с Java (част II)

## 1. СУСИ, версия 1.1

Ще рефакторираме [предната версия на СУСИ](https://github.com/fmi/java-course/blob/master/02-oop-in-java-i/lab/README.md), използвайки новопридобитите ни знания за `java.lang.Object`, static, Enums и Exceptions.

Ще добавим и някои полезни нови функции на системата.

### Промени в интерфейса `Susi`

За да не смесваме информацията за неуспешно изпълнение на даден метод с връщаната от него стойност при успешно изпълнение, променяме прототипите на `registerStudent` и `setGrade`, така че да хвърлят изключения при неуспешно изпълнение. Изключение ще хвърлят и методите `setGrade` и `getTotalCredits`, в случай че бъдат извикани за нерегистриран в системата студент.

Ще добавим и нов метод, който ще връща сумата от кредитите от курсове от даден тип, събрани от даден студент.

```java
public interface Susi {
  
  /**
  * Registers a student in the system.
  * @throws StudentAlreadyRegisteredException, if the student is already registered
  * @throws CapacityExceededException, if the university capacity is exceeded
  */
  void registerStudent(Student student) throws StudentAlreadyRegisteredException, CapacityExceededException;

  /**
   * Returns the number of registered students
   */
  int getStudentsCount();
  
  /**
  * Sets a grade for the student for the specified course and adds the credits of the
  * course to the total credits of the student.
  * @throws StudentNotFoundException, if the student is not found
  * @throws CapacityExceededException, if the student already took the maximum number of courses
  */
  void setGrade(Student student, Course course, double grade) throws StudentNotFoundException,
                                                             CapacityExceededException;
  
  /**
  * Returns the total sum of credits for this student
  * @throws StudentNotFoundException, if the student is not found
  */
  double getTotalCredits(Student student) throws StudentNotFoundException;
  
  /**
  * Returns the grade point average for the given student
  * @throws StudentNotFoundException, if the student is not found
  */
  double getGPA(Student student) throws StudentNotFoundException;
  
  /**
  * Returns the sum of credits per course type accumulated by the specified student
  * @throws StudentNotFoundException, if the student is not found
  */
  double getCreditsPerType(Student student, CourseType type) throws StudentNotFoundException;

}
```

### Въвеждане на няколко типа курсове

В университета се водят три типа курсове: задължителни, изборни и практики:

| Тип          | Код | Име      |
|:------------ |:--- |:-------- |
| задължителен | 'R' | REQUIRED |
| изборен      | 'E' | ELECTIVE |
| практика     | 'P' | PRACTICE |

Моделирайте типовете курсове с Enum `CourseType`, добавете в интерфейса `Subject` метод 

```java
CourseType getType();
```

и го реализирайте в имплементиращия клас.

### Промени в класовете `Student` и `Course`

Всеки студент се определя еднозначно от факултетния си номер, а всеки курс: от уникалния си идентификатор. С други думи, две инстанции на `Student` са еднакви тогава и само тогава, когато факултетните им номера съвпадат, а две инстанции на `Course` са еднакви тогава и само тогава, когато уникалните им идентификатори съвпадат.

Отразете този факт в имплементацията на двата класа. Уверете се, че навсякъде в останалия код сравнявате коректно инстанциите им за равенство.

В класа `Course` добавете статичен метод `countByType`, който по даден масив от курсове и тип курс връща броя на онези курсове от входния масив, които са от съответния тип.

В класа `Student` добавете метод `sortCourses`, който връща масив от курсовете, по които студентът има оценка, сортирани по оценка в низходящ ред.

### Подсказки

За сортиране използвайте някой от `Arrays.sort()` методите. За да сработи той, трябва класът, обектите от който ще сортирате, да имплементира интерфейса `java.lang.Comparable`. 

Например:

```java
public class MyClass implements Comparable<MyClass> {

    public int compareTo(MyClass other) {
        // Implementation
    }

}
```

Конструкцията `Comparable<MyClass>` ще бъде обяснена на следващата лекция. За момента я използвайте наготово. Със спецификацията на метода `compareTo` се запознайте от неговата документация.

## 2. Крави и бикове

Да се имплементира играта [крави и бикове](https://en.wikipedia.org/wiki/Bulls_and_Cows).

### Правила на играта

Крави и бикове е логическа игра за отгатване на цифри. Играе се от двама противници, като всеки се стреми да отгатне тайното число, намислено от другия. След всеки ход, противникът дава броя на съвпаденията. След това, последователно един след друг, играчите задават въпрос с предположение за числото на противника. Противникът отговаря, като посочва броя на съвпаденията - ако дадена цифра от предположението се съдържа в тайното число и се намира на точното място, тя е „бик“, ако пък е на различно място, е „крава“.

Например, ако компютърът си е намислил числото 4761, за следните ни предположения ще получим подобни отговори:

```
0438 --> 1 крава
4067 --> 2 бика и 1 крава
1674 --> 4 крави
4761 --> 4 бика
```

В нашата версия на играта играчът играе срещу компютъра, като компютърът си намисля тайно произволно число, а играчът се опитва да го отгатне. След всеки опит на играча, на стандартния изход се отпечатва съобщение, съдържащо информация за броя бикове и крави в текущото предположение. Тайните числа в нашата версия на играта ще са n-цифрени, като цифрите може да се повтарят.  

Тъй като имаме възможност за повтарящи се цифри, ако някоя цифра на определена позиция е бик, то тя не може да е крава на тази позиция. Например ако компютърът си е намислил 15615 
и пробваме да отгатнем с 11222, ще имаме 1 бик и 1 крава (първата единица е бик, втората е крава). Ако пък компютърът си е намислил 88537 и пробваме да отгатнем с 44888, ще имаме 0 бика и 2 крави. И последен пример - при намислено число 293439 и опит 695599 ще имаме 2 бика и 0 крави.

В случай, че още сте объркани, прочете следния цитат: 

> Bulls and Cows traditionally disallows repeated digits. However, the similar commercial board game Mastermind does allow duplicates. The way scoring works there, you get credit for at most one bull (black peg) or cow (white peg) per digit in the secret number (not in the guess). So if the secret number is 1122 and you guess 1112, you get three bulls (for the first two 1's and the 2), but no cow for the third 1, because both 1's in the code are already accounted for.

 В `default` пакета трябва да имате клас с име `Main` и с методи `public static void main(String[] args)`, който стартира приложението и `public static int getComputerNumber()`, който връща генерираното от компютъра произволно тайно число. Методът `getComputerNumber` трябва да връща едно и също число в рамките на една игра за всяко свое извикване (тайното число е произволно, но компютърът няма право да си сменя предположението по време на играта :)). Методът `main` трябва да приема входните данни от стандартния вход `System.in` описани долу,  както и да извежда определени съобщения на стандартния изход `System.out`, също описани долу.
 
### Входни данни

Първият ред трябва да съдържа число `n`, което представлява броя цифри в тайното число, което ще бъде генерирано.
Следващите редове представляват предположения на играча (опити да се отгатне тайното число). След всеки опит за отгатване, на стандартния изход трябва да се генерира съобщение от вида `x bulls and y cows`, където x и y са броя бикове и крави в текущото предположение. Играта приключва, когато играчът познае тайното число. Тогава освен съобщението `x bulls and 0 cows`, на стандартния изход трябва да се изведе и `Finished`.

### Изход

Генерираният изход за тайно число 15615 и опити за отгатване 11222, 12345 и 15615 би изглеждало така:

```

Enter length of play number: 
5
Enter your suggestion: 
11222
1 bulls and 1 cows
Enter your suggestion: 
12345
2 bulls and 0 cows
Enter your suggestion: 
15615
5 bulls and 0 cows
Finished

```

В примера горе 5, 11222, 12345 и 15615 са въвдени от стандартния вход `System.in` с помощта на `java.util.Scanner`. Първо потребителят въвежда дължината на тайното число и после започва да прави опити да го отгатне.
