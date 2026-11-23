# Жизнен цикъл на JUnit тест

Даден е следният тестови клас:

```java
class LifecycleTest {

    @BeforeAll
    static void beforeAll() { System.out.println("A"); }

    @BeforeEach
    void beforeEach() { System.out.println("B"); }

    @AfterEach
    void afterEach() { System.out.println("C"); }

    @AfterAll
    static void afterAll() { System.out.println("D"); }

    @Test
    void test1() { System.out.println("1"); }

    @Test
    void test2() { System.out.println("2"); }
}
```

Какъв е редът на изпълнение?

<details>
<summary>Решение</summary>

Редът е: **A B 1 C B 2 C D**

- `@BeforeAll` → веднъж преди всичко (трябва да е `static`)
- `@BeforeEach` → преди всеки тест
- `@AfterEach` → след всеки тест
- `@AfterAll` → веднъж след всичко (трябва да е `static`)

Ако `@BeforeAll` не е `static`, JUnit хвърля:
```
org.junit.platform.commons.JUnitException:
@BeforeAll method 'void LifecycleTest.beforeAll()' must be static
```

</details>
