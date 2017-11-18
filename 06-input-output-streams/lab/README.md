# Управление на вход и изход. Работа с потоци

## File Placeholder Replacer

В практиката често се налага да се направи копие на текстов файл, като определени шаблони в текста са заменени с конкретни стойности. Например, искаме да разпратим на много получатели email с покана за предстоящото издание на FMI{Codes} със следното съдържание:

```
Скъпи {name},

Включи се в най-якия хакатон по тези ширини, FMI{Codes}! Очакваме те във ФМИ на {date}.
```

Имплементирайте клас 

```java
package bg.uni.sofia.fmi.mjt.streams;

public class FilePlaceholderReplacer {

    public static void replace(String fromFileName, String toFileName, Map<String, String> placeholders) {

}
```

Методът `replace` копира файла с име `fromFileName` във файла `toFileName`, като всяко срещане на placeholder във входния файл се заменя със зададена стойност. Съответствието placehoder - стойност за замяна се задава с третия параметър - `Map`, в който ключовете са имена на placehoders, а стойностите са съдържанието, с което да бъдат заменени срещанията им.

Например, ако текстът на мейла с поканата от примера по-горе е записан във файл с име "generic_invitation.txt", извикването

```java
FilePlaceholderReplacer.replace("gen.txt", "inv.txt", Map.of("name", "Ники", "date", "15-17 декември 2017"));
```

трябва да създаде файл с име "inv.txt" със съдържание

```
Скъпи Ники,

Включи се в най-якия хакатон по тези ширини, FMI{Codes}! Очакваме те във ФМИ на 15-17 декември 2017.
```

Обърнете внимание, че в текста на мейла {Codes} не се замества (не се приема за placeholder), тъй като в подадения `Map` няма ключ "Codes". За placeholder се приема само низ, започваш с '{', завършващ с '}', в случай че поднизът, ограден с фигурните скоби бъде *буквално* открит в подадения `Map`, като не се извършва никакъв whitespace stripping, т.е. в горния пример { name} не би бил placeholder, защото няма ключ " name".

Реализирайте `FilePlaceholderReplacer` чрез следния помощен клас:

```java
package bg.uni.sofia.fmi.mjt.streams;

public class StringReplacer {
    
    public StringReplacer(String input) {
        // TODO: implement me
    }
    
    public String replace(Map<String, String> map) {
        
        // TODO implement me
    }

}
```java

#### Забележки

1. Създайте unit tests за класа `StringReplacer`.

2. Пишете чист код! Съобразете се със забележките на автоматичните инструменти относно правилата за чист код в грейдъра: стремете се кодът ви да остане с 0 забележки.
