# Обектно-ориентирано програмиране с Java (част II)

## Burnout Prevention System: Surviving University Life 🎓☕📚

Еднo от най-големите предизвикателства в университетския живот е правилното планиране на семестъра - как да запишеш избираемите предмети така, че хем да покриеш минималните кредити, хем да не се изтощиш напълно и да запазиш здравословното си състояние (поне физическото).

Задачата ни този път е да създадем система за планиране на семестър.

:warning: **Важно:**
- `javadoc` коментарите над дадените методи съдържат очакваната функционалност на системата.
- Всички случаи, които не са експлицитно упоменати там или в условието, няма да бъдат тествани и зависят единствено от вашето въображение и преценка.

## SemesterPlannerAPI

Основната функционалност на системата е представена чрез следния интерфейс `SemesterPlannerAPI`, който дефинира методите за планиране на семестъра:

```java
package bg.sofia.uni.fmi.mjt.burnout.semester;

import bg.sofia.uni.fmi.mjt.burnout.exception.CryToStudentsDepartmentException;
import bg.sofia.uni.fmi.mjt.burnout.exception.DisappointmentException;
import bg.sofia.uni.fmi.mjt.burnout.exception.InvalidSubjectRequirementsException;
import bg.sofia.uni.fmi.mjt.burnout.plan.SemesterPlan;
import bg.sofia.uni.fmi.mjt.burnout.subject.UniversitySubject;

public sealed interface SemesterPlannerAPI permits AbstractSemesterPlanner {

	/**
	 * Calculates the subject combination for this semester type based on the subjectRequirements.
	 *
	 * @param semesterPlan the current semester plan needed for the calculation
	 * @throws CryToStudentsDepartmentException when a student cannot cover his semester credits.
	 * @throws IllegalArgumentException if the semesterPlan is missing or is null
	 * @throws InvalidSubjectRequirementsException if the subjectRequirements contain duplicate categories
	 * @return the subject list that balances credits, study time, and requirements
	 */
	UniversitySubject[] calculateSubjectList(SemesterPlan semesterPlan) throws InvalidSubjectRequirementsException;

	/**
	 * Calculates the amount of jars grandma will send you
	 *
	 * @param subjects the subjects to calculate jar count for
	 * @param maximumSlackTime the rest days grandma gave as limit before stopping the jar food deliveries
	 * @param semesterDuration the duration of the semester in days
	 * @throws IllegalArgumentException if the subjects are missing or null, or maximumSlackTime/semesterDuration are not positive integers
	 * @throws DisappointmentException if you cannot make grandma happy.
	 *
	 * @return number of procrastination days available
	 */
	int calculateJarCount(UniversitySubject[] subjects, int maximumSlackTime, int semesterDuration);
}
```

От вас се иска да създадете две имплементации на `SemesterPlannerAPI`:

1. `SoftwareEngineeringSemesterPlanner` където стратегията при записването на предметите ще е да се минимизират броя предмети, с които да се покрият изискванията за семестъра.

2. `ComputerScienceSemesterPlanner`, където стратегията при записването на предметите ще е да се запишат предметите с по-добри отзиви от други състуденти, без да се гледат нужния брой предмети по категорията им.

---
:warning: **Важно:**
**Интерфейсът не трябва да позволява други имплементации, освен посочените две**

Освен да направят преценка кои предмети трябва да запишат, студентите имат нужда и да си почиват и похапват. Кой друг може да ги снабди с провизии, ако не баба им. 👵

Както забелязвате, `calculateJarCount` методът връща броя на бурканите, които ще са нужни за оцеляване.
За простота няма да има различна стратегия за имплементацията на този метод.

Ще добавяме коефициент, зависещ от категорията на предмета, въз основа на който се изчислява нужната почивка след като приключим с ученето за този предмет.

Коефициентите са както следва:
- `MATH`: 0.2 (математиката е трудна и изисква повече време за почивка, особено ако е курс по Статистика)
- `PROGRAMMING`: 0.1
- `THEORY`: 0.15
- `PRACTICAL`: 0.05

Нужната почивка след всеки предмет е `времето нужно да се учи за предмета * коефициента на категорията му` 
(При нужда нека се закръглява нагоре)

Броят буркани, които бабата би изпратила на внука си, се изчислява по следния начин:

- На всеки 5 дни работа, която ще свършиш, баба ти ще ти праща по един буркан (криза е, не може по-често).

- Ако цялата продължителност на семестъра не ти стига за ученето + почивките, ще трябва да пиеш повече редбулчета и да почиваш по-малко. Като утешителна награда, баба ти решава да извади малко допълнителна зимнина от тайните си запаси и ти изпраща (буркани * 2).

- Ако си голям тарикат и много си почиваш, баба ти остава леко разочарована и хвърля `DisappointmentException` (баба хвърля `Exception`?!?!?! Кой ги пише тия условия???).

---
#### Category

Категорията ще я представим като `Enum`, с няколко константни стойности.

```java
public enum Category {
 
    MATH,
    PROGRAMMING,
    THEORY,
    PRACTICAL

}
```

---
## UniversitySubject

Представлява университетски предмет с неговите характеристики.

```java
/**
 * @param name the name of the subject
 * @param credits number of credit hours for this subject
 * @param rating difficulty rating of the subject (1-5)
 * @param category the academic category this subject belongs to
 * @param neededStudyTime estimated study time in days required for this subject
 *                        
 * @throws IllegalArgumentException if the name of the subject is null or blank
 * @throws IllegalArgumentException if the credits are not positive
 * @throws IllegalArgumentException if the rating is not in its bounds
 * @throws IllegalArgumentException if the Category is null
 * @throws IllegalArgumentException if the neededStudy time is not positive
 */
public record UniversitySubject(String name, int credits, int rating, Category category, int neededStudyTime) {
}
```
---
## SubjectRequirement

Представлява изискването за записване на определен брой предмети по дадена категория.
```java
/**
 *
 * @param category the academic category this subject belongs to
 * @param minAmountEnrolled minimum amount of subject enrolled for the category
 *                          
 * @throws IllegalArgumentException if the category is null
 * @throws IllegalArgumentException if the credits are negative
 */
public record SubjectRequirement(Category category, int minAmountEnrolled) 

```
---
## SemesterPlan

Чрез този `record` ще си моделираме нужните характеристики и данни за семестриалния план.

```java
/**
 * Represents a requirement for the amount of subjects needed to be enrolled during the semester.
 *
 * @param subjects the array of all subjects that a student can enroll in a given semester
 * @param subjectRequirements the array of requirements for the subjects enrolled for the category
 * @param minimalAmountOfCredits minimum amount of credits enrolled for the category
 *
 * @throws IllegalArgumentException if the subjects array is null
 * @throws IllegalArgumentException if the subjectRequirements array is null
 * @throws IllegalArgumentException if the minimalAmountOfCredits is negative
 */
public record SemesterPlan(UniversitySubject[] subjects, SubjectRequirement[] subjectRequirements, int minimalAmountOfCredits)
```
---
## Примери

```java
package bg.sofia.uni.fmi.mjt.burnout;

import bg.sofia.uni.fmi.mjt.burnout.exception.InvalidSubjectRequirementsException;
import bg.sofia.uni.fmi.mjt.burnout.plan.SemesterPlan;
import bg.sofia.uni.fmi.mjt.burnout.semester.SoftwareEngineeringSemesterPlanner;
import bg.sofia.uni.fmi.mjt.burnout.subject.Category;
import bg.sofia.uni.fmi.mjt.burnout.subject.SubjectRequirement;
import bg.sofia.uni.fmi.mjt.burnout.subject.UniversitySubject;

import java.util.Arrays;


public class Main {

	public static void main(String... args) throws InvalidSubjectRequirementsException {
		SoftwareEngineeringSemesterPlanner planner = new SoftwareEngineeringSemesterPlanner();

		UniversitySubject[] subjects = {
				new UniversitySubject("Calculus", 6, 5, 40, Category.MATH),
				new UniversitySubject("Java Programming", 4, 4, 30, Category.PROGRAMMING),
				new UniversitySubject("Linear Algebra", 5, 3, 35, Category.MATH),
				new UniversitySubject("Data Structures", 3, 5, 25, Category.PROGRAMMING)
		};

		SubjectRequirement[] requirements = {
				new SubjectRequirement(Category.MATH, 1),
				new SubjectRequirement(Category.PROGRAMMING, 1)
		};

		SemesterPlan plan1 = new SemesterPlan(subjects, requirements, 5);

		printSubjects(planner.calculateSubjectList(plan1));
		//result1 = ["Calculus", "Java Programming"]

		SemesterPlan plan2 = new SemesterPlan(subjects, requirements, 10);

		//result2 = ["Calculus", "Java Programming"]
		printSubjects(planner.calculateSubjectList(plan2));

		SemesterPlan plan3 = new SemesterPlan(subjects, requirements, 15);

		//result3 = ["Calculus", "Linear Algebra", "Java Programming"]
		printSubjects(planner.calculateSubjectList(plan3));

		UniversitySubject[] selectedSubjects = planner.calculateSubjectList(plan1);

		int jarCount = planner.calculateJarCount(selectedSubjects, 11, 50);
		System.out.println("Jar count: " + jarCount);
		//jarCount = 28
	}

	private static void printSubjects(UniversitySubject[] subjects) {
		System.out.println(Arrays.toString(subjects));
	}
	
}

```

## Структура на пакетите

Спазвайте имената на пакетите на всички по-горе описани класове, интерфейси и конструктори, тъй като в противен случай, решението ви
няма да може да бъде автоматично тествано.

```bash
src
└─ bg/sofia/uni/fmi/mjt/burnout
    ├─ exception/
    │  ├─ CryToStudentsDepartmentException.java
    │  ├─ DisappointmentException.java
    │  └─ InvalidSubjectRequirementsException.java
    ├─ plan/
    │  └─ SemesterPlan.java
    ├─ semester/
    │  ├─ ComputerScienceSemester.java
    │  ├─ SemesterPlannerAPI.java
    │  └─ SoftwareEngineeringSemester.java
    └─ subject/
        ├─ Category.java
        ├─ SubjectRequirement.java
        └─ UniversitySubject.java
```

Можете и е желателно да добавите собствени класове и абстракции в съответните пакети.

## :warning: Забележки

> Задачата трябва да се реши с помощта на знанията от курса до момента и допълнителните Java APIs, указани в условието.
