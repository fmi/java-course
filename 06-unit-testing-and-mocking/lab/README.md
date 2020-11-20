# Shopping Cart :apple: :chocolate_bar:

Освен писане на *code from scratch*, в практиката много често се налага и да поддържаме, fix-ваме или пишем тестове за вече съществуващ код.

Целта на упражнението е да създадете и изпълните JUnit тестове, спрямо налична имплементация на следната задача:

### Условие

Да се имплементира пазарска количка, в която се съхраняват различни продукти, които искаме да купим от даден магазин.
* Количката има две имплементции:
  - `ListShoppingCart` - съхранява елементите си в `List`
  - `MapShoppingCart`  съхранява елементите си в `Map`
* И двете колички приемат в конструктора си `ProductCatalog`, който съхранява информация за всички продукти (*hint*: в една реална реализация на подобен софтуер, вероятно имплементацията на ProductCatalog би ползвала база данни или уеб услуга):

    ```java
    package bg.sofia.uni.fmi.mjt.shopping;

    public interface ProductCatalog {

        ProductInfo getProductInfo(String id);
    }
   ```
    
* Артикулите, които могат да бъдат добавяни в количката, са `Apple` и `Chocolate`


**Забележка**

Два артикула са еднакви, ако `id`-тата им са еднакви.

### Kод

Наличната имплементация е [тук](./shopping-cart).

**Забележка**

Имайте предвид, че имплементацията **съдържа бъгове**, които трябва да отстраните в процеса на тестване. Също така, в кода има известни **отстъпления от Clean Code принципите**, които също ще трябва да коригирате.

### :exclamation: Забележки

1. Ще упражним TDD ([Test Driven Development](https://en.wikipedia.org/wiki/Test-driven_development)). За тази цел, първо започнете с писането на тестовете. За да бъдат разпознати от грейдъра, тестовите класове трябва да имат имена, завършващи на `Test` или `Tests`

2. Уверете се, че използвате JUnit4 библиотеката

3. За да изтествате част от методите, ще ви се наложи да изполвате `mocking` с библиотеката *Mockito*. Може да си изтеглите *Mockito* библиотеката от [тук](https://repo1.maven.org/maven2/org/mockito/mockito-all/1.10.19/mockito-all-1.10.19.jar).

3. С тестовете си трябва да покриете поне 85% от кода (*line code coverage*). Можете да използвате следните plugin-и за измерване на code coverage - [EclEmma for Eclipse](https://www.eclemma.org/) или [Code coverage runner for IntelliJ](https://www.jetbrains.com/help/idea/code-coverage.html)

4. След като качите решението си в грейдъра, ще се изпълнят вашите тестове. След крайния срок за заданието, ще се изпълнят еднократно и всички reference тестове

5. Проектът ви трябва да има следната структура:

```bash
src
╷
└─ bg/sofia/uni/fmi/mjt/shopping/
    ├─ ShoppingCart.java
    ├─ ListShoppingCart.java
    ├─ MapShoppingCart.java
    ├─ ProductCatalog.java
    ├─ ProductInfo.java
    ├─ ItemNotFoundException.java
    └─ (...)
    bg/sofia/uni/fmi/mjt/shopping/item/
    ├─ Item.java
    ├─ Apple.java
    ├─ Chocolate.java
    └─ (...)
test
╷
└─ bg/sofia/uni/fmi/mjt/shopping/
    ├─ ListShoppingCartTest.java
    ├─ MapShoppingCartTest.java
    └─ (...)
```

6. В [grader.sapera.org](http://grader.sapera.org) качете общ `zip` архив на директориите `src` и `test`.
