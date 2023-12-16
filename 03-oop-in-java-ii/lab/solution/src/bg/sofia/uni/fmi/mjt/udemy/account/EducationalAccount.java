package bg.sofia.uni.fmi.mjt.udemy.account;

import bg.sofia.uni.fmi.mjt.udemy.account.type.AccountType;
import bg.sofia.uni.fmi.mjt.udemy.course.Course;
import bg.sofia.uni.fmi.mjt.udemy.exception.CourseNotCompletedException;
import bg.sofia.uni.fmi.mjt.udemy.exception.CourseNotPurchasedException;

public class EducationalAccount extends AccountBase {
    private static final int PREVIOUS_GRADES_COUNT = 5;
    private static final double MIN_AVERAGE_GRADE_FOR_DISCOUNT = 4.50;

    private final double[] grades;
    private int gradesCounter;
    private int discountEligibilityCount;

    public EducationalAccount(String username, double balance) {
        super(username, balance);
        this.grades = new double[PREVIOUS_GRADES_COUNT];
        this.gradesCounter = 0;
        this.discountEligibilityCount = 0;
    }

    @Override
    protected double applyDiscount(Course course) {
        double coursePrice = course.getPrice();

        if (discountEligibilityCount > 0) {
            discountEligibilityCount--;
            return coursePrice - coursePrice * AccountType.EDUCATION.getDiscount();
        }

        return coursePrice;
    }

    @Override
    public void completeCourse(Course course, double grade)
            throws CourseNotPurchasedException, CourseNotCompletedException {
        super.completeCourse(course, grade);

        this.grades[gradesCounter++] = grade;

        if (gradesCounter == PREVIOUS_GRADES_COUNT) {
            if (getAverageGradeFromPreviousCourses() >= MIN_AVERAGE_GRADE_FOR_DISCOUNT) {
                discountEligibilityCount++;
            }

            gradesCounter = 0;
        }
    }

    private double getAverageGradeFromPreviousCourses() {
        double sum = 0;

        for (double grade : grades) {
            sum += grade;
        }

        return sum / PREVIOUS_GRADES_COUNT;
    }
}
