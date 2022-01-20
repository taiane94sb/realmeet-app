package br.com.sw2you.realmeet.exception;

import br.com.sw2you.realmeet.validator.ValidatorErrors;

public class InvalidRequestException extends RuntimeException {
    private final ValidatorErrors validatorErrors;

    public InvalidRequestException(ValidatorErrors validatorErrors) {
        super(validatorErrors.toString());
        this.validatorErrors = validatorErrors;
    }

    public ValidatorErrors getValidatorErrors() {
        return validatorErrors;
    }
}
