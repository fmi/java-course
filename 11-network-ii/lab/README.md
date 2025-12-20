# Music Streaming Platform 🎵🎧

## Мрежово програмиране

Тази задача има за цел да ви запознае с разработката на клиент-сървър приложения чрез създаване на опростена платформа за стрийминг на музика.  
Музиката е универсален език, който свързва хора от различни култури и поколения. С развитието на интернет и мобилните устройства, приложенията за streaming музика като Spotify и Apple Music се превърнаха в неразделна част от ежедневието ни.

В рамките на задачата ще разработите **клиент-сървър приложение**, при което:
- Сървърът съхранява и управлява плейлисти и песни;
- Клиентите комуникират със сървъра чрез текстови команди.

## Music Streaming Platform Client

Всеки клиент:
- може да създава плейлисти
- може да добавя песни към плейлисти
- може да търси плейлисти по име
- може да преглежда песните в дадена плейлиста
- може да харесва и "отхаресва" песни
- може да прекрати връзката със сървъра по всяко време.

## Music Streaming Platform Server

Сървърът:
- трябва да обслужва множество клиенти едновременно
- получава команди от клиентите и връща резултат
- управлява плейлисти, песни и броя на харесванията.

Имплементирайте следния конструктор:

```java
public MusicStreamingServer(int port, PlaylistRepository playlistRepository)
```

Както и следните методи:

```java
public void start() - стартира сървъра на зададения в конструктора порт
public void stop() - спира сървъра и зачиства ресурсите
```

## Команди

### create-playlist

```
create-playlist <playlist_name>
```

Създава нова плейлиста.

Ограничения:
- името е една дума (без интервали);
- не може да съществуват две плейлисти с едно и също име.

### add-song

```
add-song <playlist_name> <song_title> <artist_name> <duration>
```

Добавя песен към плейлиста.

Ограничения:
- `song_title` и `artist_name` са една дума;
- `duration` е цяло число (секунди);
- една и съща песен (заглавие + артист) не може да бъде добавяна два пъти.

### like-song

```
like-song <playlist_name> <song_title> <artist_name>
```

Увеличава броя харесвания с 1.

### unlike-song

```
unlike-song <playlist_name> <song_title> <artist_name>
```

Намалява броя харесвания с 1 (минимум 0).

### list-playlists

```
list-playlists
```

Връща списък с всички плейлисти.

### get-playlist

```
get-playlist <playlist_name>
```

Връща детайлна информация за плейлиста.

### disconnect

```
disconnect
```

Прекратява връзката със сървъра.

## Примерна сесия

```bash
create-playlist MyFavorites
{"status":"OK","message":"Playlist MyFavorites created successfully."}

add-song MyFavorites Imagine John-Lennon 183
{"status":"OK","message":"Song Imagine by John-Lennon added successfully."}

like-song MyFavorites Imagine John-Lennon
{"status":"OK","message":"Song Imagine by John-Lennon liked. Likes: 1"}

get-playlist MyFavorites
{"status":"OK","playlist":{"name":"MyFavorites","songs":[{"title":"Imagine","artist":"John-Lennon","duration":183,"likes":1}]}}
```

## Интерфейси

Имплементирайте следния интерфейс като създадете клас `InMemoryPlaylistRepository` с конструктор по подразбиране:

```java
package bg.sofia.uni.fmi.mjt.music.server.repository;

import bg.sofia.uni.fmi.mjt.music.server.model.Playlist;

import java.util.Collection;

public interface PlaylistRepository {

    /**
     * Creates a new playlist with the given name.
     *
     * @param playlistName the name of the playlist to be created.
     *                     The name must be a single word (no whitespaces).
     * @throws PlaylistAlreadyExistsException if a playlist with the given name
     *                                        already exists.
     */
    void createPlaylist(String playlistName) throws PlaylistAlreadyExistsException;

    /**
     * Adds a new song to an existing playlist.
     *
     * @param playlistName the name of the playlist to which the song will be added.
     * @param songTitle    the title of the song.
     * @param artistName   the name of the artist.
     * @param duration     the duration of the song in seconds.
     * @throws PlaylistNotFoundException  if the playlist with the given name
     *                                    does not exist.
     * @throws SongAlreadyExistsException if a song with the same title and artist
     *                                    already exists in the playlist.
     */
    void addSong(String playlistName, String songTitle, String artistName, int duration)
        throws PlaylistNotFoundException, SongAlreadyExistsException;

    /**
     * Increases the number of likes of a given song in the playlist by 1.
     *
     * @param playlistName the name of the playlist.
     * @param songTitle    the title of the song.
     * @param artistName   the name of the artist.
     * @return the updated number of likes for the song.
     * @throws PlaylistNotFoundException if the playlist does not exist.
     * @throws SongNotFoundException     if the song does not exist in the playlist.
     */
    int likeSong(String playlistName, String songTitle, String artistName)
        throws PlaylistNotFoundException, SongNotFoundException;

    /**
     * Decreases the number of likes of a given song in the playlist by 1.
     * The number of likes cannot be less than 0.
     *
     * @param playlistName the name of the playlist.
     * @param songTitle    the title of the song.
     * @param artistName   the name of the artist.
     * @return the updated number of likes for the song.
     * @throws PlaylistNotFoundException if the playlist does not exist.
     * @throws SongNotFoundException     if the song does not exist in the playlist.
     */
    int unlikeSong(String playlistName, String songTitle, String artistName)
        throws PlaylistNotFoundException, SongNotFoundException;

    /**
     * Retrieves the names of all existing playlists.
     *
     * @return a collection containing the names of all playlists.
     * If no playlists exist, an empty collection is returned.
     */
    Collection<String> getAllPlaylists();

    /**
     * Retrieves detailed information about a playlist with the given name.
     *
     * @param playlistName the name of the playlist to retrieve.
     * @return the {@link Playlist} object containing information about the playlist
     * and all of its songs.
     * @throws PlaylistNotFoundException if the playlist does not exist.
     */
    Playlist getPlaylist(String playlistName) throws PlaylistNotFoundException;

}
```

## Модели

```java
public record Playlist(String name, Set<Song> songs) {}
public record Song(String title, String artist, int duration, int likes) {}
```

- Две песни са еднакви, ако заглавието и артистът съвпадат.

## Валидации

Уверете се, че всички команди са валидирани и връщат съобщение за грешка, ако форматът на командата не е валиден.

Пример:

```
add-song MyFavorites
```

Отговор:

```json
{"status":"ERROR","message":"Usage: add-song <playlist_name> <song_title> <artist_name> <duration>"}
```

## Тестване

⭐ Тествайте ръчно имплементацията, първо с един, а после с няколко паралелно свързани клиента, и се убедете, че работи коректно.

⭐ Писането на автоматични тестове за тази задача е по ваш избор, но съветваме всеки да пробва, тъй като ще ви е полезно и за курсовите проекти.

👉 Подсказка: Припомнете си различните имплементации на Echo Client-Server. Можем ли да ги превърнем в Music Streaming Platform 🎵🎧 Client-Server приложение?

👉 Подсказка: Решението на тази задача ще ви улесни изключително много при разработката на курсовите ви проекти, защото всички те представляват приложения тип клиент-сървър, като сървърът обслужва много потребители едновременно.

## Примерна структура на проекта

Добра практика при създаването на приложения тип клиент-сървър е да отделяте клиента и сървъра в отделни проекти. Това предотвратява грешки от типа, класове/интерфейси от клиента да се ползват от сървъра, или обратно. Също така, в реална ситуация, бихме искали да пакетираме и разпространяваме поотделно клиентската и сървърната част на нашето приложение. Като минимум, отделете имплементацията на клиента и сървъра в отделни пакети.

В грейдъра качете папки `src` и `test`, ако имате тестове (или техен общ `zip` архив).

```
src
└─ bg.sofia.uni.fmi.mjt.music
    ├── client
    │    └── MusicStreamingClient.java
    ├── server
    │    ├── MusicStreamingServer.java
    │    ├── model
    │    │    ├── Playlist.java
    │    │    ├── Song.java
    │    │    └── (...)
    │    ├── repository
    │    │    ├── InMemoryPlaylistRepository.java
    │    │    ├── PlaylistRepository.java
    │    │    ├── exception
    │    │    │    ├── PlaylistAlreadyExistsException.java
    │    │    │    ├── PlaylistNotFoundException.java
    │    │    │    ├── SongAlreadyExistsException.java
    │    │    │    └── SongNotFoundException.java
    │    │    └── (...)
    │    └── (...)
    └── (...)
```

В грейдъра качете папките `src` и `test`, ако имате тестове (или техен общ архив).
