# Static code analysis tools

### checkstyle ([link](https://github.com/checkstyle/checkstyle))

- From command line

Свалете си последния release на checkstyle от [тук](https://github.com/checkstyle/checkstyle/releases).

```bash
# point to our checkstyle rules
$ export CHECKS=https://raw.githubusercontent.com/KristianZH/Web-CAT/master/WEB-Cat/Docker/JavaTddPlugin/JavaTddPlugin/checkstyle.xml

# run for single .java file
$ java -jar checkstyle-8.14-all.jar -c $CHECKS Main.java

# run for the whole project
$ java -jar checkstyle-8.14-all.jar -c $CHECKS my-fancy-project/
```

- From Eclipse

1. _Help_ > _Eclipse Marketplace..._
2. Намирате и сваляте [Checkstyle Plug-in](https://marketplace.eclipse.org/content/checkstyle-plug).

За да активирате checkstyle за даден проект:

1. _my-fancy-project_ > _Properties_ > _Checkstyle_
2. Маркирате _Checkstyle active for this project_.
3. _Local Check Configurations..._ > _New..._.
4. Избирате _Remote Configuration_ за _Type_.
5. Давате му име - например _mjt_.
6. Поставяте [този линк](https://raw.githubusercontent.com/KristianZH/Web-CAT/master/WEB-Cat/Docker/JavaTddPlugin/JavaTddPlugin/checkstyle.xml) за _Location_ и избирате _OK_.
7. Връщате се в _Main_ и избирате вашия checkstyle, който току-що създадохте.
8. Избирате _Apply and Close_.
9. Вече би трябвало да виждате checkstyle съобщенията.
