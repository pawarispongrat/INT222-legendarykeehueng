package sit.int221.announcement.exceptions.list;

import lombok.Getter;

@Getter
public class FieldException extends RuntimeException {

    private final String field;

    public FieldException(String field) {
        super(field,new Throwable("does not exists"));
        this.field = field;
    }
    public FieldException(String field,String message) {
        super(field,new Throwable(message));
        this.field = field;
    }
}
