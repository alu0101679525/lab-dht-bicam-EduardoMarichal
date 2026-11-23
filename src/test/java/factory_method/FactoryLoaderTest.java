package factory_method;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import problem.definition.State;

class FactoryLoaderTest {

    @Test
    void testGetInstanceValidClass() throws Exception {
        Object instance = FactoryLoader.getInstance("problem.definition.State");
        assertNotNull(instance);
        assertTrue(instance instanceof State);
    }

    @Test
    void testGetInstanceInvalidClass() throws Exception {
        Object instance = FactoryLoader.getInstance("non.existent.ClassPathName");
        assertNull(instance);
    }
}
