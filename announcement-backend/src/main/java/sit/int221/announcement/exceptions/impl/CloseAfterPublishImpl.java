package sit.int221.announcement.exceptions.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import sit.int221.announcement.dtos.request.AnnouncementRequestDTO;
import sit.int221.announcement.exceptions.utils.NodeBuilder;
import sit.int221.announcement.exceptions.validator.CloseAfterPublish;

import java.time.ZonedDateTime;

public class CloseAfterPublishImpl implements ConstraintValidator<CloseAfterPublish, AnnouncementRequestDTO> {

    private String field;
    @Override
    public void initialize(CloseAfterPublish constraintAnnotation) {
        this.field = "closeDate";
    }

    @Override
    public boolean isValid(AnnouncementRequestDTO request, ConstraintValidatorContext context) {
        new NodeBuilder(context).buildPropertyNode(field);
        ZonedDateTime publish = request.getPublishDate();
        ZonedDateTime close = request.getCloseDate();
        if (close == null || publish == null) return true;
        return close.isAfter(publish);
    }
}