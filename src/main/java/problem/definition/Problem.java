package problem.definition;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import problem.extension.SolutionMethod;
import problem.extension.TypeSolutionMethod;
import factory_interface.IFFactorySolutionMethod;
import factory_method.FactorySolutionMethod;

/**
 * @file Problem.java
 * @brief Definición y configuración de un problema de optimización en BiCIAM.
 * 
 * Modela la especificación de un problema de optimización (mono u multiobjetivo),
 * administrando las funciones objetivo, codificación, operadores de mutación y cruce,
 * y métodos de resolución aplicables.
 * 
 * @author Laboratorio de Desarrollo y Herramientas (ULL)
 * @version 1.0.0
 */
public class Problem {

	/**
	 * Enumerado con el tipo de optimización a realizar.
	 */
	public enum ProblemType {
		Maximizar, Minimizar;
	}

	/**
	 * Operador por defecto que evita NullPointerException cuando no se ha configurado
	 * un operador explícito en el problema.
	 */
	private static class DefaultOperator extends Operator {
		@Override
		public java.util.List<State> generatedNewState(State stateCurrent, Integer operatornumber) {
			return new ArrayList<State>();
		}
		@Override
		public java.util.List<State> generateRandomState(Integer operatornumber) {
			ArrayList<State> list = new ArrayList<State>();
			list.add(new State());
			return list;
		}
	}

	/** Lista de funciones objetivo a evaluar */
	private ArrayList<ObjetiveFunction> function = new ArrayList<ObjetiveFunction>();
	/** Estado actual o de referencia del problema */
	private State state = new State();
	/** Criterio de optimización (Maximizar o Minimizar) */
	private ProblemType typeProblem = ProblemType.Maximizar;
	/** Esquema de codificación de soluciones */
	private Codification codification;
	/** Operador genético o de vecindad para generación de estados */
	private Operator operator = new DefaultOperator();
	/** Cantidad de valores posibles para las variables de decisión */
	private int possibleValue;
	/** Tipo de método de resolución multiobjetivo configurado */
	private TypeSolutionMethod typeSolutionMethod;
	/** Factoría encargada de instanciar el método de resolución */
	private IFFactorySolutionMethod factorySolutionMethod;
	
	/**
	 * Constructor por defecto.
	 */
	public Problem() {
		super();
	}

	/**
	 * Obtiene la lista de funciones objetivo.
	 * @return Lista de instancias de ObjetiveFunction.
	 */
	public ArrayList<ObjetiveFunction> getFunction() {
		return function;
	}

	/**
	 * Asigna la lista de funciones objetivo.
	 * @param function Lista de funciones a evaluar.
	 */
	public void setFunction(ArrayList<ObjetiveFunction> function) {
		this.function = function;
	}

	/**
	 * Obtiene el estado actual del problema.
	 * @return Estado actual.
	 */
	public State getState() {
		return state;
	}

	/**
	 * Establece el estado actual del problema.
	 * @param state Nuevo estado.
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * Obtiene el tipo de optimización (Maximizar o Minimizar).
	 * @return Tipo de problema.
	 */
	public ProblemType getTypeProblem() {
		return typeProblem;
	}

	/**
	 * Asigna el tipo de problema de optimización.
	 * @param typeProblem Maximizar o Minimizar.
	 */
	public void setTypeProblem(ProblemType typeProblem) {
		this.typeProblem = typeProblem;
	}

	/**
	 * Obtiene la codificación del problema.
	 * @return Objeto Codification asociado.
	 */
	public Codification getCodification() {
		return codification;
	}

	/**
	 * Establece la codificación del problema.
	 * @param codification Esquema de codificación.
	 */
	public void setCodification(Codification codification) {
		this.codification = codification;
	}

	/**
	 * Obtiene el operador genético/vecindad configurado.
	 * @return Operador de generación de estados.
	 */
	public Operator getOperator() {
		if (operator == null) {
			operator = new DefaultOperator();
		}
		return operator;
	}

	/**
	 * Establece el operador de variación.
	 * @param operator Instancia de Operator.
	 */
	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	/**
	 * Obtiene el número de posibles valores por variable.
	 * @return Entero con los posibles valores.
	 */
	public int getPossibleValue() {
		return possibleValue;
	}

	/**
	 * Establece el número de posibles valores por variable.
	 * @param possibleValue Número de valores discretos.
	 */
	public void setPossibleValue(int possibleValue) {
		this.possibleValue = possibleValue;
	}

	/**
	 * Evalúa un estado calculando los valores de las funciones objetivo.
	 * @param state Estado a evaluar.
	 * @throws IllegalArgumentException En caso de error en llamada reflexiva.
	 * @throws SecurityException En caso de violación de seguridad.
	 * @throws ClassNotFoundException Si no se encuentra la clase de resolución.
	 * @throws InstantiationException Si falla la instanciación.
	 * @throws IllegalAccessException Si no hay acceso a constructores reflexivos.
	 * @throws InvocationTargetException Si el constructor lanza excepción.
	 * @throws NoSuchMethodException Si no existe método requerido.
	 */
	public void Evaluate(State state) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		double eval = 0;       
		ArrayList<Double> evaluation = new ArrayList<Double>(this.function.size());
		if (typeSolutionMethod == null) {
			eval = function.get(0).Evaluation(state);
			evaluation.add(evaluation.size(), eval);
			state.setEvaluation(evaluation);
		} else {
			SolutionMethod method = newSolutionMethod(typeSolutionMethod);
			method.evaluationState(state);
		}
	}
	
	/**
	 * Obtiene el tipo de método de solución multiobjetivo.
	 * @return Tipo de método de resolución.
	 */
	public TypeSolutionMethod getTypeSolutionMethod() {
		return typeSolutionMethod;
	}

	/**
	 * Asigna el tipo de método de solución multiobjetivo.
	 * @param typeSolutionMethod Tipo de método a configurar.
	 */
	public void setTypeSolutionMethod(TypeSolutionMethod typeSolutionMethod) {
		this.typeSolutionMethod = typeSolutionMethod;
	}

	/**
	 * Obtiene la factoría de métodos de solución.
	 * @return Factoría IFFactorySolutionMethod.
	 */
	public IFFactorySolutionMethod getFactorySolutionMethod() {
		return factorySolutionMethod;
	}

	/**
	 * Asigna la factoría de métodos de solución.
	 * @param factorySolutionMethod Instancia de la factoría.
	 */
	public void setFactorySolutionMethod(IFFactorySolutionMethod factorySolutionMethod) {
		this.factorySolutionMethod = factorySolutionMethod;
	}
	
	/**
	 * Instancia un nuevo método de solución basado en el tipo especificado mediante reflexión.
	 * @param typeSolutionMethod Tipo de método deseado.
	 * @return Instancia concreta de SolutionMethod.
	 * @throws IllegalArgumentException Si argumentos son inválidos.
	 * @throws SecurityException Si hay restricción de seguridad.
	 * @throws ClassNotFoundException Si la clase no se encuentra.
	 * @throws InstantiationException Si no se puede instanciar.
	 * @throws IllegalAccessException Si no hay permisos de acceso reflexivo.
	 * @throws InvocationTargetException Si el constructor falla.
	 * @throws NoSuchMethodException Si el constructor no existe.
	 */
	public SolutionMethod newSolutionMethod(TypeSolutionMethod typeSolutionMethod) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		factorySolutionMethod = new FactorySolutionMethod();
		SolutionMethod solutionMethod = factorySolutionMethod.createdSolutionMethod(typeSolutionMethod);
		return solutionMethod;
	}
}
