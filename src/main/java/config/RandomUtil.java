package config;

import java.security.SecureRandom;

public final class RandomUtil {

    private static final SecureRandom RANDOM = new SecureRandom();

    private RandomUtil() {
        // Utility class
    }

    public static SecureRandom getRandom() {
        return RANDOM;
    }

    public static double nextDouble() {
        return RANDOM.nextDouble();
    }

    public static int nextInt(int bound) {
        return bound > 0 ? RANDOM.nextInt(bound) : 0;
    }
}
