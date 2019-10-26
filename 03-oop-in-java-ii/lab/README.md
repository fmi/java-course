# Обектно-ориентирано програмиране с Java (част II)

## Issue Tracker :bug:

Целта на това упражнение е да приложим наученото от втората лекция по ООП - абстрактни класове, интерфейси, изброими типове и изключения.

Ще създадем система за проследяване на грешки (bug tracking) и управление на разработката на софтуерни проекти, подобна на [JIRA](https://www.atlassian.com/software/jira).

Нейни основни компоненти са следните няколко класа:

### `class Jira`

- Имплементира интерфейсите:

```java
public interface Filter {
    // If there is no such Issue the method should return null
    public Issue find(String issueID);
}

public interface Repository {
    public void addIssue(Issue issue);
}
```

- Има конструктор без параметри:

```java
public Jira();
```
- Както и методите:

```java
public void addActionToIssue(Issue issue, WorkAction action, String actionDescription) {}
public void resolveIssue(Issue issue, IssueResolution resolution) {}
```

- Може да съхранява **до 100 Issue-та** наведнъж. При надвишаване на този лимит, или при опит за добавяне на `Issue`, което вече е добавено (критерий за еднаквост е уникалното ID на `Issue`-то) хвърляйте **походящо изключение**.

### `abstract class Issue`

- Има следните методи:

```java
public String getIssueID() { return null; }
public String getDescription() { return null; }
public IssuePriority getPriority() { return null; }
public IssueResolution getResolution() { return null; }
public IssueStatus getStatus() { return null; }
public Component getComponent() { return null; }
public LocalDateTime getCreatedOn() { return null; }
public LocalDateTime getLastModifiedOn() { return null; }
public String[] getActionLog(){ return null; }

public void setStatus(IssueStatus status) {}
public void addAction(WorkAction action, String description) {}

public abstract void resolve(IssueResolution resolution);
  ```

- Декларацията на конструктора му да изглежда така:

```java
public Issue(IssuePriority priority, Component component, String description) {}
```

- Има *поне* следните полета:
  * **уникално ID** - трябва да се образува от краткото име на компонента + уникално число, което при всяко създаване на `Issue` се увеличава с 1 (например, FMI-666; **започва от 0**)
  * **описание** - кратко описание на проблема, който решава
  * **приоритет** - може да бъде *TRIVIAL*, *MINOR*, *MAJOR*, *CRITICAL*
  * **resolution** - как issue-то е било разрешено. Може да бъде *FIXED*, *WONT_FIX*, *DUPLICATE*, *CANNOT_REPRODUCE*, *UNRESOLVED*. При създаване, статусът е *UNRESOLVED* по подразбиране
  * **статус** - може да бъде *OPEN*, *IN_PROGRESS*, *RESOLVED*. При създаване, статусът е *OPEN* по подразбиране
  * **компонент**, на който принадлежи issue-то
  * **action log** - пази списък от действията, които е извършвал програмистът, докато работи по `Issue`-то. Действията могат да бъдат *RESEARCH*, *DESIGN*, *IMPLEMENTATION*, *TESTS*, *FIX*

    Списъкът се състои от низове във формат `<action>: <description>`. Например *"fix: Added 'products.manage' scope check in authorizer"*. Описанието на действието е задължително

    Може да съхранява до 20 действия. При надвишаване на този лимит, хвърляйте **походящо изключение**.
  * **момент (*timestamp*) на създаване**
  * **кога е променяно** за последно във времето.

### `class Task`

Класът `Task` наследява `Issue`. Използва се за краткотрайни задачи, които обикновено не са свързани с писането на код.

Характерното за този клас е, че в action log-a си не може приема действията `Fix`, `Implementation` и `Tests`, понеже те са характерни за друг тип Issue-та. При опит за добавяне на такова действие, трябва да се хвърля подходящо изключение.

### `class Feature`

Наследява `issue`. Представлява задачата, която един програмист получава, когато работи върху добавянето на нова функционалност в компонента, по който работи.

Този тип задачи не могат да бъдат resolve-нати, ако за тях не са добавени действията `Design`, `Implementation` и `Tests`. При опит да бъдат resolve-нати, без да е добавено някое от тези задължителни действия, трябва да се хвърля подходящо изключение.

### `class Bug`

Такава задача получава програмистът, който трябва да поправи част от вече съществуващ код.

Отново наследява абстрактния клас `Issue`. Един бъг не може да се resolve-не, докато не се изпълнят действията `Fix` и `Tests`. При опит да бъде resolve-нат, без да е добавено някое от тези задължителни действия, трябва да се хвърля подходящо изключение.

### `class Component`

- Има следния конструктор:

```java
public Component(String name, String shortName);
```

- Приемаме два компонента за еднакви, ако имат еднакви имена (name) и съкращения (shortName).

-------------------------------------
:heavy_exclamation_mark: Забележки:

- В задачата е позволено използването САМО на масиви, понеже все още не сме се запознали с колекции.
- Проектът ви трябва да има следната структура:

```bash
  src
  ╷
  └─ bg/sofia/uni/fmi/mjt/jira/
    ├─ Jira.java
    ├─ issues/
    |   ├─ Issue.java
    |   ├─ Task.java
    |   ├─ Feature.java
    |   ├─ Bug.java
    |   ├─ Component.java
    |   └─(...)
    |
    ├─ interfaces/
    | ├─ Filter.java
    | ├─ Repository.java
    | └─ (...)
    |
    └─ enums/
      ├─ IssuePriority.java
      ├─ IssueResolution.java
      ├─ IssueStatus.java
      ├─ WorkAction.java
      └─ (...)
  ```

Когато сте готови, качете `zip` архив на `src` директорията на проекта в [`sapera.org`](sapera.org).
