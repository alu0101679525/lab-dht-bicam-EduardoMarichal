package evolutionary_algorithms.complement;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import problem.definition.State;

class UniformCrossoverTest {

    @Test
    void testMascara() {
        UniformCrossover crossover = new UniformCrossover();
        int length = 10;
        int[] mask = crossover.mascara(length);

        assertEquals(length, mask.length);
        for (int val : mask) {
            assertTrue(val == 0 || val == 1, "Mask element should be 0 or 1");
        }
    }

    @Test
    void testCrossover() {
        UniformCrossover crossover = new UniformCrossover();
        State father1 = new State(new ArrayList<>(List.of(1, 1, 1, 1)));
        State father2 = new State(new ArrayList<>(List.of(2, 2, 2, 2)));

        State child = crossover.crossover(father1, father2, 0.9);
        assertNotNull(child);
        assertEquals(4, child.getCode().size());
        for (Object gene : child.getCode()) {
            assertTrue((Integer) gene == 1 || (Integer) gene == 2);
        }
    }
}
