# Photo Edge Detector :framed_picture:

Тази седмица задачата ни е да разработим модули на библиотека за обработка на изображения. Библиотеката ще може да работи с изображения в различни графични формати (JPEG, PNG, BMP) и ще прилага различни трансформации върху тях. В първата версия, клиентът иска функционалност за конвертиране на цветно изображение в черно-бяло и възможност за откриване на ръбовете в изображение.

![Maserati Edge Detected](../lecture/images/07.12-maserati-edge-detected.png)

Библиотеката има два основни компонента:

- Алгоритми за обработка на изображения
- Мениджър за файловата система за зареждане и съхраняване на изображения.

## Основни интерфейси и класове

Ето интерфейсите и класовете, които трябва да реализирате. Някои класове и интерфейси са частично дефинирани, за да ви насочат.

### ImageAlgorithm

Интерфейсът `ImageAlgorithm` представлява алгоритъм за обработка на изображения.

```java
package bg.sofia.uni.fmi.mjt.imagekit.algorithm;

import java.awt.image.BufferedImage;

/**
 * Represents an algorithm that processes images.
 */
public interface ImageAlgorithm {

    /**
     * Applies the image processing algorithm to the given image.
     *
     * @param image the image to be processed
     * @return BufferedImage the processed image of type (TYPE_INT_RGB)
     * @throws IllegalArgumentException if the image is null
     */
    BufferedImage process(BufferedImage image);
}
```

Разгледайте документацията на класа [BufferedImage](https://docs.oracle.com/en/java/javase/25/docs/api/java.desktop/java/awt/image/BufferedImage.html), защото ще ви потрябва на няколко места да създавате негови инстанции и да ползвате API-то му. Когато създавате негова инстанция, ползвайте конструктора `BufferedImage(int width, int height, int imageType)`, а като трети аргумент подавайте винаги `BufferedImage.TYPE_INT_RGB`. 

### GrayscaleAlgorithm

Интерфейсът `GrayscaleAlgorithm` е маркерен интерфейс за алгоритми за конвертиране в черно-бяло изображение.

```java
package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;

public interface GrayscaleAlgorithm extends ImageAlgorithm {
}
```

### EdgeDetectionAlgorithm

Интерфейсът `EdgeDetectionAlgorithm` е друг маркерен интерфейс, този път за алгоритми за откриване на ръбове.

```java
package bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;

public interface EdgeDetectionAlgorithm extends ImageAlgorithm {
}
```

### FileSystemImageManager

Интерфейсът `FileSystemImageManager` управлява зареждането и съхраняването на изображения от файловата система.

```java
package bg.sofia.uni.fmi.mjt.imagekit.filesystem;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * An interface for loading images from the file system.
 * The supported image formats are JPEG, PNG, and BMP.
 */
public interface FileSystemImageManager {

    /**
     * Loads a single image from the given file path.
     *
     * @param imageFile the file containing the image.
     * @return the loaded BufferedImage.
     * @throws IllegalArgumentException if the file is null
     * @throws IOException              if the file does not exist, is not a regular file,
     *                                  or is not in one of the supported formats.
     */
    BufferedImage loadImage(File imageFile) throws IOException;

    /**
     * Loads all images from the specified directory.
     *
     * @param imagesDirectory the directory containing the images.
     * @return A list of BufferedImages representing the loaded images.
     * @throws IllegalArgumentException if the directory is null.
     * @throws IOException              if the directory does not exist, is not a directory,
     *                                  or contains files that are not in one of the supported formats.
     */
    List<BufferedImage> loadImagesFromDirectory(File imagesDirectory) throws IOException;

    /**
     * Saves the given image to the specified file path.
     *
     * @param image     the image to save.
     * @param imageFile the file to save the image to.
     * @throws IllegalArgumentException if the image or file is null.
     * @throws IOException              if the file already exists or the parent directory does not exist.
     */
    void saveImage(BufferedImage image, File imageFile) throws IOException;
}
```

### LocalFileSystemImageManager

Класът `LocalFileSystemImageManager` има публичен конструктор по подразбиране, имплементира интерфейса `FileSystemImageManager` и предоставя методи за зареждане и съхраняване на изображения.

:point_right: Подсказка: в имплементацията ще ви влезе в употреба класът [javax.imageio.ImageIO](https://docs.oracle.com/en/java/javase/25/docs/api/java.desktop/javax/imageio/ImageIO.html) 

### LuminosityGrayscale

Класът `LuminosityGrayscale` също има публичен конструктор по подразбиране, имплементира интерфейса `GrayscaleAlgorithm` и прилага черно-бяло конвертиране, използвайки [*Метода на осветеност (Luminosity method)*](https://www.johndcook.com/blog/2009/08/24/algorithms-convert-color-grayscale/).
В литературата се срещат и други стойности на коефициентите във формулата, но ние ще използваме класическите, които са посочени и в линкнатата по-горе статия: `0.21 R + 0.72 G + 0.07 B`. Резултатът от формулата трябва да бъде закръглен с `Math.round()` и ограничен в диапазона [0, 255].

### SobelEdgeDetection

Класът `SobelEdgeDetection` има публичен конструктор `SobelEdgeDetection(ImageAlgorithm grayscaleAlgorithm)`, имплементира интерфейса `EdgeDetectionAlgorithm` и прилага един от класическите алгоритми за откриване на ръбове в изображения, [*Оператор на Sobel*](https://en.wikipedia.org/wiki/Sobel_operator), известен също като *Оператор на Sobel–Feldman* и *Филтър на Sobel*.

За да се запознаете с алгоритъма, може да разгледате [тази статия](https://cse442-17f.github.io/Sobel-Laplacian-and-Canny-Edge-Detection-Algorithms/), която съдържа и интерактивна визуализация, която ще ви помогне да схванете как работи той.

#### Детайлна спецификация на Sobel оператора

**Ядра на Sobel оператора:**

Хоризонтално ядро (Gx):

| | | |
|:---:|:---:|:---:|
| -1 | 0 | +1 |
| -2 | 0 | +2 |
| -1 | 0 | +1 |

Вертикално ядро (Gy):

| | | |
|:---:|:---:|:---:|
| -1 | -2 | -1 |
| 0 | 0 | 0 |
| +1 | +2 | +1 |

**Алгоритъм:**

Методът `process()` първо преобразува входното изображение в черно-бяло чрез подадения `grayscaleAlgorithm`, след което прилага Sobel оператора върху получената grayscale версия.

За всеки пиксел (x, y) в черно-бялото изображение:

1. **Приложете конволюция с хоризонталното ядро Gx:**
   За пиксел на координати (x, y), стойността Gx се изчислява като сума от произведенията на стойностите на пикселите в околността 3x3 и съответните коефициенти от ядрото Gx.

2. **Приложете конволюция с вертикалното ядро Gy:**
   Аналогично, стойността Gy се изчислява със съответните коефициенти от ядрото Gy.

3. **Изчислете магнитуда на градиента:**
   Използвайте формулата: $$G = \sqrt{G_x^2 + G_y^2}$$

4. **Закръглете и ограничете резултата:**
   `int pixelValue = Math.min(255, (int) Math.round(G));`

5. **Запишете като RGB пиксел:**
   Тъй като резултатът е grayscale изображение, трите канала (R, G, B) имат еднаква стойност:
   `int rgb = (pixelValue << 16) | (pixelValue << 8) | pixelValue;`

**Обработка на граничните пиксели:**

За пикселите на ръбовете на изображението (x = 0, x = width - 1, y = 0, y = height - 1), където съседните пиксели излизат извън границите:
- Третирайте липсващите пиксели като имащи стойност **0** (*zero padding*)
- Изходното изображение трябва да има същите размери като входното

**Резултат:**

Изходното изображение показва:
- **Ръбове в бяло** (стойност близо до 255) - области с голям градиент
- **Хомогенни области в черно** (стойност близо до 0) - области с малък градиент

### Пример за използване

Ето един прост пример, как може да се използва библиотеката:

```java
FileSystemImageManager fsImageManager = new LocalFileSystemImageManager();

BufferedImage image = fsImageManager.loadImage(new File("kitten.png"));

ImageAlgorithm grayscaleAlgorithm = new LuminosityGrayscale();
BufferedImage grayscaleImage = grayscaleAlgorithm.process(image);

ImageAlgorithm sobelEdgeDetection = new SobelEdgeDetection(grayscaleAlgorithm);
        BufferedImage edgeDetectedImage = sobelEdgeDetection.process(image);

fsImageManager.saveImage(grayscaleImage, new File("kitten-grayscale.png"));
fsImageManager.saveImage(edgeDetectedImage, new File("kitten-edge-detected.png"));
```

### Тестване

Тази задача събужда изследователя във вас, затова първо тествайте библиотеката си локално, с някоe от [нашите изображения](resources/), или с произволна ваша картинка. После помислете, как може да я тествате автоматично и създайте подходящи unit тестове. Не се фиксирайте върху code coverage-a, а как да изтествате базовата функционалност на библиотеката. 

## Пакети

Спазвайте имената на пакетите на всички по-горе описани класове, тъй като в противен случай решението ви няма да може да бъде тествано от грейдъра.

```
src
└── bg.sofia.uni.fmi.mjt.imagekit
    ├── algorithm
    │   ├── detection
    │   │   ├── EdgeDetectionAlgorithm.java
    │   │   └── SobelEdgeDetection.java
    │   ├── grayscale
    │   │   ├── GrayscaleAlgorithm.java
    │   │   └── LuminosityGrayscale.java
    │   ├── ImageAlgorithm.java
    │   └── (...)
    ├── filesystem
    │   ├── FileSystemImageManager.java
    │   ├── LocalFileSystemImageManager.java
    │   └── (...)
    └── (...)

test
└── bg.sofia.uni.fmi.mjt.imagekit
     └── (...)
```

## Забележки

- В грейдъра качете директориите src и test като ги селектирате и двете. Друг вариант е да ги сложите в общ .zip архив и да качите него
- Не качвайте jar-ките на JUnit и Mockito библиотеките. На грейдъра ги има, няма смисъл решението ви да набъбва излишно.
- Не качвайте и евентуални изображения, с които си тествате локално библиотеката

Успех в първия ви изследователски поход из интересната и необятна област на [computer vision](https://en.wikipedia.org/wiki/Computer_vision), и приятно кодене! :rocket:
