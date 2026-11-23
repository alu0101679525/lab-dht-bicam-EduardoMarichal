package metaheuristics.generators;

import factory_method.FactoryGenerator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InstanceDE implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(InstanceDE.class.getName());
	private boolean terminate = false;
	
	public void run() {
		FactoryGenerator ifFactoryGenerator = new FactoryGenerator();
		Generator generatorDE = null;
		try {
			generatorDE = ifFactoryGenerator.createGenerator(GeneratorType.DistributionEstimationAlgorithm);
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error al crear generador DE");
		}
		boolean find = false;
		int i = 0;
		while (find == false) {
			if(MultiGenerator.getListGenerators()[i].getType().equals(GeneratorType.DistributionEstimationAlgorithm)){
				MultiGenerator.getListGenerators()[i] = generatorDE;
				find = true;
			}
			else i++;	
		}
		terminate = true;
	}

	public boolean isTerminate() {
		return terminate;
	}

	public void setTerminate(boolean terminate) {
		this.terminate = terminate;
	}
}
