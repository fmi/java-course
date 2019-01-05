# Meetup Client

Една от най-популярните онлайн услуги за организиране на събития е [meetup.com](https://www.meetup.com/), с над 35 милиона потребители към днешна дата. Като всяка уважаваща се съвременна онлайн услуга, meetup, освен чрез уеб сайт и мобилно приложение, е достъпна и чрез публично [REST API](https://secure.meetup.com/meetup_api), което позволява използването и интеграцията ѝ в произволно друго приложение.

### Условие

Ще напишем Java клиент, чрез който програмно ще може да търсим събития по различни критерии, както и да търсим и сваляме снимки.

### API-то "под лупа"

Според [документацията](https://secure.meetup.com/meetup_api), най-новата версия на REST API-то e v3. Ще "консумираме" нея. 

Повечето заявки към REST API-то изискват [автентикация](https://secure.meetup.com/meetup_api/auth/), като ще ползваме най-простия метод за автентикация, чрез т.нар. API key. API key представлява уникален за даден потребител низ, който, след като се [регистрирате](https://secure.meetup.com/register/) в meeting.com, може да получите от [тук](https://secure.meetup.com/meetup_api/key/). Ще трябва да подаваме този низ като параметър с име `key` в URL-то на всяка заявка, изискваща автентикация.

**Важно!** API ключът е за ваша лична употреба - не го споделяйте или публикувайте. Също, не използвайте meetup.com API-то с ключ, който не е за вашия account.

Например, чрез следното извикване към API-то, може да получим списък на всички регистрирани групи в meetup.com в близост до нашата локация (според каквато сме указали при регистрацията си), които са категоризирани като `tech` (technology), сортирани по брой членове:

```
https://api.meetup.com/find/groups?category=34&order=members&key=your_api_key
```

За да "дешифрираме" този URL,

1. api.meetup.com -- това е хостът на REST API-то
2. /find/groups -- метода, който извикваме, представен като път
3. параметри -- категория (34 е кодът на `tech`), атрибут, по който да са подредени резултатите и ключ (не забравяте да смените `your_api_key` с вашия ключ)

### Търсене на събития

Понеже в нашия клиент ще се ограничим до търсене на събития по определен критерий, трябва да се запознаем със [съответната част](https://www.meetup.com/meetup_api/docs/:urlname/events/#list) от документацията, като се интересуваме от метода `/find/events`.
Ако отворим в браузър https://api.meetup.com/find/events?key=your_api_key, ще видим списък на всички събития в близост до нашата локация, в JSON формат.

Meetup.com предоставя и [API конзола](https://secure.meetup.com/meetup_api/console/?path=/:urlname/events), с която може да тестваме в браузъра различни заявки и да разглеждаме в по-четим вид JSON отговора.

### Снимки на събития и групи

Към събитията и групите от потребители в meetup.com може да се качват снимки, които са организирани в албуми. Съответно, може да извлечем снимките от даден албум. Документацията на API-то за извличане на снимки от албум на дадена група е [тук](https://www.meetup.com/meetup_api/docs/:urlname/photo_albums/:album_id/photos/#list).

Чрез следната заявка например може да извлечем списък със снимките от албум с ID `29473987` на meetup група с име `Cloud-Native-Computing-Bulgaria`:

```
https://api.meetup.com/Cloud-Native-Computing-Bulgaria/photo_albums/29473987/photos?key=your_api_key
```

Самите снимки могат да бъдат достъпени на (и свалени от) URL, указан в атрибута `photo_link`.

### Java модел на данните

За да "преведем" JSON резултата в Java обекти, с които ще ни е по-лесно да работим, трябва да моделираме данните, които ни връща API-то, в подходящи Java класове. Тъй като се интересуваме от събитията, ще създадем клас `Event` в пакета `bg.sofia.uni.fmi.mjt.meetup.dto`, като за простота ще се интересуваме само от следните атрибути на събитието:

  - id
  - name
  - status
  - duration
  - venue
  - description

Тъй като забелязваме в JSON отговора, че `venue` също е обект, ще го моделираме чрез клас `Venue`, отново в пакета `bg.sofia.uni.fmi.mjt.meetup.dto`, като за местоположението на събитието ще ни интересуват:

  - name
  - city

Снимките ще моделираме чрез клас `Photos` в същия пакет, като атрибутите, които ни интересуват, са:

  - id
  - photo_link

### Meetup клиентът

Създайте следния клас и довършете имплементацията му:

```java
package bg.sofia.uni.fmi.mjt.meetup;

public class MeetupClient {

	public MeetupClient(HttpClient client, String apiKey) {

	}

	/**
	 * Fetches the nearby events.
	 * 
	 * @return
	 */
	public List<Event> getEventsNearby() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Fetches the nearby events with the given venue name. The comparison is case
	 * insensitive.
	 * 
	 * @param venueName
	 *            - the event venue name
	 * @return
	 */
	public List<Event> getEventsWithVenueName(String venueName) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Fetches the nearby events whose descriptions contains all of the given
	 * keywords. The comparison is case insensitive.
	 * 
	 * @param keywords
	 *            - the keywords to search in the event description
	 * @return
	 */
	public List<Event> getEventsWithKeywords(Collection<String> keywords) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Fetches the nearby event with max duration. Returns null when no events are
	 * found.
	 * 
	 * @return
	 */
	public Event getEventWithMaxDuration() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Fetches an event by group urlname and event id. Returns null in case of a
	 * missing event.
	 * 
	 * @param urlname
	 *            - the event group urlname
	 * @param id
	 *            - the event id
	 * @return
	 */
	public Event getEvent(String urlname, String id) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Fetches photos for the photo album with the given id. Returns null in case of
	 * a missing photo album.
	 * 
	 * @param urlname
	 *            - the photo album group urlname
	 * @param id
	 *            - the photo album id
	 * @return
	 */
	public List<Photo> getAlbumPhotos(String urlname, String id) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Performs a parallel download of the photos from the given photo album to the
	 * given target directory. A folder with the album id is being created in the
	 * target directory. The photos are downloaded in the newly created album
	 * directory. The file name of each photo is its id.
	 * 
	 * @param urlname
	 *            - the photo album group urlname
	 * @param albumId
	 *            - the photo album id
	 * @param target
	 *            - the target directory to save the photo album
	 * @throws IOException
	 */
	public void downloadPhotoAlbum(String urlname, String albumId, Path target) throws IOException {
		throw new UnsupportedOperationException();
	}
}
```

### Тестване

Може да тествате набързо в `main` метод така създадения клиент. Добре е обаче да напишете и unit тестове.
При тестването на тази задача, е особено удобно да ползваме mocking, защото работата на клиента ни зависи от външен модул (в случая, REST API-то на meetup.com), който приемаме, че работи коректно, а искаме да тестваме в изолация нашия код.

### Структура на проекта

```
src
╷
├─ bg/sofia/uni/fmi/mjt/meetup/
|  ├─ MeetupClient.java
|  └─ dto 
|      ├─ Event.java
|      ├─ Photo.java
|      └─ Venue.java
test
├─ bg/sofia/uni/fmi/mjt/meetup/
|  └─ MeetupClientTest.java
lib
├─ gson-x.x.x.jar
├─ mockito-core-x.xx.x.jar
└─ ...
```

### Dependencies

```bash
wget http://central.maven.org/maven2/com/google/code/gson/gson/2.8.5/gson-2.8.5.jar

# for testing
wget http://central.maven.org/maven2/org/mockito/mockito-core/2.23.0/mockito-core-2.23.0.jar
wget http://central.maven.org/maven2/net/bytebuddy/byte-buddy/1.9.0/byte-buddy-1.9.0.jar
wget http://central.maven.org/maven2/net/bytebuddy/byte-buddy-agent/1.9.0/byte-buddy-agent-1.9.0.jar
wget http://central.maven.org/maven2/org/objenesis/objenesis/2.6/objenesis-2.6.jar
```
