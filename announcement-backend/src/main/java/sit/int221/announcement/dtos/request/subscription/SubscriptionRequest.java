package sit.int221.announcement.dtos.request.subscription;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;
import sit.int221.announcement.exceptions.validator.CategoryNotFound;
import sit.int221.announcement.exceptions.validator.IsEmail;
import sit.int221.announcement.exceptions.validator.UniqueKey;
import sit.int221.announcement.models.Category;
import sit.int221.announcement.models.Subscription;
import sit.int221.announcement.models.User;

import java.util.List;

@Getter @Setter
public class SubscriptionRequest {

    @IsEmail
    private String subscriberEmail;

    @NotNull
    @CategoryNotFound
    private List<Integer> categoryId;

}
