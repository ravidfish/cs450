package neuralnet;

import java.util.ArrayList;
import java.util.List;
import weka.core.Instance;
import weka.core.Instances;

public class NeuralNetwork {
    List<Integer> numOfNodesPerLayer = new ArrayList<>();
    double learningRate = .12;
    private List<Layer> layersOfNetwork = new ArrayList<>();
    
    public NeuralNetwork(Instances instances) {
        
        numOfNodesPerLayer.add(instances.numAttributes() - 1);
        numOfNodesPerLayer.add(20);
        numOfNodesPerLayer.add(instances.numClasses());
        
        for (int i = 0; i < numOfNodesPerLayer.size(); i++) {
            
            Layer layer = new Layer(numOfNodesPerLayer.get(i), learningRate);
            layersOfNetwork.add(layer);
        }
        
        for (int i = 0; i < layersOfNetwork.size() - 1; i++) {
            layersOfNetwork.get(i).connectNextLayer(layersOfNetwork.get(i+1).getNueronsWithoutBios());
        }
        
        for (int i = 1; i < layersOfNetwork.size(); i++) {
            
            layersOfNetwork.get(i).connectPrevLayer(layersOfNetwork.get(i-1).getNuerons());
            layersOfNetwork.get(i).createWeights();
        }
        
        learn(instances);
    }
    
    public void learn(Instances instances){
        for(int i = 0; i < instances.numInstances(); i++) {
            
            learnNext(instances.instance(i));
        }        
    }
    
    public void learnNext(Instance instance){
        
        layersOfNetwork.get(0).setFirstLayer(instance);
        propagate();
        
        layersOfNetwork.get(layersOfNetwork.size() - 1).backwardPropagateOutputLayer((int)instance.classValue());
        
        for (int i = layersOfNetwork.size() - 2; i > 0; i--){
            
            layersOfNetwork.get(i).backwardPropagate();
        }
        
        for (int i = 1; i < layersOfNetwork.size(); i++) {
            
            layersOfNetwork.get(i).updateWeights();
        }
    }
    
    public double classifyInstance(Instance instance) {
       
        layersOfNetwork.get(0).setFirstLayer(instance);
        propagate();
        
        return layersOfNetwork.get(layersOfNetwork.size() - 1).GreatestNeuron();
    }
    
    private void propagate() {
        
        for (int i = 1; i < layersOfNetwork.size(); i++){
            
            layersOfNetwork.get(i).forwardPropagate();
        }
    }

   public void output() {
       
       System.out.println("num of layers : " + layersOfNetwork.size());
       
       for (int i = 0; i < layersOfNetwork.size(); i++) {
           
           System.out.println("Layer : " + i + "\n");
           layersOfNetwork.get(i).print();
       }
   }
}
