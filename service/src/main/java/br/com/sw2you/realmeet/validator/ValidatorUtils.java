package br.com.sw2you.realmeet.validator;

import br.com.sw2you.realmeet.exception.InvalidRequestException;

public final class ValidatorUtils {

    private ValidatorUtils() {}

    public static void throwOnError(ValidatorErrors validatorErrors) {
        if (validatorErrors.hasErrors()) {
            throw new InvalidRequestException(validatorErrors);
        }
    }
}
