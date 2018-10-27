# Issue Tracker

### Условие

Ще създадем система за проследяване на грешки (bug tracking), проблеми (issue tracking) и управление на разработката на софтуерни проекти, подобна на [JIRA](https://www.atlassian.com/software/jira).

Issue Tracker-ът трябва да имплементира следния интерфейс:

```java
public interface IssueTracker {

	public Issue[] findAll(Component component, IssueStatus status);

	public Issue[] findAll(Component component, IssuePriority priority);

	public Issue[] findAll(Component component, IssueType type);

	public Issue[] findAll(Component component, IssueResolution resolution);

	public Issue[] findAllIssuesCreatedBetween(LocalDateTime startTime, LocalDateTime endTime);

	public Issue[] findAllBefore(LocalDateTime dueTime);
}
```

Класът, който го имплементира, трябва да има следния конструктор:

```java
public Jira(Issue[] issues);
```
*Issue* класът трябва да имплементира следния интерфейс:

```java
public interface IIssue {

	public void resolve(IssueResolution resolution);

	public void setStatus(IssueStatus status);

	public String getId();
}
```

и декларацията на конструктора му да изглежда така:

```java
public Issue(IssuePriority priority, Component component, User reporter, String description) throws InvalidReporterException; 
```

* подаденият reporter трябва да се валидира
* останалите 3 аргумента трябва да се валидират и ако са невалидни, да се хвърлят подходящ тип изключения.

...и да има поне следните полета:
  * уникално ID = трябва да се образува от краткото име на компонентата + уникално число, което при всяко създаване на Issue се увеличава с 1 (например, FMI-666)
  * приоритет, който може да бъде TRIVIAL, MINOR, MAJOR, CRITICAL.
  * resolution(как issue-то е било resolve-нато), който може да бъде FIXED, WONT_FIX, DUPLICATE, CANNOT_REPRODUCE, UNRESOLVED. При създаване, статусът е UNRESOLVED по подразбиране.
  * статус на issue-то, който може да бъде OPEN, IN_PROGRESS, RESOLVED, REOPENED. При създаване, статусът е OPEN по подразбиране.
  * тип на issue-то, който може да бъде TASK, BUG, NEW_FEATURE, кaтo TASK и NEW_FEATURE са Issue-та, които имат и допълнително поле за време, до което трябва да бъдат изпълнени (dueTime).
  * компонента, на която принадлежи issue-то.
  * кога е създадено във времето.
  * кога е променяно за последно във времето.

Класът `Component` трябва да е със следния конструктор:

```java
public Component(String name, String shortName, User creator);
```
  
Класът `User` трябва да е със следния конструктор:

```java
public User(String userName);
```
-------------------------------------
* *Requirement* В задачата са позволени само масиви и НЕ трябва да се ползват колекции и Map–ове.
* *Requirement* За да качите успешно задачата си в Grader-a, тя трябва да има следната структура:
```
src
╷
└─ bg/sofia/uni/fmi/jira/
   └─ Component.java
   └─ Issue.java
   └─ User.java
   └─ Jira.java
   (...)
   └─ issues/
      (...)
      └─ exceptions/
   └─ interfaces/
     └─ IIssue.java
     ├─ IssueTracker.java
     └─ (...)
   └─ enums/
      └─ (...)
```
* *Hint* Задачата има за цел да ви стимулира да пишете обектно-ориентиран код на Java като прилагате принципите на ООП, както и да упражните добро структуриране на кода в пакети.
* *Hint* За местата, на които смятате, че ви трябва тип време, използвайте [java.time API](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html) и обърнете по-специално внимание на `LocalDateTime` класа и неговите методи.

Успех, и не се срамувайте да задавате въпроси!
