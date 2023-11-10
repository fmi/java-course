# SimCity Mayor :city_sunrise:

Сашко от [Lab 01](https://github.com/fmi/java-course/tree/master/01-intro-to-java/lab/03-broken-keyboard) си оправил клавиатурата и решил да се кандидатира за кмет.

Раздал някое и друго кебапче, и спечелил изборите в града SimCity. 

Задачата тази седмица е да му помогнем в кметските задължения.

SimCity представлява множество от парцели (plots) с предварително зададена площ. 
На всеки парцел можем да строим и разрушаваме постройки, а някои постройки може да насъбират и различни видове сметки за потребления.

От вас се очаква да имплементирате следните класове и интерфейси:

## Plot

В пакета `bg.sofia.uni.fmi.mjt.simcity.plot` създайте публичен клас `Plot`, който има конструктор със следната сигнатура:

```java
public Plot(int buildableArea);
```

и имплементира интерфейса `PlotAPI`: 

```java
package bg.sofia.uni.fmi.mjt.simcity.plot;

import bg.sofia.uni.fmi.mjt.simcity.exception.BuildableAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.simcity.exception.BuildableNotFoundException;
import bg.sofia.uni.fmi.mjt.simcity.exception.InsufficientPlotAreaException;
import bg.sofia.uni.fmi.mjt.simcity.property.buildable.Buildable;

import java.util.Map;

public interface PlotAPI<E extends Buildable> {

    /**
     * Constructs a buildable on the plot.
     *
     * @param address   the address where the buildable should be constructed.
     * @param buildable the buildable that should be constructed on the given address.
     * @throws IllegalArgumentException        if the address is null or blank.
     * @throws IllegalArgumentException        if the buildable is null.
     * @throws BuildableAlreadyExistsException if the address is already occupied on the plot.
     * @throws InsufficientPlotAreaException   if the required area exceeds the remaining plot area.
     */
    void construct(String address, E buildable);

    /**
     * Constructs multiple buildables on the plot.
     * This method ensures that either all operations are successfully completed
     * or no changes are made to the plot's state.
     *
     * @param buildables a Map containing the addresses and corresponding buildable entities.
     * @throws IllegalArgumentException        if the map of buildables is null, empty.
     * @throws BuildableAlreadyExistsException if any of the addresses is already occupied on the plot.
     * @throws InsufficientPlotAreaException   if the combined area of the provided buildables exceeds
     *                                         the remaining plot area.
     */
    void constructAll(Map<String, E> buildables);

    /**
     * Demolishes a buildable from the plot.
     *
     * @param address the address of the buildable which should be demolished.
     * @throws IllegalArgumentException   if the provided address is null or blank.
     * @throws BuildableNotFoundException if buildable with such address does not exist on the plot.
     */
    void demolish(String address);

    /**
     * Demolishes all buildables from the plot.
     */
    void demolishAll();

    /**
     * Retrieves all buildables present on the plot.
     *
     * @return An unmodifiable copy of the buildables present on the plot.
     */
    Map<String, E> getAllBuildables();

    /**
     * Retrieves the remaining buildable area on the plot.
     *
     * @return The remaining buildable area on the plot.
     */
    int getRemainingBuildableArea();

}
```

## Buildings

Всяка сграда трябва да имплементира следните интерфейси:

```java
package bg.sofia.uni.fmi.mjt.simcity.property.billable;

import bg.sofia.uni.fmi.mjt.simcity.property.buildable.Buildable;

public interface Billable extends Buildable {

    /**
     * Retrieves the monthly water consumption of the billable building.
     *
     * @return The monthly water consumption of the building in cubic meters (m³)
     */
    double getWaterConsumption();

    /**
     * Retrieves the monthly electricity consumption of the billable building.
     *
     * @return The monthly electricity consumption of the building in kilowatt-hours (kWh)
     */
    double getElectricityConsumption();

    /**
     * Retrieves the monthly natural gas consumption of the billable building.
     *
     * @return The monthly natural gas consumption of the building in cubic meters (m³)
     */
    double getNaturalGasConsumption();

}
```

```java
package bg.sofia.uni.fmi.mjt.simcity.property.buildable;

public interface Buildable {

    /**
     * Retrieves the type of the building.
     *
     * @return The specific type of the building, represented by a BuildableType.
     * Examples include Residential, Commercial, Industrial, etc.
     */
    BuildableType getType();

    /**
     * Retrieves the total area of the building.
     *
     * @return The total area of the building in square metric units.
     */
    int getArea();

}
```

Всяка сграда има даден тип, моделиран от следния enum:

```java
package bg.sofia.uni.fmi.mjt.simcity.property.buildable;

public enum BuildableType {
    RESIDENTIAL,
    COMMERCIAL,
    INDUSTRIAL,
    INFRASTRUCTURE
}
```

## UtilityService 

В пакета `bg.sofia.uni.fmi.mjt.simcity.utility` създайте клас `UtilityService`, който има публичен конструктор със следната сигнатура:

```java
public UtilityService(Map<UtilityType, Double> taxRates);
```

Map-ът - параметър на конструктора съдържа данъчен процент за всяко потребление. 

За да се получи дължимата сума на дадено потребление, трябва да се направи следната операция: ```taxrate * monthly consumption of utility = monthly cost of a utility to the city```

Нека `UtilityService` имплементира следния интерфейс:

```java
package bg.sofia.uni.fmi.mjt.simcity.utility;

import bg.sofia.uni.fmi.mjt.simcity.property.billable.Billable;

import java.util.Map;

public interface UtilityServiceAPI {

    /**
     * Retrieves the costs of a specific utility for a given billable building.
     *
     * @param utilityType The utility type used for the costs' calculation.
     * @param billable    The billable building for which the utility costs will be calculated.
     * @return The cost of the specified utility for the billable building, rounded up to two decimal places.
     * @throws IllegalArgumentException if the utility or billable is null.
     */
    <T extends Billable> double getUtilityCosts(UtilityType utilityType, T billable);

    /**
     * Calculates the total utility costs for a given billable building.
     *
     * @param billable The billable building for which total utility costs are calculated.
     * @return The total cost of all utilities for the billable building, rounded up to two decimal places.
     * @throws IllegalArgumentException if the billable is null.
     */
    <T extends Billable> double getTotalUtilityCosts(T billable);

    /**
     * Computes the absolute difference in utility costs between two billable buildings for each utility type.
     *
     * @param firstBillable  The first billable building used for the cost comparison.
     * @param secondBillable The second billable building used for the cost comparison.
     * @return An unmodifiable map containing the absolute difference in costs between the buildings for each
     * utility. Each cost difference is rounded up to two decimal places.
     * @throws IllegalArgumentException if any billable is null.
     */
    <T extends Billable> Map<UtilityType, Double> computeCostsDifference(T firstBillable, T secondBillable);

}
```

Всеки плаща различни видове сметки всеки месец. 

За улеснение, ще имате само следните три за постройките - ток, вода и газ. 

За това ви трябва следният enum:

```java
package bg.sofia.uni.fmi.mjt.simcity.utility;

public enum UtilityType {
    WATER,
    ELECTRICITY,
    NATURAL_GAS
}
```

## Подсказки

:point_right: За смислена реализация и локално тестване, ще трябва да си създадете имплементации на някои от описаните интерфейси, но имате свобода да прецените, с колко и какви класове. 

### Пакети

Спазвайте имената на пакетите на всички по-горе описани типове, тъй като в противен случай, решението ви няма да може да бъде автоматично тествано.

```
src
└── bg.sofia.uni.fmi.mjt.simcity
    ├── exception
    │      ├── BuildableAlreadyExistsException.java
    │      ├── BuildableNotFoundException.java
    │      └── InsufficientPlotAreaException.java
    ├── plot
    │      ├── PlotAPI.java
    │      └── Plot.java
    ├── property
    │      ├── billable
    │      │      └── Billable.java
    │      └── buildable
    │             ├── Buildable.java
    │             └── BuildableType.java
    └── utility
           ├── UtilityService.java
           ├── UtilityServiceAPI.java
           └── UtilityType.java
```

### :warning: Забележки

- Не променяйте по никакъв начин интерфейсите, дадени в условието.
- Задачата трябва да се реши с помощта на знанията от курса до момента. Това в частност изключва използването на ламбда изрази и Stream API.
- НЕ очакваме да закръгляте double value-тата
