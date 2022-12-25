package bg.sofia.uni.fmi.mjt.grading.simulator.grader;

import java.util.List;

import bg.sofia.uni.fmi.mjt.grading.simulator.Assistant;
import bg.sofia.uni.fmi.mjt.grading.simulator.assignment.Assignment;

public interface AdminGradingAPI extends StudentGradingAPI {

    /**
     * Retrieves an assignment to be graded from the set of submitted but still ungraded assignments.
     * If there are several ungraded assignments, which particular one to return is undefined.
     * Getting an assignment removes it from the set of ungraded assignments.
     *
     * @return an assignment to be graded.
     */
    Assignment getAssignment();

    /**
     * Returns the total number of assignments submitted.
     *
     * @return the total number of assignments submitted.
     */
    int getSubmittedAssignmentsCount();

    /**
     * Notifies the assistants to finalize grading. All already submitted assignments must be graded.
     */
    void finalizeGrading();

    /**
     * Returns a list of the course assistants, in undefined order.
     *
     * @return a list of the assistants
     */
    List<Assistant> getAssistants();

}
