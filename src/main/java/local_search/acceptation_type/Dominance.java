package local_search.acceptation_type;

import java.util.List;

import metaheurictics.strategy.Strategy;
import metaheuristics.generators.GeneratorType;
import metaheuristics.generators.MultiobjectiveHillClimbingDistance;
import problem.definition.Problem.ProblemType;
import problem.definition.State;

/**
 * @file Dominance.java
 * @brief Criterio de dominancia de Pareto para problemas de optimización multiobjetivo.
 * 
 * Implementa las relaciones de dominancia entre soluciones candidatas conforme a la teoría
 * de Pareto y mantiene el conjunto o frente de soluciones no dominadas.
 * 
 * @author Laboratorio de Desarrollo y Herramientas (ULL)
 * @version 1.0.0
 */
public class Dominance {

	/**
	 * Determina si la solución candidata solutionX domina a alguna solución de la lista
	 * de no dominadas, actualizando la lista eliminando las dominadas y añadiendo solutionX
	 * si no es dominada.
	 * 
	 * @param solutionX Solución candidata a verificar.
	 * @param list Lista de estados que conforman el frente actual de Pareto.
	 * @return true si la solución fue añadida a la lista, false en caso contrario.
	 */
	public boolean ListDominance(State solutionX, List<State> list){
		boolean domain = false;
		for (int i = 0; i < list.size() && domain == false; i++) {
			// Si la solución X domina a la solución de la lista
			if (dominance(solutionX, list.get(i))) {
				// Se elimina el elemento de la lista
				list.remove(i);
				if (i != 0) {
					i--;	
				}
				if (Strategy.getStrategy().generator.getType().equals(GeneratorType.MultiobjectiveHillClimbingDistance) && list.size() != 0) {
					MultiobjectiveHillClimbingDistance.DistanceCalculateAdd(list);
				}
			}
			if (list.size() > 0) {
				if (dominance(list.get(i), solutionX)) {
					domain = true;
				}
			}
		}
		// Si la solución X no fue dominada
		if (!domain) {
			// Comprobando que la solución no exista
			boolean found = false;
			for (int k = 0; k < list.size() && !found; k++) {
				State element = list.get(k);
				found = solutionX.Comparator(element);
			}
			// Si la solución no existe
			if (!found) {
				// Se guarda la solución candidata en la lista de soluciones óptimas de Pareto
				list.add(solutionX.clone());
				if (Strategy.getStrategy().generator.getType().equals(GeneratorType.MultiobjectiveHillClimbingDistance)) {
					MultiobjectiveHillClimbingDistance.DistanceCalculateAdd(list);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Determina si la solución X domina en el sentido de Pareto a la solución Y.
	 * Una solución X domina a Y si no es peor en ningún objetivo y es estrictamente
	 * mejor en al menos uno.
	 * 
	 * @param solutionX Primera solución a evaluar.
	 * @param solutionY Segunda solución contra la que comparar.
	 * @return true si solutionX domina a solutionY, false en caso contrario.
	 */
	public boolean dominance(State solutionX, State solutionY) {
		boolean dominance = false;
		int countBest = 0;
		int countEquals = 0;
		// Si el problema es de maximización
		if (Strategy.getStrategy().getProblem().getTypeProblem().equals(ProblemType.Maximizar)) {
			// Recorriendo las evaluaciones de las funciones objetivo
			for (int i = 0; i < solutionX.getEvaluation().size(); i++) {
				if (solutionX.getEvaluation().get(i).floatValue() > solutionY.getEvaluation().get(i).floatValue()) {
					countBest++;
				}
				if (solutionX.getEvaluation().get(i).floatValue() == solutionY.getEvaluation().get(i).floatValue()) {
					countEquals++;
				}	
			}
		} else {
			// Recorriendo las evaluaciones de las funciones objetivo para minimización
			for (int i = 0; i < solutionX.getEvaluation().size(); i++) {
				if (solutionX.getEvaluation().get(i).floatValue() < solutionY.getEvaluation().get(i).floatValue()) {
					countBest++;
				}
				if (solutionX.getEvaluation().get(i).floatValue() == solutionY.getEvaluation().get(i).floatValue()) {
					countEquals++;
				}	
			}
		}
		if ((countBest >= 1) && (countEquals + countBest == solutionX.getEvaluation().size())) {
			dominance = true;
		}
		return dominance;
	} 
}
