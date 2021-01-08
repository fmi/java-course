# HTTP, REST, JSON и HttpClient

## Прогноза за времето :umbrella:

Ще създадем приложение, което ще ни дава прогноза за времето за дадена географска локация. Прогнозата ще получаваме от [Open Weather](https://openweathermap.org/). Като всяка уважаваща себе си съвременна онлайн услуга, Open Weather, освен чрез уеб сайт и мобилно приложение, е достъпна и чрез [REST API](https://openweathermap.org/current), което позволява използването и интеграцията ѝ в произволно друго приложение.

### API-то "под лупа"

Заявките към REST API-то на Open Weather изискват автентикация чрез API key, какъвто ще получим след регистрация [тук](https://home.openweathermap.org/users/sign_up) и който може да намерим в нашия [account](https://home.openweathermap.org/api_keys). Ще трябва да подаваме този API key като част от пътя в URI-то на всяка заявка.

**Важно!** Въпросният API key е за ваша лична употреба - не го споделяйте или публикувайте. Също, не използвайте Open Weather API-то с ключ, който не е за вашия account.

Трябва да се запознаем с [документацията](https://openweathermap.org/current) на API-то, и по-конкретно с раздела за извличане на прогноза за времето по име на град.

Например, чрез следното извикване, ще получим прогнозата за времето в София, с human-readable описание на български:

```
http://api.openweathermap.org/data/2.5/weather?q=Sofia&units=metric&lang=bg&appid=<your_api_key>
```

За да "дешифрираме" този URL,

1. api.openweathermap.org -- това е хостът на REST API-то
2. /data/2.5/weather -- метод на API-то, който извикваме, представен като път
3. параметри -- градът, за който се интересуваме, метрична система за мерните единици, български език за описанието и нашият API key

Ако отворим в браузър

```
http://api.openweathermap.org/data/2.5/weather?q=Sofia&units=metric&lang=bg&appid=<your_api_key>
```

ще видим прогнозата за времето в София, в JSON формат. Необходимо е разбира се да замените `<your_api_key>` с вашия (като се погрижите да остане за ваша лична употреба) и в случай че името на града съдържа интервали, да съобразите, че в URL по спецификация те се заместват със символа %20.

```
http://api.openweathermap.org/data/2.5/weather?q=Стара%20Загора&units=metric&lang=bg&appid=<your_api_key>
```

### Java модел на данните

За да "преведем" JSON резултата в Java обекти, с които ще ни е по-лесно да работим, трябва да моделираме данните, които ни връща API-то, в подходящи Java класове. Ще създадем клас `WeatherForecast` в пакета `bg.sofia.uni.fmi.mjt.weather.dto`, като за простота ще се интересуваме само от следните два атрибута на прогнозата:

  - `weather`, който представлява масив от метеорологични условия. Дадено метеорологично условие ще моделираме с класа `WeatherCondition`. Една и съща локация (град) може да се характеризира с различни метеорологични условия, като първото в JSON отговора е основното (primary). За простота, ще се интересуваме само от един атрибут на метеорологичното условие: `description`

  - `main`, който съдържа основните метеорологични данни. Ще ги моделираме с класа `WeatherData`, като от атрибутите ще ни интересуват температурата `temp` и като колко градуса се усеща, `feels_like`

:warning: Трите гореописани Data Transfer Object (DTO) класа трябва да имат канонични конструктори (т.е. такива със списък параметри, отговарящ на списъка член-данни на класа, в реда, в който се срещат в JSON отговора).

:warning: Не използвайте records за моделиране на DTO-тата - причината е, че Gson понастоящем не поддържа records.

### Weather Forecast клиента

Създайте следния клас в пакета `bg.sofia.uni.fmi.mjt.weather`и довършете имплементацията му:

```java
package bg.sofia.uni.fmi.mjt.weather;

public class WeatherForecastClient {

    public WeatherForecastClient(HttpClient weatherHttpClient) {

    }

    /**
     * Fetches the weather forecast for the specified city.
     * 
     * @return the forecast
     * @throws LocationNotFoundException if the city is not found
     * @throws WeatherForecastClientException if information regarding the weather for this location could not be retrieved
     */
    public WeatherForecast getForecast(String city) throws WeatherForecastClientException {
        throw new UnsupportedOperationException();
    }

}
```
:warning: **Важно:** `LocationNotFoundException` е наследник на `WeatherForecastClientException` и затова не присъства в сигнатурата на метода. Приемаме, че една локация не може да бъде намерена, ако REST API-то върне отговор със статус код 404 за нея.

### Тестване

Валидирайте решението си като използвате *Mockito* за тестване - работата на клиента ни зависи от външен модул (в случая, HTTP клиентът, който вика REST API-то на Open Weather) и използването на *Mockito* е подходящо. Приемаме, че REST API-то работи коректно и искаме да тестваме в изолация нашия код.

Стремете се да достигнете **code coverage от поне 60%**.

:point_right: Примерни тестове за подобна задача може да намерите [тук](https://github.com/fmi/java-course/blob/mjt-2018-2019/10-http-rest/lab/solution/test/bg/sofia/uni/fmi/mjt/meetup/MeetupClientTest.java).

### Структура на проекта

```bash
src
╷
├─ bg/sofia/uni/fmi/mjt/weather/
|  ├─ dto 
|  |   ├─ WeatherCondition.java
|  |   ├─ WeatherData.java
|  |   └─ WeatherForecast.java
|  ├─ exceptions
|  |   ├─ LocationNotFoundException.java
|  |   └─ WeatherForecastClientException.java
|  └─ WeatherForecastClient.java
|
test
├─ bg/sofia/uni/fmi/mjt/weather/
|  └─ WeatherForecastClientTest.java
|
lib
├─ gson-x.x.x.jar
├─ mockito-core-x.xx.x.jar
└─ ...
```

### Dependencies

```bash
# gson
wget https://repo1.maven.org/maven2/com/google/code/gson/gson/2.8.6/gson-2.8.6.jar

# mockito
wget https://repo1.maven.org/maven2/org/mockito/mockito-core/3.1.0/mockito-core-3.1.0.jar
wget https://repo1.maven.org/maven2/net/bytebuddy/byte-buddy-agent/1.9.10/byte-buddy-agent-1.9.10.jar
wget https://repo1.maven.org/maven2/net/bytebuddy/byte-buddy/1.9.10/byte-buddy-1.9.10.jar
wget https://repo1.maven.org/maven2/org/objenesis/objenesis/2.6/objenesis-2.6.jar
```

### Добавяне на решение

В grader.sapera.org качете `.zip` архив на `src` и `test` директориите на проекта ви.
