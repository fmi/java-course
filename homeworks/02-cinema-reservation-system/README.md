# Домашно #2

## Cinema Reservation System

`Краен срок: 18.11.2017, 23:45`

Да се имплементира система за резервиране на билети за кино, която има следната функционалност:

* запазване на билет за определена прожекция
* отмяна на резервация за определена прожекция
* извеждане на броя свободни места за определна прожекция
* сортиране по жанр на всички филми, които предлага системата

Създайте клас `CinemaCity`, който имплементира следния интерфейс:

```java
public interface CinemaReservationSystem {

    /**
     * Books a ticket for a particular projection
     * 
     * @param ticket
     *            The ticket that we want to book
     * @throws AlreadyReservedException
     *             If the same ticket was already reserved
     * @throws ProjectionNotFoundException
     *             If the ticket's corresponding projection is not in the system
     * @throws InvalidSeatException
     *             If there is no such seat in the hall for this projection
     * @throws ExpiredProjectionException
     *             If the projection is already expired, i.e if the projection's day
     *             passed.
     */
    public void bookTicket(Ticket ticket) throws AlreadyReservedException, ProjectionNotFoundException,
            InvalidSeatException, ExpiredProjectionException;

    /**
     * Cancels a reservation for a particular projection
     * 
     * @param ticket
     *            The ticket that we want to cancel
     * @throws ReservationNotFoundException
     *             If the ticket is not found in the system
     * @throws ProjectionNotFoundException
     *             If the projection is not found in the system
     */
    public void cancelTicket(Ticket ticket) throws ReservationNotFoundException, ProjectionNotFoundException;

    /**
     * Gets the number of free seats for a particular projection
     * 
     * @param projection
     *            The projection for which we want to get the free seats
     * @return the number of free seats for a particular projection
     * @throws ProjectionNotFoundException
     *             if the projection is not found in the system
     */
    public int getFreeSeats(Projection projection) throws ProjectionNotFoundException;

    /**
     * Returns a collection of movies sorted by their genre in alphabetic order.
     * 
     * @return Collection of movies sorted by their genre in alphabetic order.
     */
    public Collection<Movie> getSortedMoviesByGenre();

}
```
и конструктор :
```java
public CinemaCity(Map<Movie, List<Projection>> cinemaProgram)
```

Класът `Ticket` дава информация за конкретна резервация на билет за определена прожекция като трябва да имплементира следния конструктор:
```java
public Ticket(Projection projection, Seat seat, String owner)
```
* *projection* - прожекцията, за която е резервацията
* *seat* - конктретно място от залата, което сме резервирали
* *owner* - името на човека, който е направил резервацията

Класът `Projection` дава информация за конкретна прожекция на даден филм. Той трябва да имплементира следния конструктор:
```java
public Projection(Movie movie, Hall hall, LocalDateTime date)
```
* *movie* - филмът, който ще се излъчва на конкретната прожекция
* *hall* - залата, в която се провежда прожекцията
* *date* - датата на прожекцията

Класът `Seat` представлява конкретно място от залата, в която се провежда определена прожекция. Той трябва да имплементира следния конструктор:
```java
public Seat(int row, int seat)
```
* *row* - редът, на който се намира мястото (започва от 1)
* seat - конкретното място от реда (започва от 1)

Класът `Hall` дава информация за залата, в която се провежда определена прожекция. Той трябва да имплементира следния конструктор:
```java
public Hall(int number, int rows, int rowSeats)
```
* *number* - номер на залата
* *rows* - брой на редовете в залата
* *rowSeats* - брой на местата на всеки ред

Класът `Movie` дава информация за конкретен филм. Той трябва да имплементира следния конструктор:
```java
public Movie(String name, int duration, MovieGenre genre)
```
* *name* - името на филма
* *duration* - дължината на филма в минути
* *genre* - жанр на филма

`МоvieGenre` е `enum` със следните стойности:
* *ACTION*
* *ADVENTURE*
* *COMEDY*
* *FANTASY*
* *HORROR*
* *THRILLER*
* *DRAMA*

### Забележки

* Класовете `Projection`, `Movie`, `Ticket`, `Hall` и `Seat` трябва да се намират в пакет с име `bg.uni.sofia.fmi.mjt.cinema.reservation.system.core`

* Класът `CinemaCity`, както и интерфейсът `CinemaReservationSystem` трябва да се намират в пакет с име `bg.uni.sofia.fmi.mjt.cinema.reservation.system`

* Всички изключения трябва се намират в пакет с име `bg.uni.sofia.fmi.mjt.cinema.reservation.system.exceptions`

* Всички останали класове и интерфейси, различни от гореспоменатите, може да сложите в пакети по ваш избор.

### Оценяване

Решението може да ви донесе до 100 точки, като ще бъде оценявано:

* за функционална пълнота и коректност: чрез автоматични тестове (90% от оценката)
* за добър обектно-ориентиран дизайн (10% от оценката)
