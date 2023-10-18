package sit.int221.announcement.dtos.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import sit.int221.announcement.exceptions.validator.CategoryNotFound;
import sit.int221.announcement.exceptions.validator.CloseAfterPublish;
import sit.int221.announcement.exceptions.validator.EnumValidator;
import sit.int221.announcement.utils.enums.Display;

import java.time.ZonedDateTime;
@Getter @Setter
@CloseAfterPublish
public class AnnouncementRequest {

    @NotBlank
    @Size(min = 1, max = 200)
    private String announcementTitle;

    @NotBlank
    @Size(min = 1, max = 10000)
    private String announcementDescription;
    @FutureOrPresent
    private ZonedDateTime publishDate;
    @Future
    private ZonedDateTime closeDate;

    @EnumValidator(enumClass = Display.class,message = "must be either 'Y' or 'N'")
    private String announcementDisplay;
    @NotNull
    @CategoryNotFound
    private Integer categoryId;

    private Integer viewCount;

    private String getDefaultDisplay(String announcementDisplay) {
        return announcementDisplay == null ? Display.N.toString() : announcementDisplay;
    }
    private Integer getDefaultViewCount(Integer viewCount) {
        return viewCount == null ? 0 : viewCount;
    }

    public Display getAnnouncementDisplay() {
        return Display.valueOf(getDefaultDisplay(announcementDisplay));
    }

    public void setAnnouncementDisplay(String announcementDisplay) {
        this.announcementDisplay = getDefaultDisplay(announcementDisplay);
    }

    public Integer getViewCount() {
        return getDefaultViewCount(viewCount);
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = getDefaultViewCount(viewCount);
    }
}
