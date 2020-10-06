package com.github.marcoral.versioning;

class Utils {
    static final String VERSION_DELIMITER = "\\.";
    static final String ALLOWED_FORMAT = "x.x.x";

    static void requireNonNegative(Integer value, String fieldName) {
        if(value != null && value < 0)
            throw new IllegalArgumentException("Attempted to set " + fieldName + " to " + value + " but it must not be negative!");
    }

    static int parseVersionFieldOrThrow(String value, String fieldName) {
        try {
            int fieldValue = Integer.parseInt(value);
            Utils.requireNonNegative(fieldValue, fieldName);
            return fieldValue;
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("Invalid value for " + fieldName + ": " + value);
        }
    }
}
