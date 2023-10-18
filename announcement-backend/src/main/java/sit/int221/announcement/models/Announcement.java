package sit.int221.announcement.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sit.int221.announcement.utils.enums.Display;

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

    @ManyToOne
    @JoinColumn(name = "announcementOwner")
    @JsonIgnore
    private User announcementOwner;

    public Integer getCategoryId() { return category.getCategoryId(); }

    public Integer getOwnerId() { return announcementOwner.getId(); }

    public String getAnnouncementCategory() {
        return category.getCategoryName();
    }


}
