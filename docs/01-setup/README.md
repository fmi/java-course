# Подготовка на инструментите и някои tips & tricks

- [Java Development Kit](#java-development-kit)  
- [Integrated Development Environments](#integrated-development-environments)  
  - [IntelliJ IDEA](#intellij-idea)  
  - [Visual Studio Code](#visual-studio-code)  
  - [Eclipse](#eclipse)  
- [Чести проблеми и решения](#чести-проблеми-и-решения)  
- [Online Auto Grader](#online-auto-grader)

---

## Java Development Kit

За курса ще използваме **Java 25 (LTS)** — това е последната LTS версия, излязла на **16 септември 2025**.
Можете да използвате отворените (OpenJDK) builds от официалния сайт или други доставчици.

### Инсталация на JDK 25

- **Windows**  
  Използвайте официалния Oracle JDK инсталатор (.msi или .exe) и следвайте стъпките. ([docs.oracle.com](https://docs.oracle.com/en/java/javase/25/install/installation-jdk-microsoft-windows-platforms.html))  
  Примерна команда за silent инсталация:  
  ```bat
  msiexec.exe /i jdk-25_windows-x64_bin.msi /qn /L C:\path\install.log
  ```

- **Linux**  
  Изтеглете съответното `.tar.gz` от [jdk.java.net/25](https://jdk.java.net/25) или използвайте пакетен мениджър (ако е наличен).  
  Разархивирайте, поставете в желана директория (например `/usr/lib/jvm/jdk-25`) и конфигурирайте символични линкове или environment променливи (`JAVA_HOME`, `PATH`).

- **macOS**  
  Свалете `.tar.gz` или `.pkg` версия от [jdk.java.net/25](https://jdk.java.net/25) и инсталирайте.  
  Ако използвате Homebrew или друг пакетен мениджър, може да се наложи да се конфигурира пряко.

> **Бележка:** Уверете се, че няма по-стари версии на Java, които да пречат (напр. по-висок приоритет в PATH).

След инсталация уверете се, че `java --version` показва нещо като:

  ```text
  openjdk 25 2025‑09‑16
  OpenJDK Runtime Environment (build 25+36‑3489)
  OpenJDK 64‑Bit Server VM (build 25+36‑3489, mixed mode, sharing)
  ```
---

## Integrated Development Environments

Може да използате което и да е _IDE_ или любимия ви текстов редактор. Ето инструкции за трите най-популярни IDE-та и как да ги настроите за JDK 25.

### IntelliJ IDEA

#### Изтегляне и инсталация

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

---

### Visual Studio Code

#### Изтегляне и инсталация

- Свалете от тук https://code.visualstudio.com/
- Инсталирайте **Extension Pack for Java** от Marketplace.  
- Документация: [Java in VS Code](https://code.visualstudio.com/docs/languages/java)

#### Настройка да използва JDK 25

1. `Ctrl+Shift+P` → `Java: Configure Java Runtime`  
2. Добавете път към JDK 25 под „JDKs“  
3. Настройте `"java.home"` в `settings.json`, ако е нужно.

#### Полезни shortcut-и

- `F5` → Debug  
- `Ctrl+F5` → Run  
- `Ctrl+.` → Quick fix  
- `Alt+Up/Down` → Преместване на ред  
- `Ctrl+Shift+O` → Символи в текущия файл

#### Дебъгване

- Натиснете `F5`, поставете breakpoints  
- Използвайте *Variables*, *Call Stack*, *Debug Console*  
- Уверете се, че launch.json използва правилния JDK.

---

### Eclipse

#### Инсталация на Eclipse и Hello World

#### Изтегляне и инсталация

- Изтеглете **Eclipse IDE for Java Developers**: https://www.eclipse.org/downloads/  
- Препоръчана версия: **Eclipse 2025‑09 (4.37)**  
- За Java 25 поддръжка може да е нужен плъгин: [Java 25 Support for Eclipse 2025‑09](https://marketplace.eclipse.org/content/java-25-support-eclipse-2025-09-437)

#### Настройка да използва JDK 25

1. Preferences → Java → Installed JREs → Add → Standard VM → JDK 25  
2. Маркирайте го като default  
3. Project → Properties → Java Build Path → Libraries → JRE System Library → JDK 25  
4. Project → Properties → Java Compiler → Compiler compliance level → 25

Един [подробен tutorial](https://www.vogella.com/tutorials/Eclipse/article.html) за подготовка на [Eclipse](https://www.eclipse.org/downloads/eclipse-packages/), включващ описание как да си го инсталирате и как да направите първия си проект.

#### Полезни shortcut-и

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

---

## Чести проблеми и решения

| Проблем | Причина / симптом | Решение |
|---|---|---|
| `java --version` не показва 25 | PATH сочи към друга версия | Задайте `JAVA_HOME` и PATH да сочат към JDK 25 |
| IDE не намира JDK 25 | Не е добавен в настройките | Добавете JDK 25 в IDE настройките |
| “Unsupported source release: 25” | Стар компилатор | Обновете IDE / инсталирайте Java 25 support |
| Eclipse стартира с грешен JDK | IDE се стартира с друга JVM | Добавете `-vm` път към JDK 25 в `eclipse.ini` |
| VS Code не намира JDK | `java.home` не е настроено | Добавете `"java.home"` в settings.json |
| IntelliJ не открива JDK | Не е конфигуриран Project SDK | File → Project Structure → добавете JDK 25 |

---

## Online Auto Grader

Онлайн системата за автоматично тестване e [codepost.it](https://codepost.io/).

Ще получите от екипа на курса указания за регистрация и насоки как се използва.
