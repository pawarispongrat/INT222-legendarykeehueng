package sit.int221.announcement.exceptions.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.type.AnyType;
import org.springframework.beans.factory.annotation.Autowired;
import sit.int221.announcement.exceptions.validator.CategoryNotFound;
import sit.int221.announcement.services.AnnouncementService;
import sit.int221.announcement.services.CategoryService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryNotFoundImpl implements ConstraintValidator<CategoryNotFound, Object> {
    @Autowired
    private CategoryService service;
    @Override
    public void initialize(CategoryNotFound annotation) {}

    @Override
    public boolean isValid(Object id, ConstraintValidatorContext context) {
        if (id == null) return true;
        if (id instanceof Integer categoryId) return service.hasCategory(categoryId);
        else if (id instanceof ArrayList<?> list) {
            List<Integer> categoryIds = (List<Integer>) list;
            for (Integer categoryId : categoryIds) {
                if (!service.hasCategory(categoryId)) return false;
            }
            return true;
        }
        return false;
    }
}