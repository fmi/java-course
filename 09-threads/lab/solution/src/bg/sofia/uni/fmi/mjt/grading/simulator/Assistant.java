package bg.sofia.uni.fmi.mjt.grading.simulator;

import bg.sofia.uni.fmi.mjt.grading.simulator.assignment.Assignment;
import bg.sofia.uni.fmi.mjt.grading.simulator.grader.AdminGradingAPI;

public class Assistant extends Thread {

    private final String name;
    private final AdminGradingAPI grader;
    private int numberOfGradedAssignments;

    public Assistant(String name, AdminGradingAPI grader) {
        this.name = name;
        this.grader = grader;
    }

    @Override
    public void run() {
        gradeAssignments();
    }

    private void gradeAssignments() {
        Assignment assignment;
        while ((assignment = grader.getAssignment()) != null) {
            try {
                Thread.sleep(assignment.type().getGradingTime());
                numberOfGradedAssignments++;
            } catch (InterruptedException e) {
                System.err.print("Unexpected exception was thrown: " + e.getMessage());
                e.printStackTrace();
            }
        }

        System.out.println("Assistant " + name + " graded " + numberOfGradedAssignments + " assignments");
    }

    public int getNumberOfGradedAssignments() {
        return numberOfGradedAssignments;
    }

}
