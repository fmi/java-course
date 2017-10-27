# Обектно-ориентирано програмиране с Java (част I)

## СУСИ, версия 0.5

СУСИ е система за управление на студентска информация. В тази си ранна версия, СУСИ има следната базова функционалност:

1. Регистриране на нов студент
2. Въвеждане на оценка от изпит по предмет
3. Извеждане на справки за даден студент (общ брой натрупани кредити; среден успех от положените изпити)

Създайте клас `SusiCockpit`, който имплементира следния интерфейс:

```java
public interface Susi {
	
	/**
	* Registers a student in the system.
	* Returns true, if the operation is successful and false, if the student
	* is already registered
	*/
	boolean registerStudent(Student student);

	/**
	 * Returns the number of registered students
	 */
	int getStudentsCount();
	
	/**
	* Sets a grade for the student for the specified course and adds the credits of the
	* course to the total credits of the student.
	* Returns true, if the operation is successful and false, if the student is not found
	*/
	boolean setGrade(Student student, Course course, double grade);
	
	/**
	* Returns the total sum of credits for this student
	*/
	double getTotalCredits(Student student);
	
	/**
	* Returns the grade point average for the given student
	*/
	double getGPA(Student student);
}
```

Всеки студент е инстанция на клас `Student`, който имплементира интерфейса `User`

```java
public interface User {
	String getName();
	void setName(String name);
	
	int getFacultyNumber();
	void setFacultyNumber(int facultyNumber);
}
```

Всеки курс се характеризира с уникален идентификатор и име. Курсът е инстанция на клас `Course` и имплементира интерфейсa `Subject`.

```java
public interface Subject {
	void setId(String id);
	String getId();
	
	void setName(String name);
	String getName();
}
```

Имайте предвид, че, за щастие, всеки студент може да запише не повече от 50 курса за цялото си следване. Също, капацитетът на университета не е безграничен: могат да бъдат регистрирани не повече от 1000 студента.

Всеки от класовете `SusiCockpit`, `Student` и `Course` трябва да имат конструктор по подразбиране.

## Допълнителни задачи

1. Реализирайте конзолен клиент към `SusiCockpit`, който извежда на стандартния изход меню с възможните операции, при избор на операция прочита от стандартния вход необходимите параметри и изпълява операцията, като извежда резултата на стандартния изход.

Разяснения
---

### Модификатори на методи в интерфейс

Всеки метод в интерфейс по подразбиране е `public` и `abstract`. Единият или и двата модификатора могат да фигурират и явно в декларацията на метода. До Java 8, *всички* методи в интерфейсите са само и единствено `public` и `abstract`, и явната им декларация като такива не се окуражава, защото е излишна и не носи допълнителна информация. В Java 8 интерфейсите могат да имат също `default` и `static` методи, а в Java 9 - и `private` методи. Въпросът какво изисква добрият стил в този случай е отворен, но при всички положения, добре е в рамките на вашите интерфейси да сте консистенти в избора си.

### Коментари в Java код

Java поддържа три вида коментари в кода:

```java
/* text */
```
Компилаторът игнорира всичко от `/*` до `*/`

```java
/** documentation */
```

Това е коментар с документация на кода. Компилаторът го игнорира, но JDK-то съдържа инструмент (изпълним файл с име `javadoc` в `/bin` директорията, където са също изпълнимите файлове `javac`, `java` и `jshell`), който по тези специални коментари в кода генерира API документация в HTML формат, която да послужи на потребителите на вашия код. Повече информация за `javadoc` ще намериш в [документацията на инструмента](http://www.oracle.com/technetwork/java/javase/documentation/javadoc-137458.html).

```java
// text
```

Компилаторът игнорира всичко от `//` до края на реда.

### Още подсказки
- За един студент и един курс може да има най-много една оценка
- Ако студент вече има оценка за даден курс, то `set-ването`  на нова такава ще обнови първата (все едно поправка от изпит)
- Ако се опитаме да регистрираме вече регистриран студент, то `registerStudent` трябва да върне `false` и да изведе подходящо съотщение в конзолата
- При викане на `getGPA` или `getTotalCredits` за студент, който не е регистриран в суси, методите трябва да връщат 0.0
- При достигане на някой от капацитетите (брой студенти в суси или брой курсове за даден студент) се очаква съответния  метод за добавяне на върне `false` и в конзолата да се изведе подходящо съобщение
