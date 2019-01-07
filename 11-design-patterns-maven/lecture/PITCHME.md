#### `Java ARchive - JAR`

@ul

- файлов формат за пакетиране, който най-често събира множество Java class файлове и ресурси в един файл, който е готов за използване и разпространение
- просто архив, който съдържа манифест
- дефакто стандарт за пакетиране на Java библиотеки/приложения

@ulend

---

#### `Executable JAR`

```
project
  └─ bin/
    └─ com/project/
      └─ Main.class
  └─ src/
    └─ com/project/
      └─ Main.java
```

```bash
javac -d bin/ src/com/project/Main.java
cd bin/
jar cvfe project.jar com.project.Main *
java -jar project.jar
# or java -cp bin/project.jar com.project.Main

jar tf project.jar
unzip project.jar
cat META-INF/MANIFEST.MF
```

---

#### `Executable JAR with dependency`

```
project
  └─ bin/
    └─ com/project/
      └─ Main.class
  └─ src/
    └─ com/project/
      └─ Main.java
  └─ lib/
    └─ gson.jar
```

```bash
javac -cp lib/*.jar -d bin/ src/com/project/Main.java
cd bin
jar cvfe project.jar com.project.Main *

cd ..
java -cp bin/project.jar:lib/* com.project.Main
```

---

#### Classpath

- параметър в JVM или Java compiler-a, който специфицира местоположението на потребителските класове или пакети
- може да бъде специфициран чрез параметър на команда или environment variable

---

#### The hard way

@ul

- Нужни са доста стъпки за създането на jar
- Потребителите най-често искат една бутонка
- А програмистите - една команда

@ulend

---

#### `maven`

@ul

- Инструмент, който:
  - Улеснява build процеса
  - Предоставя dependency management (transitive dependencies, dependency scope)
  - Предоставя release management
  - Налага конвенции, които са се утвърдили като best practices (Convention over Configuration)

@ulend

---

#### The maven way

```bash
mvn -B archetype:generate \
  -DarchetypeGroupId=org.apache.maven.archetypes \
  -DgroupId=com.project \
  -DartifactId=my-app

cd my-app
ls -la

mvn compile
mvn test
mvn package

java -cp target/my-app-1.0-SNAPSHOT.jar com.project.App
# or
vi target/my-app-1.0-SNAPSHOT.jar 
java -jar target/my-app-1.0-SNAPSHOT.jar
```

---

#### Standard Directory Layout

- maven налага конвенция за структурата на проекта
  - ${baseDir} е директорията, която съдържа pom.xml
  - ${baseDir}/src/main/java - сорс кода на приложението
  - ${basedir}/src/main/resources - ресурси
  - ${baseDir}/src/test/java - тестовете на приложението
  - ${basedir}/src/test/resources - тестови ресурси

---

#### Add dependency

1. Find you dependency from https://mvnrepository.com/repos/central.
2. Add the dependency tag to your pom.xml.
3. mvn clean install

---

#### Полезни четива

- [JAR files](https://docs.oracle.com/javase/tutorial/deployment/jar/index.html)
- [Maven User Centre](https://maven.apache.org/users/index.html)
