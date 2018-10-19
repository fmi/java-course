# Кафе машина

### Условие 

Всяка кафе машина се характеризира със:

 - специфичен набор от кофеинови напитки, които може да прави
 - вместимост на отделните контейнери - вода, кафе, мляко и други
 - функция късметче - с всяко кафе да получаваме и късметче
 
Целта нa задачата е да създадем софтуер за два вида 'smart' кафе машини - `Basic` и `Premium`.  Те се отличават със следните особености:

||Basic|Premium |
|--|--|--|
|**вместимост на контейнерите**|  вода - 600ml <br>кафе - 600gr <br> мляко - 0ml <br> какао - 0gr| вода - 1000ml <br> кафе - 1000gr <br> мляко - 1000ml <br> какао - 300gr |
|**напитки, които може да прави**|  Espresso|Espresso, Cappuccino, Mochaccino  | 
| **функция късметче** | не | да |

Рецептите за кафетата са следните:

    Espresso   - 10gr кафе + 30ml вода
    Cappuccino - 18gr кафе + 150ml мляко
    Mochaccino - 18gr кафе + 150ml мляко + 10gr какао

За кафе машините създайте два класа - `BasicCoffeeMachine` и `PremiumCoffeeMachine`, които имплементират следния интерфейс:

``` java
package bg.fmi.mjt.lab.coffee_machine;

public interface CoffeeMachine {
	/**
	* If the Coffee Machine was able to successfully make the 
	* beverage it returns a Product instance that represents
	* the actual beverage. Otherwise, it returns null.
	*/
	public Product brew(Beverage beverage);

	/**
	* Returns the Container of the Coffee Machine
	*/
	public Container getSupplies();

	/**
	* Reffils the Container of the Coffee Machine with its default values
	*/
	public void refill();
}

```
**Hint** - при какви обстоятелства една напитка ще бъде успешно направена за всяка от двете кафе машини?
 - `BasicCoffeeMachine` и `PremiumCoffeeMahcine` трябва да имат конструктори по подразбиране (без параметри).
 - `PremiumCoffeeMachine` трябва да имплементира и следния конструктор:
	 ``` java
	/**
	 * @param autoRefill - if true, it will automatically refill the container 
	 * if there are not enough ingredients to make the coffee drink
	 */
	 public PremiumCoffeeMachine(boolean autoRefill)
	 ``` 
 -  `PremiumCoffeeMachine` също така трябва да може да прави по 3 напитки едновременно. За тази цел имплементирайте следния метод:
	 ``` java
	 /**
	 * If quantity is <= 0 or the quantity is not supported for 
	 * the particular Coffee Machine the method returns null
	 */
	 public Product brew(Beverage beverage, int quantity)
	 ```
 
Всяка кафе машина се асоциира с контейнер, който съдържа информация за текущото наличие на отделните съставки (кафе, мляко, вода, ..). Напишете интерфейс или абстрактен клас, който съдържа следните методи:
``` java
package bg.fmi.mjt.lab.coffee_machine.container;
[...]

	/**
	* Returns the current quantity (in milliliters) of the water in the container
	*/
	public double getCurrentWater();

	/**
	* Returns the current quantity (in milliliters) of the milk in the container
	*/
	public double getCurrentMilk();

	/**
	* Returns the current quantity (in grams) of the coffee in the container
	*/
	public double getCurrentCoffee();

	/**
	* Returns the current quantity (in grams) of the cacao in the container
	*/
	public double getCurrentCacao();

[...]
```
						 
Създайте клас `Product`, съдържащ конкретните характеристиките за текущата напитка, направена от машината, със следните методи:
- `public String getName()` - връща името на напитката, която е била направено (Espresso, Cappucinno или Mochaccino)
- `public int getQuantity()` - връща количеството напитки, които машината е направила (след извикване на метода `brew()`)
- `public String getLuck()` - връща произволно късметче от списъка:
	 - If at first you don't succeed call it version 1.0.
	 - Today you will make magic happen!
	 - Have you tried turning it off and on again?
	 - Life would be much more easier if you had the source code.
		
		Списъкът се обхожда циклично - когато прочетем и последното късметче започваме пак отначало.
		Ако машината не поддържа функция късметче, методът връща `null`.

Създайте класове `Espresso`, `Cappuccino` и `Mochaccino`, които имплементират интерфейса `Beverarge`. Те ще дават информация за това как се прави конкретната напитка.
**Note** : рецептите за всяка напитка са в началото на условието
``` java
package bg.fmi.mjt.lab.coffee_machine.supplies;

public interface Beverage {
	/**
	* Returns the name of the beverage
	* Espresso,Cappuccino or Mochaccino
	*/
	public String getName();
	
	/**
	* Returns the quantity of milk (in milliliters) that the beverage 
	* requires in order to be made
	*/
	public double getMilk();
	
	/**
	* Returns the quantity of coffee (in grams) that the beverage 
	* requires in order to be made
	*/
	public double getCoffee();
	
	/**
	* Returns the quantity of water (in milliliters) that the beverage 
	* requires in order to be made
	*/
	public double getWater();
	
	/**
	* Returns the quantity of cacao (in grams) that the beverage 
	* requires in order to be made
	*/
	public double getCacao();
} 
``` 
При качването в Grader-a уверете се, че примерните тестове минават успешно (това ви гарантира, че нямате грешка в naming на пакет, клас или метод). Проектът ви трябва да има следната структура:
```bash
src
╷
└─ bg/fmi/mjt/lab/coffee_machine/ 
   └─ CoffeeMachine.java
   ├─ Product.java
   └─ (...)
   └─ container/
      └─ Container.java
      └─ (...)
   └─ supplies/ 
      └─ Beverage.java
      └─ (...)      
```

Качете архив на папката `src`.