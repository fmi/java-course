package sealed;

public sealed interface Exercise permits GymWorkout, Jogging {

    int getCaloriesBurnt();

}
