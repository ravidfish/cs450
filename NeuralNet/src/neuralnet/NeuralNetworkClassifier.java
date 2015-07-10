package neuralnet;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

public class NeuralNetworkClassifier extends Classifier {
    
    NeuralNetwork network;

    @Override
    public void buildClassifier(Instances instances) {
        
        network = new NeuralNetwork(instances);
        
        for (int i = 0; i < 2000; i++) {
            
            network.learn(instances);
        }
    }
    
    @Override
    public double classifyInstance(Instance instance) {
    
        return network.classifyInstance(instance);
    }
}
