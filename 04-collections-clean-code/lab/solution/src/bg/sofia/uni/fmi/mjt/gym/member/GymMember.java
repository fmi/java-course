package bg.sofia.uni.fmi.mjt.gym.member;

import bg.sofia.uni.fmi.mjt.gym.workout.Exercise;
import bg.sofia.uni.fmi.mjt.gym.workout.Workout;

import java.time.DayOfWeek;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface GymMember {

    /**
     * Returns the member's name.
     */
    String getName();

    /**
     * Returns the member's age.
     */
    int getAge();

    /**
     * Returns the member's id number.
     */
    String getPersonalIdNumber();

    /**
     * Returns the member's gender.
     */
    Gender getGender();

    /**
     * Returns the member's address.
     */
    Address getAddress();

    /**
     * Returns an immutable Map representing the workout a member does on the DayOfWeek.
     */
    Map<DayOfWeek, Workout> getTrainingProgram();

    /**
     * Sets the workout for a specific day.
     *
     * @param day     - DayOfWeek on which the workout will be trained
     * @param workout - the workout to be trained
     * @throws IllegalArgumentException if day or workout is null.
     */
    void setWorkout(DayOfWeek day, Workout workout);

    /**
     * Returns a collection of days in undefined order on which the workout finishes with a specific exercise.
     *
     * @param exerciseName - the name of the exercise.
     * @throws IllegalArgumentException if exerciseName is null or empty.
     */
    Collection<DayOfWeek> getDaysFinishingWith(String exerciseName);

    /**
     * Adds an Exercise to the Workout trained on the given day. If there is no workout set for the day, the day is
     * considered a day off and no exercise can be added.
     *
     * @param day      - DayOfWeek to train the exercise.
     * @param exercise - the trained Exercise.
     * @throws DayOffException          if the Workout on this day is null.
     * @throws IllegalArgumentException if day or exercise is null
     */
    void addExercise(DayOfWeek day, Exercise exercise);

    /**
     * Adds Exercises to the Workout trained on the given day. If there is no workout set for the day, the day is
     * considered a day off and no exercise can be added.
     *
     * @param day       - DayOfWeek to train the exercise.
     * @param exercises - list of the trained Exercises
     * @throws DayOffException          if the Workout on this day is null or the exercises list is empty.
     * @throws IllegalArgumentException if day is null or exercises is null or empty
     */
    void addExercises(DayOfWeek day, List<Exercise> exercises);

}
