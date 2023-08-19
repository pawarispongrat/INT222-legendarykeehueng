package sit.int221.announcement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import sit.int221.announcement.utils.enums.Role;

import java.time.ZonedDateTime;

@Getter @Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    private ZonedDateTime createOn;
    @UpdateTimestamp
    private ZonedDateTime updateOn;
}
