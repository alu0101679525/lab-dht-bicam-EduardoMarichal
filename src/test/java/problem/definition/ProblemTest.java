package problem.definition;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import problem.definition.Problem.ProblemType;
import problem.extension.TypeSolutionMethod;
import factory_interface.IFFactorySolutionMethod;
import factory_method.FactorySolutionMethod;

class ProblemTest {

    @Test
    void testGettersAndSetters() {
        Problem problem = new Problem();

        problem.setTypeProblem(ProblemType.Maximizar);
        assertEquals(ProblemType.Maximizar, problem.getTypeProblem());

        problem.setTypeProblem(ProblemType.Minimizar);
        assertEquals(ProblemType.Minimizar, problem.getTypeProblem());

        State state = new State();
        problem.setState(state);
        assertEquals(state, problem.getState());

        problem.setPossibleValue(15);
        assertEquals(15, problem.getPossibleValue());

        Operator op = new Operator() {
            @Override
            public List<State> generatedNewState(State stateCurrent, Integer operatornumber) { return null; }
            @Override
            public List<State> generateRandomState(Integer operatornumber) { return null; }
        };
        problem.setOperator(op);
        assertEquals(op, problem.getOperator());

        Codification cod = new Codification() {
            @Override
            public boolean validState(State s) { return true; }
            @Override
            public Object getVariableAleatoryValue(int key) { return 0; }
            @Override
            public int getAleatoryKey() { return 0; }
            @Override
            public int getVariableCount() { return 2; }
        };
        problem.setCodification(cod);
        assertEquals(cod, problem.getCodification());

        problem.setTypeSolutionMethod(TypeSolutionMethod.MultiObjetivoPuro);
        assertEquals(TypeSolutionMethod.MultiObjetivoPuro, problem.getTypeSolutionMethod());

        IFFactorySolutionMethod factory = new FactorySolutionMethod();
        problem.setFactorySolutionMethod(factory);
        assertEquals(factory, problem.getFactorySolutionMethod());
    }

    @Test
    void testEvaluateWithSingleFunction() throws Exception {
        Problem problem = new Problem();
        ArrayList<ObjetiveFunction> functions = new ArrayList<>();
        functions.add(new ObjetiveFunction() {
            @Override
            public Double Evaluation(State s) {
                return 42.0;
            }
        });
        problem.setFunction(functions);

        State state = new State();
        problem.Evaluate(state);

        assertNotNull(state.getEvaluation());
        assertEquals(1, state.getEvaluation().size());
        assertEquals(42.0, state.getEvaluation().get(0));
    }

    @Test
    void testNewSolutionMethod() throws Exception {
        Problem problem = new Problem();
        assertNotNull(problem.newSolutionMethod(TypeSolutionMethod.MultiObjetivoPuro));
    }
}
