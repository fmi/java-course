package bg.sofia.uni.fmi.mjt.udemy.account;

import bg.sofia.uni.fmi.mjt.udemy.course.Course;
import bg.sofia.uni.fmi.mjt.udemy.course.Resource;
import bg.sofia.uni.fmi.mjt.udemy.exception.CourseAlreadyPurchasedException;
import bg.sofia.uni.fmi.mjt.udemy.exception.CourseNotCompletedException;
import bg.sofia.uni.fmi.mjt.udemy.exception.CourseNotPurchasedException;
import bg.sofia.uni.fmi.mjt.udemy.exception.InsufficientBalanceException;
import bg.sofia.uni.fmi.mjt.udemy.exception.MaxCourseCapacityReachedException;
import bg.sofia.uni.fmi.mjt.udemy.exception.ResourceNotFoundException;

public abstract class AccountBase implements Account {
    private static final int MAX_CAPACITY = 100;
    private static final double MIN_GRADE = 2.0;
    private static final double MAX_GRADE = 6.0;

    private final String username;
    private double balance;
    protected final Course[] courses;
    private int courseCounter;

    public AccountBase(String username, double balance) {
        this(username, balance, new Course[MAX_CAPACITY], 0);
    }

    AccountBase(String username, double balance, Course[] courses, int courseCounter) {
        this.username = username;
        this.balance = balance;
        this.courses = courses;
        this.courseCounter = courseCounter;
    }

    public String getUsername() {
        return username;
    }

    public void addToBalance(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot add negative amount of money");
        }

        this.balance += amount;
    }

    public double getBalance() {
        return balance;
    }

    protected abstract double applyDiscount(Course course);

    public void buyCourse(Course course)
            throws InsufficientBalanceException, CourseAlreadyPurchasedException, MaxCourseCapacityReachedException {
        if (this.balance < course.getPrice()) {
            throw new InsufficientBalanceException(
                    "Account %s cannot buy course %s due to insufficient account balance".formatted(username, course));
        }

        if (isCoursePurchased(course)) {
            throw new CourseAlreadyPurchasedException("Course is already purchased");
        }

        if (this.courseCounter == MAX_CAPACITY) {
            throw new MaxCourseCapacityReachedException(
                    "Account %s has reached the limit of courses to buy".formatted(username));
        }

        double newPrice = applyDiscount(course);

        this.balance -= newPrice;
        Course courseToAdd = new Course(course);
        this.courses[courseCounter++] = courseToAdd;
        courseToAdd.purchase();
    }

    public void completeResourcesFromCourse(Course course, Resource[] resourcesToComplete)
            throws CourseNotPurchasedException, ResourceNotFoundException {
        if (!isCoursePurchased(course)) {
            throw new CourseNotPurchasedException(
                    "Course %s is not purchased and its content cannot be viewed".formatted(course.getName()));
        }

        for (Resource resourceToComplete : resourcesToComplete) {
            course.completeResource(resourceToComplete);
        }
    }

    @Override
    public void completeCourse(Course course, double grade)
            throws CourseNotPurchasedException, CourseNotCompletedException {
        if (grade < MIN_GRADE || grade > MAX_GRADE) {
            throw new IllegalArgumentException(
                    "The grade for the course completion is invalid. It should be between 2.0 and 6.0");
        }

        Course courseToComplete = getCourse(course);

        if (courseToComplete == null) {
            throw new CourseNotPurchasedException("The given course is not purchased and thus cannot be completed");
        }

        if (!courseToComplete.isCompleted()) {
            throw new CourseNotCompletedException("The given course has resources that are not yet completed");
        }
    }

    private boolean isCoursePurchased(Course course) {
        return getCourse(course) != null;
    }

    private Course getCourse(Course course) {
        for (int i = 0; i < courseCounter; i++) {
            if (courses[i].equals(course) && courses[i].isPurchased()) {
                return courses[i];
            }
        }

        return null;
    }

    public Course getLeastCompletedCourse() {
        if (courseCounter == 0) {
            return null;
        }

        Course leastCompleted = courses[0];
        int leastCompletedPercentage = leastCompleted.getCompletionPercentage();

        for (Course course : courses) {
            if (course == null) {
                break;
            }

            int currentCompletedPercentage = course.getCompletionPercentage();
            if (currentCompletedPercentage < leastCompletedPercentage) {
                leastCompleted = course;
                leastCompletedPercentage = currentCompletedPercentage;
            }
        }

        return leastCompleted;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof AccountBase other) {
            return this.username.equals(other.username);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.username.hashCode();
    }
}
