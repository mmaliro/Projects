package learn.recipes.validation;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {

    private ResultType type = ResultType.SUCCESS;
    private T payload;
    private ArrayList<Err> errs = new ArrayList<>();

    public boolean isSuccess() {
        return type == ResultType.SUCCESS;
    }

    public ResultType getType() {
        return type;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public List<Err> getErrs() {
        return new ArrayList<>(errs);
    }

    public void addErr(String field, String message, ResultType type) {
        errs.add(new Err(field, message));
        this.type = type;
    }
}
