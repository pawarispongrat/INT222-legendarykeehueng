package sit.int221.announcement.dtos.request.subscription;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import sit.int221.announcement.exceptions.validator.IsEmail;
import sit.int221.announcement.exceptions.validator.UniqueKey;
import sit.int221.announcement.models.Subscription;

@Getter
@Setter
public class SubscriptionOtpRequest {

    @IsEmail
    private String subscriberEmail;

    @NotNull
    @Pattern(regexp = "[0-9]+",message = "String must be in number [0-9]")
    private String otp;

}
