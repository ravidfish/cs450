package neuralnet;

import java.util.ArrayList;
import java.util.List;
import weka.core.Instance;

public class Layer {
    
    private List<Neuron> neurons = new ArrayList<>();
    
    Layer(Integer NumOfNodes, double learningRate) {
        
        Neuron neuron = new Neuron(learningRate);
        neuron.setValue(-1.0);
        neurons.add(neuron);
        
        for (int i = 0; i < NumOfNodes; i++) {
            
            neuron = new Neuron(learningRate);
            neurons.add(neuron);
        }
    }
    
    public List<Neuron> getNuerons() {
    
        return neurons;
    }
     
    public void createWeights() {
        
        for (int i = 1; i < neurons.size(); i++) {
    
            neurons.get(i).initializeWeights();
        }
    }
     
    public List<Neuron> getNueronsWithoutBios() {
        
        List <Neuron> neuronsNoBias = new ArrayList <>();
        
        for (int i = 1; i < neurons.size(); i++) {
            
            neuronsNoBias.add(neurons.get(i));
        }
        
        return neuronsNoBias;
    }
    
    public void connectNextLayer(List<Neuron> neurons) {
        
        for (int i = 0; i < this.neurons.size(); i++) {
            
            this.neurons.get(i).connectNextNodes(neurons);
        }
    }
 
    public void connectPrevLayer(List<Neuron> neurons) {
       
        for (int i = 1; i < this.neurons.size(); i++) {
            
            this.neurons.get(i).connectPrevNodes(neurons);
        }
    }
    
    public void setFirstLayer(Instance instance) {
        
        for (int i = 1; i < neurons.size(); i++) {
            
            neurons.get(i).setValue(instance.value(i - 1));
        }
    }
    
    public void forwardPropagate(){
        
        for (int i = 1; i < neurons.size(); i++) {
            
            neurons.get(i).forwardPropagate();
        }
    }
    
    public void backwardPropagate() {
        
        for (int i = 1; i < neurons.size(); i++) {
        
            neurons.get(i).backwardPropagate();
        }
    }
    
    public void backwardPropagateOutputLayer(int correctNode) {
        
        for (int i = 1; i < neurons.size(); i++) {
            
            if (i == correctNode + 1) {
                
                neurons.get(i).backwardPropagateOutput(true);
            } else {
                
                neurons.get(i).backwardPropagateOutput(false);
            }
        }
    }
    
    public void updateWeights() {
        
        for (int i = 1; i < neurons.size(); i++) {
            
            neurons.get(i).updateWeights();
        }
    }
    
    public double GreatestNeuron() {
        
        double highestValue = -Double.MAX_VALUE;
        double highestValueNeuron = -1;
        
        for (int i = 1; i < neurons.size(); i++) {
            
            if (neurons.get(i).getValue() > highestValue) {
                
                highestValue = neurons.get(i).getValue();
                highestValueNeuron = i;
            }
        }
        
        return highestValueNeuron - 1;
    }
    
    public void print() {
        
        for (int i = 0; i < neurons.size(); i++) {
            
            neurons.get(i).print();
        }
    }
}
