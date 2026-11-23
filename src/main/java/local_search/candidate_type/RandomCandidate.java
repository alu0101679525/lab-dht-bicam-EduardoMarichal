/**
 * @(#) AleatoryCandidate.java
 */

package local_search.candidate_type;

import config.RandomUtil;
import java.util.List;
import problem.definition.State;

public class RandomCandidate extends SearchCandidate {

	@Override
	public State stateSearch(List<State> listNeighborhood) {
		int bound = listNeighborhood.size();
		int pos = bound > 0 ? RandomUtil.nextInt(bound) : 0;
		State stateAleatory = listNeighborhood.get(pos);
		return stateAleatory;
	}
}
