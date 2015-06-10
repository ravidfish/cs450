/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neuralnet;

import java.util.ArrayList;
import java.util.Arrays;
import weka.core.Instances;
import weka.core.Debug.Random;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemovePercentage;

/**
 *
 * @author Mark
 */

public class Main {
	
    private static int runs = 1000;
    private static ArrayList<Integer> layers = new ArrayList<Integer>(Arrays.asList(5,4)); 
	
    public static void main(String[] args) throws Exception {

        String file = "C:\\FishFiles\\School\\2015\\Spring2015\\cs450\\cs450\\files\\iris.csv";
        DataSource source = new DataSource(file);
        Instances data = source.getDataSet();
		
        data.randomize(new Random());
		
        RemovePercentage filter = new RemovePercentage();
        filter.setPercentage(30);
        filter.setInputFormat(data);
        Instances training = Filter.useFilter(data, filter);
        int training_size = training.numInstances();
		
        System.err.println("count of training: " + training.numInstances());
		
        training.setClassIndex(training.numAttributes() - 1);
        filter.setInputFormat(data);
        filter.setInvertSelection(true);
        Instances test = Filter.useFilter(data, filter);
        int test_size = test.numInstances();
		
        System.err.println("count of test: " + test.numInstances());
		
        test.setClassIndex(test.numAttributes() - 1);
		
        NeuralNetwork nn = new NeuralNetwork(layers);
		
        for (int i = 0; i < runs; ++i) {
	
            for (int j = 0; j < training_size; ++j) {
	
                nn.evaluate(training.instance(j));
                nn.backPropagate();
            }
        }
		
        int success_count = 0;
        int failure_count = 0;
        int total_classified = 0;
		
        for (int i = 0; i < test_size; ++i) {
                
            Double class_index = nn.evaluate(test.instance(i));
            boolean success = nn.classificationValidation(class_index);
	
            if (success) {
	
                success_count++;
            } else {
	
                failure_count++;
            }
	
            total_classified++;
        }
		
        System.out.println("Test set instances classified: " + total_classified);
        System.out.println("  Correctly classified: " + success_count + "    Percent: " + ((double) success_count) / total_classified + "%");
        System.out.println("Incorrectly classified: " + failure_count + "    Percent: " + ((double) failure_count) / total_classified + "%");
    }
}
