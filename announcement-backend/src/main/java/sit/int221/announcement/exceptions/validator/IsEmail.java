package sit.int221.announcement.exceptions.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@NotBlank
@Size(min = 1,max = 150)
@Email(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9._-]+", message = "Email should be valid")
public @interface IsEmail {

    String message() default "Email should be valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
