package br.com.sw2you.realmeet.validator;

public class ValidatorError {
    private final String field;
    private final String errorCode;

    public ValidatorError(String field, String errorCode) {
        this.field = field;
        this.errorCode = errorCode;
    }

    public String getField() {
        return field;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValidatorError that = (ValidatorError) o;

        if (field != null ? !field.equals(that.field) : that.field != null) return false;
        return errorCode != null ? errorCode.equals(that.errorCode) : that.errorCode == null;
    }

    @Override
    public int hashCode() {
        int result = field != null ? field.hashCode() : 0;
        result = 31 * result + (errorCode != null ? errorCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ValidatorError{" + "field='" + field + '\'' + ", errorCode='" + errorCode + '\'' + '}';
    }
}
