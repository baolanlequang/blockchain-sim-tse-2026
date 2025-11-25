package org.palladiosimulator.blockchainsystems.plugin.utils;

// NOTE: Needed in Java code, not in Kotlin code. Maybe replace completely with Kotlin code in the future.
public final class ValidationUtils {

    public static final long MINIMUM_PORT = 1;
    public static final long MAXIMUM_PORT = 65_536;

    public static boolean isInRange(long value, long lowerBoundInclusive, long upperBoundInclusive) {
        return lowerBoundInclusive <= value && value <= upperBoundInclusive;
    }

    public static boolean isStringPopulated(String text) {
        return text != null && !text.isEmpty();
    }

    public static boolean isNumber(String text) {
        try {
            Long.parseLong(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isPort(String text) {
        return isStringPopulated(text)
                && isNumber(text)
                && isInRange(Long.parseLong(text), MINIMUM_PORT, MAXIMUM_PORT);
    }
}
