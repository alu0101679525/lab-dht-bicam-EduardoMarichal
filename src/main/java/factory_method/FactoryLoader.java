package factory_method;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FactoryLoader {

    private static final Logger LOGGER = Logger.getLogger(FactoryLoader.class.getName());

    private FactoryLoader() {
        // Utility class
    }

    public static Object getInstance(String className) throws ClassNotFoundException, IllegalArgumentException,
            SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        Class<?> c = null;
        try {
            c = Class.forName(className);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.WARNING, "El nombre de la clase no existe en el classpath");
            return null;
        }

        Object o = null;
        try {
            o = c.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            LOGGER.log(Level.WARNING, "Error al instanciar la clase");
        }
        return o;
    }
}
