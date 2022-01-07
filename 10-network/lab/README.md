# Мрежово програмиране

## Cocktail Recipes :cocktail: :tropical_drink:

В делници и празници, на плажа и по снежните писти, алкохолни и безалкохолни, коктейлите са любимо питие за мнозина. Има голямо разнообразие от коктейли, и приготвянето им е основно умение на всеки уважаващ себе си барман. Понякога ни се иска сами да си забъркаме любимото Мохито, но какво съдържа то?
Нека помогнем на всички любители, като създадем приложение, с което да търсят и споделят рецепти за коктейли. Ще го разработим като клиент-сървър приложение, като сървърът ще съхранява и менажира рецептите, а потребителите ще използват клиента, за да комуникират със сървъра и търсят и качват рецепти.

### Cocktail Recipes Client

- Всеки потребител може да качва рецепти за коктейли
- Потребителите също могат да търсят рецепти за коктейли (по име или по съставки)
- Потребителите могат да прекратят връзката си към сървъра по всяко време

### Cocktail Recipes Server

- Сървърът трябва да може да обслужва множество клиенти едновременно
- Сървърът получава команди от клиентите и връща подходящ резултат

### Описание на клиентските команди

```bash
- create <cocktail_name> [<ingredient_name>=<ingredient_amount> ...] - изпраща заявка за създаване на нова рецепта за коктейл
  с даденото име и списък със съставки. Името трябва да се състои от една дума (без whitespaces). Всеки коктейл съдържа
  поне една съставка в описания формат (пример: whisky=100ml, където "whisky" e името на съставката, а "100ml" е количеството,
  като двете са произволни низове, които не съдържат whitespaces и символа '='). Съставките се изброяват на командния ред като
  се разделят с whitespace. Приемаме, че в една рецепта няма повтарящи се съставки с различни количества.

- get
  - get all – връща списък с всички рецепти за коктейли
  - get by-name <cocktail_name> – връща рецепта на коктейл с даденото име
  - get by-ingredient <ingredient_name> – връща списък с всички рецепти на коктейли, които съдържат дадената съставка

- disconnect – потребителят прекратява връзката си със сървъра
```

### Пример

```bash
# 1. start a cocktail recipes client

# 2.1. create a new cocktail recipe
=> create manhattan whisky=100ml
=> {"status":"CREATED"}

# 2.2. attempt to create a recipe for a cocktail that already exists
=> create manhattan whisky=100ml
=> {"status":"ERROR","errorMessage":"cocktail manhattan already exists"}

# 3.1. list all cocktail recipes when there are none
=> get all
=> {"status":"OK","cocktails":[]}

# 3.2. list all cocktail recipes after creation
=> get all
=> {"status":"OK","cocktails":[{"name":"manhattan","ingredients":[{"name":"whisky","amount":"100ml"}]}]}

# 4. random
=> some random commands
=> Unknown command

# 5. disconnect from the server
=> disconnect
=> Disconnected from the server
```

### Дизайн

Един от принципите на чистия дизайн е разделянето на бизнес логиката от входно-изходната логика. Ако разгледаме сървърната част, бизнес логиката е поддържането на базата с рецепти и операциите върху тях, а входно-изходната логика е мрежовата комуникация с клиентите. Подобно разделение прави кода по-четим и лесен за разширение, а също и значително улеснява тестването на бизнес логиката в изолация от мрежовата комуникация.

Реализирайте бизнес логиката на сървъра чрез класа `DefaultCocktailStorage`, който има публичен конструктор по подразбиране и имплементира следния интерфейс:

```java
package bg.sofia.uni.fmi.mjt.cocktail.server.storage;

public interface CocktailStorage {

    /**
     * Creates a new cocktail recipe
     *
     * @param cocktail cocktail
     * @throws CocktailAlreadyExistsException if the same cocktail already exists
     */
    void createCocktail(Cocktail cocktail) throws CocktailAlreadyExistsException;

    /**
     * Retrieves all cocktail recipes
     *
     * @return all cocktail recipes from the storage, in undefined order.
     * If there are no cocktails in the storage, returns an empty collection.
     */
    Collection<Cocktail> getCocktails();

    /**
     * Retrieves all cocktail recipes with given ingredient
     *
     * @param ingredientName name of the ingredient (case-insensitive)
     * @return all cocktail recipes with given ingredient from the storage, in undefined order.
     * If there are no cocktails in the storage with the given ingredient, returns an empty collection.
     */
    Collection<Cocktail> getCocktailsWithIngredient(String ingredientName);

    /**
     * Retrieves a cocktail recipe with the given name
     *
     * @param name cocktail name (case-insensitive)
     * @return cocktail with the given name
     * @throws CocktailNotFoundException if a cocktail with the given name does not exist in the storage
     */
    Cocktail getCocktail(String name) throws CocktailNotFoundException;

}
```

Коктейл ще моделираме чрез record-а `Cocktail(String name, Set<Ingredient> ingredients)`, където `Ingredient` е друг record, `Ingredient(String name, String amount)`, моделиращ съставка на коктейл. Двата record-a трябва да имат публични канонични конструктори. Два коктейла са еднакви, ако имената им съвпадат и двe съставки са еднакви, ако имената им съвпадат, независимо от количеството.

### Тестване

:star: Тествайте ръчно имплементацията, първо с един, а после с няколко паралелно свързани клиента, и се убедете, че работи коректно.

:star: Писането на автоматични тестове за тази задача е по ваш избор, но съветваме всеки да пробва, тъй като ще ви е полезно и за курсовите проекти.

**Подсказка:** Припомнете си различните имплементации на [Echo Client-Server](https://github.com/fmi/java-course/tree/master/10-network/snippets), които разгледахме на упражнението. Можем ли да ги превърнем в Cocktail Recipes Client/Server приложение?

**Подсказка:** Решението на тази задача ще ви улесни изключително много при разработката на курсовите ви проекти, защото всички те представляват приложения тип клиент-сървър, като сървърът обслужва много потребители едновременно.

### Примерна структура на проекта

Добра практика при създаването на приложения тип клиент-сървър е да отделяте клиента и сървъра в отделни проекти. Това предотвратява грешки от типа, класове/интерфейси от клиента да се ползват от сървъра, или обратно. Също така, в реална ситуация, бихме искали да пакетираме и разпространяваме поотделно клиентската и сървърната част на нашето приложение.

В грейдъра качете общ `zip` архив на папки `src` и `test`, ако имате тестове.

```
src
└─ bg.sofia.uni.fmi.mjt.cocktail.server
    ├── storage
    │    ├─ exceptions
    │    │    ├── CocktailAlreadyExistsException.java
    │    │    └── CocktailNotFoundException.java
    │    ├─ CocktailStorage.java
    │    └─ DefaultCocktailStorage.java
    ├── Cocktail.java
    ├── Ingredient.java
    └── (...)
```
