package org.mmapp.util;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>MathUtility</b><br>
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class MathUtil {
    public MathUtil() {}

    /**
     * @return normalized float value between 0.0 and 1.0; minValue = 0.0, maxValue = 1.0
     */
    public static float normalizeValue(float value, float minValue, float maxValue) {
        return (value - minValue) / (maxValue - minValue);
    }

    /**
     * @return random integer between 0 and boundary excluded.
     */
    public static int nextRandomInt(int boundary) {
        return (int) (Math.random() * boundary);
    }

    /**
     * @return random integer between startBoundary and endBoundary excluded.
     */
    public static int nextRandomInt(int startBoundary, int endBoundary) {
        return (int) (Math.random() * (endBoundary - startBoundary) + startBoundary);
    }


}
