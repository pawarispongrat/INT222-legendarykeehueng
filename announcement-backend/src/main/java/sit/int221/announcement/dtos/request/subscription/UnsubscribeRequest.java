package sit.int221.announcement.dtos.request.subscription;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import sit.int221.announcement.exceptions.validator.CategoryNotFound;
import sit.int221.announcement.exceptions.validator.IsEmail;

@Getter
@Setter
public class UnsubscribeRequest {

    @IsEmail
    private String subscriberEmail;

    @NotNull
    @CategoryNotFound
    private Integer categoryId;
}
