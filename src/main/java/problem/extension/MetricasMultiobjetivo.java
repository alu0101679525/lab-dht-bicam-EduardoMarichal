package problem.extension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import problem.definition.State;

/**
 * @file MetricasMultiobjetivo.java
 * @brief Cálculo de métricas de calidad y convergencia para frentes de Pareto.
 * 
 * Proporciona métodos para evaluar la calidad de los frentes de Pareto aproximados respecto
 * al frente verdadero: Tasa de Error, Distancia Generacional, Dispersión y estadísticas básicas.
 * 
 * @author Laboratorio de Desarrollo y Herramientas (ULL)
 * @version 1.0.0
 */
public class MetricasMultiobjetivo {

	/**
	 * Calcula la tasa de error: porcentaje de soluciones del frente aproximado
	 * que no pertenecen al frente de Pareto verdadero.
	 * 
	 * @param solutionsFPcurrent Frente de Pareto aproximado obtenido por el algoritmo.
	 * @param solutionsFPtrue Frente de Pareto de referencia (verdadero).
	 * @return Valor en el rango [0.0, 1.0] representando la tasa de error.
	 */
	public double TasaError(List<State> solutionsFPcurrent, List<State> solutionsFPtrue) {
		int tasaError = 0;
		for (int i = 0; i < solutionsFPcurrent.size(); i++) {
			State solutionVO = solutionsFPcurrent.get(i);
			if (!Contains(solutionVO, solutionsFPtrue)) {
				tasaError++;
			}
		}
		return (double) tasaError / solutionsFPcurrent.size();
	}
	
	/**
	 * Calcula la Distancia Generacional (GD): mide cuán lejos se encuentran
	 * las soluciones del frente aproximado respecto al frente de Pareto de referencia.
	 * 
	 * @param solutionsFPcurrent Frente aproximado actual.
	 * @param solutionsFPtrue Frente de referencia verdadero.
	 * @return Distancia generacional promedio euclidiana.
	 */
	public double DistanciaGeneracional(List<State> solutionsFPcurrent, List<State> solutionsFPtrue) {
		float distanciaGeneracional = 0;
		for (int i = 0; i < solutionsFPcurrent.size(); i++) {
			State solutionVO = solutionsFPcurrent.get(i);
			float min = 1000;
			for (int j = 0; j < solutionsFPtrue.size(); j++) { 
				float distancia = 0;
				for (int j2 = 0; j2 < solutionVO.getEvaluation().size(); j2++) {
					State solutionFPV = solutionsFPtrue.get(j);
					distancia += (solutionVO.getEvaluation().get(j2) - solutionFPV.getEvaluation().get(j2)) *  
							(solutionVO.getEvaluation().get(j2) - solutionFPV.getEvaluation().get(j2));
				}
				if (distancia < min) {
					min = distancia;
				}
			}
			distanciaGeneracional += min;
		}
		return Math.sqrt(distanciaGeneracional) / solutionsFPcurrent.size();
	}

	/**
	 * Calcula la dispersión o espaciamiento entre las soluciones del frente obtenido.
	 * 
	 * @param solutions Lista de soluciones a evaluar.
	 * @return Valor double con la desviación o dispersión del frente.
	 */
	public double Dispersion(ArrayList<State> solutions) {
		LinkedList<Float> distancias = new LinkedList<Float>();
		for (Iterator<State> iter = solutions.iterator(); iter.hasNext();) {
			State solutionVO = iter.next();
			float min = 1000;
			for (Iterator<State> iterator = solutions.iterator(); iterator.hasNext();) {
				State solVO = iterator.next();
				float distancia = 0;
				for (int i = 0; i < solutionVO.getEvaluation().size(); i++) {
					if (!solutionVO.getEvaluation().equals(solVO.getEvaluation())) {
						distancia += (solutionVO.getEvaluation().get(i) - solVO.getEvaluation().get(i));
					}
				}
				if (distancia < min) {
					min = distancia;
				}
			}
			distancias.add(Float.valueOf(min));
		}
		float sum = 0;
		for (Iterator<Float> iter = distancias.iterator(); iter.hasNext();) {
			Float dist = iter.next();
			sum += dist;
		}
		float media = sum / distancias.size();
		float sumDistancias = 0;
		for (Iterator<Float> iter = distancias.iterator(); iter.hasNext();) {
			Float dist = iter.next();
			sumDistancias += Math.pow((media - dist), 2);
		}
		double dispersion = 0;
		if (solutions.size() > 1) {
			dispersion = Math.sqrt((1.0 / (solutions.size() - 1)) * sumDistancias);
		}
		return dispersion;
	}

	/**
	 * Comprueba si una solución está contenida en una lista según sus evaluaciones.
	 * 
	 * @param solA Solución a buscar.
	 * @param solutions Lista de soluciones.
	 * @return true si coincide la evaluación, false en caso contrario.
	 */
	private boolean Contains(State solA, List<State> solutions) {
		int i = 0;
		boolean result = false;
		while (i < solutions.size() && !result) {
			if (solutions.get(i).getEvaluation().equals(solA.getEvaluation())) {
				result = true;
			} else {
				i++;
			}
		}
		return result;
	}

	/**
	 * Calcula el valor mínimo en una lista de métricas.
	 * @param allMetrics Colección de valores numéricos de métricas.
	 * @return Valor mínimo encontrado.
	 */
	public double CalcularMin(ArrayList<Double> allMetrics) {
		double min = 1000;
		for (Iterator<Double> iter = allMetrics.iterator(); iter.hasNext();) {
			double element = iter.next();
			if (element < min) {
				min = element;
			}
		}
		return min;
	}

	/**
	 * Calcula el valor máximo en una lista de métricas.
	 * @param allMetrics Colección de valores numéricos de métricas.
	 * @return Valor máximo encontrado.
	 */
	public double CalcularMax(ArrayList<Double> allMetrics) {
		double max = 0;
		for (Iterator<Double> iter = allMetrics.iterator(); iter.hasNext();) {
			double element = iter.next();
			if (element > max) {
				max = element;
			}
		}
		return max;
	}

	/**
	 * Calcula el valor medio o promedio en una lista de métricas.
	 * @param allMetrics Colección de valores numéricos de métricas.
	 * @return Media aritmética de las métricas.
	 */
	public double CalcularMedia(ArrayList<Double> allMetrics) {
		double sum = 0;
		for (Iterator<Double> iter = allMetrics.iterator(); iter.hasNext();) {
			double element = iter.next();
			sum = sum + element;
		}
		return sum / allMetrics.size();
	}
}
