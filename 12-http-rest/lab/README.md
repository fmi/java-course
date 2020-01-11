# HTTP, REST, JSON и HttpClient

## Прогноза за времето :umbrella:

Ще създадем приложение, което ще ни дава прогноза за времето за дадена географска локация. Прогнозата ще получаваме от [Dark Sky](https://darksky.net/). Като всяка уважаваща се съвременна онлайн услуга, Dark Sky, освен чрез уеб сайт и мобилно приложение, е достъпна и чрез [REST API](https://darksky.net/dev/docs), което позволява използването и интеграцията ѝ в произволно друго приложение.

### API-то "под лупа"

Заявките към REST API-то на Dark Sky изискват автентикация чрез secret key, какъвто ще получим след регистрация [тук](https://darksky.net/dev/register) и който може да намерим в нашия новосъздаден [account](https://darksky.net/dev/account). Ще трябва да подаваме този secret key като част от пътя в URI-то на всяка заявка.

**Важно!** Въпросният secret key, както подсказва и името му, е за ваша лична употреба - не го споделяйте или публикувайте. Също, не използвайте Dark Sky API-то с ключ, който не е за вашия account.

Трябва да се запознаем с [документацията](https://darksky.net/dev/docs) на API-то, и по-конкретно с раздела за извличане на [прогноза за времето](https://darksky.net/dev/docs#forecast-request).

Например, чрез следното извикване, ще получим прогнозата за времето в София, с human-readable описание на български:

```
https://api.darksky.net/forecast/<your_secret_key>/42.6978634,23.3221789?units=si&lang=bg
```

За да "дешифрираме" този URL,

1. api.darksky.net -- това е хостът на REST API-то
2. /forecast/<your_secret_key>/42.6978634,23.3221789 -- метод на API-то, който извикваме, secret key (не забравяте да смените `your_secret_key` с вашия ключ), и [географски координати](https://en.wikipedia.org/wiki/Geographic_coordinate_system) на локацията, която ни интересува - трите, представени като път
3. параметри -- които указват метрична система за мерните единици и български език за описанието

Ако отворим в браузър

```
https://api.darksky.net/forecast/<your_secret_key>/42.6978634,23.3221789?units=si&lang=bg
```

ще видим прогнозата за времето, в JSON формат.

### Java модел на данните

За да "преведем" JSON резултата в Java обекти, с които ще ни е по-лесно да работим, трябва да моделираме данните, които ни връща API-то, в подходящи Java класове. Ще създадем клас `WeatherForecast` в пакета `bg.sofia.uni.fmi.mjt.weather.dto`, като ще се интересуваме само от следните атрибути на прогнозата:

  - latitude
  - longitude
  - timezone
  - currently
  - hourly
  - daily

Тъй като забелязваме в JSON отговора, че `currently`, `hourly` и `daily` са също обекти, ще ги моделираме съответно чрез класовете `DataPoint` и `DataBlock`, отново в пакета `bg.sofia.uni.fmi.mjt.weather.dto`. От `DataPoint` ще ни интересуват:

  - time
  - summary
  - precipProbability
  - temperature

Почасовата и седмичната прогнози ще моделираме с класа `DataBlock`, като атрибутите, които ни интересуват, са:

  - data
  - summary

Създайте `getter` методи за всички полета на тези три класа.

#### Geocoding

Понеже ни е по-удобно да търсим прогноза по локация (населено място), а не по георграфски координати, ще трябва да можем да конвертираме локация в координати - трансформация, известна като [Forward Geocoding](https://en.wikipedia.org/wiki/Geocoding).

За целта, ще ползваме друга уеб услуга, [LocationIQ](https://locationiq.com/), и нейното REST API. Отново ще трябва да се [регистрираме](https://locationiq.com/register) и да получим [token](https://my.locationiq.com/dashboard), с който ще се автентицираме.

Според [документацията](https://locationiq.com/docs#forward-geocoding), може да получим координатите на дадена локация, например Стара Загора, в JSON формат, чрез следната заявка:

```
https://eu1.locationiq.com/v1/search.php?key=your_token&q=Stara%20Zagora&format=json
```

Необходимо е разбира се да замените `your_token` с вашия (като се погрижите да остане за ваша лична употреба) и да съобразите, че в URL по спецификация интервалите се заместват със символа %20%.

Заявката връща масив от резултати от търсенето, сортирани по релевантност (`importance`), и за простота ще се интересуваме само от първия резултат, и по-конкретно от полетата `lat` и `lon`, като ще ги моделираме с класа `Geocode` в пакета `bg.sofia.uni.fmi.mjt.weather.dto`.

### Weather Forecast клиентът

Създайте следния клас в пакета `bg.sofia.uni.fmi.mjt.weather`и довършете имплементацията му:

```java
package bg.sofia.uni.fmi.mjt.weather;

public class WeatherForecastClient {

    public WeatherForecastClient(HttpClient client, String secretKey, String token) {

    }

    /**
     * Fetches the weather forecast for the specified location.
     * 
     * @return the forecast
     */
    public WeatherForecast getForecast(String location) {
        throw new UnsupportedOperationException();
    }

    /**
     * Fetches the current weather for the specified location.
     * 
     * @return the current weather
     */
    public DataPoint getCurrent(String location) {
        throw new UnsupportedOperationException();
    }

    /**
     * Fetches the hourly weather forecast
     * 
     * @return the hourly weather forecast
     */
    public Collection<DataPoint> getHourlyForecast(String location) {
        throw new UnsupportedOperationException();
    }

    /**
     * Fetches the weekly weather forecast
     * 
     * @return the weekly weather forecast
     */
    public Collection<DataPoint> getWeeklyForecast(String location) {
        throw new UnsupportedOperationException();
    }

}
```

### Тестване

Може да тествате набързо в `main` метод така създадения клиент. Добре е обаче да напишете и unit тестове.
При тестването на тази задача, е особено удобно да ползваме mocking, защото работата на клиента ни зависи от външни модули (в случая, REST API-тата на Dark Sky и LocationIQ), които приемаме, че работят коректно, а искаме да тестваме в изолация нашия код.

### Структура на проекта

```bash
src
╷
├─ bg/sofia/uni/fmi/mjt/weather/
|  ├─ WeatherForecastClient.java
|  └─ dto 
|      ├─ DataBlock.java
|      ├─ DataPoint.java
|      ├─ Geocode.java
|      └─ WeatherForecast.java
test
├─ bg/sofia/uni/fmi/mjt/weather/
|  └─ WeatherForecastClientTest.java
lib
├─ gson-x.x.x.jar
├─ mockito-core-x.xx.x.jar
└─ ...
```

### Dependencies

```bash
wget https://repo1.maven.org/maven2/com/google/code/gson/gson/2.8.6/gson-2.8.6.jar

# for testing
wget https://repo1.maven.org/maven2/org/mockito/mockito-core/3.1.0/mockito-core-3.1.0.jar
wget https://repo1.maven.org/maven2/net/bytebuddy/byte-buddy-agent/1.9.10/byte-buddy-agent-1.9.10.jar
wget https://repo1.maven.org/maven2/net/bytebuddy/byte-buddy/1.9.10/byte-buddy-1.9.10.jar
wget https://repo1.maven.org/maven2/org/objenesis/objenesis/2.6/objenesis-2.6.jar
```
