package sit.int221.announcement.exceptions.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import sit.int221.announcement.exceptions.impl.CategoryNotFoundImpl;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CategoryNotFoundImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface CategoryNotFound {
    String message() default "does not exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}