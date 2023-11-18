package sit.int221.announcement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sit.int221.announcement.enumeration.Role;

import java.time.ZonedDateTime;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String username;
    @Getter
    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "announcementOwner")
    private List<Announcement> announcements;

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
