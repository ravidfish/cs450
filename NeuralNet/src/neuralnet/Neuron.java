package neuralnet;

import java.util.ArrayList;
import java.util.List;

public class Neuron {
    
    private List<Double> weights = new ArrayList<>();
    private List<Double> newWeights = new ArrayList <>();
    private List<Neuron> nextNeurons = new ArrayList <>();
    private List<Neuron> prevNeurons = new ArrayList <>();
    private Double value = 0.0;
    double learningRate = 0;
    double nueronError = 0;
    
    public Neuron(double learningRate) {
        
        this.learningRate = learningRate;
    }
        
    public double getError() {
        
        return nueronError;
    }
    
    public void setValue(Double value) {
       
        this.value = value;
    }
    
    public Double getValue() {
        
        return value;
    }
        
    public void connectNextNodes(List<Neuron> neurons) {
        
        nextNeurons = neurons;
    }
    
    public void connectPrevNodes(List<Neuron> neurons) {
        
        prevNeurons = neurons;
    }

    public void initializeWeights() {
        
        for (int i = 0; i < prevNeurons.size(); i++) {
            
            Double weight = Math.random() - Math.random();
            newWeights.add(weight);
            weights.add(weight);
        }
    }
    
    public void updateWeights() {
        
        weights = newWeights;
        newWeights = new ArrayList<>(weights);
    }
        
    public void forwardPropagate() {
        
        double propagateValue = 0;
        
        for (int i =0; i < weights.size(); i++) {
            
            propagateValue += prevNeurons.get(i).getValue() * weights.get(i);
        }
        
        value = 1 / ( 1 + Math.exp(-propagateValue));
    }
    
    public void backwardPropagateOutput(boolean correct) {
        
        int correctValue;
       
        if (correct) {
            
            correctValue = 1;
        } else {
            
            correctValue = 0;
        }
        
        nueronError = value * (1-value) * (value - correctValue);
        
        for (int i = 0; i < weights.size(); i++) {
         
            updateWeight(i, nueronError);
        }
    }
    
    public double getWeightOfNode(Neuron neuron) {
        
        int neuronIndex = -1;
        
        for (int i = 0; i < prevNeurons.size(); i++) {
            
            if (prevNeurons.get(i) == neuron) {
                neuronIndex = i;
            }
        }

        return weights.get(neuronIndex);
    }
    
    public void backwardPropagate() {
        nueronError = 0;
        
        for (int i = 0; i < nextNeurons.size(); i++) {
            
            nueronError += nextNeurons.get(i).getWeightOfNode(this) * nextNeurons.get(i).getError(); 
        }
               
        nueronError *= (value * (1-value));
    }
    
    private void updateWeight(int weightIndex, double error) {
        
        newWeights.set(weightIndex, weights.get(weightIndex) - learningRate * error * prevNeurons.get(weightIndex).value);  
    }
    
    public void print(){
        
        System.out.println("");
        System.out.println("value : " + value);
        System.out.println("Error : " + nueronError);
        
        System.out.println("num of prev nodes : " + prevNeurons.size());
        System.out.println("num of Next nodes : " + nextNeurons.size());
        
        for(int i = 0; i < weights.size(); i++) {
            System.out.println("weight " + i + " : " + weights.get(i));
        }
        
        for(int i = 0; i < newWeights.size(); i++) {
            System.out.println("New weights " + i + " : " + newWeights.get(i));
        }
                
        for(int i = 0; i < prevNeurons.size(); i++) {
            System.out.println("Prev Nueron " + i + " : " + prevNeurons.get(i).getValue());
        }
        
        for(int i = 0; i < nextNeurons.size(); i++) {
            System.out.println("Next Nueron " + i + " : " + nextNeurons.get(i).getValue());
        }
                
        System.out.println("");
    }
}
