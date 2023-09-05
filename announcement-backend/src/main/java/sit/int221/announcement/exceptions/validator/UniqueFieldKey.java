package sit.int221.announcement.exceptions.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import sit.int221.announcement.exceptions.impl.UniqueFieldKeyImpl;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueFieldKeyImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE })
public @interface UniqueFieldKey {


    Class<?> model();

    String field();
    String message() default "does not unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}