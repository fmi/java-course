# Generics and Clean Code

## Generic In-Memory Cache :package:

Кеширането (caching) в паметта е често използван подход за подобряване на бързодействието на алгоритми, използващи интензивно данни, съхранявани на бавно устройство.
Ще създадем собствена имплементация на generic кеш (cache).

Достъпът до кеширани данни е в порядъци по-бърз, но за съжаление, кешът си има фиксиран капацитет, който не може да се надвишава. По тази причина, всеки кеш има т.нар. *eviction policy*, т.е. правило, по което се определя, кои елементи се изтриват от кеша при опит за добавяне на нов елемент при запълнен капацитет.

### Interface

Даден е интерфейсът `Cache<K, V>`, който може да оперира с обекти от произволен тип

```java
package bg.sofia.uni.fmi.mjt.cache;

public interface Cache<K, V> {

    /**
     * Returns the value associated with the key, if it is present in the cache, or
     * null otherwise.
     */
    V get(K key);

    /**
     * Adds the value to the cache and associates it with the key, if both key and
     * value are not null. If key or value (or both) are null, the method does nothing.
     * If the key is already present in the cache, replaces the old value with the
     * currently supplied. If the cache is full, exactly one existing item is removed
     * - the one suggested by the respective eviction policy - and the new key-value pair is added
     */
    void set(K key, V value);

    /**
     * Removes the item associated with the specified key from the cache.
     * Returns true, if an item with the specified key was found and false otherwise.
     */
    boolean remove(K key);

    /**
     * Returns the number of all actual items stored currently in the cache.
     */
    long size();

    /**
     * Removes all items in the cache and resets the hit rate.
     */
    void clear();

    /**
     * Returns the percentage of the successful hits for this cache. It is a
     * double-precision number in the interval [0.0, 1.0] and is equal to the ratio
     * of get(K, V) calls that returned a non-null value versus the calls that
     * returned null. If there is not a single successful hit, the hit rate is 0.0.
     * If there is at least one successful hit and the missed hits are zero, the hit
     * rate is 1.0
     */
    double getHitRate();
    
    /**
     * Returns the number of times that the value of the given key has been accessed.
     * Returns 0 if the key is not present in the cache.
     */
    int getUsesCount(K key);
    
}
```

### Implementation

Ще създадем две имплементации на този интерфейс, различаващи се по това, какво *eviction policy* използват:

- [Random Replacement (RR)](https://en.wikipedia.org/wiki/Cache_replacement_policies#Random_replacement_(RR))
- [Least-Frequently Used (LFU)](https://en.wikipedia.org/wiki/Cache_replacement_policies#Least-frequently_used_(LFU))

Конкретна имплементация на интерфейса `Cache`, използваща избрано от нас eviction policy, създаваме чрез следните статични методи на интерфейса `CacheFactory`:

```java
/**
 * Constructs a new Cache<K, V> with the specified maximum capacity and eviction policy
 * @throws IllegalArgumentException if the given capacity is less than or equal to zero.
 * Note that IllegalArgumentException is a `RuntimeException` from the JDK
 */
static <K, V> Cache<K, V> getInstance(long capacity, EvictionPolicy policy);

/**
 * Constructs a new Cache<K, V> with maximum capacity of 10_000 items and the specified eviction policy 
 */
static <K, V> Cache<K, V> getInstance(EvictionPolicy policy); 
```

където `EvictionPolicy`, както вече се досещате, е от изброим тип:

```java
public enum EvictionPolicy {
    RANDOM_REPLACEMENT,
    LEAST_FREQUENTLY_USED
}
```

#### Уточнения

:point_right: При Least Frequently Used имплементацията:
- за "използване" смятаме всяко извикване на `get` или `set` за даден елемент.
- когато се извика `set` за вече съществуващ key с ново или същото value, броят пъти, в които сме използвали този елемент, се увеличава с 1, а не се занулява.
- ако съществуват два или повече елемента с равна (минимална) честота на използване, при добавяне на нов елемент при пълен кеш, трием произволен от тях.

:point_right: При Random Replacement имплементацията, методът `getUsesCount` хвърля `UnsupportedOperationException` (съществуващ `RuntimeException` от JDK-то).

#### Забележки

:exclamation: Обмислете внимателно, какви структури от данни ще изберете за имплементацията си. Кои методи на кеша е вероятно да бъдат използвани най-често?

:exclamation: *Пишете чист код*! Съобразете се със забележките на автоматичните инструменти относно правилата за чист код в грейдъра: стремете се кодът ви да остане с 0 забележки.

Проектът ви трябва да има следната структура:

```bash
  src
  ╷
  └─ bg/sofia/uni/fmi/mjt/cache/
    ├─ Cache.java
    ├─ CacheFactory.java
    |
    ├─ enums/
    |  └─ EvictionPolicy.java
    | 
    └─ (...)
```
В sapera.org качете *.zip* архив на *src* директорията.
