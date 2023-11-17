package bg.sofia.uni.fmi.mjt.gym.workout;

import java.util.SequencedCollection;

public record Workout(SequencedCollection<Exercise> exercises) {
    public boolean containsExercise(String exerciseName) {
        for (var exercise : exercises) {
            if (exercise.name().equals(exerciseName)) {
                return true;
            }
        }
        return false;
    }
}
