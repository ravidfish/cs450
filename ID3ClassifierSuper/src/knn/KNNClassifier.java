/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knn;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.Instance;

/**
 *
 * @author Mark
 */

public class KNNClassifier extends Classifier {

    Integer k = 5;
    Instances data;
    TreeMap<Double,Integer> map;
    
    @Override
    public void buildClassifier(Instances i) throws Exception {
        
        data = i;
    }
    
    @Override
    public double classifyInstance(Instance instance) throws Exception {
    
        createMap(instance);
        
        return classify(instance);
    }
    
    public static double getClassification(List<Instance> instances) {
        
        int index = instances.get(0).classIndex();
        HashMap<Double, Integer> counts = new HashMap<>();
        
        for (Instance instance : instances) {
    
            double val = instance.value(index);
            
            if (!counts.containsKey(val)) {
            
                counts.put(val, 1);
            } else {
              
                counts.put(val, counts.get(val) + 1);
            }        
        }
        
        int maxCount = 0;
        double maxValue = 0;
        
        for (Entry<Double, Integer> entry : counts.entrySet()) {
            
            if (entry.getValue() > maxCount) {
                
                maxCount = entry.getValue();
                maxValue = entry.getKey();
            }      
        }
        
        return maxValue;
    }
    
    double classify(Instance instance) {
        
        int tally[] = new int[data.numClasses()];
        int i = 0;
 
        for (Double key : map.keySet()) {
        
            tally[map.get(key)]++;
            
            if (i >= k) {
            
                break;
            }
            
            i++;
        }
            
        int highestValue = 0;
        int highestIndex = 0;
        i = 0;
        
        for (int value : tally) {          

            if(highestValue < value) {

                highestValue = value;
                highestIndex = i;
            }
            
            i++;
        }
        
        System.out.println(highestIndex);
        return highestIndex;
    }
    
    double EuclideanDistance(Instance instanceLHS, Instance instanceRHS) {
       
        double distance = 0;
        
        for (int i = 0; i < instanceLHS.numAttributes() -1 && i < instanceRHS.numAttributes() - 1 ; i++) {
            
            if (instanceLHS.attribute(i).isNumeric() && instanceRHS.attribute(i).isNumeric()) {
                
                distance += pow(instanceLHS.value(i) - instanceRHS.value(i), 2);
            } else {
                
                if (instanceLHS.stringValue(i).equals(instanceRHS.stringValue(i))) {
                    
                    distance += 0;    
                }
                
                distance += 5;            
            }
        }
        
        return distance;
    }
    
    void createMap(Instance instance) {
        
        map = new TreeMap<>();
        
        for (int i = 0; i < data.numInstances(); i++) {
        
            map.put(EuclideanDistance(data.instance(i), instance), (int)(data.instance(i).classValue()));
        }
    }
    
    double ManhattenDistance(Instance instanceLHS, Instance instanceRHS) {
        
        double distance = 0;
        
        for (int i = 0; i < instanceLHS.numAttributes() -1 && i < instanceRHS.numAttributes() - 1 ; i++) {
            
            if (instanceLHS.attribute(i).isNumeric() && instanceRHS.attribute(i).isNumeric()) {
                
                distance += abs(instanceLHS.value(i) - instanceRHS.value(i));
            } else {
                
                if (instanceLHS.stringValue(i).equals(instanceRHS.stringValue(i))) {
                    
                    distance = 0;    
                }
                    
                distance += 5;            
            }
        }
        
        return distance;
    }
}
