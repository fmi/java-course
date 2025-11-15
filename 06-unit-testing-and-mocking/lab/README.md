# Unit Testing and Mocking

Освен писане на code from scratch, в практиката много често се налага и да поддържаме, fix-ваме или пишем тестове за вече съществуващ код.

Целта на упражнението днес е, да създадете и изпълните JUnit тестове спрямо налична имплементация.

---

# Poor Man's Jenkins

Вашият колега Стамат Програмистов, който е част от вашия екип, един ден се събудил с идеята да си подобри работата с файлове чрез създаване на система, чиято идея се базира на потокова обработка на данни. Тъй като на английски това се превежда като `pipeline` и Стамат знаел само за Jenkins, която използва такава терминология, той решил да кръсти системата си "Poor Man's Jenkins". Тя е Poor Man's, защото Стамат нямал толкова задълбочени познания, че да напише нещо като Jenkins.

Стамат е направил имплементацията на системата, но както всеки програмист някога, го е домързяло да напише тестове за нея и така да се увери, че всичко работи коректно. Затова, той решава да ви даде имплементация и ви моли да напишете тестове за нея, като обещава че ще ви черпи по няколко бири за тази услуга.

**Имплементацията може да бъде намерена в директорията [resources](./resources).**

Разбира се, в имплементацията на Стамат се крият бъгове. Ще трябва да ги откриете и отстраните в процеса на тестване. За да бъде той ефективен, първо напишете тест за някой сценарий, след това оправете бъга, който сте намерили с него.

## Основни класове и тяхната функционалност

### Pipeline

```java
/**
 * Represents a generic data processing pipeline consisting of multiple {@link Stage stages}.
 * Each stage transforms data from one type to another, allowing sequential transformations
 * from the pipeline input type {@code I} to the output type {@code O}.
 * <p>
 * The pipeline also caches execution results for previously seen inputs. When a cached
 * input is provided, the pipeline returns the cached output instead of re-executing
 * the stages.
 *
 * @param <I> the type of input data for the pipeline
 * @param <O> the type of output data produced by the pipeline
 */
public final class Pipeline<I, O> {

    /**
     * Creates a new pipeline with an initial stage.
     *
     * @param initialStage the first stage of the pipeline
     * @param <I>          the type of input data for the pipeline
     * @param <O>          the type of output produced by the initial stage
     * @return a new pipeline starting with the given stage
     *
     * @throws IllegalArgumentException if initialStage is null
     */
    public static <I, O> Pipeline<I, O> start(Stage<I, O> initialStage);

    /**
     * Private constructor used to initialize the pipeline with a list of stages.
     *
     * @param stages the initial list of stages
     */
    private Pipeline(List<Stage<?, ?>> stages);
	
    /**
     * Adds a new stage at the end of the pipeline.
     * <p>
     * The input type of the new stage must be compatible with the output type of
     * the current pipeline. The returned pipeline is updated to reflect the new
     * output type {@code NEW_O}.
     * <p>
     * All currently cached results should be cleared when a new stage is added.
     *
     * @param stage the stage to add
     * @param <NEW_O> the output type of the new stage
     * @return this pipeline instance cast to a pipeline producing {@code NEW_O}
     */
    public <NEW_O> Pipeline<I, NEW_O> addStage(Stage<? super O, NEW_O> stage);

    /**
     * Executes the pipeline on the given input.
     * <p>
     * The input flows through all stages sequentially. If the same input has been
     * processed before, the cached output is returned instead of re-executing the
     * stages.
     *
     * @param input the input data to process
     * @return the output produced by the last stage of the pipeline
     */
    public O execute(I input);
	
}
```

### Stage

```java
/**
 * Represents a stage in a generic pipeline, consisting of a sequence of {@link Step steps}.
 * <p>
 * Each stage has a defined input type {@code I} and output type {@code O}. Steps within the
 * stage are executed sequentially, where the output of one step is passed as input to the
 * next step.
 *
 * @param <I> the type of input accepted by the stage
 * @param <O> the type of output produced by the stage
 */
public final class Stage<I, O> {

    /**
     * Creates a new stage starting with the given initial step.
     *
     * @param initialStep the first step of this stage
     * @param <I> the input type of the stage
     * @param <O> the output type of the initial step
     * @return a new stage instance containing the initial step
     *
     * @throws IllegalArgumentException if initialStep is null
     */
    public static <I, O> Stage<I, O> start(Step<I, O> initialStep);

    /**
     * Private constructor used to initialize a stage with a list of steps.
     *
     * @param steps the list of steps to include in the stage
     */
    private Stage(List<Step<?, ?>> steps);

    /**
     * Adds a new step to the stage.
     * <p>
     * The input type of the new step must be compatible with the current output type {@code O}.
     * The returned stage updates the output type to {@code NEW_O}.
     *
     * @param step the step to add
     * @param <NEW_O> the output type of the new step
     * @return this stage instance cast to have output type {@code NEW_O}
     *
     * @throws IllegalArgumentException if step is null
     */
    public <NEW_O> Stage<I, NEW_O> addStep(Step<? super O, NEW_O> step);

    /**
     * Executes all steps in this stage sequentially.
     * <p>
     * The input is passed to the first step, and the output of each step is passed to the
     * next. The result of the last step is returned as the output of the stage.
     *
     * @param input the input data to process
     * @return the output produced by the last step of this stage
     */
    public O execute(I input);
	
}
```

### Step

```java
/**
 * Represents a single processing step in a pipeline.
 * <p>
 * Each step transforms input of type {@code I} into output of type {@code O}.
 * Implementations should provide the transformation logic in the {@link #process(Object)}
 * method.
 *
 * @param <I> the type of input the step accepts
 * @param <O> the type of output the step produces
 */
public interface Step<I, O> {

    /**
     * Processes the given input and produces an output.
     *
     * @param input the input data to process
     * @return the output data produced by this step
     */
    O process(I input);

}
```

### Cache

```java
/**
 * A simple generic cache for storing key-value pairs in memory.
 * <p>
 * This class is used internally by {@link Pipeline} to cache results of previous
 * executions and avoid re-computation for the same input. The keys and values
 * are stored as {@code Object}, so type safety is not enforced at compile time.
 * Be careful when retrieving cached values to cast them to the correct type.
 */
public class Cache {

    /**
     * Creates a new, empty cache.
     */
    public Cache();

    /**
     * Stores a value associated with the given key.
     * If the key already exists, the previous value is overwritten.
     *
     * @param key   the key to store
     * @param value the value associated with the key
     *
     * @throws IllegalArgumentException if key or value is null
     */
    public void cacheValue(Object key, Object value);

    /**
     * Retrieves the cached value for the given key.
     *
     * @param key the key whose value is to be returned
     * @return the cached value, or {@code null} if the key is not present
     *
     * @throws IllegalArgumentException if key is null
     */
    public Object getCachedValue(Object key);

    /**
     * Checks if a value is cached for the given key.
     *
     * @param key the key to check
     * @return {@code true} if the cache contains the key, {@code false} otherwise
     *
     * @throws IllegalArgumentException if key is null
     */
    public boolean containsKey(Object key);

    /**
     * Clears all entries from the cache.
     */
    public void clear();

    /**
     *  Checks if the cache is empty
     * */
    public boolean isEmpty();

}
```

### File

Тъй като все още не сме придобили знания как да работим с файлове в Java, на това упражнение ще използваме този клас, за да симулираме текстови файлове.

```java
/**
 * A simple in-memory representation of a file containing textual content.
 */
public class File {
	
    /**
     * Creates a new File with the given content.
     *
     * @param content the initial content of the file
     * @throws IllegalArgumentException if content is null
     */
    public File(String content);

    public String getContent();

    public void setContent(String content);

}
```

## Пакети

Спазвайте имената на пакетите на всички по-горе описани класове, тъй като в противен случай решението ви няма да може да бъде тествано от грейдъра.

```
src
└── bg.sofia.uni.fmi.mjt
     ├── file
     │     │── exception
     │     │     │── EmptyFileException.java
     │     │     └── (...) 
     │     │── step
     │     │     │── CheckEmptyFile.java
     │     │     │── CountFiles.java
     │     │     │── PrintFiles.java
     │     │     │── SplitFile.java
     │     │     │── UpperCaseFile.java
     │     │     └── (...) 
     │     └── (...)   
     ├── pipeline
     │     ├── stage
     │     │     │── Stage.java
     │     │     └── (...) 
     │     ├── step
     │     │     │── Step.java
     │     │     └── (...)
     │     │── Cache.java
     │     │── Pipeline.java
     │     └── (...) 
     └── (...)     
test
└── bg.sofia.uni.fmi.mjt
     │── file
     │     └── (...)
     │── pipeline
     │     └── (...)
     └── (...)
```

## Забележки

- В грейдъра качете общ .zip архив на двете директории src и test.
- Не включвайте в архива jar-ките на JUnit и Mockito библиотеките. На грейдъра ги има, няма смисъл архивът с решението ви да набъбва излишно.

Успех и не се притеснявайте да задавате въпроси! ⭐