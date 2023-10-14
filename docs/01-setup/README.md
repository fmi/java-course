# Подготовка на инструментите и някои tips & tricks

- [Java Development Kit](#java-development-kit)
- [Integrated Development Environment](#integrated-development-environment)
    - [IntelliJ IDEA](#intellij-idea)
    - [Eclipse](#eclipse)
- [Online Auto Grader](#autograder)

## Java Development Kit

- [Инсталация за Windows](https://docs.oracle.com/en/java/javase/21/install/installation-jdk-microsoft-windows-platforms.html#GUID-DAF345BA-B3E7-4CF2-B87A-B6662D691840)

- [Инсталация на Linux](https://docs.oracle.com/en/java/javase/21/install/installation-jdk-linux-platforms.html#GUID-A35B89D1-7EBB-4463-B293-55C8E9713357)

- [Инсталация на Mac OS](https://docs.oracle.com/en/java/javase/21/install/installation-jdk-macos.html#GUID-EB197354-E07E-4C6A-8AF6-642E23241D39)

**Забележка:** Изтеглете JDK 21 от **Builds** за вашата операционна система от [тук](
https://jdk.java.net/21).

**Забележка:** Уверете се, че инсталацията е успешна:

```console
$ java -version
openjdk version "21" 2023-09-19
OpenJDK Runtime Environment (build 21+35-2513)
OpenJDK 64-Bit Server VM (build 21+35-2513, mixed mode, sharing)
```

## Integrated Development Environment

Може да използате което и да е _IDE_ или любимия ви текстов редактор. Все пак, ние бихме препоръчали _IntelliJ IDEA_ или _Eclipse_:

### IntelliJ IDEA

За целите на курса безплатната (Community) версия на [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) ще ни бъде напълно достатъчна.

#### Инсталация на IntelliJ IDEA и Hello World

- Официалната документация на _IntelliJ IDEA_ има подробни [стъпки за инсталация](https://www.jetbrains.com/help/idea/installation-guide.html)

- [Кратко и полезно видео](https://www.youtube.com/watch?time_continue=245&v=c0efB_CKOYo) за създаване, пускане и конфигуриране на Hello World проект

- Подобно на горното видео, но в текстов вариант: [създаване и стартиране на Hello World приложение](https://www.jetbrains.com/help/idea/creating-and-running-your-first-java-application.html)

#### Полезни IntelliJ IDEA Shortcuts

Най-често използваните са описани в [Mastering IntelliJ IDEA keyboard shortcuts](https://www.jetbrains.com/help/idea/mastering-keyboard-shortcuts.html#d1746384e89).

Кратко описание на UI-а, както и полезни shortcut-и са включени в [IntelliJ IDEA overview](https://www.jetbrains.com/help/idea/discover-intellij-idea.html).

#### IntelliJ Live Templates

*Live Templates* са бърз и удобен начин да пишем често повтарящи се езикови конструкции, въвеждайки само няколко букви (*hint*) и оставяйки IDE-то да генерира целия код. Ето най-често използваните:

| Hint              | Шаблон                                       |
| :---------------- | :------------------------------------------- |
| `psvm` или `main` | `public static void main(String[] args) { }` |
| `psf`             | `public static final `                       |
| `prsf`            | `private static final `                      |
| `sout`            | `System.out.println();`                      |
| `fori`            | `for (int i = 0; i < ; i++) { }`             |
| `ifn`             | `if (var == null) { }`                       |

Може да разгледаш [документацията](https://www.jetbrains.com/help/idea/using-live-templates.html), а най-якото е, че може да ги "тунинговаш" според предпочитанията ти и дори [да създаваш нови](https://www.jetbrains.com/help/idea/creating-and-editing-live-templates.html).

#### Дебъгване в IntelliJ IDEA

Beginner's Tutorial за дебъгване в _IntelliJ IDEA_ може да намерите [тук](https://www.jetbrains.com/help/idea/debugging-your-first-java-application.html).

### Eclipse

#### Инсталация на Eclipse и Hello World

Един [подробен tutorial](https://www.vogella.com/tutorials/Eclipse/article.html) за подготовка на [Eclipse](https://www.eclipse.org/downloads/eclipse-packages/), включващ описание как да си го инсталирате и как да направите първия си проект.

**Забележка**: За да използвате Java 21, ще ви трябва [Eclipse 2023-09](https://www.eclipse.org/downloads/) или по-нов. За момента (към 12-10-2023) се налага и инсталация на допълнителен feature, който се намира [тук](https://marketplace.eclipse.org/content/java-21-support-eclipse-2023-09-429) (за да го инсталирате, просто drag-вате `Install` върху отворения workspace).

#### Полезни Eclipse Shortcuts

Ще ви спестят огромно време и усилие, инвестицията определено си струва.
Някои от най-полезните:

##### Редактиране

      Ctrl + space                   auto-complete-ва имена на променливи и методи по първите няколко букви
      Ctlr + Shift + F               форматира ви кода
      Ctrl + O                       организира ви import-ите
      Ctrl + D                       изтрива текущия ред
      Alt  + стрелка нагоре/надолу   премества текущия ред (или цялата селекция) нагоре/надолу
      Ctrl + 7                       коментира / разкоментира маркирани редове или блокове

##### Навигиране

      Ctrl + N                       oтваря New Type wizard, с който може да създадете нов проект, клас, интерфейс
      Ctrl + T                       oтваря тип: най-бързият начин да отворите клас или интерфейс, ваш или от JDK-то
      Ctrl + F7                      превключва между различните view-та
      Ctrl + F8                      превключва между различните перспективи (например от Java към Debug и обратно)
      Ctrl + Q                       прехвърля ви в позицията на последна редакция на сорса

##### Run & Debug

      Ctrl + F11                     стартира приложението
      F11                            стартира приложението в дебъг режим

Изчерпателен списък може да намерите тук: [Eclipse cheatsheet](https://github.com/pellaton/eclipse-cheatsheet).

#### Дебъгване в Eclipse

Чудесно [ръководство по debugging](https://www.eclipse.org/community/eclipse_newsletter/2017/june/article1.php) за начинаещи.

## Autograder

Онлайн системата за автоматично тестване e [codepost.it](https://codepost.io/).

1. Ще я ползваме както за упражненията, така и за домашните и курсовия проект.
2. Ще получите от екипа на курса указания за регистрация и насоки как се използва.
