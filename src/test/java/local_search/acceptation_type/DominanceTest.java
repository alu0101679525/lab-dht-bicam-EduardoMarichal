package local_search.acceptation_type;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metaheurictics.strategy.Strategy;
import metaheuristics.generators.RandomSearch;
import problem.definition.Problem;
import problem.definition.Problem.ProblemType;
import problem.definition.State;

class DominanceTest {

    private Dominance dominance;
    private Problem problem;

    @BeforeEach
    void setUp() {
        dominance = new Dominance();
        problem = new Problem();
        Strategy.getStrategy().setProblem(problem);
        Strategy.getStrategy().generator = new RandomSearch();
    }

    private State createState(List<Object> code, List<Double> eval) {
        State s = new State(new ArrayList<>(code));
        s.setEvaluation(new ArrayList<>(eval));
        return s;
    }

    @Test
    void testDominanceMaximizar() {
        problem.setTypeProblem(ProblemType.Maximizar);

        State better = createState(List.of(1, 2), List.of(20.0, 30.0));
        State worse = createState(List.of(1, 2), List.of(10.0, 25.0));

        assertTrue(dominance.dominance(better, worse));
        assertFalse(dominance.dominance(worse, better));

        State equal = createState(List.of(1, 2), List.of(20.0, 30.0));
        assertFalse(dominance.dominance(better, equal));
    }

    @Test
    void testDominanceMinimizar() {
        problem.setTypeProblem(ProblemType.Minimizar);

        State better = createState(List.of(1, 2), List.of(5.0, 10.0));
        State worse = createState(List.of(1, 2), List.of(15.0, 20.0));

        assertTrue(dominance.dominance(better, worse));
        assertFalse(dominance.dominance(worse, better));
    }

    @Test
    void testListDominanceAddingDominatingSolution() {
        problem.setTypeProblem(ProblemType.Maximizar);

        State s1 = createState(List.of(1, 2), List.of(10.0, 20.0));
        List<State> paretoFront = new ArrayList<>();
        paretoFront.add(s1);

        State dominating = createState(List.of(3, 4), List.of(30.0, 40.0));
        boolean added = dominance.ListDominance(dominating, paretoFront);

        assertTrue(added);
        assertEquals(1, paretoFront.size());
        assertEquals(dominating.getCode(), paretoFront.get(0).getCode());
    }

    @Test
    void testListDominanceCandidateIsDominated() {
        problem.setTypeProblem(ProblemType.Maximizar);

        State strong = createState(List.of(1, 2), List.of(50.0, 50.0));
        List<State> paretoFront = new ArrayList<>();
        paretoFront.add(strong);

        State weak = createState(List.of(3, 4), List.of(10.0, 10.0));
        boolean added = dominance.ListDominance(weak, paretoFront);

        assertFalse(added);
        assertEquals(1, paretoFront.size());
    }

    @Test
    void testListDominanceCandidateAlreadyExists() {
        problem.setTypeProblem(ProblemType.Maximizar);

        State s1 = createState(List.of(1, 2), List.of(20.0, 30.0));
        List<State> paretoFront = new ArrayList<>();
        paretoFront.add(s1);

        State duplicate = createState(List.of(1, 2), List.of(20.0, 30.0));
        boolean added = dominance.ListDominance(duplicate, paretoFront);

        assertFalse(added);
    }
}
