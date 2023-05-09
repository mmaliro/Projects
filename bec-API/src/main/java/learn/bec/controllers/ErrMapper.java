package learn.bec.controllers;

import learn.bec.validation.Err;
import learn.bec.validation.Result;
import learn.bec.validation.ResultType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

public class ErrMapper {

    static <T> List<Err> mapErrs(BindingResult bindingResult) {
        Result<T> result = new Result<>();
        for (FieldError err : bindingResult.getFieldErrors()) {
            result.addErr(err.getField(), err.getDefaultMessage(), ResultType.INVALID);
        }
        for (ObjectError err : bindingResult.getGlobalErrors()) {
            result.addErr("", err.getDefaultMessage(), ResultType.INVALID);
        }
        return result.getErrs();
    }

    static <T> List<Err> mapErrs(String field, String message) {
        return List.of(new Err(field, message));
    }
}
