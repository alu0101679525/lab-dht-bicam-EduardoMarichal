package config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RandomUtilTest {

    @Test
    void testGetRandomNotNull() {
        assertNotNull(RandomUtil.getRandom());
    }

    @Test
    void testNextDouble() {
        for (int i = 0; i < 50; i++) {
            double value = RandomUtil.nextDouble();
            assertTrue(value >= 0.0 && value < 1.0, "Value should be in range [0.0, 1.0): " + value);
        }
    }

    @Test
    void testNextIntWithPositiveBound() {
        int bound = 10;
        for (int i = 0; i < 50; i++) {
            int value = RandomUtil.nextInt(bound);
            assertTrue(value >= 0 && value < bound, "Value should be in range [0, " + bound + "): " + value);
        }
    }

    @Test
    void testNextIntWithZeroOrNegativeBound() {
        assertEquals(0, RandomUtil.nextInt(0));
        assertEquals(0, RandomUtil.nextInt(-5));
    }
}
