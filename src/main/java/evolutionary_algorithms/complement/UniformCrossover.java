package evolutionary_algorithms.complement;

import config.RandomUtil;
import problem.definition.State;

/**
 * @file UniformCrossover.java
 * @brief Operador de cruce uniforme para algoritmos genéticos y evolutivos.
 * 
 * Implementa el cruce uniforme entre dos estados progenitores mediante una máscara
 * binaria pseudoaleatoria generada con SecureRandom.
 * 
 * @author Laboratorio de Desarrollo y Herramientas (ULL)
 * @version 1.0.0
 */
public class UniformCrossover extends Crossover {
	
	/**
	 * Genera una máscara binaria pseudoaleatoria de ceros y unos.
	 * @param length Longitud del vector o número de genes.
	 * @return Array de enteros con valores 0 o 1.
	 */
	public int[] mascara(int length){
		int[] mascara = new int[length];
		for (int i = 0; i < mascara.length; i++) {
			mascara[i] = RandomUtil.nextInt(2);
		}
		return mascara;
	}	
    
	/**
	 * Realiza la recombinación de dos progenitores para producir un descendiente.
	 * @param father1 Primer estado progenitor.
	 * @param father2 Segundo estado progenitor.
	 * @param PC Probabilidad de cruce.
	 * @return Nuevo estado descendiente producto de la recombinación.
	 */
	@Override
	public State crossover(State father1, State father2, double PC) {
		Object value = new Object();
		State state = (State) father1.getCopy();
		int[] mascara = mascara(father1.getCode().size());
		for (int k = 0; k < mascara.length; k++) {
			if (mascara[k] == 1) {
				value = father1.getCode().get(k);
				state.getCode().set(k, value);
			} else {
				if (mascara[k] == 0) {
					value = father2.getCode().get(k);  
					state.getCode().set(k, value);
				}
			}
		}
		return state;
	}
}
