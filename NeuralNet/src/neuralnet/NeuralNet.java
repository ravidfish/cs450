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
public class NeuralNet {
   List<Layers> layers = new ArrayList<>();

   Double bias = 1.0;
   
    public NeuralNet(int inputCount, List<Integer> numberOfNeuronPerLayer) {
        
        if (numberOfNeuronPerLayer.isEmpty()) {
            throw new UnsupportedOperationException("numberOfNeuronPerLayer is empty");
        }

        // 1 extra for the bias
        layers.add(new Layers(numberOfNeuronPerLayer.get(0), inputCount + 1));

        for (int i = 1; i < numberOfNeuronPerLayer.size(); i++) {
            
            layers.add(new Layers(numberOfNeuronPerLayer.get(i),
                    numberOfNeuronPerLayer.get(i - 1) + 1));
        }
    }

    public List<Double> getOutputs(List<Double> inputs) {
        
        List<Double> outputs = new ArrayList<>(inputs);

        for (Layers layer : layers) {
            
            addBias(outputs);
            
            outputs = layer.produceOutputs(outputs);
        }

        return outputs;
    } 
    
    public void addBias(List<Double> outputs) {
        outputs.add(bias);
    }
    
    public void setBias(Double bias) {
        this.bias = bias;
    }
    
    public Layers getLayer(int index) {
        
        return layers.get(index);
    }
}
