package sit.int221.announcement.utils.security.entra;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class EntraUser {

    private String email;
    private String name;

}
