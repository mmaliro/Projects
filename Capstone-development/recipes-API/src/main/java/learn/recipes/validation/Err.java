package learn.recipes.validation;

import lombok.Data;

@Data
public class Err {
    private final String field;
    private final String message;
}