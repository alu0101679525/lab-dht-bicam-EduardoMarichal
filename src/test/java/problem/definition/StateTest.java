package problem.definition;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metaheuristics.generators.GeneratorType;

class StateTest {

    private State state;
    private ArrayList<Object> code;
    private ArrayList<Double> evaluation;

    @BeforeEach
    void setUp() {
        code = new ArrayList<>();
        code.add(1);
        code.add(2);
        code.add(3);

        evaluation = new ArrayList<>();
        evaluation.add(10.5);
        evaluation.add(20.0);

        state = new State(code);
        state.setEvaluation(evaluation);
        state.setNumber(42);
        state.setTypeGenerator(GeneratorType.GeneticAlgorithm);
    }

    @Test
    void testDefaultConstructor() {
        State defaultState = new State();
        assertNotNull(defaultState.getCode());
        assertTrue(defaultState.getCode().isEmpty());
    }

    @Test
    void testConstructorWithCode() {
        assertEquals(code, state.getCode());
    }

    @Test
    void testCopyConstructor() {
        State copy = new State(state);
        assertEquals(state.getNumber(), copy.getNumber());
        assertEquals(state.getTypeGenerator(), copy.getTypeGenerator());
        assertEquals(state.getEvaluation(), copy.getEvaluation());
        assertEquals(state.getCode(), copy.getCode());
    }

    @Test
    void testGettersAndSetters() {
        state.setNumber(99);
        assertEquals(99, state.getNumber());

        state.setTypeGenerator(GeneratorType.HillClimbing);
        assertEquals(GeneratorType.HillClimbing, state.getTypeGenerator());

        ArrayList<Object> newCode = new ArrayList<>(List.of(4, 5, 6));
        state.setCode(newCode);
        assertEquals(newCode, state.getCode());

        ArrayList<Double> newEval = new ArrayList<>(List.of(5.0, 15.0));
        state.setEvaluation(newEval);
        assertEquals(newEval, state.getEvaluation());
    }

    @Test
    void testCloneAndGetCopy() {
        State cloned = state.clone();
        assertNotNull(cloned);
        assertEquals(state.getCode(), cloned.getCode());

        Object copyObj = state.getCopy();
        assertTrue(copyObj instanceof State);
        State copyState = (State) copyObj;
        assertEquals(state.getCode(), copyState.getCode());
    }

    @Test
    void testComparator() {
        State identical = new State(new ArrayList<>(code));
        assertTrue(state.Comparator(identical));

        State different = new State(new ArrayList<>(List.of(1, 2, 4)));
        assertFalse(state.Comparator(different));
    }

    @Test
    void testDistance() {
        State identical = new State(new ArrayList<>(code));
        assertEquals(0.0, state.Distance(identical));

        State diffOne = new State(new ArrayList<>(List.of(1, 2, 99)));
        assertEquals(1.0, state.Distance(diffOne));

        State diffAll = new State(new ArrayList<>(List.of(97, 98, 99)));
        assertEquals(3.0, state.Distance(diffAll));
    }
}
