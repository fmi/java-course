# Домашно 1

## Social Media Inator 📱

`Краен срок: 25.11.2020, 23:45`

Пери Птицечовката е по петите Ви! Имате мисия да помогнете на Дуфеншмърц да създаде зла платформа за социални мрежи, с която да превземе района на трите щата. В тайните му планове, са заложени следните функционалности:
1. регистриране на нов потребител
2. публикуване на `post` или `story`
3. харесване и коментиране на `post` или `story`
4. поддържане на дневник на дейностите (`activity log`) за всеки потребител
5. изчисляване на различни статистики за платформата

Създайте клас `EvilSocialInator` с default-ен конструктор, който да имплементира интерфейса `SocialMediaInator`:

```java
package bg.sofia.uni.fmi.mjt.socialmedia;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import bg.sofia.uni.fmi.mjt.socialmedia.content.Content;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.NoUsersException;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.UsernameAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.UsernameNotFoundException;

public interface SocialMediaInator {

    /**
     * Registers a new user in the platform.
     * @param username
     * @throws IllegalArgumentException If {@code username} is null
     * @throws UsernameAlreadyExistsException If there is already a user with {@code username}
     * registered in the platform
     */
    void register(String username);

    /**
     * Publishes a post with {@code description}.
     * -> A post expires in 30 days after it was published
     * -> The description of the post may contain arbitrary number of mentions (i.e @someuser) and hash-tags
     * (i.e #programming)
     * -> If a non-existing user is mentioned in the description, the actual mention does not have any effect
     * -> The tags and mentions are always separated with at least one space from the other words in the description
     * -> The id of each post is generated as follows: [username]-[auto-incremented integer starting from 0]
     * @param username
     * @param publishedOn
     * @param description
     * @return The id of the newly created post
     * @throws IllegalArgumentException If any of the parameters is null
     * @throws UsernameNotFoundException If a user with {@code username} does not exist in the platform
     */
    String publishPost(String username, LocalDateTime publishedOn, String description);
    
    /**
     * Publishes a story with {@code description}.
     * -> A story expires in 24 hours after it was published
     * -> The description of the story may contain arbitrary number of mentions (i.e @someuser) and tags
     * (i.e #programming)
     * -> If a non-existing user is mentioned in the description, the actual mention does not have any effect
     * -> The tags and mentions are always separated with at least one space from the other words in the description
     * -> The id of each story is generated as follows: [username]-[auto-incremented integer starting from 0]
     * @param username
     * @param publishedOn
     * @param description
     * @return The id of the newly created story
     * @throws IllegalArgumentException If any of the parameters is null
     * @throws UsernameNotFoundException If a user with {@code username} does not exist in the platform
     */
    String publishStory(String username, LocalDateTime publishedOn, String description);

    /**
     * Likes a content with id {@code id}.
     * @param username The name of the user who liked the content
     * @param id The id of the content
     * @throws IllegalArgumentException If any of the parameters is null
     * @throws UsernameNotFoundException If a user with {@code username} does not exist in the platform
     * @throws ContentNotFoundException If there is no content with id {@code id} in the platform
     */
    void like(String username, String id);

    /**
     * Comments on a content with id {@code id}.
     * @param username The name of the user who commented the content
     * @param text The actual comment
     * @param id The id of the content
     * @throws IllegalArgumentException If any of the parameters is null
     * @throws UsernameNotFoundException If a user with {@code username} does not exist in the platform
     * @throws ContentNotFoundException If there is no content with id {@code id} in the platform
     */
    void comment(String username, String text, String id);

    /**
     * Returns the {@code n} most popular content on the platform.
     * -> The popularity of a content is calculated by the total number of likes and comments
     * -> If there is no content in the platform, an empty Collection should be returned
     * -> If the total number of posts and stories is less than {@code n} return as many as available
     * -> The returned Collection should not contain expired content
     * @param n The number of content to be returned
     * @throws IllegalArgumentException If {@code n} is a negative number
     * @return Unmodifiable collection of Content sorted by popularity in descending order
     */
    Collection<Content> getNMostPopularContent(int n);

    /**
     * Returns the {@code n} most recent content of user {@code username}.
     * -> If the given user does not have any content, an empty Collection should be returned.
     * -> If the total number of posts and stories is less than {@code n} return as many as available
     * -> The returned Collection should not contain expired content
     * @param username
     * @param n The number of content to be returned
     * @throws IllegalArgumentException If {@code username} is null or {@code n} is a negative number
     * @throws UsernameNotFoundException if a user with {@code username} does not exist in the platform
     * @return Unmodifiable collection of Content sorted by popularity
     */
    Collection<Content> getNMostRecentContent(String username, int n);

    /**
     * Returns the username of the most popular user.
     * -> This is the user which was mentioned most times in stories and posts
     * @throws NoUsersException if there are currently no users in the platform
     */
    String getMostPopularUser();

    /**
     * Returns all posts and stories containing the tag {@code tag} in their description.
     * -> If there are no posts or stories with the given tag in the platform, an empty Collection should be returned
     * -> Note that {@code tag} should start with '#'
     * -> The returned Collection should not contain expired content
     * @param tag
     * @throws IllegalArgumentException If {@code tag} is null
     * @return Unmodifiable collection of Content
     */
    Collection<Content> findContentByTag(String tag);
    
    /**
     * Returns the activity log of user {@code username}. It contains a history of all activities of a given user.
     * -> The activity log is maintained in reversed chronological order (i.e newest events first).
     * -> It has the following format:
     * HH:mm:ss dd.MM.yy: Commented "[text]" on a content with id [id]
     * HH:mm:ss dd.MM.yy: Liked a content with id [id]
     * HH:mm:ss dd.MM.yy: Created a post with id [id]
     * HH:mm:ss dd.MM.yy: Created a story with id [id]
     * -> HH:mm:ss dd.MM.yy is a time format
     * -> If the given user does not have any activity on the platform, an empty List should be returned
     * @param username
     * @throws IllegalArgumentException If {@code username} is null
     * @throws UsernameNotFoundException if a user with {@code username} does not exist in the platform
     * @return List of activities in the above format
     */
    List<String> getActivityLog(String username);

}
```

Всяко съдържание (`post`-ове и `story`-та) в нашата социална мрежа, представлява клас, който имплементира следния интерфейс:

```java
package bg.sofia.uni.fmi.mjt.socialmedia.content;

import java.util.Collection;

public interface Content {

    /**
     * Returns the total number of likes.
     */
    int getNumberOfLikes();

    /**
     * Returns the total number of comments.
     */
    int getNumberOfComments();
    
    /**
     * Returns the unique id of the content
     */
    String getId();

    /**
     * Returns a Collection of all tags used in the description.
     * Аll tags should start with '#'.
     */
    Collection<String> getTags();

    /**
     * Returns a Collection of all users mentioned in the description.
     * Аll mentions should start with '@'.
     */
    Collection<String> getMentions();

}

```

### Бележки

- Съдържание, което вече е `expire`-нало, **не трябва да присъства** като резултат в методите, които връщат колекция от Content
- От дневника на дейностите **не трябва да се премахва** expire-налoто съдържание
- Id-тата на постовете и сторитата се образуват по следния начин: **[username]-[auto-incremented integer starting from 0]**, където (1) **[username]** e името на user-a, който публикува конкретния content, a (2) **[auto-incremented integer starting from 0]** е число, което започва от 0 и е глобално за цялата социална мрежа (т.е не е `per-user` или `per-content`)
- :exclamation::exclamation: **Решения, използващи [Java Stream API](https://docs.oracle.com/en/java/javase/13/docs/api/java.base/java/util/stream/package-summary.html), lambdas, и всичко останало, което не е покрито до момента, няма да се приемат за това домашно.**

### Пакети

Спазвайте имената на пакетите на всички по-горе описани класове, тъй като в противен случай решението ви няма да може да бъде тествано от грейдъра.

```bash
src
╷
└─ bg/sofia/uni/fmi/mjt/socialmedia/
   └─ SocialMediaInator.java
   └─ EvilSocialInator.java
   └─ (...)
   bg/sofia/uni/fmi/mjt/socialmedia/content
   └─ Content.java
   └─ (...)
   bg/sofia/uni/fmi/mjt/socialmedia/exceptions
      └─ ContentNotFoundException.java
      └─ NoUsersException.java
      └─ UsernameAlreadyExistsException.java
      └─ UsernameNotFoundException.java
      └─ (...)
```

### **Предаване**

За да предадете решението си, архивирайте в **zip** цялата **src** папка на проекта и я качете в [grader.sapera.org](http://grader.sapera.org/WebObjects/Web-CAT.woa).

### **Оценяване**

Решението може да ви донесе до 100 точки, като ще бъде оценявано за:

* функционална пълнота и коректност: чрез автоматични тестове (80% от оценката)
* добър обектно-ориентиран дизайн и спазване на правилата за чист код (20% от оценката: 10% от инструментите за статичен код анализ на грейдъра и 10% от code review от асистент)

### **Желаем ви успех!**
