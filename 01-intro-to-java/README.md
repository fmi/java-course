# Подготовка на инструментите и някои tips & tricks

* [Инсталиране на Java и среда за разработка](#Инсталиране-на-java-и-среда-за-разработка)
* [Полезни Eclipse shortcuts](#Полезни-eclipse-shortcuts)
* [Дебъгване в Eclipse](#Дебъгване-в-eclipse)
* [Online Auto Grader](#online-auto-grader)

## Инсталиране на Java и среда за разработка

1. Инсталирате си [Java SE Development Kit (JDK)](http://www.oracle.com/technetwork/java/javase/downloads/jdk9-downloads-3848520.html)

2. Инсталирате си среда за разработка

Трите най-популярни IDE-та за Java разработка са [Eclipse](https://www.eclipse.org/downloads/eclipse-packages/), [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) и [NetBeans](https://netbeans.org/downloads/).
Може да ползвате което и да е от тях, друго IDE или любимия ви текстов редактор. Демонстрациите по време на лекциите и упражненията ще правим с Eclipse.

## Полезни Eclipse shortcuts

Ще ви спестят огромно време и усилие, инвестицията определено си струва.
Някои от най-полезните:

###### Редактиране

      Ctrl + space                   auto-complete-ва имена на променливи и методи по първитете няколко букви
      Ctlr + Shift + F               форматира ви кода
      Ctrl + O                       организира ви import-ите
      Ctrl + D                       изтрива текущия ред
      Alt  + стрелка нагоре/надолу   премества текущия ред (или цялата селекция) нагоре/надолу
      Ctrl + или Ctrl -              коментира / разкоментира редове и блокове

###### Навигиране

      Ctrl + N                       oтваря New Type wizard, с който може да създадете нов проект, клас, интерфейс
      Ctrl + T                       oтваря тип: най-бързия начин да отворите клас или интерфейс, ваш или от JDK-то
      Ctrl + F7                      превключва между различните view-та
      Ctrl + F8                      превключва между различните перспективи (например от Java към Debug и обратно)
      Ctrl + Q                       прехвърля ви в позицията на последна редакция на сорса

###### Run & Debug

      Ctrl + F11                     стартира приложението
      F11                            стартира приложението в дебъг режим

Изчерпателен списък може да намерите тук: [Eclipse cheatsheet](https://github.com/pellaton/eclipse-cheatsheet).

## Дебъгване в Eclipse

Чудесно [ръководство по debugging](https://www.eclipse.org/community/eclipse_newsletter/2017/june/article1.php) за начинаещи.

## Online Auto Grader

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
