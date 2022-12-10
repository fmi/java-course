# MJT Grading Simulator :mortar_board:

Ще създадем прост симулатор на процеса по оценяване на задачите в курса. Студентите ще качват решенията си в грейдъра, а асистентите ще взимат решения и ще ги оценяват.

:bulb: Днешната задача, макар и в максимално опростен вид, ни запознава с един често срещан класически concurrency *design pattern*: [Producer-Consumer](https://jenkov.com/tutorials/java-concurrency/producer-consumer.html). Той се ползва за отделяне (*decoupling*) на *producer* и *consumer* логиката в алгоритми, чрез разделяне на *идентифицирането* на задачи/работа от *изпълнението* на тези задачи/работа, така че те да са *слабо свръзани* (*loosely coupled*) и да могат да се изпълняват едновременно и асинхронно.

![Producer-Consumer Pattern](./producer-consumer-pattern.png)

### Условие

Задачата е разделена на 6 стъпки, като препоръката ни е да започнете с проста частична имплементация, която да допълвате и подобрявате стъпка по стъпка.

**1. Създайте клас `CodePostGrader`**

Класът `CodePostGrader` има публичен конструктор с параметри`(int numberOfAssistants)`, който приема цяло число (броят на асистентите в екипа на курса) и имплементира интерфейса `AdminGradingAPI`:

```java
package bg.sofia.uni.fmi.mjt.grading.simulator.grader;

import java.util.List;

import bg.sofia.uni.fmi.mjt.grading.simulator.Assistant;
import bg.sofia.uni.fmi.mjt.grading.simulator.assignment.Assignment;

public interface AdminGradingAPI extends StudentGradingAPI {

    /**
     * Retrieves an assignment to be graded from the set of submitted but still ungraded assignments.
     * If there are several ungraded assignments, which particular one to return is undefined.
     * Getting an assignment removes it from the set of ungraded assignments.
     *
     * @return an assignment to be graded.
     */
    Assignment getAssignment();

    /**
     * Returns the total number of assignments submitted.
     *
     * @return the total number of assignments submitted.
     */
    int getSubmittedAssignmentsCount();

    /**
     * Notifies the assistants to finalize grading. All already submitted assignments must be graded.
     */
    void finalizeGrading();

    /**
     * Returns a list of the course assistants, in undefined order.
     *
     * @return a list of the assistants
     */
    List<Assistant> getAssistants();

}
```

Този интерфейс от своя страна разширява интерфейса `StudentGradingAPI`:

```java
package bg.sofia.uni.fmi.mjt.grading.simulator.grader;

import bg.sofia.uni.fmi.mjt.grading.simulator.assignment.Assignment;

public interface StudentGradingAPI {

    /**
     * Submits a new {@link Assignment} for grading.
     * 
     * @param assignment which is submitted for grading
     */
    void submitAssignment(Assignment assignment);

}
```

Решенията на задачите ще моделираме с `record`-a `Assignment`:

```java
package bg.sofia.uni.fmi.mjt.grading.simulator.assignment;

public record Assignment(int studentFn, String studentName, AssignmentType type) {
}
```

Има няколко вида `Assignment`-и, които се описват от `enum`-a `AssignmentType`:

```java
package bg.sofia.uni.fmi.mjt.grading.simulator.assignment;

public enum AssignmentType {
    LAB(20), PLAYGROUND(40), HOMEWORK(80), PROJECT(120);

    private final int gradingTime;

    AssignmentType(int gradingTime) {
        this.gradingTime = gradingTime;
    }

    public int getGradingTime() {
        return gradingTime;
    }
}
```

⭐ Забележки:
 - Използвайте подходяща структура от данни, в която да съхранявате решенията (assignments), които са качени в грейдъра и чакат свободен асистент, който да се заеме с оценяването им.
 - Асистентите се създават и започват работа при създаването на `CodePostGrader`.

**2. Създайте клас `Student`, който е нишка, и има следния вид:**

```java
package bg.sofia.uni.fmi.mjt.grading.simulator;

import bg.sofia.uni.fmi.mjt.grading.simulator.grader.StudentGradingAPI;

public class Student implements Runnable {

    public Student(int fn, String name, StudentGradingAPI studentGradingAPI) {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    public int getFn() {
        return fn;
    }

    public String getName() {
        return name;
    }

    public StudentGradingAPI getGrader() {
        return studentGradingAPI;
    }

}
```

При стартирането си, студентът създава решение на някоя задача (`Assignment`) и го качва в грейдъра чрез метода `submitAssignment(Assignment assignment)`. За простота, ще приемем, че всеки студент предава решение на една-единствена задача. Има няколко вида задачи, като студентът избира на случаен принцип един от тях. Създаването на решение отнема известно време, което можем да моделираме с паузиране на нишката - студент за случаен отрязък от 0 до 1000 милисекунди, след което решението се качва в грейдъра.

**3. Създайте клас `Assistant`, който е нишка и има следния вид:**

```java
package bg.sofia.uni.fmi.mjt.grading.simulator;

import bg.sofia.uni.fmi.mjt.grading.simulator.grader.AdminGradingAPI;

public class Assistant extends Thread {

    public Assistant(String name, AdminGradingAPI grader) {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    public int getNumberOfGradedAssignments() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

}
```

Задачата на асистентите е, да преглеждат и оценяват решенията на задачите, качени в грейдъра:

  1. Избиране на решение (assignment) измежду качените и все още непрегледани и неоценени решения.
  2. Преглеждане и оценяване на решение - ще го симулираме отново с паузиране на нишката - асистент за период от определен брой милисекунди, който е различен за всеки тип задача и се определя от метода `getGradingTime()` на `AssignmentType`.
  3. След като прегледа и оцени решението, асистентът подхваща следващо неоценено решение. 

:star: Забележка:

- В даден момент (докато оценяването не е финализирано), може да няма качени непрегледани решения, които чакат да бъдат оценени. В такъв случай, асистентите без работа изчакват да се появи ново решение.

**4. В клас по ваш избор, създайте инстанция на `CodePostGrader` и пуснете студенти да решават и качват задачи**

- При създаването на `CodePostGrader`, изберете подходящ брой асистенти в екипа на курса - например 5.
- Създайте и пуснете да решават задачи някакъв брой студенти - например 30.
- Изчакайте всички студенти да качат решенията си.

**5. Тествайте локално решението ви**

- В основната нишка, изчакайте няколко секунди, за да дадете време на асистентите да проверят и оценят решенията.
- След това, извикайте метода `finalizeGrading()` на `CodePostGrader`.
- Всички решения, качени в грейдъра преди извикването на `finalizeGrading()`, трябва да бъдат прегледани и оценени.
- След като даден асистент приключи работа (когато оценяването е финализирано и всички качени решения са прегледани и оценени), изведете на конзолата името на асистента и броя на проверените от него/нея решения (за ваши тестови цели).
- Уверете се, че общият брой проверени решения е равен на общия брой решения, качени от студенти в грейдъра.

**6. Изполвайте насоките от горната точка за създаване на unit тестове**



## Структура на проекта

```
src
└─ bg.sofia.uni.fmi.mjt.grading.simulator
    ├── assignment
    │    ├─ Assignment.java
    │    └─ AssignmentType.java
    ├── grader
    │    ├─ AdminGradingAPI.java
    │    ├─ CodePostGrader.java
    │    └─ StudentGradingAPI.java
    ├── Assistant.java
    ├── Student.java
    └── (...)
test
└─ bg.sofia.uni.fmi.mjt.grading.simulator
    └── (...)
```

В грейдъра качете общ `zip` архив на папки `src` и `test`.
