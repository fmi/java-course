# MayTheCourseBeWithYou - оптимална система за записване на курсове 🎓

И ето идва пак краят на септември и трябва да се запишем за избираемите курсове, които сме си набелязали за този семестър.
Но стане ли 00:00, сайтът е забил, не знаем дали курсовете вече са запълнени, нямаме план Б и влизаме в паника.

За да си спестим тези мъки, нека създадем система (ще я наречем MayTheCourseBeWithYou), която ни помага да се записваме за курсове и да следим различни статистики относно тях в реално време.

## MayTheCourseBeWithYouAPI

Чрез интерфейса `MayTheCourseBeWithYouAPI` ще моделираме основната функционалност на нашето приложение.
Тя включва записване и отписване от курсове, както и добавяне и премахване на курсове от системата.
Чрез това API, ние ще можем и да достъпваме дашборда за статистики относно курсовете:

```java
package bg.sofia.uni.fmi.mjt.maythecoursebewithyou;

import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.entity.Course;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.entity.Student;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.exception.AlreadyEnrolledException;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.exception.CourseNotEnlistedException;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.exception.CourseAlreadyEnrolledException;

public interface MayTheCourseBeWithYouAPI {

    /**
     * Enlists (opens) a course for enrollment.
     *
     * @param course the course to enlist
     * @throws IllegalArgumentException if course is null
     * @throws CourseAlreadyEnrolledException if a course with the same code is already enlisted
     */
    void enlistCourse(Course course);

    /**
     * Delists (removes) the course with the given code, together with its enrollments and its
     * waitlist.
     *
     * @param courseCode the code of the course
     * @return the delisted course
     * @throws IllegalArgumentException if courseCode is null or blank
     * @throws CourseNotEnlistedException  if no course with the given code is enlisted
     */
    Course delistCourse(String courseCode);

    /**
     * Adds the student to the enrollment for the course with the given code. If the student is not
     * yet known to the system, enrolling registers them.
     *
     * @param student    the student who wants to enroll
     * @param courseCode the code of the course
     * @return true if the student got a seat, false if the student was put on the waitlist
     * @throws IllegalArgumentException if student is null, or courseCode is null or blank
     * @throws AlreadyEnrolledException if the student is already enrolled in or already waitlisted
     *                                  for this course
     * @throws CourseNotEnlistedException  if no course with the given code is enlisted
     */
    boolean enroll(Student student, String courseCode);

    /**
     * Removes the student from the enrollment for the course with the given code. If the student
     * is neither enrolled nor waitlisted for the course, the system is left unchanged.
     *
     * @param student    the student who drops the course
     * @param courseCode the code of the course
     * @return true if the student got dropped from the course, false if the student was neither enrolled nor waitlisted for the course
     * @throws IllegalArgumentException if student is null, or courseCode is null or blank
     * @throws CourseNotEnlistedException  if no course with the given code is enlisted
     */
    boolean drop(Student student, String courseCode);

    /**
     * Provides access to the statistics dashboard. The dashboard reflects the current state of the
     * system in terms of courses and students, and sees any further changes to the state in real time.
     * 
     * @return the statistics dashboard for the system
     */
    StatisticsDashboard getStatisticsDashboard();

}
```

В пакета `bg.sofia.uni.fmi.mjt.maythecoursebewithyou.impl` създайте публичен клас `MayTheCourseBeWithYou`, който
имплементира интерфейса и има следния публичен конструктор по подразбиране:

```java
public MayTheCourseBeWithYou()
```

## CourseEnrollment

Записванията за всеки курс се управляват от класа `CourseEnrollment` в пакета
`bg.sofia.uni.fmi.mjt.maythecoursebewithyou.impl`. Той пази актуалното състояние на **един**
курс - кои студенти в момента са записани и кои чакат за място на опашка.

```java
package bg.sofia.uni.fmi.mjt.maythecoursebewithyou.impl;

import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.entity.Course;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.exception.AlreadyEnrolledException;

import java.util.List;
import java.util.Set;

class CourseEnrollment {

    /**
     * Creates an empty enrollment (no seated students, empty waitlist) for the given course.
     *
     * @param course the course this enrollment tracks
     * @throws IllegalArgumentException if course is null
     */
    CourseEnrollment(Course course);

    /**
     * Enrolls the student with the given faculty number. Seats them if the course has room (its
     * seated count is below the course's {@code capacity}), otherwise places them at the back of
     * the waitlist.
     *
     * @param facultyNumber the faculty number of the student to enroll
     * @return true if the student got a seat, false if they were placed on the waitlist
     * @throws AlreadyEnrolledException if the student is already seated or already waitlisted
     */
    boolean enroll(String facultyNumber);

    /**
     * Drops the student with the given faculty number. If they held a seat, the freed seat is
     * immediately given to the student at the head of the waitlist, if any. If they were only on
     * the waitlist, they are simply removed.
     *
     * @param facultyNumber the faculty number of the student to drop
     * @return true if the student was seated or waitlisted (and thus removed), false if they were
     *         neither (the enrollment is left unchanged)
     */
    boolean drop(String facultyNumber);

    /**
     * @return the course this enrollment tracks
     */
    Course course();

    /**
     * @return the number of students currently holding a seat
     */
    int seatedCount();

    /**
     * @return the number of students currently on the waitlist
     */
    int waitlistCount();

    /**
     * @return an unmodifiable copy of the faculty numbers currently holding a seat
     */
    Set<String> seatedIds();

    /**
     * @return an unmodifiable copy of the waitlisted faculty numbers, in the order they will
     *         be seated
     */
    List<String> waitlistIds();

}
```

## StatisticsDashboard

Другата основна функционалност на нашето приложение `MayTheCourseBeWithYou` се предоставя от чрез `StatisticsDashboard`.
Чрез него потребителите ще могат да следят за различни видове статистики относно записването:

```java
package bg.sofia.uni.fmi.mjt.maythecoursebewithyou;

import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.entity.Course;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.entity.Student;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.exception.CourseNotEnlistedException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface StatisticsDashboard {

    /**
     * Ranks the enlisted courses by contention, i.e. how over-subscribed they are. The primary
     * rank key is the number of waitlisted students, in descending order; ties are broken first by
     * the number of enrolled students (descending), then by course title in ascending lexicographic
     * order.
     *
     * @return an unmodifiable Set of all enlisted courses in the described order
     *         (empty if there are no courses)
     */
    Set<Course> getMostContestedCourses();

    /**
     * Ranks the registered students by course load. The rank key is the number of courses the
     * student currently holds a seat in, in descending order; ties are broken by faculty number in
     * ascending lexicographic order. Waitlisted-only placements do not count towards the load.
     *
     * @return an unmodifiable List of all registered students in the described order
     *         (empty if there are no students)
     */
    List<Student> getBusiestStudents();

    /**
     * Counts the enrolled students of every enlisted course.
     *
     * @return an unmodifiable Map whose keys are the codes of the enlisted courses, and whose values
     *         are integers representing the count of students enrolled (seated) in the given course.
     */
    Map<String, Integer> countStudentsPerCourse();

    /**
     * Returns the students currently enrolled in the course with the given code.
     * 
     * @param courseCode the code of the course
     * @return an unmodifiable set of the students currently enrolled in the course
     *         (empty if none); iteration order is unspecified
     * @throws CourseNotEnlistedException  if no course with the given code is enlisted
     * @throws IllegalArgumentException if courseCode is null or blank
     */
    Set<Student> getEnrolledStudents(String courseCode);

    /**
     * Returns the students currently on the waitlist for the course with the given code in the order they will be seated.
     * 
     * @param courseCode the code of the course
     * @return an unmodifiable list of the students on the course's waitlist, in the order they
     *         will be seated; empty if none
     * @throws CourseNotEnlistedException  if no course with the given code is enlisted
     * @throws IllegalArgumentException if courseCode is null or blank
     */
    List<Student> getWaitlist(String courseCode);

}
```

## Entities

В основата на системата стоят два вида обекти, които се идентифицират чрез уникални идентификатори -
факултетен номер за `Student` и код за `Course`. Моделираме ги като прости
record-и в пакета `bg.sofia.uni.fmi.mjt.maythecoursebewithyou.entity`.

### Student

```java
public record Student(String facultyNumber, String name)
```

- `facultyNumber` е **уникалният идентификатор** на студента.

### Course

```java
public record Course(String code, String title, Department department, int capacity)
```

- `code` е **уникалният идентификатор** на курса (например `MJT`).
- `title` е името на курса (например `"Modern Java Technologies"`).
- `capacity` е броят места за записване. Над този брой студентите отиват на
  опашката.

### Department

Специалностите се моделират от следния enum в пакета
`bg.sofia.uni.fmi.mjt.maythecoursebewithyou.entity`:

```java
public enum Department {
    COMPUTER_SCIENCE, MATH, PHYSICS, STATISTICS
}
```

## Пример

```java
MayTheCourseBeWithYouAPI app = new MayTheCourseBeWithYou();

Course mjt = new Course("MJT", "Modern Java Technologies", Department.COMPUTER_SCIENCE, 2);
app.enlistCourse(mjt);

Student ivan = new Student("1", "Иван");
Student mariya = new Student("2", "Мария");
Student petar = new Student("3", "Петър");

app.enroll(ivan, "MJT");   // true  (1-во място)
app.enroll(mariya, "MJT"); // true  (2-ро място, курсът вече е пълен)
app.enroll(petar, "MJT");  // false (1-ви на опашката)

StatisticsDashboard dashboard = app.getStatisticsDashboard();
dashboard.getEnrolledStudents("MJT"); // {Иван, Мария}
dashboard.getWaitlist("MJT");         // [Петър]
dashboard.countStudentsPerCourse();   // {MJT=2}

app.enroll(ivan, "MJT");   // AlreadyEnrolledException
app.enroll(petar, "MJT");  // AlreadyEnrolledException (вече е в опашката)

app.drop(mariya, "MJT");   // true
// Мария освобождава място; Петър заема освободеното място.
dashboard.getEnrolledStudents("MJT"); // {Иван, Петър}
dashboard.getWaitlist("MJT");         // []

app.drop(ivan, "MJT");     // true (опашката е празна, никой не заема място)
dashboard.getEnrolledStudents("MJT"); // {Петър}

app.drop(ivan, "MJT");     // false (Иван вече не е записан)

Course delisted = app.delistCourse("MJT"); // връща курса MJT и го маха от системата
app.enroll(mariya, "MJT"); // CourseNotEnlistedException (вече не е enlist-нат)
```

## Структура на пакетите

Спазвайте имената на пакетите, тъй като в противен случай решението ви няма да може да бъде автоматично тествано.

```
src
└── bg.sofia.uni.fmi.mjt.maythecoursebewithyou
    ├── entity
    │      ├── Course.java
    │      ├── Department.java
    │      ├── Student.java
    │      └── (...)
    ├── exception
    │      ├── AlreadyEnrolledException.java
    │      ├── CourseAlreadyEnrolledException.java
    │      ├── CourseNotEnlistedException.java
    │      └── (...)
    ├── impl
    │      ├── CourseEnrollment.java
    │      ├── MayTheCourseBeWithYou.java
    │      ├── StatisticsDashboardImpl.java
    │      └── (...)
    ├── MayTheCourseBeWithYouAPI.java
    ├── StatisticsDashboard.java
    └── (...)
```

## ⚠️ Забележки

- Не променяйте интерфейсите (`MayTheCourseBeWithYouAPI`, `StatisticsDashboard`) по никакъв начин. Ако го направите, решението ви няма да може да бъде автоматично тествано.
- Използването на [Java Stream API](https://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/util/stream/package-summary.html) и/или [lambdas](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html) **не е разрешено**. Задачата трябва да се реши с помощта на знанията от курса до момента.