package sit.int221.announcement.exceptions.utils;

import jakarta.validation.ConstraintValidatorContext;

public class NodeBuilder {

    private String message;
    private ConstraintValidatorContext.ConstraintViolationBuilder constraint;
    public NodeBuilder(ConstraintValidatorContext context,String message) {
        this.message = message;
        this.constraint = context.buildConstraintViolationWithTemplate(message);
    }

    public NodeBuilder(ConstraintValidatorContext context) {
        this.message = context.getDefaultConstraintMessageTemplate();
        this.constraint = context.buildConstraintViolationWithTemplate(this.message);
    }

    public void buildPropertyNode(String field) {
        constraint.addPropertyNode(field).addConstraintViolation();
    }


}
