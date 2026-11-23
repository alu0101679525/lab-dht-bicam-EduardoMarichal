package factory_method;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @file FactoryLoader.java
 * @brief Cargador reflexivo de clases e instanciador dinámico para patrones de factoría.
 * 
 * Permite la carga dinámica de clases y la instanciación de objetos en tiempo de ejecución
 * mediante la API de reflexión de Java, con manejo seguro de excepciones y registro por Logger.
 * 
 * @author Laboratorio de Desarrollo y Herramientas (ULL)
 * @version 1.0.0
 */
public class FactoryLoader {

    /** Logger para registro de incidencias en instanciación reflexiva */
    private static final Logger LOGGER = Logger.getLogger(FactoryLoader.class.getName());

    /**
     * Constructor privado para prevenir instanciación de clase de utilidad.
     */
    private FactoryLoader() {
        // Utility class
    }

    /**
     * Carga dinámicamente una clase por su nombre cualificado e instancia un nuevo objeto.
     * 
     * @param className Nombre completamente cualificado de la clase a instanciar.
     * @return Nueva instancia del objeto o null si la clase no existe o falla la instanciación.
     * @throws ClassNotFoundException Si la clase no se encuentra.
     * @throws IllegalArgumentException Si los argumentos para el constructor son inválidos.
     * @throws SecurityException Si las políticas de seguridad impiden la reflexión.
     * @throws InstantiationException Si la clase no se puede instanciar (abstracta, interfaz, etc.).
     * @throws IllegalAccessException Si no hay acceso al constructor.
     * @throws InvocationTargetException Si el constructor lanza una excepción interna.
     * @throws NoSuchMethodException Si la clase carece de constructor por defecto.
     */
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
