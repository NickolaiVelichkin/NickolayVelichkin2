package hw8;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public final class Validator {

    public static ValidationResult validate(Object object) {
        if (object == null) {
            throw new NullPointerException();
        }

        List<ValidationError> errors = new ArrayList<>();
        Class<?> clazz = object.getClass();

        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic()) {
                    continue;
                }
                if (!field.trySetAccessible()) {
                    throw new ValidationConfigurationException(
                            "Cannot access " + clazz.getName() + "." + field.getName());
                }

                Object value;
                try {
                    value = field.get(object);
                } catch (IllegalAccessException e) {
                    throw new ValidationConfigurationException(
                            "Cannot read " + clazz.getName() + "." + field.getName());
                }

                checkField(field, value, errors);
            }
            clazz = clazz.getSuperclass();
        }

        return new ValidationResult(errors);
    }

    private static void checkField(Field field, Object value, List<ValidationError> errors) {
        Class<?> type = field.getType();

        if (field.isAnnotationPresent(NotNull.class)) {
            NotNull ann = field.getAnnotation(NotNull.class);
            if (!type.isPrimitive() && value == null) {
                errors.add(new ValidationError(field.getName(), "NotNull", ann.message()));
            }
        }

        if (field.isAnnotationPresent(Size.class)) {
            Size ann = field.getAnnotation(Size.class);
            if (!CharSequence.class.isAssignableFrom(type) || ann.min() < 0 || ann.min() > ann.max()) {
                throw new ValidationConfigurationException("Bad @Size on " + field.getName());
            }
            if (value != null) {
                int len = ((CharSequence) value).length();
                if (len < ann.min() || len > ann.max()) {
                    errors.add(new ValidationError(field.getName(), "Size", ann.message()));
                }
            }
        }

        if (field.isAnnotationPresent(Range.class)) {
            Range ann = field.getAnnotation(Range.class);
            if (!isNumeric(type)
                    || Double.isNaN(ann.min())
                    || Double.isNaN(ann.max())
                    || Double.isInfinite(ann.min())
                    || Double.isInfinite(ann.max())
                    || ann.min() > ann.max()) {
                throw new ValidationConfigurationException("Bad @Range on " + field.getName());
            }
            if (value != null) {
                double num = ((Number) value).doubleValue();
                if (Double.isNaN(num) || num < ann.min() || num > ann.max()) {
                    errors.add(new ValidationError(field.getName(), "Range", ann.message()));
                }
            }
        }

        if (field.isAnnotationPresent(Email.class)) {
            Email ann = field.getAnnotation(Email.class);
            if (!CharSequence.class.isAssignableFrom(type)) {
                throw new ValidationConfigurationException("Bad @Email on " + field.getName());
            }
            if (value != null && !isValidEmail(value.toString())) {
                errors.add(new ValidationError(field.getName(), "Email", ann.message()));
            }
        }
    }

    private static boolean isNumeric(Class<?> type) {
        return Number.class.isAssignableFrom(type)
                || type == byte.class
                || type == short.class
                || type == int.class
                || type == long.class
                || type == float.class
                || type == double.class;
    }

    private static boolean isValidEmail(String s) {
        if (s.isEmpty() || s.contains(" ") || s.contains("\t") || s.contains("\n")) {
            return false;
        }
        int at = s.indexOf('@');
        if (at <= 0 || at != s.lastIndexOf('@') || at == s.length() - 1) {
            return false;
        }
        String afterAt = s.substring(at + 1);
        int dot = afterAt.indexOf('.');
        return dot > 0 && dot < afterAt.length() - 1;
    }
}