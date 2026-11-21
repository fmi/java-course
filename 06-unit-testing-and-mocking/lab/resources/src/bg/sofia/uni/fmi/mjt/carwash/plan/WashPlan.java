package bg.sofia.uni.fmi.mjt.carwash.plan;

import java.util.Collections;
import java.util.List;

public record WashPlan(String packageName, int priceCents, List<WashStep> steps) {

    public WashPlan {
        if (packageName == null || packageName.isBlank()) {
            throw new IllegalArgumentException("Package name must not be null or blank");
        }

        if (priceCents <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }

        if (steps == null || steps.isEmpty()) {
            throw new IllegalArgumentException("Wash steps must not be null or empty");
        }

        for (WashStep step : steps) {
            if (step == null) {
                throw new IllegalArgumentException("Wash steps must not contain null");
            }
        }

        steps = Collections.unmodifiableList(steps);
    }

}
