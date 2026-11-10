package hw8;

import java.util.Objects;

public final class ValidationError {
    private final String fieldName;
    private final String annotationName;
    private final String message;

    public ValidationError(String fieldName, String annotationName, String message) {
        this.fieldName = fieldName;
        this.annotationName = annotationName;
        this.message = message;
    }

    public String fieldName() {
        return fieldName;
    }

    public String annotationName() {
        return annotationName;
    }

    public String message() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationError that = (ValidationError) o;
        return Objects.equals(fieldName, that.fieldName)
                && Objects.equals(annotationName, that.annotationName)
                && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldName, annotationName, message);
    }
}