package bg.sofia.uni.fmi.mjt.grading.simulator.grader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

import bg.sofia.uni.fmi.mjt.grading.simulator.Assistant;
import bg.sofia.uni.fmi.mjt.grading.simulator.assignment.Assignment;

public class CodePostGrader implements AdminGradingAPI {

    private final Queue<Assignment> assignmentsForGrading = new LinkedList<>();
    private final List<Assistant> assistants = new ArrayList<>();
    private final AtomicInteger totalSubmittedAssignments = new AtomicInteger();

    private boolean deadlineHasPassed;

    public CodePostGrader(int numberOfAssistants) {
        for (int i = 0; i < numberOfAssistants; i++) {
            Assistant assistant = new Assistant("Assistant " + i, this);
            assistants.add(assistant);
            new Thread(assistant).start();
        }
    }

    @Override
    public void submitAssignment(Assignment assignment) {
        if (!deadlineHasPassed) {
            totalSubmittedAssignments.incrementAndGet();

            synchronized (this) {
                assignmentsForGrading.add(assignment);
                this.notifyAll();
            }
        }
    }

    @Override
    public synchronized Assignment getAssignment() {
        while (assignmentsForGrading.isEmpty() && !deadlineHasPassed) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                System.err.print("Unexpected exception was thrown: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return assignmentsForGrading.poll();
    }

    @Override
    public synchronized void finalizeGrading() {
        deadlineHasPassed = true;
        this.notifyAll();
    }

    @Override
    public int getSubmittedAssignmentsCount() {
        return totalSubmittedAssignments.get();
    }

    @Override
    public List<Assistant> getAssistants() {
        return Collections.unmodifiableList(assistants);
    }

}
