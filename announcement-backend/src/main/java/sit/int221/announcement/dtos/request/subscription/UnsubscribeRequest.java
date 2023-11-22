package sit.int221.announcement.dtos.request.subscription;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import sit.int221.announcement.exceptions.validator.CategoryNotFound;
import sit.int221.announcement.exceptions.validator.IsEmail;

import java.util.List;

@Getter
@Setter
public class UnsubscribeRequest {

    @IsEmail
    private String subscriberEmail;
    @NotBlank
    private String hashEmail;

    @NotNull
    @CategoryNotFound
    private List<Integer> categoryId;
}
