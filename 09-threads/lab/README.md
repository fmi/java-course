# Retro Photo Album Maker :camera:

С наближаването на празниците, всеки от нас мисли с какъв подарък да зарадва близките си. Този път задачата ни е да направим "ретро" албум с черно-бели снимки, който после да можем да разпечатаме и да използваме за подарък на някой фен на фотографията.

:bulb: Днешната задача, макар и в максимално опростен вид, ни запознава с един често срещан класически concurrency *design pattern*: [Producer-Consumer](https://jenkov.com/tutorials/java-concurrency/producer-consumer.html). Той се ползва за отделяне (*decoupling*) на *producer* и *consumer* логиката в алгоритми, чрез разделяне на *идентифицирането* на задачи/работа от *изпълнението* на тези задачи/работа, така че те да са *слабо свръзани* (*loosely coupled*) и да могат да се изпълняват едновременно и асинхронно.

![Producer-Consumer Pattern](./producer-consumer-pattern.png)

### Условие

Задачата ни ще работи с файлове на файловата система - цветни снимки с разширения `.jpeg`, `.jpg` и `.png`.
За всеки файл в зададена папка ще създаваме по една Producer нишка, чиято задача е да зареди снимката в паметта и да я добави в опашка за обработване. Понеже не става въпрос за стандартни файлове, ще предоставим метод, който да използвате за зареждането на снимката.

Consumer нишките ни ще имат фиксиран брой и задачата им ще бъде:
- да вземат снимка от опашката;
- да обработят снимката - да я направят черно бяла. За тази цел ще получите готов метод, който да използвате;
- да запазят обработената снимка в друга директория, която отново е дадена и която може и все още да не съществува на файловата система - т.е. може да трябва да я създадем.

#### Създайте интерфайс `MonochromeAlbumCreator`

```java
package bg.sofia.uni.fmi.mjt.photoalbum;

public class MonochromeAlbumCreator {

    /**
     * Iterates over all files from @sourceDirectory and picks up image ones - those with extensions jpeg, jpg, and png.
     * Starts a new thread for each image and loads it into a shared data structure.
     * Starts @imageProcessorsCount threads that process the images from the mentioned above shared data structure,
     * and save them into the provided @outputDirectory. In case the @outputDirectory does not exist, it is created.
     *
     * @param sourceDirectory directory from where the image files are taken. The directory should exist,
     *                        throw the appropriate exception if there are issues with loading the files.
     * @param outputDirectory the directory where the output b&w images are stored, if it does not exist, it is created.
     */
    public void processImages(String sourceDirectory, String outputDirectory);

}
```

#### Създайте клас `ParallelMonochromeAlbumCreator`

Класът имплементира `MonochromeAlbumCreator` и има публичен конструктор с параметри`(int imageProcessorsCount)`, който приема цяло число - броят на нишките, които обработват цветните ни снимки и ги запазват като черно-бели.

#### Обработване на снимките

Снимките ни могат да бъдат репрезентирани с класа `Image`, който може да бъде и в друг пакет - няма да счупи компилацията:
```java
package bg.sofia.uni.fmi.mjt.photoalbum;

import java.awt.image.BufferedImage;

public class Image {
    String name;
    BufferedImage data;

    public Image(String name, BufferedImage data) {
        this.name = name;
        this.data = data;
    }
}
```

Както обещахме, даваме и методите, които зареждат снимките в паметта и правят снимките ни черно-бели.
Могат да бъдат част от класове по ваш избор, единственото, кото трябва да направите е да добавите необходимите import-и:
```java
# uses:
# javax.imageio.ImageIO;
# java.awt.image.BufferedImage;
# java.io.IOException;
# java.io.UncheckedIOException;
# java.nio.file.Path;
public Image loadImage(Path imagePath) {
    try {
        BufferedImage imageData = ImageIO.read(imagePath.toFile());
        return new Image(imagePath.getFileName().toString(), imageData);
    } catch (IOException e) {
        throw new UncheckedIOException(String.format("Failed to load image %s", imagePath.toString()), e);
    }
}

# uses java.awt.image.BufferedImage;
private Image convertToBlackAndWhite(Image image) {
    BufferedImage processedData = new BufferedImage(image.data.getWidth(), image.data.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
    processedData.getGraphics().drawImage(image.data, 0, 0, null);

    return new Image(image.name, processedData);
}
```

⭐ Бележки:
 - Използвайте подходяща структура от данни, в която да съхранявате снимките, които са заредени от producer нишките.
 - Producer и Consumer нишките започват работа _сравнително_ едновременно - не карайте Consumer-ите да чакат всички Producer нишки да приключат.
 - В даден момент (докато директорията все още се обхожда и имаме по-големи файлове), може да няма заредени снимки за обработка. В такъв случай, Consumer нишките си "почиват" и изчакват да се появят нови снимки.
- При създаването на `ParallelMonochromeAlbumCreator`, пробвайте имплементацията с различен брой нишки, които да обработват снимките - например 5, 7 и т.н.

#### Тествайте локално решението ви

Пуснете няколко пъти решението си с различен брой снимки за обработка. Пробвайте със снимки, които имат по-голям размер.

**Уверете се, че програмата ви завършва успешно, когато всички снимки са обработени.**

## Структура на проекта

Този път имате пълна свобода в дизайна на задачата. Единственото условие е да имате клас `ParallelMonochromeAlbumCreator`, имплементиращ `MonochromeAlbumCreator`.
Както се казва - "with great power comes great responsibility".

```
src
└─ bg.sofia.uni.fmi.mjt.photoalbum
    ├── MonochromeAlbumCreator.java
    ├── ParallelMonochromeAlbumCreator.java
    └── (...)
```

В грейдъра качете `zip` архив на папката `src`.
