/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neuralnet;

import java.util.ArrayList;
import java.util.Collections;
import weka.core.Instance;

/**
 *
 * @author Mark
 */

public class NeuralNetwork {
	
    ArrayList<ArrayList<Neuron>> layers = new ArrayList<>();
    ArrayList<ArrayList<Double>> activations = new ArrayList<>();
    Instance instance;
    int length;
	
    public NeuralNetwork(ArrayList<Integer> layer_counts) {

        length = layer_counts.size();
	boolean bias = (length > 1);
				
	for (int i = 0; i < length; ++i) {
            
            int count = layer_counts.get(i);
            boolean bias_added = ((!bias) || ((i + 1) == length));
			
            if (bias && ((i + 1) < length)) {
	
                count++;
            }
			
            layers.add(new ArrayList<Neuron>());
	
            for (int j = 0; j < count; ++j) {
	
                if (bias_added) {
                            
                    Neuron n = new Neuron();
                    layers.get(i).add(n);
                } else {
		
                    bias_added = !bias_added;
                    Neuron temp = new Neuron(true);
                    layers.get(i).add(temp);
                }
            }
        }
    }
	
    public double evaluate (Instance inst) {
            
        instance = inst;
	int count = inst.numAttributes();
        ArrayList<Double> inputs = new ArrayList<>();
        activations = new ArrayList<>();
		
        for (int i = 0; i < count; ++i) {
	
            inputs.add(inst.value(i));
        }
        
        activations.add(inputs);
		
        for (int i = 0; i < length; ++i) {
	
            ArrayList<Double> layer_outputs = new ArrayList<Double>();
            
            for (Neuron n : layers.get(i)) {
                        
                layer_outputs.add(n.evaluate(inputs));
            }
            
            inputs = layer_outputs;
            activations.add(layer_outputs);
        }
		
        Double max_value = Collections.max(inputs);
        return inputs.indexOf(max_value);
    }
	
    public void backPropagate() {

        boolean output_layer = true;
        ArrayList<ArrayList<Double>> errors = new ArrayList<>();
        
        for (int i = layers.size(); i > 0; --i) {
                
            ArrayList<Neuron> layer = layers.get(i - 1);
            ArrayList<Neuron> next_layer = new ArrayList<>();
	
            if (i != layers.size()) {
	
                next_layer = layers.get(i);
            }
	
            ArrayList<Double> new_layer_errors = new ArrayList<>();
            ArrayList<Double> inputs = activations.get(i - 1);
            int size = layer.size();
			
            for (int j = 0; j < size; ++j) {
                    
                Neuron n = layer.get(j);
		boolean correct_class = ((int) instance.classValue() == j);
                Double error = n.getError(output_layer, j, next_layer, correct_class);
		new_layer_errors.add(error);
		n.updateWeights(inputs);
            }
			
            errors.add(new_layer_errors);
            output_layer = false;
        }
    }
	
    public boolean classificationValidation(Double class_index) {

        return this.classificationValidation(instance, class_index);
    }
	
    public boolean classificationValidation(Instance i, Double class_index) {

        return (i.classValue() == class_index);
    }
}
