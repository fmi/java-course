package bg.sofia.uni.fmi.mjt.gym.member;

import bg.sofia.uni.fmi.mjt.gym.workout.Exercise;
import bg.sofia.uni.fmi.mjt.gym.workout.Workout;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Member implements GymMember {
    private final Address address;
    private final String name;
    private final int age;
    private final String personalIdNumber;
    private final Gender gender;
    private Map<DayOfWeek, Workout> trainingProgram;

    public Member(Address address, String name, int age, String personalIdNumber, Gender gender) {
        this.address = address;
        this.name = name;
        this.age = age;
        this.personalIdNumber = personalIdNumber;
        this.gender = gender;
        this.trainingProgram = new HashMap<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public String getPersonalIdNumber() {
        return personalIdNumber;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public Map<DayOfWeek, Workout> getTrainingProgram() {
        return Collections.unmodifiableMap(trainingProgram);
    }

    @Override
    public void setWorkout(DayOfWeek day, Workout workout) {
        if (day == null) {
            throw new IllegalArgumentException("Day should not be null");
        }
        if (workout == null) {
            throw new IllegalArgumentException("Workout should not be null");
        }
        trainingProgram.put(day, workout);
    }

    @Override
    public Collection<DayOfWeek> getDaysFinishingWith(String exerciseName) {
        if (exerciseName == null || exerciseName.isEmpty()) {
            throw new IllegalArgumentException("ExerciseName should not be null or empty");
        }

        List<DayOfWeek> result = new ArrayList<>();
        for (Map.Entry<DayOfWeek, Workout> entry : trainingProgram.entrySet()) {
            Workout workout = entry.getValue();
            if (workout.exercises().getLast().name().equals(exerciseName)) {
                result.add(entry.getKey());
            }
        }

        return result;
    }

    @Override
    public void addExercise(DayOfWeek day, Exercise exercise) {
        if (day == null || exercise == null) {
            throw new IllegalArgumentException("Day and exercise should not be null");
        }

        Workout workout = trainingProgram.get(day);
        if (workout == null) {
            throw new DayOffException("There is no defined workout");
        }

        workout.exercises().add(exercise);
    }

    @Override
    public void addExercises(DayOfWeek day, List<Exercise> exercises) {
        if (day == null) {
            throw new IllegalArgumentException("Day should not be null");
        }
        if (exercises == null || exercises.isEmpty()) {
            throw new IllegalArgumentException("List of exercises should not be null or empty");
        }

        var workout = trainingProgram.get(day);
        if (workout == null) {
            throw new DayOffException("There is no defined workout for this day.");
        }

        workout.exercises().addAll(exercises);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(personalIdNumber, member.personalIdNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personalIdNumber);
    }
}
