package sit.int221.announcement.exceptions.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import sit.int221.announcement.exceptions.validator.CategoryNotFound;
import sit.int221.announcement.services.AnnouncementService;
import sit.int221.announcement.services.CategoryService;

public class CategoryNotFoundImpl implements ConstraintValidator<CategoryNotFound, Integer> {
    @Autowired
    private CategoryService service;
    @Override
    public void initialize(CategoryNotFound annotation) {}

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext context) {

        if (id == null) return true;
        return service.hasCategory(id);
    }
}