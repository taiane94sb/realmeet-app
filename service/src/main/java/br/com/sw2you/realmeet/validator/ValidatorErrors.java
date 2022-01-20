package br.com.sw2you.realmeet.validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.data.util.Streamable;

public class ValidatorErrors implements Streamable<ValidatorError> {
    private final List<ValidatorError> validatorErrorList;

    public ValidatorErrors() {
        this.validatorErrorList = new ArrayList<>();
    }

    public ValidatorErrors add(String field, String errorCode) {
        return add(new ValidatorError(field, errorCode));
    }

    public ValidatorErrors add(ValidatorError validatorError) {
        validatorErrorList.add(validatorError);
        return this;
    }

    public ValidatorError getError(int index) {
        return validatorErrorList.get(index);
    }

    public int getNumberOfErrors() {
        return validatorErrorList.size();
    }

    public boolean hasErrors() {
        return !validatorErrorList.isEmpty();
    }

    @Override
    public String toString() {
        return "ValidatorErrors { validatorErrorList=" + validatorErrorList + '}';
    }

    @Override
    public Iterator<ValidatorError> iterator() {
        return validatorErrorList.iterator();
    }
}
