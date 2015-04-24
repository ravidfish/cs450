package HardCodedClassifier;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.Instance;

public class HardCodedClassifier extends Classifier{

	@Override
	public void buildClassifier(Instances arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public double classifyInstance(Instance i) {
		return 0;
	}
}
