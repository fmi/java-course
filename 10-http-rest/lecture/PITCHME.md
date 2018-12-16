## HTTP, REST, JSON и `HttpClient`

_18.12.2018_

---

#### Днес ще разгледаме:

@ul

- HTTP
- REST
- JSON
- java.net.http.HttpClient

@ulend

---

## REST

---

#### Representational State Transfer (REST)

@ul

- REST е стил софтуерна архитектура за реализация на уеб услуги.
  - REST не е спецификация или протокол
- Състои се от 6 принципа, които допринасят към това уеб услугата да бъде проста, бързодействаща и скалируема.

@ulend

---

#### REST (2)

@ul

- Има за цел да подобри и улесни комуникацията между две системи.
- Уеб услугите, които покриват REST принципите, се наричат RESTful уеб услуги.
- WWW е RESTful

@ulend

---

#### Принципи на REST архитектурата

@ul

- REST е клиент-сървър архитектура
- REST предоставя единен интерфейс (контракт)
- REST не пази състояние (stateless)
- REST предлага възможности за кеширане
- REST е многослойна система\*
- REST предоставя code on demand\*

@ulend

---

#### REST is client-server architecture

@ul

- Клиентът и сървърът имат различни задачи.
- Сървърът най-често съдържа бизнес логиката и се грижи за съхранението на данните
- Клиентът най-често извлича данни от сървъра и ги визуализира по даден начин:
  - уеб клиенти - браузъри
  - конзолни клиенти -  например kubectl, gcloud
  - ...

@ulend

---

#### REST is client-server architecture (2)

@ul

- Единният интерфейс позволява разделянето на клиент и сървър.
- Клиентът и сървърът могат да бъдат променяни независимо един от друг, стига това да не променя единния интерфейс помежду им

@ulend

---

#### Пример за клиент-сървър

@ul

- Дa предположим, че искаме да извлечем всички vm инстанции от GCP. REST API документация за въпросната операция - [link](https://cloud.google.com/compute/docs/reference/rest/v1/instances/list).
- Можем да го направим през:
  - уеб клиент - @size[1.0em](https://console.cloud.google.com/compute/instances?project={project-name})
  - конзолен клиент - $ gcloud compute instances list
  - Java клиент - [jclouds](http://jclouds.apache.org/)
  - ...

@ulend

---

#### Ресурси

@ul

- REST въвежда концепцията за ресурс.
- Всяка информация, която може да бъде именувана, може да бъде ресурс.
- Ние, като програмисти, дефинираме кои са ресурсите, с които ще оперира нашата уеб услуга.
- В примера, който разгледахме, ресурс бяха vm инстанциите. 
- Ресурси могат да бъдат html страници, js файлове, изображения, потребители и др.

@ulend

---

#### Идентификатори на ресурси

- Всеки ресурс се индентифицира чрез URI (unified resource identifier)
- Примери:

```bash
# Get all repos in fmi organisation
$ curl https://api.github.com/orgs/fmi/repos

# Get fmi/java-course repo
$ curl https://api.github.com/repos/fmi/java-course
```

---

#### Представяне на ресурси

@ul

- Ресурсите са концептуално разделени от представянията, които се връщат към клиентите.
- Например сървър пази ресурсите си в база от данни и на файловата система, а връща на клиентите ресурси под формата на HTML, JSON и XML.
- Не винаги върнатото представяне е вътрешното представяне.

@ulend

---

#### REST provides a uniform interface

- REST предоставя единен интерфейс (контракт) между клиента и сървъра.
- Контрактът се изгражда въз основа на ресурсите.
- [Github REST API](https://developer.github.com/v3/)

```bash
# Github API does not support text/html
# instead use application/json
$ curl --header "Accept: text/html" \
  https://api.github.com/repos/fmi/java-course

# repozzz resource does not exist
$ curl https://api.github.com/repozzz/fmi/java-course
```

---

#### REST is stateless

- Комуникацията между клиента и сървъра винаги съдържа цялата информация, която е нужна за осъществяване на заявката.
- Сесията се пази client side.
- Ако даден ресурс е защитен, клиентът трябва да се автентицира с всяка заявка.

```bash
# Fetch you user info
$ curl -u <your_username> https://api.github.com/user
```

---

#### REST is ...

@ul

- cacheable
  - Клиентът, сървърът или междинен компонент могат да кешират ресурси, за да подобрят бързодействието.
- a layered system
  - Обикновено клиентът не знае дали е свързан с крайния сървър или със сървър-посредник
  - Сървърите-посредници подобряват ефективността.

@ulend

---

#### REST is language-independent

@ul

- Голяма част от езиците предлагат инструменти за създаване на RESTful уеб услуги:
  - Java EE - JAX-RS е спецификацията, [apache/cxf](https://github.com/apache/cxf) и [jersey/jersey](https://github.com/jersey/jersey)* са едни от най-популярните имплементации
  - Spring - Spring MVC, част от [spring-framework](https://github.com/spring-projects/spring-framework)
  - Ruby - [sinatra](https://github.com/sinatra/sinatra) или [rails](https://github.com/rails/rails)
  - Go - net/http + [gorilla/mux](https://github.com/gorilla/mux)
  - ...

@ulend

---

## JSON

---

#### История на форматите за обмен на данни

@ul

- Remote procedure call (RPC), RMI - RPC протоколи като JRMP, XML-RPC и други.
  - популярни през 90-те 
- Уеб услуги, базирани на SOAP - само XML
  - набират популярност от 1999
- Уеб услуги, базирани на REST - HTML, XML, JSON, YAML и други.

@ulend

---

#### JavaScript Object Notation (JSON)

@ul

- един от най-популярните формати за обмен на данни
- произлиза от JavaScript
- използва се за предаване на структурирани данни по мрежова комуникация
- намира широко приложение в RESTful уеб приложенията за предоставяне на данни

@ulend

---

#### JSON - характеристики

@ul

- web friendly - тривиално парсване (за разлика от XML)
- кратък и интуитивен
- разбираема едновременно от човек и машина
- езиково независима спецификация
- почти всички езици поддържат JSON

@ulend

---

#### Пример

```bash
$ curl https://api.github.com/users/kelseyhightower
```

```json
{
  "login": "kelseyhightower",
  "name": "Kelsey Hightower",
  "company": "Google, Inc",
  "location": "Portland, OR",
  "email": null,
  "hireable": true,
  "followers": 10041,
  "following": 13
}
```

---

#### Типове данни

@ul

- Boolean - `true` / `false`
- Number - `42`, `3.14156`, `-1`
- String - `"foo"`, `"bar"`
- Array - `["John", "Anna", "Peter"]`
- Object - `{"name": "John", "age": 30}`
- Поддържа `null`, `{}`, но не и функция, дата или `undefined`
@ulend

---

#### Пример (2)

```bash
$ curl https://api.github.com/repos/kubernetes/kubernetes/tags
```

```json
[
  {
    "name": "v1.14.0-alpha.0",
    "commit": {
      "sha": "1f56cd801e795fd063ec3e61fe4f6fa8841f4222"
    }
  },
  {
    "name": "v1.13.2-beta.0",
    "commit": {
      "sha": "efe48f3cd6436737d37fd2fcd6beb9e2328f7cce"
    }
  }
]
```

---

#### Java библиотеки за работа с JSON


@ul

- [google/gson](https://github.com/google/gson)
- [FasterXML/jackson](https://github.com/FasterXML/jackson)
- ...

@ulend

---

#### `google/gson`

@ul

- Предоставя лесен механизъм за преобразуване от Java обект към JSON и обратно
    - .toJson() и .fromJson()
- Не изисква добавяне на анотации или прекомпилиране
- Позволява вмъкването на custom serializer/deserializer
- Комплексен Builder
- [Gson User Guide](https://github.com/google/gson/blob/master/UserGuide.md)

@ulend

---

#### `.toJson(Object src)`

```java
public class Developer {
    private String name;
    private int age;
    private transient String company;

    public Developer(String name, int age, String company) {
        this.name = name;
        this.age = age;
        this.company = company;
    }
}
```

```java
Developer dev = new Developer("Kelsey", 28, "Google, Inc");

Gson gson = new Gson();
String json = gson.toJson(dev);

// {"name":"Kelsey","age":28}
System.out.println(json);
```

---

#### `.fromJson(String json, Class<T> classOfT)`

```java
String json = "{\"name\": \"Wesley\", \"age\": 20 }";

Gson gson = new Gson();
Developer dev = gson.fromJson(json, Developer.class);

// Wesley, 20 years old, null
System.out.printf("%s, %d years old, %s%n",
    dev.getName(), dev.getAge(), dev.getCompany());
```

---

#### `.fromJson(Reader json, Class<T> classOfT)`

```java
Gson gson = new Gson();

FileReader reader = new FileReader(new File("devs.json"));
// unchecked conversion
List<Developer> devs = gson.fromJson(reader, List.class);
```

---

#### `.fromJson(String json, Type typeOfT)`

```java
List<Developer> devs = List.of(
        new Developer("Kelsey", 28, "Google, Inc"),
        new Developer("Wesley", 20, "Google, Inc"));

Gson gson = new Gson();
String json = gson.toJson(devs);

Type type = new TypeToken<List<Developer>>(){}.getType();
List<Developer> devsAgain = gson.fromJson(json, type);

System.out.println(devsAgain.size()); // 2
```

---



