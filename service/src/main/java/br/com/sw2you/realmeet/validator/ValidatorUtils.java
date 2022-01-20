package br.com.sw2you.realmeet.validator;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.com.sw2you.realmeet.exception.InvalidRequestException;
import java.util.Objects;

public final class ValidatorUtils {

    private ValidatorUtils() {}

    public static void throwOnError(ValidatorErrors validatorErrors) {
        if (validatorErrors.hasErrors()) {
            throw new InvalidRequestException(validatorErrors);
        }
    }

    public static boolean validateRequired(String field, String fieldName, ValidatorErrors validatorErrors) {
        if (isBlank(field)) {
            validatorErrors.add(fieldName, fieldName + ValidatorConstants.MISSING);
            return false;
        }
        return true;
    }

    public static boolean validateRequired(Object field, String fieldName, ValidatorErrors validatorErrors) {
        if (isNull(field)) {
            validatorErrors.add(fieldName, fieldName + ValidatorConstants.MISSING);
            return false;
        }
        return true;
    }

    public static boolean validateMaxLength(
        String field,
        String fieldName,
        int maxLength,
        ValidatorErrors validatorErrors
    ) {
        if (!isBlank(field) && field.trim().length() > maxLength) {
            validatorErrors.add(fieldName, fieldName + ValidatorConstants.EXCEEDS_MAX_LENGTH);
            return true;
        }
        return false;
    }
}
