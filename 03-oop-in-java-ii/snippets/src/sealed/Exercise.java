package sealed;

// remove Swimming from the permits clause to break compilation
public sealed interface Exercise permits GymWorkout, Jogging, Swimming {

    int getCaloriesBurnt();

}
