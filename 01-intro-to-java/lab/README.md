# Подготовка на инструментите и някои tips & tricks

- [Java Development Kit](#java-development-kit)
- [Integrated Development Environment](#integrated-development-environment)
    - [Eclipse](#eclipse)
    - [Intellij IDEA](#intellij-idea)
- [Online Auto Grader](#online-auto-grader)
    - [Eclipse Plugin за Grader](#eclipse-plugin-за-grader)
       
## Java Development Kit

- [Инсталация на Windows](https://docs.oracle.com/en/java/javase/11/install/installation-jdk-microsoft-windows-platforms.html#GUID-A740535E-9F97-448C-A141-B95BF1688E6F)

- [Инсталация на Linux](https://docs.oracle.com/en/java/javase/11/install/installation-jdk-linux-platforms.html#GUID-737A84E4-2EFF-4D38-8E60-3E29D1B884B8)

- [Инсталация на Mac OS](https://docs.oracle.com/en/java/javase/11/install/installation-jdk-macos.html#GUID-2FE451B0-9572-4E38-A1A5-568B77B146DE)

**Забележка:** Изтеглете JDK 11 от [тук](
http://jdk.java.net/11/).

**Забележка:** Уверете се, че инсталацията е успешна:

```console
$ java -version
java version "11" 2018-09-25
Java(TM) SE Runtime Environment 18.9 (build 11+28)
Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11+28, mixed mode)
```

## Integrated Development Environment

Може да използате което и да е _IDE_ или либимия ви текстов редактор. Все пак, ние бихме препоръчали _Eclipse_ или _Intellij IDEA_:

### Eclipse

#### Инсталация на Eclipse и Hello World

Един доста [подробен tutorial](https://www.ntu.edu.sg/home/ehchua/programming/howto/EclipseJava_HowTo.html) за подготовка на [Eclipse]((https://www.eclipse.org/downloads/eclipse-packages/)). Точка едно описва как да си инсталирате _JDK_ и _IDE_-то. Точка две показва как да направите първия си проект.  

**Забележка**: Ако използвате Java 11, ще ви трябва [Eclipse 2018-09 (4.9)](https://marketplace.eclipse.org/content/java-11-support-eclipse-2018-09-49) или по-нов. За момента (към 10-10-2018) се налага и инсталация на допълнителен feature, който се намира в предния [линк](https://marketplace.eclipse.org/content/java-11-support-eclipse-2018-09-49) (за да го инсталирате просто drag-вате `Install` върху отворения workspace).

#### Полезни Eclipse Shortcuts

Ще ви спестят огромно време и усилие, инвестицията определено си струва.
Някои от най-полезните:

##### Редактиране

      Ctrl + space                   auto-complete-ва имена на променливи и методи по първитете няколко букви
      Ctlr + Shift + F               форматира ви кода
      Ctrl + O                       организира ви import-ите
      Ctrl + D                       изтрива текущия ред
      Alt  + стрелка нагоре/надолу   премества текущия ред (или цялата селекция) нагоре/надолу
      Ctrl + или Ctrl -              коментира / разкоментира редове и блокове

##### Навигиране

      Ctrl + N                       oтваря New Type wizard, с който може да създадете нов проект, клас, интерфейс
      Ctrl + T                       oтваря тип: най-бързия начин да отворите клас или интерфейс, ваш или от JDK-то
      Ctrl + F7                      превключва между различните view-та
      Ctrl + F8                      превключва между различните перспективи (например от Java към Debug и обратно)
      Ctrl + Q                       прехвърля ви в позицията на последна редакция на сорса

##### Run & Debug

      Ctrl + F11                     стартира приложението
      F11                            стартира приложението в дебъг режим

Изчерпателен списък може да намерите тук: [Eclipse cheatsheet](https://github.com/pellaton/eclipse-cheatsheet).

#### Дебъгване в Eclipse

Чудесно [ръководство по debugging](https://www.eclipse.org/community/eclipse_newsletter/2017/june/article1.php) за начинаещи.

#### JUnit Тестове

Към условието на всяка задача са прикачени и тестове за примерния вход и изход от условието. Ако след като сложите файлът с тестовете в проекта си виждате грешки от вида `the import org.junit cannot be resolved`, ще необходимо да добавите _JUnit_ в _Build Path_ на проекта. Бихте могли ръчно да добавите `JUnit` в `Build Path` като следвате стъпките [тук](https://www.tutorialspoint.com/junit/junit_plug_with_eclipse.htm).


Пускане на тестовете - отворете файла с тестове в _IDE_-то и натиснете `Mouse Right Click -> Run As -> JUnit Test`.

**Забележка**: Тестовете в курса поне в началото ще пишем с JUnit 4. Все пак, малко полезна информация за JUnit 5 може да намерите [тук](https://www.eclipse.org/community/eclipse_newsletter/2017/october/article5.php).

### Intellij IDEA

За целите на курса безплатната (Community) версия на [Intellij IDEA](https://www.jetbrains.com/idea/download/) ще ни бъде достатъчна.

#### Инсталация на Intellij IDEA и Hello World

- официалната документация на _Intellij IDEA_ има подробни [стъпки за инсталация](https://www.jetbrains.com/help/idea/install-and-set-up-product.html)

- [кратко и полезно видео](https://www.youtube.com/watch?time_continue=245&v=c0efB_CKOYo) за създаване, пускане и конфигуриране на Hello World проект

- подобно на горното видео, но в текстов вариант: [създаване и стартиране на Hello World приложение](https://www.jetbrains.com/help/idea/creating-and-running-your-first-java-application.html)

#### Полезни Intellij IDEA Shortcuts

 Най-често използваните са описани в [Mastering IntelliJ IDEA keyboard shortcuts](https://www.jetbrains.com/help/idea/mastering-keyboard-shortcuts.html#d1746384e89).

Кратко описане на UI-а, както и полезни shortcut-и са включени в [Discover Intellij IDEA](https://www.jetbrains.com/help/idea/discover-intellij-idea.html).

#### Дебъгване в Intellij IDEA

Beginner's Tutorial за дебъгване в _Intellij IDEA_ може да намерите [тук](https://www.jetbrains.com/help/idea/debugging-your-first-java-application.html).

#### JUnit Тестове

Към условието на всяка задача са прикачени и тестове за примерния вход и изход от условието. Ако след като сложите файлът с тестовете в проекта си виждате грешки от вида `Cannot resolve symbol 'Test'`, ще е необходимо да добавите _JUnit_ в _Build Path_ на проекта. [Официалната документация](https://www.jetbrains.com/help/idea/configuring-testing-libraries.html) на _Intellij IDEA_ включва  обяснение за това как да конфигурирате JUnit 4/5 в проекта си - вместо JUnit 5.2 добавете JUnit 4 (`Alt+Enter on @Test -> Add Junit 4 to classpath`).

Пускане на тестовете - отворете файла с тестове в _IDE_-то и натиснете `ctrl+shift+f10` или `Mouse Right Click -> Run {{filename}}`.

## Online Auto Grader

Онлайн системата за автоматично тестване e [grader.sapera.org](http://grader.sapera.org).

За да се регистрирате, свържете се с администратора.
Ще я ползваме както за упражненията, така и за домашните.

Решенията може да качвате за оценяване през уеб интерфейса на системата.

### Eclipse Plugin за Grader

В случай че ползвате Eclipse, може директно от IDE-то да качвате решенията си чрез plugin-а за интеграция с grader-a.

#### Инсталация

1. Отваряте _Eclipse_.
2. _Help_ > _Install New Software_...
3. В полето _Work with..._ въвеждате "http://web-cat.org/eclstats/".
4. Маркирате check box-a _Web-CAT Electronic Assignments Featurе_ и натискате бутона _Next>_.
5. Тук ще почакате малко. Като ви се отвори диалога, отново натискате бутона _Next>_.
6. Маркирате радио бутона _I accept the terms of the license agreements_ и натискате бутона _Finish_.
7. Свалянето и инсталирането ще отнеме известно време. Ако ви се появи pop-up с предупреждение, че инсталирате неподписано съдържание, натискате бутона _Install Anyway_.
8. Когато инсталацията приключи, съгласявате се с подканата да рестартирате _Eclipse_.

#### Конфигурация

1. Отваряте _Eclipse_.
2. _Window_ > _Preferences_.
3. В навигацията в десния панел на _Preferences_ диалога избирате _Configured Assignments_.
4. В полетата _Download URL_ и _Submit URL_ въвеждате "http://grader.sapera.org/WebObjects/Web-CAT.woa/wa/assignments/eclipse" и натискате бутона _Apply and Close_.

#### Употреба

1. В _Navigator_ панела селектирате проекта, който искате да предадете.
2. _Project_ > _Submit Assignment_...
3. В диалога _Submit An Assignment for Grading_ селектирате задачата, за която искате да предадете решение и натискате бутона _Finish_.
4. В появилия се диалог за автентикация, въвеждате потребителското ви име и парола за grader-a. Може да селектирате check box-a _Remember my password for the rest of this session_. Натискате бутона _ОК_.
5. В новоотворилия се прозорец на default-ния ви браузър след няколко секунди ще видите оценката си.
6. Повтаряте стъпки 2-5, докато не сте удовлетворени от резултата или не изчерпите броя submissions, в случай че са ограничени за съответната задача :).
