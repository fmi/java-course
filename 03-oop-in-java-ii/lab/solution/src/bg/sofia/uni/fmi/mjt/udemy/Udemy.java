package bg.sofia.uni.fmi.mjt.udemy;

import bg.sofia.uni.fmi.mjt.udemy.account.AccountBase;
import bg.sofia.uni.fmi.mjt.udemy.course.Category;
import bg.sofia.uni.fmi.mjt.udemy.course.Course;
import bg.sofia.uni.fmi.mjt.udemy.course.duration.CourseDuration;
import bg.sofia.uni.fmi.mjt.udemy.exception.AccountNotFoundException;
import bg.sofia.uni.fmi.mjt.udemy.exception.CourseNotFoundException;

import java.util.Arrays;

public class Udemy implements LearningPlatform {
    private final AccountBase[] accountBases;
    private final Course[] courses;

    public Udemy(AccountBase[] accountBases, Course[] courses) {
        this.accountBases = accountBases;
        this.courses = courses;
    }

    @Override
    public Course findByName(String name) throws CourseNotFoundException {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Null or blank search parameter name");
        }

        for (Course course : courses) {
            if (course.getName().equals(name)) {
                return course;
            }
        }

        throw new CourseNotFoundException("Course with name %s does not exist".formatted(name));
    }

    @Override
    public Course[] findByKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Null or blank search parameter keyword");
        }

        Course[] matchedCourses = new Course[courses.length];
        int currentMatchedCourse = 0;

        for (Course course : courses) {
            if (course.getName().contains(keyword) || course.getDescription().contains(keyword)) {
                matchedCourses[currentMatchedCourse++] = course;
            }
        }

        return Arrays.copyOf(matchedCourses, currentMatchedCourse);
    }

    @Override
    public Course[] getAllCoursesByCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Null category given");
        }

        Course[] matchedCourses = new Course[courses.length];
        int currentMatchedCourse = 0;

        for (Course course : courses) {
            if (course.getCategory().equals(category)) {
                matchedCourses[currentMatchedCourse++] = course;
            }
        }

        return Arrays.copyOf(matchedCourses, currentMatchedCourse);
    }

    @Override
    public AccountBase getAccount(String name) throws AccountNotFoundException {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Null or blank search parameter name");
        }

        for (AccountBase accountBase : accountBases) {
            if (accountBase.getUsername().equals(name)) {
                return accountBase;
            }
        }

        throw new AccountNotFoundException("Account with name %s does not exist".formatted(name));
    }

    @Override
    public Course getLongestCourse() {
        Course longestCourse = null;
        CourseDuration longestCourseDuration = new CourseDuration(0, 0);

        for (Course course : courses) {
            if (course.getTotalTime().isLongerThan(longestCourseDuration)) {
                longestCourse = course;
                longestCourseDuration = longestCourse.getTotalTime();
            }
        }

        return longestCourse;
    }

    @Override
    public Course getCheapestByCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Null category given");
        }

        if (courses.length == 0) {
            return null;
        }

        Course cheapestCourse = null;
        double cheapestPrice = Double.MAX_VALUE;

        for (Course course : courses) {
            if (course.getCategory().equals(category) && course.getPrice() < cheapestPrice) {
                cheapestCourse = course;
                cheapestPrice = cheapestCourse.getPrice();
            }
        }

        return cheapestCourse;
    }
}