package config;

import java.security.SecureRandom;

/**
 * @file RandomUtil.java
 * @brief Clase de utilidad para la generación de números pseudoaleatorios criptográficamente seguros.
 * 
 * Esta clase sustituye el uso inseguro de java.util.Random por SecureRandom para mitigar
 * vulnerabilidades de tipo PREDICTABLE_RANDOM detectadas en el análisis SAST.
 * 
 * @author Laboratorio de Desarrollo y Herramientas (ULL)
 * @version 1.0.0
 */
public final class RandomUtil {

    /** Instancia compartida de SecureRandom para generación aleatoria segura */
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Constructor privado para prevenir instanciación de clase de utilidad.
     */
    private RandomUtil() {
        // Utility class
    }

    /**
     * Obtiene la instancia compartida de SecureRandom.
     * @return Instancia única de SecureRandom.
     */
    public static SecureRandom getRandom() {
        return RANDOM;
    }

    /**
     * Genera un número pseudoaleatorio de coma flotante entre 0.0 (inclusivo) y 1.0 (exclusivo).
     * @return Valor double pseudoaleatorio en el rango [0.0, 1.0).
     */
    public static double nextDouble() {
        return RANDOM.nextDouble();
    }

    /**
     * Genera un entero pseudoaleatorio uniformemente distribuido entre 0 (inclusivo)
     * y el límite especificado (exclusivo).
     * @param bound Límite superior exclusivo (debe ser mayor que 0).
     * @return Entero pseudoaleatorio entre 0 y bound - 1, o 0 si bound <= 0.
     */
    public static int nextInt(int bound) {
        return bound > 0 ? RANDOM.nextInt(bound) : 0;
    }
}
