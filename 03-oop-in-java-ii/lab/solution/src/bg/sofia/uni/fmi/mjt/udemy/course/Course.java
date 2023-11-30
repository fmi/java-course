package bg.sofia.uni.fmi.mjt.udemy.course;

import bg.sofia.uni.fmi.mjt.udemy.course.duration.CourseDuration;
import bg.sofia.uni.fmi.mjt.udemy.exception.ResourceNotFoundException;

import java.util.Arrays;
import java.util.Objects;

public class Course implements Purchasable, Completable {
    private final String name;
    private final String description;
    private final double price;
    private final Resource[] content;
    private final Category category;
    private final CourseDuration totalTime;
    private boolean isPurchased;

    public Course(String name, String description, double price, Resource[] content, Category category) {
        this(name, description, price, content, category, CourseDuration.of(content), false);
    }

    public Course(Course otherCourse) {
        this.name = otherCourse.name;
        this.description = otherCourse.description;
        this.price = otherCourse.price;
        this.content = Arrays.copyOf(otherCourse.content, otherCourse.content.length);
        this.category = otherCourse.category;
        this.totalTime = otherCourse.totalTime;
        this.isPurchased = otherCourse.isPurchased;
    }

    Course(String name, String description, double price, Resource[] content,
           Category category, CourseDuration totalTime, boolean isPurchased) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.content = content;
        this.category = category;
        this.totalTime = totalTime;
        this.isPurchased = isPurchased;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public Resource[] getContent() {
        return content;
    }

    public CourseDuration getTotalTime() {
        return totalTime;
    }

    @Override
    public void purchase() {
        isPurchased = true;
    }

    @Override
    public boolean isPurchased() {
        return isPurchased;
    }

    public void completeResource(Resource resourceToComplete) throws ResourceNotFoundException {
        for (Resource resource : content) {
            if (resource.equals(resourceToComplete)) {
                resource.complete();
                return;
            }
        }

        throw new ResourceNotFoundException(
                "Resource with name %s could not be found in course %s".formatted(resourceToComplete.getName(), name));
    }

    @Override
    public boolean isCompleted() {
        for (Resource resource : content) {
            if (!resource.isCompleted()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getCompletionPercentage() {
        int totalCourses = content.length;
        int completedCourses = 0;

        int complPercentage = 0;
        for (Resource resource : content) {
            if (resource.isCompleted()) {
                completedCourses++;
                complPercentage += resource.getCompletionPercentage();
            }
        }

        return completedCourses == 0 ? 0 : (int)Math.round((double)complPercentage / totalCourses);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Course other) {
            return this.name.equals(other.name) && this.description.equals(other.description) &&
                    this.price == other.price && Arrays.equals(this.content, other.content) &&
                    this.category.equals(other.category) && this.totalTime.equals(other.totalTime);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, Arrays.hashCode(content), category, totalTime);
    }
}
