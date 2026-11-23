package es.ull.laboratorio;

import config.RandomUtil;
import problem.definition.Problem;
import problem.definition.State;

import java.util.ArrayList;

/**
 * @file Main.java
 * @brief Punto de entrada ejecutable principal para el contenedor Docker y la aplicación BiCIAM.
 * 
 * Permite ejecutar y verificar el funcionamiento del framework BiCIAM dentro de una
 * imagen Docker ligera (openjdk:17-jre-slim).
 * 
 * @author Laboratorio de Desarrollo y Herramientas (ULL)
 * @version 1.0.0
 */
public class Main {

    /**
     * Método principal que inicializa una demostración básica del framework BiCIAM.
     * 
     * @param args Argumentos de la línea de comandos (opcionales).
     */
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  BiCIAM - Framework de Optimizacion Multiobjetivo");
        System.out.println("  Practica 5: Gestion de Proyecto y Pruebas Unitarias");
        System.out.println("  Laboratorio de Desarrollo y Herramientas (ULL)");
        System.out.println("==================================================");

        // Inicializar componentes de BiCIAM
        Problem problem = new Problem();
        problem.setTypeProblem(Problem.ProblemType.Maximizar);

        State state = new State();
        ArrayList<Object> code = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            code.add(RandomUtil.nextInt(100));
        }
        state.setCode(code);

        ArrayList<Double> eval = new ArrayList<>();
        eval.add(RandomUtil.nextDouble() * 10.0);
        eval.add(RandomUtil.nextDouble() * 10.0);
        state.setEvaluation(eval);

        System.out.println("Estado inicial generado:");
        System.out.println(" - Codificacion (genes): " + state.getCode());
        System.out.println(" - Evaluacion multiobjetivo: " + state.getEvaluation());
        System.out.println(" - Tipo de optimizacion: " + problem.getTypeProblem());
        System.out.println("==================================================");
        System.out.println("  Aplicacion BiCIAM ejecutada con exito en contenedor!");
        System.out.println("==================================================");
    }
}
