package sit.int221.announcement.exceptions.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@NotBlank
@Size(min = 8,max = 14)
@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,14}$",message = "must be 8-14 characters long, at least 1 of uppercase, lowercase, number and special characters")
public @interface IsPassword {

    String message() default "Password should be valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
