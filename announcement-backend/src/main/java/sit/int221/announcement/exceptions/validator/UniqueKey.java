package sit.int221.announcement.exceptions.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import sit.int221.announcement.exceptions.impl.UniqueKeyImpl;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueKeyImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface UniqueKey {


    Class<?> model();

    String[] fields();

    boolean composite() default false;

    String message() default "does not unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}