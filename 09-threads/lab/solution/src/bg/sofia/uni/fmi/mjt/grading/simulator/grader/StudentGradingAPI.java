package bg.sofia.uni.fmi.mjt.grading.simulator.grader;

import bg.sofia.uni.fmi.mjt.grading.simulator.assignment.Assignment;

public interface StudentGradingAPI {

    /**
     * Submits a new {@link Assignment} for grading.
     *
     * @param assignment which is submitted for grading
     */
    void submitAssignment(Assignment assignment);

}
