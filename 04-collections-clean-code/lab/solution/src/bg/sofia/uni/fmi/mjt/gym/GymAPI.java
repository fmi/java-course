package bg.sofia.uni.fmi.mjt.gym;

import bg.sofia.uni.fmi.mjt.gym.member.GymMember;

import java.util.Collection;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

public interface GymAPI {

    /**
     * Returns an unmodifiable copy of all members of the gym.
     * If there are no members, return an empty collection.
     */
    SortedSet<GymMember> getMembers();

    /**
     * Returns an unmodifiable copy of all members of the gym sorted by their name in lexicographic order.
     * If there are no members, return an empty collection.
     */
    SortedSet<GymMember> getMembersSortedByName();

    /**
     * Returns an unmodifiable copy of all members of the gym sorted by their proximity to the gym in increasing order.
     * If there are no members, return an empty collection.
     */
    SortedSet<GymMember> getMembersSortedByProximityToGym();

    /**
     * Adds a single member to the gym.
     *
     * @param member the member to add
     * @throws GymCapacityExceededException - if the gym is full
     * @throws IllegalArgumentException     if member is null
     */
    void addMember(GymMember member) throws GymCapacityExceededException;

    /**
     * Adds a group of members to the gym. If the gym does not have the capacity to accept all the
     * new members then no members are added
     *
     * @param members the members to add
     * @throws GymCapacityExceededException if the gym is full
     * @throws IllegalArgumentException     if members is null or empty
     */
    void addMembers(Collection<GymMember> members) throws GymCapacityExceededException;

    /**
     * Checks if a given member is member of the gym.
     *
     * @param member - the member
     * @throws IllegalArgumentException if member is null
     */
    boolean isMember(GymMember member);

    /**
     * Checks if an Exercise is trained on a given day.
     *
     * @param exerciseName - the name of the Exercise
     * @param day          - the day for which the check is done
     * @throws IllegalArgumentException if day is null or if exerciseName is null or empty
     */
    boolean isExerciseTrainedOnDay(String exerciseName, DayOfWeek day);

    /**
     * Returns an unmodifiable Map representing each day and the names of the members that do this exercise on it.
     *
     * @param exerciseName - the name of the exercise being done
     * @throws IllegalArgumentException if exerciseName is null or empty
     */
    Map<DayOfWeek, List<String>> getDailyListOfMembersForExercise(String exerciseName);
}
