package sealed;

// uncomment the next line in order for Swimming to compile:
//public sealed interface Exercise permits GymWorkout, Jogging, Swimming {
public sealed interface Exercise permits GymWorkout, Jogging {

    int getCaloriesBurnt();

}
