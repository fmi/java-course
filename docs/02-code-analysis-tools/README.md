# Static code analysis tools

### checkstyle ([link](https://github.com/checkstyle/checkstyle))

- From command line

Свалете си последния release на checkstyle от [тук](https://github.com/checkstyle/checkstyle/releases).

```bash
# point to our checkstyle rules
$ export CHECKS=https://raw.githubusercontent.com/fmi/java-course/master/docs/01-code-analysis-tools/checkstyle/checkstyle.xml

# run for single .java file
$ java -jar checkstyle-9.1-all.jar -c $CHECKS Main.java

# run for the whole project
$ java -jar checkstyle-9.1-all.jar -c $CHECKS my-fancy-project/
```

- From IntelliJ

1. _File_ → _Settings..._ → _Plugins_ → _Marketplace_
2. Търсите "CheckStyle-IDEA" → _Install_
3. Рестартирате IDE-то

За да конфигурирате правилата:

1. _File_ → _Settings..._ → _Plugins_ → _Tools_ → _Checkstyle_
2. В секцията _Configuration File_ имате две предварително инсталирани конфигурации, _Sun Checks_ и _Google Checks_
3. За да добавите custom конфигурация, натискате _+_, слагате в _Description_ описание тип "MJT" и избирате локален или remote Checkstyle конфигурационен файл ще ползвате:
   - _Use a local Checkstyle file_ → _Browse_, като може да си свалите [този](https://github.com/fmi/java-course/blob/master/docs/01-code-analysis-tools/checkstyle/checkstyle.xml) от нашия грейдър
   - _Use a Checkstyle file accessible via HTTP_ и в полето _URL_ задавате локация на remote файл, например [този](https://raw.githubusercontent.com/fmi/java-course/master/docs/01-code-analysis-tools/checkstyle/checkstyle.xml) от нашия грейдър

За да настроите автоматичното форматиране в IDE-то (това, което задействате с Ctrl+Alt+L) да ползва същата custom конфигурация:

1. _File_ → _Settings..._ → _Editor_ → _Code Style_ → _Java_
2. В полето _Scheme_ до drop down-а цъкате бутончето със зъбното колело → _Import Scheme_ → _Chechstyle Configuration_ и избирате локацията на конфигурационния файл

- From Eclipse

1. _Help_ > _Eclipse Marketplace..._
2. Намирате и сваляте [Checkstyle Plug-in](https://marketplace.eclipse.org/content/checkstyle-plug).

За да активирате checkstyle за даден проект:

1. _my-fancy-project_ > _Properties_ > _Checkstyle_
2. Маркирате _Checkstyle active for this project_.
3. _Local Check Configurations..._ > _New..._.
4. Избирате _Remote Configuration_ за _Type_.
5. Давате му име - например _mjt_.
6. Поставяте [този линк](https://www.dropbox.com/s/l5tuq4qsisiymsu/checkstyle.xml?dl=0) за _Location_ и избирате _OK_.
7. Връщате се в _Main_ и избирате вашия checkstyle, който току-що създадохте.
8. Избирате _Apply and Close_.
9. Вече би трябвало да виждате checkstyle съобщенията.
