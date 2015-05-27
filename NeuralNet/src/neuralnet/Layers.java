/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnet;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mark
 */
public class Layers {
    
    List<Neuron> neurons = new ArrayList<>();

    public Layers(int neuronCount, int inputCount) {
        
        for (int i = 0; i < neuronCount; i++) {
            
            neurons.add(new Neuron(inputCount));
        }
    }

    public List<Double> produceOutputs(List<Double> inputs) {
        
        List<Double> outputs = new ArrayList<>();
        
        for (Neuron neuron : neurons) {
        
            outputs.add(neuron.produceOutput(inputs));
        }

        return outputs;
    }
    
    public Neuron getNeuron(int index) {
        
        return neurons.get(index);
    }
}
