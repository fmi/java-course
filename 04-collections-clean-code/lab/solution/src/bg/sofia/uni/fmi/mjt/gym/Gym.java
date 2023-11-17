package bg.sofia.uni.fmi.mjt.gym;

import bg.sofia.uni.fmi.mjt.gym.member.Address;
import bg.sofia.uni.fmi.mjt.gym.member.GymMember;
import bg.sofia.uni.fmi.mjt.gym.member.MembersByNameComparator;
import bg.sofia.uni.fmi.mjt.gym.member.MembersByPersonalIdComparator;
import bg.sofia.uni.fmi.mjt.gym.member.MembersByProximityToGymComparator;
import bg.sofia.uni.fmi.mjt.gym.workout.Workout;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class Gym implements GymAPI {
    private final int capacity;
    private SortedSet<GymMember> members;
    private final Address address;

    public Gym(int capacity, Address address) {
        this.address = address;
        this.capacity = capacity;
        this.members = new TreeSet<>(new MembersByPersonalIdComparator());
    }

    @Override
    public SortedSet<GymMember> getMembers() {
        return Collections.unmodifiableSortedSet(members);
    }

    @Override
    public SortedSet<GymMember> getMembersSortedByName() {
        return getUnmodifiableSortedMembers(new MembersByNameComparator());
    }

    @Override
    public SortedSet<GymMember> getMembersSortedByProximityToGym() {
        return getUnmodifiableSortedMembers(new MembersByProximityToGymComparator(this.address));
    }

    @Override
    public void addMember(GymMember member) throws GymCapacityExceededException {
        if (member == null) {
            throw new IllegalArgumentException("Member should not be null");
        }
        if (members.size() == capacity) {
            throw new GymCapacityExceededException("Cannot add member to the gym. Gym is full.");
        }

        members.add(member);
    }

    @Override
    public void addMembers(Collection<GymMember> members) throws GymCapacityExceededException {
        if (members == null || members.isEmpty()) {
            throw new IllegalArgumentException("Members should not be null or empty");
        }
        if (this.members.size() + members.size() > capacity) {
            throw new GymCapacityExceededException("Cannot add all of the provided members. Gym is full.");
        }

        this.members.addAll(members);
    }

    @Override
    public boolean isMember(GymMember member) {
        if (member == null) {
            throw new IllegalArgumentException("Member should not be null");
        }

        return this.members.contains(member);
    }

    @Override
    public boolean isExerciseTrainedOnDay(String exerciseName, DayOfWeek day) {
        if (exerciseName == null || exerciseName.isEmpty()) {
            throw new IllegalArgumentException("ExerciseName should not be null or empty");
        }
        if (day == null) {
            throw new IllegalArgumentException("Day should not be null");
        }

        for (GymMember member : this.members) {
            Map<DayOfWeek, Workout> memberProgram = member.getTrainingProgram();
            Workout memberWorkout = memberProgram.get(day);
            if (memberWorkout == null) {
                continue;
            }
            if (memberWorkout.containsExercise(exerciseName)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Map<DayOfWeek, List<String>> getDailyListOfMembersForExercise(String exerciseName) {
        if (exerciseName == null || exerciseName.isEmpty()) {
            throw new IllegalArgumentException("ExerciseName should not be null or empty");
        }

        Map<DayOfWeek, List<String>> result = new HashMap<>();
        for (GymMember member : this.members) {
            Map<DayOfWeek, Workout> memberProgram = member.getTrainingProgram();
            for (Map.Entry<DayOfWeek, Workout> entry : memberProgram.entrySet()) {
                if (entry.getValue().containsExercise(exerciseName)) {
                    List<String> membersForExercise = result.get(entry.getKey());
                    if (membersForExercise == null) {
                        membersForExercise = new ArrayList<>();
                        result.put(entry.getKey(), membersForExercise);
                    }

                    membersForExercise.add(member.getName());
                }
            }
        }

        return Collections.unmodifiableMap(result);
    }

    private SortedSet<GymMember> getUnmodifiableSortedMembers(Comparator<GymMember> comparator) {
        SortedSet<GymMember> sortedMembers = new TreeSet<>(comparator);
        sortedMembers.addAll(this.members);
        return Collections.unmodifiableSortedSet(sortedMembers);
    }
}
