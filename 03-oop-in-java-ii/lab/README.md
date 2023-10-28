# Udemy :mortar_board:

[Udemy](https://www.udemy.com) е популярна платформа за обучение, особено сред програмистите. Целта на упражнението тази седмица е да създадем набор от класове и интерфейси, с които да имплементираме основните ѝ функционалности.

### Udemy

В пакета `bg.sofia.uni.fmi.mjt.udemy` създайте публичен клас `Udemy`, който има конструктор

```java
public Udemy(Account[] accounts, Course[] courses)
```

и имплементира интерфейса `LearningPlatform`:

```java
package bg.sofia.uni.fmi.mjt.udemy;

import bg.sofia.uni.fmi.mjt.udemy.account.Account;
import bg.sofia.uni.fmi.mjt.udemy.course.Category;
import bg.sofia.uni.fmi.mjt.udemy.course.Course;
import bg.sofia.uni.fmi.mjt.udemy.exception.AccountNotFoundException;
import bg.sofia.uni.fmi.mjt.udemy.exception.CourseNotFoundException;

public interface LearningPlatform {

    /**
     * Returns the course with the given name.
     * 
     * @param name the exact name of the course.
     * @throws IllegalArgumentException if name is null or blank.
     * @throws CourseNotFoundException if course with the specified name is not present in the platform.
     */
    Course findByName(String name) throws CourseNotFoundException;

    /**
     * Returns all courses which name or description containing keyword.
     * A keyword is a word that consists of only small and capital latin letters.
     * 
     * @param keyword the exact keyword for which we will search.
     * @throws IllegalArgumentException if keyword is null, blank or not a keyword.
     */
    Course[] findByKeyword(String keyword);

    /**
     * Returns all courses from a given category.
     * 
     * @param category the exact category the courses for which we want to get.
     * @throws IllegalArgumentException if category is null.
     */
    Course[] getAllCoursesByCategory(Category category);

    /**
     * Returns the account with the given name.
     * 
     * @param name the exact name of the account.
     * @throws IllegalArgumentException if name is null or blank.
     * @throws AccountNotFoundException if account with such a name does not exist in the platform.
     */
    Account getAccount(String name) throws AccountNotFoundException;

    /**
     * Returns the course with the longest duration in the platform.
     * Returns null if the platform has no courses.
     */
    Course getLongestCourse();

    /**
     * Returns the cheapest course from the given category.
     * Returns null if the platform has no courses.
     * 
     * @param category the exact category for which we want to find the cheapest course.
     * @throws IllegalArgumentException if category is null.
     */
    Course getCheapestByCategory(Category category);
}

```

### AccountBase

Потребителските акаунти в рамките на платформата са три типа:

* Стандартен (Standard), който не може да се възползва от намаление и трябва да закупува курсове на стандартната им цена.
* Ученически (Education), който може да се възползва от намаление от 15% по следния начин:
  * Еднократно за шестия курс, когато постигне среден успех по-голям или равен на 4.50 от пет последователно завършени курса (дори и някой от тях да е с оценка 2).
  * Следващо намаление може да се получи едва след още пет последователно завършени курса с среден успех по-голям или равен на 4.50.
* Бизнес (Business), който винаги ползва намаление от 20%, но само за курсовете зададените при създаването му категории. Този акаунт няма правото да купува курсове извън зададените му категории.

Типът на даден потребителски акаунт се моделира от следния enum:

```java
package bg.sofia.uni.fmi.mjt.udemy.account.type;

public enum AccountType {
    STANDARD(0),
    EDUCATION(0.15),
    BUSINESS(0.20);

    private final double discount;

    AccountType(double discount) {
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }
}
```

 Акаунтите се идентифицират еднозначно по потребителското си име. В пакета `bg.sofia.uni.fmi.mjt.udemy.account` създайте абстрактен клас `AccountBase`, който има конструктор

 ```java
 public AccountBase(String username, double balance)
 ```

 и имплементира интерфейса `Account`.

```java
    public interface Account {

    /**
     * Returns the username of the account.
     */
    String getUsername();

    /**
     * Adds the given amount of money to the account's balance.
     * 
     * @param amount the amount of money which will be added to the account's balance.
     * @throws IllegalArgumentException if amount has a negative value.
     */
    void addToBalance(double amount);

    /**
     * Returns the balance of the account.
     */
    double getBalance();

    /**
     * Buys the given course for the account.
     * 
     * @param course the course which will be bought.
     * @throws IllegalArgumentException if the account buyer is of type BusinessAccount and course has category which is not among the permitted for this account
     * @throws InsufficientBalanceException if the account does not have enough funds in its balance.
     * @throws CourseAlreadyPurchasedException if the course is already purchased for this account.
     * @throws MaxCourseCapacityReachedException if the account has reached the maximum allowed course capacity.
     */
    void buyCourse(Course course) throws InsufficientBalanceException, CourseAlreadyPurchasedException, MaxCourseCapacityReachedException;

    /**
     * Completes the given resources that belong to the given course provided that the course was previously purchased by this account.
     *
     * @param resources the resources which will be completed.
     * @param course the course in which the resources will be completed.
     * @throws IllegalArgumentException if course or resourcesToComplete are null.
     * @throws CourseNotPurchasedException if course is not currently purchased for this account.
     * @throws ResourceNotFoundException if a certain resource could not be found in the course.
     */
    void completeResourcesFromCourse(Course course, Resource[] resourcesToComplete) throws CourseNotPurchasedException, ResourceNotFoundException;

    /**
     * Completes the whole course.
     *
     * @param course the course which will be completed.
     * @param grade the grade with which the course will be completed.
     * @throws IllegalArgumentException if grade is not in range [2.00, 6.00] or course is null.
     * @throws CourseNotPurchasedException if course is not currently purchased for this account.
     * @throws CourseNotCompletedException if there is a resource in the course which is not completed.
     */
    void completeCourse(Course course, double grade) throws CourseNotPurchasedException, CourseNotCompletedException;

    /**
     * Gets the course with the least completion percentage. 
     * Returns null if the account does not have any purchased courses.
     */
    Course getLeastCompletedCourse();
}
```

В пакета `bg.sofia.uni.fmi.mjt.udemy.account` създайте три класа - `StandardAccount`, `EducationalAccount` и `BusinessAccount`, които наслеядават абстрактния клас `AccountBase`.
Приемаме, че даден акаунт може да закупи най-много **100** курса.

### BusinessAccount

Kласът `BusinessAccount` има следния конструктор:

 ```java
public BusinessAccount(String username, double balance, Category[] allowedCategories)
 ```

### Course

Курсовете се представят чрез класа `Course` в пакета `bg.sofia.uni.fmi.mjt.udemy.course`, който има конструктор

```java
public Course(String name, String description, double price, Resource[] content, Category category)
```

и имплементира интерфейсите `Completable` и `Purchasable`.

Допълнително, `Course` съдържа и следните методи (при нужда или желание, може да добавяте допълнителни):

```java
    /**
     * Returns the name of the course.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the course.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the price of the course.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the category of the course.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Returns the content of the course.
     */
    public Resource[] getContent() {
        return content;
    }

    /**
     * Returns the total duration of the course.
     */
    public CourseDuration getTotalTime() {
        return totalTime;
    }

    /**
     * Completes a resource from the course.
     * 
     * @param resourceToComplete the resource which will be completed.
     * @throws IllegalArgumentException if resourceToComplete is null.
     * @throws ResourceNotFoundException if the resource could not be found in the course.
     */
    public void completeResource(Resource resourceToComplete) throws ResourceNotFoundException {
        // TODO: add implementation here
    }
```

## Category

Видът на даден курс се представя от следния `enum`:

```java
package bg.sofia.uni.fmi.mjt.udemy.course;

public enum Category {
    DEVELOPMENT, 
    BUSINESS, 
    FINANCE, 
    SOFTWARE_ENGINEERING, 
    DESIGN, 
    MARKETING, 
    HEALTH_AND_FITNESS, 
    MUSIC
}
```

## Resource

Ресурсите се представят чрез класа `Resource` в пакета `bg.sofia.uni.fmi.mjt.udemy.course`, който има конструктор `public Resource(String name, ResourceDuration duration)` и имплементира интерфейса `Completable`:

Класът `Resource` допълнително съдържа и следните методи (при нужда или желание, може да добавяте допълнителни):

```java
    /**
     * Returns the resource name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the total duration of the resource.
     */
    public ResourceDuration getDuration() {
        return duration;
    }

    /**
     * Marks the resource as completed.
     */
    public void complete() {
        // TODO: add implementation here    
    }
```

## Completable

```java
public interface Completable {
    /**
     * Returns true if and only if the course is completed.
     */
    boolean isCompleted();

    /**
     * Returns the completion percentage of the resource.
     * The percentage is an integer in the range [0, 100].
     */
    int getCompletionPercentage();
}
```

## Purchasable

```java
public interface Purchasable {
    /**
     * Marks the object as purchased.
     */
    void purchase();

    /**
     * Returns true if and only if the object is purchased.
     */
    boolean isPurchased();
}
```

### ResourceDuration

Продължителността на даден ресурс се моделира от `record`-a `ResourceDuration(int minutes)`. `ResourceDuration` трябва да има компактен конструктор, който да валидира, че минутите са число в интервала [0, 60]. При неуспешна валидация, конструкторът трябва да хвърля `IllegalArgumentException`.

### CourseDuration

Продължителността на даден курс се моделира от `record`-a `CourseDuration(int hours, int minutes)`, инстанции от който не могат да се създават директно, а се получават чрез публичен статичен метод със сигнатура `of(Resource[] content)`. Продължителността на даден курс се дефинира като сумата от продължителностите на ресурсите му. `CourseDuration` трябва да има компактен конструктор, който да валидира, че часовете са число в интервала [0, 24], а минутите са число в интервала [0, 60]. При неуспешна валидация, конструкторът трябва да хвърля `IllegalArgumentException`.

### Пакети

Спазвайте имената на пакетите на всички по-горе описани типове, тъй като в противен случай, решението ви няма да може да бъде автоматично тествано.

```
src
└── bg.sofia.uni.fmi.mjt.udemy
    ├── exception
    │      ├── AccountNotFoundException.java
    │      ├── CourseAlreadyPurchasedException.java
    │      ├── CourseNotCompletedException.java
    │      ├── CourseNotFoundException.java
    │      ├── CourseNotPurchasedException.java
    │      ├── InsufficientBalanceException.java
    │      ├── MaxCourseCapacityReachedException.java
    │      └── ResourceNotFoundException.java
    ├── account
    │      ├── type
    │      │   └── AccountType.java
    │      ├── Account.java
    │      ├── AccountBase.java
    │      ├── BusinessAccount.java
    │      ├── EducationalAccount.java
    │      └── StandardAccount.java
    ├── course
    │      ├── duration
    │      │   ├── CourseDuration.java
    │      │   └── ResourceDuration.java
    │      ├── Category.java
    │      ├── Completable.java
    │      ├── Course.java
    │      ├── Purchasable.java
    │      └── Resource.java
    ├── LearningPlatform.java
    └── Udemy.java
```

### :warning: Забележки

- Не променяйте по никакъв начин интерфейсите, дадени в условието.
- Използването на структури от данни, различни от масив, **не е позволено**. Задачата трябва да се реши с помощта на знанията от курса до момента.
