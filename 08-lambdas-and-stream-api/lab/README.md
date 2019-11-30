# Ламбда изрази и Stream API

## Условие

Ще създадем програма, която анализира и предоставя статистики върху реален `dataset`, съдържащ данни за *trending* видеата в [YouTube](https://www.youtube.com/) в САЩ в периода 2017-2018 година. `Dataset`-ът е генериран чрез публичното YouTube API, съдържа около 40.500 записа и е взет от [kaggle](https://www.kaggle.com/datasnaek/youtube-new/version/115#USvideos.csv), онлайн платформа за machine learning и data science на Google. За да опростим парсването на файла, сме го обработили предварително и може да го свалите [от тук](./resources/USvideos.txt).

Всеки ред от файла съдържа информация за наличието на дадено видео в trending списъка на YouTube за определена дата, в следния формат:

`video_id   trending_date   title   publish_time   tags   views   likes   dislikes`

**Забележки:**

- Първият ред на файла съдържа имената на полетата
- Разделител между полетата на всеки ред е символа за табулация (`\t`)
- Едно и също видео може да е trending за различни дати, което значи, че може да се среща многократно във файла
- Таговете за всяко видео са разделени със символа `|` и могат да са или да не са заградени в кавички;

### 1. Зареждане и обработка на данните

Преди да започнем със същинската част на анализа на видеата от `dataset`-a, ще заредим данните в паметта.

Една от първите стъпки в задачите за анализ на данни и machine learning винаги е "изчистване/подготовка" на данните. В общия случай допускаме, че може да има зле форматирани данни, може да липсват елементи, на които разчитаме, или да има такива, които не очакваме.

Понеже целта на занятието е да разберем и упражним Java Stream API-то, ще получите [готова имплементация](./resources/TrendingVideo.java) на `immutable` класа `TrendingVideo`, с метод за парсване на ред от информацията във файла:

```java
public static TrendingVideo createTrendingVideo(String line)
```

С него създаваме обекти от тип `TrendingVideo` на базата на ред от `dataset`-a.

Ако все пак имате желание да упражните знанията си за работа с файлове и потоци, окуражаваме ви да го имплементирате сами.

### 2. Статистики

След като разполагаме с имплементация на този клас, можем да заредим данните в подходяща колекция, от която да вземем поток и (декларативно) да определим разнообразни статистики.

Класът, който ще предоставя API за статистиките, е `YoutubeTrendsExplorer`:

``` java
package bg.sofia.uni.fmi.mjt.youtube;

import bg.sofia.uni.fmi.mjt.youtube.model.TrendingVideo;

import java.io.InputStream;
import java.util.Collection;

public class YoutubeTrendsExplorer {

    /**
     * Loads the dataset from the given {@code dataInput} stream.
     */
    public YoutubeTrendsExplorer(InputStream dataInput) {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    /**
     * Returns all videos loaded from the dataset.
     **/
    public Collection<TrendingVideo> getTrendingVideos() {
        return null;
    }

    // Other methods ...

}
```

#### Методи

1. Връща ID-то на trending видеото с най-малко харесвания

    ``` java
    public String findIdOfLeastLikedVideo()
    ```

2. Връща ID-то на най-одобряваното trending видео - като от броя харесвания вадим броя нехаресвания

    ``` java
    public String findIdOfMostLikedLeastDislikedVideo()
    ```

3. Връща списък от заглавията на трите най-гледани trending видеа, подредени в намаляващ ред на гледанията

    ``` java
    public List<String> findDistinctTitlesOfTop3VideosByViews()
    ```
4. Връща ID-то на видеото с най-много тагове

    ``` java
    public String findIdOfMostTaggedVideo()
    ```

5. Връща заглавието на най-рано публикуваното видео, станало trending преди да е събрало 100.000 гледания

    ``` java
    public String findTitleOfFirstVideoTrendingBefore100KViews()
    ```

6. Връща ID-то на видеото, което най-често е било trending

    ``` java
    public String findIdOfMostTrendingVideo()
    ```

## Тестване

Валидирайте решението си чрез `unit` тестове.

Tестовете, които качвате в sapera.org, трябва да работят с входни потоци и да не разчитат на предварително създадени файлове.

## Структура на проекта

```bash
src
╷
└─ bg/sofia/uni/fmi/mjt/youtube/
╷   ├─ YoutubeTrendsExplorer.java
|   └─ (...)
└─ bg/sofia/uni/fmi/mjt/youtube/model
╷   ├─ TrendingVideo.java
|   └─ (...)
test
╷
└─ bg/sofia/uni/fmi/mjt/youtube/
    └─ (...)
```

В sapera.org качете общ `zip` архив на папки `src` и `test`.
