# Social Network :speech_balloon:

Тази седмица ще запретнем ръкави и създадем проста социална мрежа, която ще моделира потребители, мрежи от приятелства, постове и реакции.

### Social Network

В пакета `bg.sofia.uni.fmi.mjt.socialnetwork` създайте публичен клас `SocialNetworkImpl`, който има публичен конструктор по подразбиране и имплементира интерфейса `SocialNetwork`:

```java
package bg.sofia.uni.fmi.mjt.socialnetwork;

import bg.sofia.uni.fmi.mjt.socialnetwork.exception.UserRegistrationException;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.Post;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;

public interface SocialNetwork {

    /**
     * Registers a user in the social network.
     *
     * @param userProfile the user profile to register
     * @throws IllegalArgumentException  if the user profile is null
     * @throws UserRegistrationException if the user profile is already registered
     */
    void registerUser(UserProfile userProfile) throws UserRegistrationException;

    /**
     * Returns all the registered users in the social network.
     *
     * @return unmodifiable set of all registered users (empty one if there are none).
     */
    Set<UserProfile> getAllUsers();

    /**
     * Posts a new post in the social network.
     *
     * @param userProfile the user profile that posts the content
     * @param content     the content of the post
     * @return the created post
     * @throws UserRegistrationException if the user profile is not registered
     * @throws IllegalArgumentException  if the user profile is null
     * @throws IllegalArgumentException  if the content is null or empty
     */
    Post post(UserProfile userProfile, String content) throws UserRegistrationException;

    /**
     * Returns all posts in the social network.
     *
     * @return unmodifiable collection of all posts (empty one if there are none).
     */
    Collection<Post> getPosts();

    /**
     * Returns a collection of unique user profiles that can see the specified post in their feed. A
     * user can view a post if both of the following conditions are met:
     * <ol>
     *     <li>The user has at least one common interest with the author of the post.</li>
     *     <li>The user has the author of the post in their network of friends.</li>
     * </ol>
     * <p>
     * Two users are considered to be in the same network of friends if they are directly connected
     * (i.e., they are friends) or if there exists a chain of friends connecting them.
     * </p>
     *
     * @param post The post for which visibility is being determined
     * @return A set of user profiles that meet the visibility criteria (empty one if there are none).
     * @throws IllegalArgumentException if the post is <code>null</code>.
     */
    Set<UserProfile> getReachedUsers(Post post);

    /**
     * Returns a set of all mutual friends between the two users.
     *
     * @param userProfile1 the first user profile
     * @param userProfile2 the second user profile
     * @return a set of all mutual friends between the two users or an empty set if there are no
     * mutual friends
     * @throws UserRegistrationException if any of the user profiles is not registered
     * @throws IllegalArgumentException  if any of the user profiles is null
     */
    Set<UserProfile> getMutualFriends(UserProfile userProfile1, UserProfile userProfile2)
        throws UserRegistrationException;

    /**
     * Returns a sorted set of all user profiles ordered by the number of friends they have in
     * descending order.
     *
     * @return a sorted set of all user profiles ordered by the number of friends they have in
     * descending order
     */
    SortedSet<UserProfile> getAllProfilesSortedByFriendsCount();

}
```

### User Profile

В пакетa `bg.sofia.uni.fmi.mjt.socialnetwork.profile` създайте публичен клас `DefaultUserProfile`, чрез който ще моделираме потребителите на социалната мрежа. Той трябва да има конструктор

```java
public DefaultUserProfile(String username)
```

и да имплементира интерфейса `UserProfile`:

```java
package bg.sofia.uni.fmi.mjt.socialnetwork.profile;

import java.util.Collection;

public interface UserProfile {

    /**
     * Returns the username of the user.
     * Each user is guaranteed to have a unique username.
     *
     * @return the username of the user
     */
    String getUsername();

    /**
     * Returns an unmodifiable view of the user's interests.
     *
     * @return an unmodifiable view of the user's interests
     */
    Collection<Interest> getInterests();

    /**
     * Adds an interest to the user's profile.
     *
     * @param interest the interest to be added
     * @return true if the interest is newly added, false if the interest is already present
     * @throws IllegalArgumentException if the interest is null
     */
    boolean addInterest(Interest interest);

    /**
     * Removes an interest from the user's profile.
     *
     * @param interest the interest to be removed
     * @return true if the interest is removed, false if the interest is not present
     * @throws IllegalArgumentException if the interest is null
     */
    boolean removeInterest(Interest interest);

    /**
     * Return unmodifiable view of the user's friends.
     *
     * @return an unmodifiable view of the user's friends
     */
    Collection<UserProfile> getFriends();

    /**
     * Adds a user to the user's friends.
     *
     * @param userProfile the user to be added as a friend
     * @return true if the user is added, false if the user is already a friend
     * @throws IllegalArgumentException if the user is trying to add themselves as a friend,
     *                                  or if the user is null
     */
    boolean addFriend(UserProfile userProfile);

    /**
     * Removes a user from the user's friends.
     *
     * @param userProfile the user to be removed
     * @return true if the user is removed, false if the user is not a friend
     * @throws IllegalArgumentException if the user is null
     */
    boolean unfriend(UserProfile userProfile);

    /**
     * Checks if a user is already a friend.
     *
     * @param userProfile the user to be checked
     * @return true if the user is a friend, false if the user is not a friend
     * @throws IllegalArgumentException if the user is null
     */
    boolean isFriend(UserProfile userProfile);
}
```

Обърнете внимание, че приятелството в нашата социална мрежа е симетрична релация.

Интересите на даден потребител се моделират от следния enum:

```java
package bg.sofia.uni.fmi.mjt.socialnetwork.profile;

public enum Interest {
    SPORTS, BOOKS, TRAVEL, MUSIC, MOVIES, GAMES, FOOD
}
```

### Post

Пост в социалнта мрежа се моделира от класа `SocialFeedPost` в пакета `bg.sofia.uni.fmi.mjt.socialnetwork.post`. Класът трябва да има конструктор

```java
public SocialFeedPost(UserProfile author, String content)
```
и да имплементира интерфейса

```java
package bg.sofia.uni.fmi.mjt.socialnetwork.post;

import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

public interface Post {

    /**
     * Returns the unique id of the post.
     * Each post is guaranteed to have a unique id.
     *
     * @return the unique id of the post
     */
    String getUniqueId();

    /**
     * Returns the author of the post.
     *
     * @return the author of the post
     */
    UserProfile getAuthor();

    /**
     * Returns the date and time when the post was published.
     * A post is published once it is created.
     *
     * @return the date and time when the post was published
     */
    LocalDateTime getPublishedOn();

    /**
     * Returns the content of the post.
     *
     * @return the content of the post
     */
    String getContent();

    /**
     * Adds a reaction to the post.
     * If the profile has already reacted to the post, the reaction is updated to the latest one.
     * An author of a post can react to their own post.
     *
     * @param userProfile  the profile that adds the reaction
     * @param reactionType the type of the reaction
     * @return true if the reaction is added, false if the reaction is updated
     * @throws IllegalArgumentException if the profile is null
     * @throws IllegalArgumentException if the reactionType is null
     */
    boolean addReaction(UserProfile userProfile, ReactionType reactionType);

    /**
     * Removes a reaction from the post.
     *
     * @param userProfile the profile that removes the reaction
     * @return true if the reaction is removed, false if the reaction is not present
     * @throws IllegalArgumentException if the profile is null
     */
    boolean removeReaction(UserProfile userProfile);

    /**
     * Returns all reactions to the post.
     * The returned map is unmodifiable.
     *
     * @return an unmodifiable view of all reactions to the post
     */
    Map<ReactionType, Set<UserProfile>> getAllReactions();

    /**
     * Returns the count of a specific reaction type to the post.
     *
     * @param reactionType the type of the reaction
     * @return the count of reactions of the specified type
     * @throws IllegalArgumentException if the reactionType is null
     */
    int getReactionCount(ReactionType reactionType);

    /**
     * Returns the total count of all reactions to the post.
     *
     * @return the total count of all reactions to the post
     */
    int totalReactionsCount();
}
```

Реакциите се моделират от следния enum:

```java
package bg.sofia.uni.fmi.mjt.socialnetwork.post;

public enum ReactionType {
    LIKE, LOVE, ANGRY, LAUGH, SAD
}
```

### Пакети

Спазвайте имената на пакетите на всички по-горе описани типове, тъй като в противен случай, решението ви няма да може да бъде автоматично тествано.

```
src
└── bg.sofia.uni.fmi.mjt.socialnetwork
    ├── exception
    │      └── UserRegistrationException.java 
    ├── post
    │      ├── Post.java
    │      ├── ReactionType.java
    │      ├── SocialFeedPost.java
    │      └── (...)
    ├── profile
    │      ├── DefaultUserProfile.java
    │      ├── Interest.java
    │      ├── UserProfile.java
    │      └── (...)
    ├── SocialNetwork.java
    ├── SocialNetworkImpl.java
    └── (...)
```

### :warning: Забележки

- Не променяйте по никакъв начин интерфейсите, дадени в условието.
- Използването на [Java Stream API](https://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/util/stream/package-summary.html) и/или [lambdas](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html) **не е разрешено**. Задачата трябва да се реши с помощта на знанията от курса до момента.
