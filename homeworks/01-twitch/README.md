# Домашно 1

## **Twitch** :video_game: :computer:

`Краен срок: 01.12.2021, 23:45`

[Twitch](https://www.twitch.tv/) е онлайн услуга за видео стрийминг, която се фокусира върху стрийминг на видео игри и излъчване на електронни спортни състезания. Освен това предлага музикални предавания, творческо съдържание, а напоследък и "in real life" стриймове. Нашата задача е да създадем такава платформа за видео стрийминг, която да поддържа следните функционалности:
1. Стартиране на `Stream`
2. Спиране на активен `Stream`
3. Гледане на `Content`
4. Изчисляване на различни статистики за платформата

Създайте клас `Twitch`, който да имплементира интерфейса `StreamingPlatform`:

```java
package bg.sofia.uni.fmi.mjt.twitch;

import java.util.List;

import bg.sofia.uni.fmi.mjt.twitch.content.Category;
import bg.sofia.uni.fmi.mjt.twitch.content.Content;
import bg.sofia.uni.fmi.mjt.twitch.content.stream.Stream;
import bg.sofia.uni.fmi.mjt.twitch.content.video.Video;
import bg.sofia.uni.fmi.mjt.twitch.user.User;
import bg.sofia.uni.fmi.mjt.twitch.user.UserNotFoundException;
import bg.sofia.uni.fmi.mjt.twitch.user.UserStreamingException;

public interface StreamingPlatform {

    /**
     * Starts a new {@link Stream} and returns a reference to it.
     *
     * @param username the username of the streamer
     * @param title    the title of the stream
     * @param category the {@link Category} of the stream
     * @return the started {@link Stream}
     * @throws IllegalArgumentException if any of the parameters are null or if strings are empty
     * @throws UserNotFoundException    if a user with this username is not found in
     *                                  the service
     * @throws UserStreamingException   if a user with this username is currently
     *                                  streaming
     */
    Stream startStream(String username, String title, Category category) throws UserNotFoundException, UserStreamingException;

    /**
     * Ends an existing {@link Stream} and returns a new {@link Video} which was
     * made of it.
     *
     * @param username the username of the streamer
     * @param stream   the stream to end
     * @return the created {@link Video}
     * @throws IllegalArgumentException if any of the parameters are null or if {@code username} is empty
     * @throws UserNotFoundException    if a user with this username is not found in
     *                                  the service
     * @throws UserStreamingException   if a user with this username is currently not
     *                                  streaming
     */
    Video endStream(String username, Stream stream) throws UserNotFoundException, UserStreamingException;

    /**
     * Watches a content.
     *
     * @param username the username of the watcher
     * @param content  the content to watch
     * @throws IllegalArgumentException if any of the parameters are null or if {@code username} is empty
     * @throws UserNotFoundException    if a user with this username is not found in
     *                                  the service
     * @throws UserStreamingException   if the user with the specified username is
     *                                  currently streaming
     */
    void watch(String username, Content content) throws UserNotFoundException, UserStreamingException;

    /**
     * Returns the {@link User} whose {@link Content}s have the most views combined in the
     * service or null if there is no such user.
     *
     * @return the {@link User} whose {@link Content}s have the most views combined in the
     * service or null if there is no such user
     */
    User getMostWatchedStreamer();

    /**
     * Returns the {@link Content} which has the most views in the service 
     * or null if there is not such content.
     *
     * @return the {@link Content} which has the most views in the service
     * or null if there is not such content
     */
    Content getMostWatchedContent();

    /**
     * Returns the {@link Content} from user with name username which has the most
     * views in the service or null if there is not such content.
     *
     * @return the {@link Content} from user with name username which has the most
     *         views in the service or null if there is not such content
     * @throws IllegalArgumentException if {@code username} is null or empty
     * @throws UserNotFoundException if a user with this username is not found in
     *                               the service
     */
    Content getMostWatchedContentFrom(String username) throws UserNotFoundException;

    /**
     * Returns an immutable copy of a sorted list of the watched categories by user
     * with name username in descending order of the count. Categories with zero views
     * should not be returned.
     *
     * @param username
     * @return an immutable copy of a sorted list of the watched categories by user
     *         with name username in descending order of the count
     * @throws IllegalArgumentException if {@code username} is null or empty
     * @throws UserNotFoundException if a user with this username is not found in
     *                               the service
     */
    List<Category> getMostWatchedCategoriesBy(String username) throws UserNotFoundException;

}
```

Класът трябва да има следния конструктор: `Twitch(UserService userService)`. 

### **User service**

`UserService` е интерфейс, който предоставя информация за потребителите на платформата и има следния вид:

```java
package bg.sofia.uni.fmi.mjt.twitch.user.service;

import java.util.Map;

import bg.sofia.uni.fmi.mjt.twitch.user.User;

public interface UserService {

    /**
     * Returns a map of users, where the key is the username.
     *
     * @return a map of users, where the key is the username
     */
    Map<String, User> getUsers();

}
```

### **User**

`User` е интерфейс, който предоставя информация за даден потребител в платформата и има следния вид:

```java
package bg.sofia.uni.fmi.mjt.twitch.user;

public interface User {

    /**
     * Returns the name of the user.
     *
     * @return the name of the user
     */
    String getName();

    /**
     * Returns the {@link UserStatus} of the user.
     *
     * @return the {@link UserStatus} of the user
     */
    UserStatus getStatus();

    /**
     * Sets the {@link UserStatus} of the user.
     *
     * @param status the new {@link UserStatus} to set
     */
    void setStatus(UserStatus status);

}
```
`UserStatus` е enum, който представлява статуса на даден потребител в платформата. При създаването си, *User*-ите са със статус ***OFFLINE***:
```java
package bg.sofia.uni.fmi.mjt.twitch.user;

public enum UserStatus {

    STREAMING("User is currently streaming"), OFFLINE("User is offline");

    private final String message;

    private UserStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
```

### **Content**


`Content` е интерфейс, който представлява различното съдържание (в нашата платформа това са `Stream` и `Video`) и има следния вид:

```java
package bg.sofia.uni.fmi.mjt.twitch.content;

import java.time.Duration;

import bg.sofia.uni.fmi.mjt.twitch.user.User;

public interface Content {

    /**
     * Returns the {@link Metadata} of the content.
     *
     * @return the {@link Metadata} of the content
     */
    Metadata getMetadata();

    /**
     * Returns the {@link Duration} of the content.
     *
     * @return the {@link Duration} of the content
     */
    Duration getDuration();

    /**
     * {@link User} starts watching the content.
     *
     * @param user which starts watching the content
     */
    void startWatching(User user);

    /**
     * {@link User} stops watching the content.
     *
     * @param user which stops watching the content
     */
    void stopWatching(User user);

    /**
     * Returns the number of views of the content.
     *
     * @return the number of views of the content
     */
    int getNumberOfViews();

}
```

`Metadata` съдържа информация за заглавието, категорията (`Category`) и стриймъра (`User`) на даден `Content`.

`Category` е enum, който има следния вид: 

```java
public enum Category {
    GAMES, IRL, MUSIC, ESPORTS
}
```

### Бележки

- Съдържанията в платформата се представят чрез интерфейсите `Stream` и `Video`.
- Под `Content` в платформата, разбираме видео или стрийм, който в момента е активен.
- Методът *getNumberOfViews* от интерфейса *Content* връща текущия брой потребители, които гледат *Stream*-а или текущия брой гледания за *Video*-то.
- Методът *stopWatching* от интерфейса *Content*, няма да бъде извикван извън вашата програма.
- Може да добавяте нови класове и интерфейси, но без да променяте структурата на тези от условието.
- :exclamation::exclamation: **Решения, използващи [Java Stream API](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/stream/package-summary.html), lambdas и всичко останало, което не е покрито до момента, няма да се приемат за това домашно.**

### Пакети

Спазвайте имената на пакетите на всички по-горе описани класове, тъй като в противен случай решението ви няма да може да бъде тествано от грейдъра.

```
src
╷
└─ bg/sofia/uni/fmi/mjt/twitch
    ├── content
    │      ├─ stream
    │      │     ├─ Stream.java
    │      │     └─ (...)
    │      ├─ video
    │      │     ├─ Video.java
    │      │     └─ (...)
    │      ├─ Category.java
    │      ├─ Content.java
    │      ├─ Metadata.java
    │      └─ (...)
    ├── user
    │      ├─ service
    │      │     ├─ UserService.java
    │      │     └─ (...)
    │      ├─ User.java
    │      ├─ UserNotFoundException.java
    │      ├─ UserStatus.java
    │      ├─ UserStreamingException.java
    │      └─ (...)
    ├── StreamingPlatform.java
    ├── Twitch.java
    └── (...)
```

### **Предаване**

За да предадете решението си, архивирайте в **zip** цялата **src** папка на проекта и я качете в съответния assignment в грейдъра.

### **Оценяване**

Решението може да ви донесе до 100 точки, като ще бъде оценявано за:

* функционална пълнота и коректност: чрез автоматични тестове (80% от оценката)
* добър обектно-ориентиран дизайн и спазване на правилата за чист код (20% от оценката: 10% от инструментите за статичен код анализ на грейдъра и 10% от code review от асистент)

### **Желаем ви успех!**
