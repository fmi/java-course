# Shopping Cart :apple: :chocolate_bar:

Освен писане на *code from scratch*, в практиката много често се налага и да поддържаме, fix-ваме или пишем тестове за вече съществуващ код.

Целта на упражнението е да създадете и изпълните JUnit тестове, спрямо налична имплементация на следната задача:

### Условие

Да се имплементира пазарска количка, в която се съхраняват различни продукти, които искаме да купим от даден магазин.
* Количката има две имплементции - едната съхранява елементите си в `List`, а другата в `Map`.
* Артикулите, които могат да бъдат добавяни в количката, са `Apple` и `Chocolate`.
* Всеки артикул се характеризира с _име_, _описание_ и _цена_.

**_Забележка:_** Два артикула са еднакви, ако всичките им характеристики са еднакви. 

### Kод

Наличната имплементация е [тук](./shopping-cart).

**_Забележка:_** Имайте предвид, че имплементацията **съдържа бъгове**, които трябва да отстраните в процеса на тестване. Също така, в кода има известни **отстъпления от Clean Code принципите**, които също ще трябва да коригирате.

### :exclamation: Забележки

1. Ще упражним TDD ([Test Driven Development](https://en.wikipedia.org/wiki/Test-driven_development)). За тази цел, първо започнете с писането на тестовете. За да бъдат разпознати от грейдъра, тестовите класове трябва да имат имена, завършващи на `Test` или `Tests`.

2. Уверете се, че използвате JUnit4 библиотеката.

3. С тестовете си трябва да покриете поне 85% от кода (*line code coverage*). Можете да използвате следните plugin-и за измерване на code coverage - [EclEmma for Eclipse](https://www.eclemma.org/) или [Code coverage runner for IntelliJ](https://www.jetbrains.com/help/idea/code-coverage.html).

4. След като качите решението си в грейдъра, ще се изпълнят вашите тестове. След крайния срок за заданието, ще се изпълнят еднократно и всички reference тестове.

5. Проектът ви трябва да има следната структура:

```bash
src
╷
└─ bg/sofia/uni/fmi/mjt/shopping/
    ├─ ShoppingCart.java
    ├─ ListShoppingCart.java
    ├─ MapShoppingCart.java
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
