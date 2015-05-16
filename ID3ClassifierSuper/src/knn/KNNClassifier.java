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

    // private members:
    // instances data structure, the size of neighbors, and the map to store
    Integer k = 5;
    Instances data;
    TreeMap<Double,Integer> map;
    
    // method: takes instances (instances) and sets the data (instances)
    @Override
    public void buildClassifier(Instances instances) throws Exception {
        
        data = instances;
    }
    
    // method: takes instances (instances) and creates the map and classifies
    @Override
    public double classifyInstance(Instance instance) throws Exception {
    
        createMap(instance);
        
        return classify(instance);
    }
    
    // method: takes instances (list of instance) and classifies
    public static double getClassification(List<Instance> instances) {
        
        // grab the class index, create a blank map of type (double, integer)
        // set the maximum counts and values to zero
        int index = instances.get(0).classIndex();
        HashMap<Double, Integer> counts = new HashMap<>();
        int maxCount = 0;
        double maxValue = 0;
        
        // for each instance in instances: take val of instance and put in map
        for (Instance instance : instances) {
    
            double val = instance.value(index);
            
            // is it the first time? if not put in second slot
            if (!counts.containsKey(val)) {
            
                counts.put(val, 1);
            } else {
              
                counts.put(val, counts.get(val) + 1);
            }        
        }
        
        // for each entry of type entry (double, integer) in counts (map)
        // if the entry's value is greater than the maxCount...
        for (Entry<Double, Integer> entry : counts.entrySet()) {
            
            // if val is greater maxCount, assign the maxCount and maxVal
            // to the entry's val and key respectively
            if (entry.getValue() > maxCount) {
                
                maxCount = entry.getValue();
                maxValue = entry.getKey();
            }      
        }
        
        return maxValue;
    }
    
    // method: takes instance (instance) and classifies it
    double classify(Instance instance) {
        
        // variables: array of ints of the size of the number of classes
        // indexes, highest vals, and highest index equal to zero
        int tally[] = new int[data.numClasses()]; 
        int highestValue = 0;
        int highestIndex = 0;
        int i = 0;
        int j = 0;
        
 
        // for each key of type doube in the keyset of map
        for (Double key : map.keySet()) {
        
            // get the key of the map and add it to the array, then increment
            tally[map.get(key)]++;
            
            // if the index is greater than or equal to the num of neighbors...
            if (i >= k) {
            
                // end if the above is true
                break;
            }
            
            // increment the index
            i++;
        }
        
        // for each value in list tally
        for (int value : tally) {          

            // if highest val is less than val, set highest val to val and
            // highest index to index
            if (highestValue < value) {

                highestValue = value;
                highestIndex = j;
            }
            
            // increment index
            j++;
        }
        
        // print and return index
        System.out.println(highestIndex);
        return highestIndex;
    }
    
    // method: takes instance of type instance
    void createMap(Instance instance) {
        
        // create new tree map and index from 0 to data's number of instances
        // fill map with the euclidean distance
        map = new TreeMap<>();
        
        for (int i = 0; i < data.numInstances(); i++) {
        
            map.put(EuclideanDistance(data.instance(i), instance), (int)(data.instance(i).classValue()));
        }
    }
    
    // euclidean distance, takes two instances
    double EuclideanDistance(Instance instanceLHS, Instance instanceRHS) {
       
        // set dist to 0
        double distance = 0;
        
        // from index 0 to left and right side's number of attributes - 1...
        for (int i = 0; i < instanceLHS.numAttributes() -1 && i < instanceRHS.numAttributes() - 1 ; i++) {
            
            // if left and right side's attributes are numeric, set the distance equal
            // to the value of left value - right value all squared
            if (instanceLHS.attribute(i).isNumeric() && instanceRHS.attribute(i).isNumeric()) {
                
                distance += pow(instanceLHS.value(i) - instanceRHS.value(i), 2);
            } else {
                
                // else add 5 to the distance unless left and right side's string converted
                // values are equal to one another, in which case set dist back to zero
                if (instanceLHS.stringValue(i).equals(instanceRHS.stringValue(i))) {
                    
                    distance += 0;    
                }
                
                distance += 5;            
            }
        }
        
        return distance;
    }
    
    // manhatten distance, takes two instances
    double ManhattenDistance(Instance instanceLHS, Instance instanceRHS) {
        
        // set dist to 0
        double distance = 0;
        
        // from index 0 to number of attributes - 1 on both sides...
        for (int i = 0; i < instanceLHS.numAttributes() -1 && i < instanceRHS.numAttributes() - 1 ; i++) {
            
            // if left and right side's attributes are numbers set distance equal
            // to absolute value of left's value - right's value
            if (instanceLHS.attribute(i).isNumeric() && instanceRHS.attribute(i).isNumeric()) {
                
                distance += abs(instanceLHS.value(i) - instanceRHS.value(i));
            } else {
                
                // else add 5 to distance unless left and right are equal, in which 
                // case set the distance back to 0
                if (instanceLHS.stringValue(i).equals(instanceRHS.stringValue(i))) {
                    
                    distance = 0;    
                }
                    
                distance += 5;            
            }
        }
        
        return distance;
    }
}
