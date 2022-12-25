package bg.sofia.uni.fmi.mjt.grading.simulator.assignment;

public enum AssignmentType {
    LAB(20), PLAYGROUND(40), HOMEWORK(80), PROJECT(120);

    private final int gradingTime;

    AssignmentType(int gradingTime) {
        this.gradingTime = gradingTime;
    }

    public int getGradingTime() {
        return gradingTime;
    }
}
