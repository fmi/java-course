# Структури от данни в Java (Collections)

## Smart City Hub

Ще създадем глобална система за съхранение на информация за всички *умни* устройства, инсталирани в даден град, наречена `SmartCityHub`.

Тя ще предоставя възможност за:
- регистриране и изтриване на *умни* устройства
- извличане и сортиране на *умни* устройства по определен критерий

### SmartDevice

Устройствата, които ще могат да се инсталират в градовете, са:

- :traffic_light: `SmartTrafficLight`
- :bulb: `SmartLamp`
- :video_camera: `SmartCamera`

Те трябва да имплементират интерфейса `SmartDevice`:

```java
package bg.sofia.uni.fmi.mjt.smartcity.device;

public interface SmartDevice {
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

**Бележки**
- Всяко устройство трябва да има задължителен констуктор с параметри:\
`(String name, double powerConsumption, LocalDateTime installationDateTime)`
- Две устройства се считат за еднакви, ако ID-тата им съвпадат

#### Device Type

Типовете устройства представляват `enum` със стойности `TRAFFIC_LIGHT`, `LAMP` и `CAMERA`.

```java
package bg.sofia.uni.fmi.mjt.smartcity.enums;

public enum DeviceType {
    TRAFFIC_LIGHT("TFL"),
    LAMP("LM"),
    CAMERA("CM");

    private final String shortName;

    private DeviceType(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }
}
```

#### Device ID

ID-то на всяко устройство се конструира по следния начин:\
`<short name of device type>-<device name>-<unique number per device type>`, където:
- `number` е число, което се увеличава с 1 (започвайки от 0) при всяко създаване на устройство **от конкретния тип**.\
Например, ID на умен светофар би могло да бъде `TFL-eaglesbridge-7`

### SmartCityHub

Създайте клас `SmartCityHub`:

```java
package bg.sofia.uni.fmi.mjt.smartcity.hub;

public class SmartCityHub {

    public SmartCityHub() {

    }

    /**
     * Adds a @device to the SmartCityHub.
     *
     * @throws IllegalArgumentException in case @device is null.
     * @throws DeviceAlreadyRegisteredException in case the @device is already registered.
     */
    public void register(SmartDevice device) throws DeviceAlreadyRegisteredException {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the @device from the SmartCityHub.
     *
     * @throws IllegalArgumentException in case null is passed.
     * @throws DeviceNotFoundException in case the @device is not found.
     */
    public void unregister(SmartDevice device) throws DeviceNotFoundException {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a SmartDevice with an ID @id.
     *
     * @throws IllegalArgumentException in case @id is null.
     * @throws DeviceNotFoundException in case device with ID @id is not found.
     */
    public SmartDevice getDeviceById(String id) throws DeviceNotFoundException {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the total number of devices with type @type registered in SmartCityHub.
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
     * in the SmartCityHub (registration != installation).
     * 
     * If @n exceeds the total number of devices, return all devices available sorted by the given criterion.
     *
     * @throws IllegalArgumentException in case @n is a negative number.
     */
    public Collection<SmartDevice> getFirstNDevicesByRegistration(int n) {
        throw new UnsupportedOperationException();
    }
}
```
**Бележки**
- За изчисляване на изминало време между двa `LocalDateTime` обекта, използвайте класа `java.time.Duration` и по-конкретно метода `java.time.Duration.between(...)`

Например:

```java
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

public class DurationSnippet {
    public static void main(String... args) {
        LocalDateTime first = LocalDateTime.of(2019, Month.NOVEMBER, 1, 9, 0); // 2019-11-01T09:00
        LocalDateTime second = LocalDateTime.of(2019, Month.NOVEMBER, 1, 11, 0); // 2019-11-01T11:00
        System.out.println(Duration.between(first, second).toHours()); // 2
    }
}
```

## Бързодействие

Обърнете внимание, че в един град може да има хиляди и дори милиони инсталирани устройства. Вашето решение трябва ефективно да се справя с обработване на голямо количество данни.

## Пакети

Спазвайте имената на пакетите на всички по-горе описани класове, тъй като в противен случай решението ви няма да може да бъде тествано от грейдъра.

```bash
src
╷
└─ bg/sofia/uni/fmi/mjt/smartcity/hub/
   └─ SmartCityHub.java
   └─ DeviceAlreadyRegisteredException.java
   └─ DeviceNotFoundException.java
   └─ (...)
   bg/sofia/uni/fmi/mjt/smartcity/device/
    └─ SmartDevice.java
    └─ SmartTrafficLight.java
    └─ SmartLamp.java
    └─ SmartCamera.java
    └─ (...)
   bg/sofia/uni/fmi/mjt/smartcity/enums/
      └─ DeviceType.java
      └─ (...)
```

## Забележки

За да тествате решението си в [grader.sapera.org](http://grader.sapera.org), архивирайте в `zip` цялата `src` папка на проекта.
