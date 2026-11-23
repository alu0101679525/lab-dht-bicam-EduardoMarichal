package problem.extension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import problem.definition.State;

class MetricasMultiobjetivoTest {

    private MetricasMultiobjetivo metricas;

    @BeforeEach
    void setUp() {
        metricas = new MetricasMultiobjetivo();
    }

    private State createState(List<Object> code, List<Double> eval) {
        State s = new State(new ArrayList<>(code));
        s.setEvaluation(new ArrayList<>(eval));
        return s;
    }

    @Test
    void testTasaErrorAllMatching() {
        State s1 = createState(List.of(1, 2), List.of(10.0, 20.0));
        State s2 = createState(List.of(3, 4), List.of(30.0, 40.0));

        List<State> current = List.of(s1, s2);
        List<State> trueFP = List.of(s1, s2);

        double error = metricas.TasaError(current, trueFP);
        assertEquals(0.0, error, 0.0001);
    }

    @Test
    void testTasaErrorNoneMatching() {
        State s1 = createState(List.of(1, 2), List.of(10.0, 20.0));
        State s2 = createState(List.of(3, 4), List.of(30.0, 40.0));

        List<State> current = List.of(s1);
        List<State> trueFP = List.of(s2);

        double error = metricas.TasaError(current, trueFP);
        assertEquals(1.0, error, 0.0001);
    }

    @Test
    void testDistanciaGeneracional() {
        State s1 = createState(List.of(1, 2), List.of(1.0, 2.0));
        State s2 = createState(List.of(1, 2), List.of(1.0, 2.0));

        List<State> current = List.of(s1);
        List<State> trueFP = List.of(s2);

        double dist = metricas.DistanciaGeneracional(current, trueFP);
        assertEquals(0.0, dist, 0.0001);

        State s3 = createState(List.of(1, 2), List.of(4.0, 6.0));
        double distDiff = metricas.DistanciaGeneracional(List.of(s3), trueFP);
        assertTrue(distDiff > 0.0);
    }

    @Test
    void testDispersion() {
        State s1 = createState(List.of(1, 2), List.of(1.0, 2.0));
        State s2 = createState(List.of(3, 4), List.of(3.0, 5.0));
        State s3 = createState(List.of(5, 6), List.of(6.0, 9.0));

        ArrayList<State> solutions = new ArrayList<>(List.of(s1, s2, s3));
        double dispersion = metricas.Dispersion(solutions);
        assertTrue(dispersion >= 0.0);
    }

    @Test
    void testCalcularMinMaxMedia() {
        ArrayList<Double> values = new ArrayList<>(List.of(10.0, 20.0, 30.0));

        assertEquals(10.0, metricas.CalcularMin(values), 0.0001);
        assertEquals(30.0, metricas.CalcularMax(values), 0.0001);
        assertEquals(20.0, metricas.CalcularMedia(values), 0.0001);
    }
}
