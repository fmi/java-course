package bg.sofia.uni.fmi.mjt.udemy.account;

import bg.sofia.uni.fmi.mjt.udemy.account.type.AccountType;
import bg.sofia.uni.fmi.mjt.udemy.course.Category;
import bg.sofia.uni.fmi.mjt.udemy.course.Course;

public class BusinessAccount extends AccountBase {
    private final Category[] allowedCategories;

    public BusinessAccount(String username, double balance, Category[] allowedCategories) {
        super(username, balance);
        this.allowedCategories = allowedCategories;
    }

    @Override
    protected double applyDiscount(Course course) {
        if (!isCategoryValid(course.getCategory())) {
            throw new IllegalArgumentException(
                    "Cannot purchase a course with category %s".formatted(course.getCategory()));
        }

        return course.getPrice() - course.getPrice() * AccountType.BUSINESS.getDiscount();
    }

    private boolean isCategoryValid(Category category) {
        for (Category allowedCategory : allowedCategories) {
            if (allowedCategory.equals(category)) {
                return true;
            }
        }

        return false;
    }
}
