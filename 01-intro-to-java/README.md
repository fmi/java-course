# Подготовка

## Инсталиране на Java и среда за разработка

1. Инсталирате си [Java SE Development Kit (JDK)](http://www.oracle.com/technetwork/java/javase/downloads/jdk9-downloads-3848520.html)

2. Инсталирате си среда за разработка

Трите най-популярни IDE-та за Java разработка са [Eclipse](https://www.eclipse.org/downloads/eclipse-packages/), [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) и [NetBeans](https://netbeans.org/downloads/).
Може да ползвате което и да е от тях, друго IDE или любимия ви текстов редактор. Демонстрациите по време на лекциите и упражненията ще правим с Eclipse.

## Online (Auto) Grader

Онлайн системата за автоматично тестване e [grader.sapera.org](http://grader.sapera.org).

За да се регистрирате, свържете се с администратора.
Ще я ползваме както за упражненията, така и за домашните.

Решенията се качват за оценяване през уеб интерфейса на системата.

Ако решението е единствен клас в проекта, или предавате целия проект, в случай че ползвате Eclipse, може директно от IDE-то да качвате решенията си чрез plug-in за интеграция с grader-a.

### Инсталация

1. Отваряте Eclipse
2. Help > Install New Software...
3. В полето Work with... въвеждате "http://web-cat.org/eclstats/"
4. Маркирате check box-a "Web-CAT Electronic Assignments Featurе" и натискате бутона Next>
5. Тук ще почакате малко. Като ви се отвори диалога, отново натискате бутона Next>
6. Маркирате радио бутона "I accept the terms of the license agreements" и натискате бутона Finish
7. Свалянето и инсталирането ще отнеме известно време. Ако ви се появи pop-up с предупреждение, че инсталирате неподписано съдържание, натискате бутона "Install Anyway"
8. Когато инсталацията приключи, съгласявате се с подканата да рестартирате Eclipse

### Конфигурация

1. Отваряте Eclipse
2. Window > Preferences
3. В навигацията в десния панел на Preferences диалога избирате Configured Assignments
4. В полетата Download URL и Submit URL въвеждате "http://grader.sapera.org/WebObjects/Web-CAT.woa/wa/assignments/eclipse" и натискате бутона "Apply and Close"

### Употреба

1. В Navigator панела селектирате проекта, който искате да предадете
2. Project > Submit Assignment...
3. В диалога "Submit An Assignment for Grading" селектирате задачата, за която искате да предадете решение и натискате бутона Finish
4. В появилия се диалог за автентикация, въвеждате потребителското ви име и парола за grader-a. Може да селектирате check box-a "Remember my password for the rest of this session". Натискате бутона ОК
5. В новоотворилия се прозорец на default-ния ви браузър след няколко секунди ще видите оценката си
6. Повтаряте стъпки 2-5, докато не сте удовлетворени от резултата или не изчерпите броя submissions, в случай че са ограничени за съответната задача:)
