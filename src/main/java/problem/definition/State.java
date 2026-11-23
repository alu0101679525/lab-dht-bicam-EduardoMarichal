package problem.definition;

import java.util.ArrayList;
import metaheuristics.generators.GeneratorType;

/**
 * @file State.java
 * @brief Representación del estado de una solución en un problema de optimización.
 * 
 * Modela una solución candidata en el espacio de búsqueda del problema, almacenando su
 * codificación cromosómica, evaluaciones multiobjetivo y tipo de generador empleado.
 * 
 * @author Laboratorio de Desarrollo y Herramientas (ULL)
 * @version 1.0.0
 */
public class State implements Cloneable {
	
	/** Tipo de generador heurístico que produjo este estado */
	protected GeneratorType typeGenerator;
	/** Vector con los valores de las funciones objetivo asociadas al estado */
	protected ArrayList<Double> evaluation;
	/** Identificador numérico del estado dentro de una población o secuencia */
	protected int number;
	/** Codificación interna de la solución (alelos, variables o parámetros) */
	protected ArrayList<Object> code;
	
	/**
	 * Constructor copia.
	 * @param ps Estado fuente del cual clonar atributos y valores.
	 */
	public State(State ps) {
		typeGenerator = ps.getTypeGenerator();
		evaluation = ps.getEvaluation();
		number = ps.getNumber();
		code = new ArrayList<Object>(ps.getCode());
	}
	
	/**
	 * Constructor que inicializa el estado con una codificación específica.
	 * @param code Lista de objetos que representan la codificación de la solución.
	 */
	public State(ArrayList<Object> code) {
		super();
		this.code = code;
	}
	
	/**
	 * Constructor por defecto. Inicializa una lista vacía de codificación.
	 */
	public State() {
		code = new ArrayList<Object>();
	}	
	
	/**
	 * Obtiene la codificación de la solución.
	 * @return Lista de objetos de la codificación.
	 */
	public ArrayList<Object> getCode() {
		return code;
	}

	/**
	 * Establece la codificación de la solución.
	 * @param listCode Lista de objetos para la nueva codificación.
	 */
	public void setCode(ArrayList<Object> listCode) {
		this.code = listCode;
	}

	/**
	 * Obtiene el tipo de generador heurístico asociado al estado.
	 * @return Tipo de generador.
	 */
	public GeneratorType getTypeGenerator() {
		return typeGenerator;
	}

	/**
	 * Asigna el tipo de generador heurístico al estado.
	 * @param typeGenerator Tipo de generador a asignar.
	 */
	public void setTypeGenerator(GeneratorType typeGenerator) {
		this.typeGenerator = typeGenerator;
	}

	/**
	 * Obtiene el vector de evaluaciones de las funciones objetivo.
	 * @return Lista de evaluaciones de tipo Double.
	 */
	public ArrayList<Double> getEvaluation() {
		return evaluation;
	}

	/**
	 * Establece el vector de evaluaciones de las funciones objetivo.
	 * @param evaluation Lista de evaluaciones calculadas.
	 */
	public void setEvaluation(ArrayList<Double> evaluation) {
		this.evaluation = evaluation;
	}

	/**
	 * Obtiene el número identificador del estado.
	 * @return Entero con el número de estado.
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Asigna el identificador numérico al estado.
	 * @param number Número de estado.
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * Crea y devuelve una copia superficial o profunda de este estado.
	 * @return Nueva instancia de State con los mismos valores.
	 */
	@Override
	public State clone(){
		try {
			return (State) super.clone();
		} catch (CloneNotSupportedException e) {
			return new State(this);
		}
	}
	
	/**
	 * Obtiene una copia del estado basada en su codificación.
	 * @return Nueva instancia de State con copia de código.
	 */
	public Object getCopy(){
		return new State(this.getCode());
	}
	
	/**
	 * Compara si dos estados tienen idéntica codificación.
	 * @param state Estado con el que comparar.
	 * @return true si las codificaciones coinciden, false en caso contrario.
	 */
	public boolean Comparator(State state){
		boolean result = false;
		if (state.getCode().equals(getCode())) {
			result = true;
		}
		return result;
	}

	/**
	 * Calcula la distancia de Hamming entre las codificaciones de dos estados.
	 * @param state Estado de referencia.
	 * @return Número de posiciones diferentes entre ambas codificaciones.
	 */
	public double Distance(State state){
		int distancia = 0;
		for (int i = 0; i < state.getCode().size(); i++) {
			if (!(state.getCode().get(i).equals(this.getCode().get(i)))) {
				distancia++;
			}
		}
		return distancia;
	}
}
