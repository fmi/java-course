package bg.sofia.uni.fmi.mjt.udemy.course;

import bg.sofia.uni.fmi.mjt.udemy.course.duration.ResourceDuration;

public class Resource implements Completable {
    private static final int MAX_COMPLETION_PERCENTAGE = 100;
    private final String name;
    private boolean isCompleted;
    private final ResourceDuration duration;

    public Resource(String name, ResourceDuration duration) {
        this(name, false, duration);
    }

    public Resource(Resource otherResource) {
        this(otherResource.name, otherResource.isCompleted, otherResource.duration);
    }

    Resource(String name, boolean isCompleted, ResourceDuration duration) {
        this.name = name;
        this.isCompleted = isCompleted;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public int getCompletionPercentage() {
        return isCompleted ? MAX_COMPLETION_PERCENTAGE : 0;
    }

    public ResourceDuration getDuration() {
        return duration;
    }

    public void complete() {
        this.isCompleted = true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Resource other) {
            return this.name.equals(other.name);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
