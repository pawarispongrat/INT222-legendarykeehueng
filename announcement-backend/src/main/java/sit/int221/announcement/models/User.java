package sit.int221.announcement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sit.int221.announcement.utils.enums.Role;
import sit.int221.announcement.utils.security.Argon;

import java.time.ZonedDateTime;

@Getter @Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    @Getter
    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(insertable = false,updatable = false)
    private ZonedDateTime createdOn;

    @Column(insertable = false,updatable = false)
    private ZonedDateTime updatedOn;


    public void trim() {
        this.name = name.trim();
        this.username = username.trim();
        this.email = email.trim();
        this.password = password.trim();
    }


}
