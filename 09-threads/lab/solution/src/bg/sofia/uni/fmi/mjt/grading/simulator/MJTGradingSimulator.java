package bg.sofia.uni.fmi.mjt.grading.simulator;

import java.util.List;

import bg.sofia.uni.fmi.mjt.grading.simulator.grader.AdminGradingAPI;
import bg.sofia.uni.fmi.mjt.grading.simulator.grader.CodePostGrader;

public class MJTGradingSimulator {

    private static final int NUMBER_OF_STUDENTS = 30;
    private static final int SLEEP_TIME = 5_000;
    private static final String STUDENT_THREAD_NAME_PREFIX = "Student ";

    public static void main(String[] args) {
        try {
            new MJTGradingSimulator().simulate();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void simulate() throws InterruptedException {
        AdminGradingAPI grader = new CodePostGrader(5);

        int numberOfStudents = NUMBER_OF_STUDENTS;
        Thread[] students = new Thread[numberOfStudents];
        for (int i = 0; i < numberOfStudents; i++) {
            students[i] = new Thread(new Student(i, STUDENT_THREAD_NAME_PREFIX + i, grader));
            students[i].start();
        }

        for (Thread student : students) {
            student.join();
        }

        Thread.sleep(SLEEP_TIME);
        grader.finalizeGrading();

        List<Assistant> assistants = grader.getAssistants();
        for (Assistant assistant : assistants) {
            assistant.join();
        }

        int totalGradedAssignments = assistants.stream().mapToInt(Assistant::getNumberOfGradedAssignments).sum();

        System.out.println("Total number of submitted assignments: " + grader.getSubmittedAssignmentsCount());
        System.out.println("Total number of graded assignments: " + totalGradedAssignments);
    }

}
