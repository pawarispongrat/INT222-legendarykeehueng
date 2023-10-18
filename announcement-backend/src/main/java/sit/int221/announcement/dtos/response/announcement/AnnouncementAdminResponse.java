package sit.int221.announcement.dtos.response.announcement;

import lombok.Getter;
import lombok.Setter;
import sit.int221.announcement.models.User;


@Getter @Setter
public class AnnouncementAdminResponse extends AnnouncementGuestResponse {

    private User announcementOwner;
    private Integer viewCount;

    public String getAnnouncementOwner() {
        return announcementOwner.getUsername();
    }
}
