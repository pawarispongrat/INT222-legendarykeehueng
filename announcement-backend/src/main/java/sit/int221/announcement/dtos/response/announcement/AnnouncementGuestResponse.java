package sit.int221.announcement.dtos.response.announcement;

import lombok.Getter;
import lombok.Setter;
import sit.int221.announcement.enumeration.Display;

import java.time.ZonedDateTime;
@Getter @Setter
public class AnnouncementGuestResponse {
    private Integer id;
    private String announcementTitle;
    private String announcementDescription;
    private ZonedDateTime publishDate;
    private ZonedDateTime closeDate;
    private Display announcementDisplay;
    private String announcementCategory;

    private Integer viewCount;
}
