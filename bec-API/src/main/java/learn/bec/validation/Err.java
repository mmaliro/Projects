package learn.bec.validation;

import lombok.Data;

@Data
public class Err {

    private final String field;
    private final String message;
}
