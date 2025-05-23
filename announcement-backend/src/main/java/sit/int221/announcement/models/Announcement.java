package sit.int221.announcement.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sit.int221.announcement.enumeration.Display;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "announcement")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcementId")
    private Integer id;

    private String announcementDescription;
    private String announcementTitle;

    @Enumerated(EnumType.STRING)
    private Display announcementDisplay;

    private ZonedDateTime publishDate;
    private ZonedDateTime closeDate;
    
    private Integer viewCount;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    @JsonIgnore
    private Category category;

//    @ManyToOne
//    @JoinColumn(name = "announcementOwner", referencedColumnName = "username")
//    @JsonIgnore
    // MARK - Owner email
    private String announcementOwner;

    public Integer getCategoryId() { return category.getCategoryId(); }

    public String getAnnouncementCategory() {
        return category.getCategoryName();
    }


    @Override
    public String toString() {
        return "Announcement{" +
                "id=" + id +
                ", announcementDescription='" + announcementDescription + '\'' +
                ", announcementTitle='" + announcementTitle + '\'' +
                ", announcementDisplay=" + announcementDisplay +
                ", publishDate=" + publishDate +
                ", closeDate=" + closeDate +
                ", viewCount=" + viewCount +
                ", category=" + category +
                ", announcementOwner=" + announcementOwner +
                '}';
    }
}
