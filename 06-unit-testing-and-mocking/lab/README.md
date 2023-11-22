# Unit Testing and Mocking

Освен писане на code from scratch, в практиката много често се налага и да поддържаме, fix-ваме или пишем тестове за вече съществуващ код.

Целта на упражнението днес е, да създадете и изпълните JUnit тестове спрямо налична имплементация.

## Intelligent Home :bulb:

Умните устройства в днешно време придават модерна визия на дома. Разполагаме със система за съхранение на информация за *умни* устройства, инсталирани в даден дом, наречена `IntellgentHome`.
Тя предоставя възможност за:
- регистриране и изтриване на *умни* устройства
- извличане и сортиране на *умни* устройства по определен критерий

За съжаление, системата все още няма написани тестове и в имплементацията ѝ се крият някои **бъгове**. Ще трябва да ги откриете и отстраните в процеса на тестване. За да бъде той ефективен, първо напишете тест за някой сценарий, след това оправете бъга, който сте намерили с него. Също така, в кода има **известни отстъпления от Clean Code принципите**, които също ще трябва да коригирате.

**Имплементацията може да бъде намерена в директорията [resources](./resources).**

### Основни класове и тяхната функционалност

#### IoTDevice

Всички устройства, които могат да се инсталират в нашия дом, имплементират интерфейса `IoTDevice`. Има три основни имплементации:

- :speaker: `AmazonAlexa`
- :bulb: `RgbBulb`
- :sunny: `WiFiThermostat`

Те трябва да имплементират интерфейса `IoTDevice`:

```java
package bg.sofia.uni.fmi.mjt.intelligenthome.device;

public interface IoTDevice {

    /**
     * Returns the ID of the device.
     */
    String getId();

    /**
     * Returns the name of the device.
     */
    String getName();

    /**
     * Returns the power consumption of the device.
     * For example, a lamp may consume 1kW/hour.
     */
    double getPowerConsumption();

    /**
     * Returns the date and time of device installation.
     * This is a time in the past when the device was 'physically' installed.
     * It is not related to the time when the device is registered in the Hub.
     */
    LocalDateTime getInstallationDateTime();

    /**
     * Returns the type of the device.
     */
    DeviceType getType();

}
```

Всяко устройство трябва да има задължителен констуктор с параметри `(String name, double powerConsumption, LocalDateTime installationDateTime)`

#### DeviceType

Типът на устройството е представен с `enum` със стойности `SMART_SPEAKER`, `BULB` и `THERMOSTAT`.

```java
package bg.sofia.uni.fmi.mjt.intelligenthome.enums;

public enum DeviceType {

    SMART_SPEAKER("SPKR"),
    BULB("BLB"),
    THERMOSTAT("TMST");

    private final String shortName;

    private DeviceType(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

}
```

#### ID

ID-то на устройствата се конструира по следния начин:

**\<short name of device type\>-\<device name\>-\<unique number per device type\>**, където `number` е число, което се увеличава с 1 (започвайки от 0) при всяко създаване на устройство **от който и да е тип**. Например, ID на *WiFi* термостат би могло да бъде `TMST-livingroom-7` и не може да съществува друго устройство от какъвто и да е тип с ID **\<short name of device type\>-\<device name\>-7**

Две устройства се считат за еднакви, когато ID-тата им съвпадат.

### IntelligentHomeCenter

Основната логика на системата се съдържа в класа`IntelligentHomeCenter`, чиято имплементация може да разгледате подробно в конкретния файл, където се намира класа му.

```java
package bg.sofia.uni.fmi.mjt.intelligenthome;

import bg.sofia.uni.fmi.mjt.intelligenthome.device.IoTDevice;

public class IntelligentHomeCenter {

    /**
     * Adds a @device to the IntelligentHomeCenter.
     *
     * @throws IllegalArgumentException in case @device is null.
     * @throws DeviceAlreadyRegisteredException in case the @device is already registered.
     */
    public void register(IoTDevice device) throws DeviceAlreadyRegisteredException {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the @device from the IntelligentHomeCenter.
     *
     * @throws IllegalArgumentException in case null is passed.
     * @throws DeviceNotFoundException in case the @device is not found.
     */
    public void unregister(IoTDevice device) throws DeviceNotFoundException {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a IoTDevice with an ID @id.
     *
     * @throws IllegalArgumentException in case @id is null, empty or blank.
     * @throws DeviceNotFoundException in case device with ID @id is not found.
     */
    public IoTDevice getDeviceById(String id) throws DeviceNotFoundException {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the total number of devices with type @type registered in IntelligentHomeCenter.
     *
     * @throws IllegalArgumentException in case @type is null.
     */
    public int getDeviceQuantityPerType(DeviceType type) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a collection of IDs of the top @n devices which consumed
     * the most power from the time of their installation until now.
     *
     * The total power consumption of a device is calculated by the hours elapsed
     * between the two LocalDateTime-s: the installation time and the current time (now)
     * multiplied by the stated nominal hourly power consumption of the device.
     *
     * If @n exceeds the total number of devices, return all devices available sorted by the given criterion.
     * @throws IllegalArgumentException in case @n is a negative number.
     */
    public Collection<String> getTopNDevicesByPowerConsumption(int n) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a collection of the first @n registered devices, i.e the first @n that were added
     * in the IntelligentHomeCenter (registration != installation).
     *
     * If @n exceeds the total number of devices, return all devices available sorted by the given criterion.
     *
     * @throws IllegalArgumentException in case @n is a negative number.
     */
    public Collection<IoTDevice> getFirstNDevicesByRegistration(int n) {
        throw new UnsupportedOperationException();
    }

}
```

### DeviceStorage

`DeviceStorage` е интерфейс, който се използва в `IntelligentHomeCenter` за съхранение на устройствата. В момента има една проста имплементация базирана на Map - `MapDeviceStorage`.
Когато тествате `IntelligentHomeCenter` може да използвате Mockito и да мокнете `DeviceStorage` - в него не сме скрили никакви бъгове така или иначе и няма нужда да го тествате (дори и имплицитно през тестовете на `IntelligentHomeCenter`.

## Пакети

Спазвайте имената на пакетите на всички по-горе описани класове, тъй като в противен случай решението ви няма да може да бъде тествано от грейдъра.

```
src
└── bg.sofia.uni.fmi.mjt.intelligenthome
     ├── device
     │     ├── AmazonAlexa.java
     │     ├── DeviceType.java
     │     ├── IoTDevice.java
     │     ├── IoTDeviceBase.java
     │     ├── RgbBulb.java
     │     └── WiFiThermostat.java
     ├── hub
     │     ├── IntelligentHomeCenter.java
     │     ├── comparator
     │     │      ├── KWhComparator.java
     │     │      └── RegistrationComparator.java
     │     └── exceptions
     │            ├── DeviceAlreadyRegisteredException.java
     │            └── DeviceNotFoundException.java
     └── storage
           ├── DeviceStorage.java
           └── MapDeviceStorage.java
test
└── bg.sofia.uni.fmi.mjt.intelligenthome
     └── (...)
```

## Забележки

В грейдъра качете общ .zip архив на двете директории `src` и `test`.

Успех и не се притеснявайте да задавате въпроси! :star: 
