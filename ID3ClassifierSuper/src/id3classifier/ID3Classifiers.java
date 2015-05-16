/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id3classifier;

// this usefull import, which allows easy comparisons between key value pairs...
// is the whole reason this project has to be a javaFX project, unless I set 
// this up wrong
import javafx.util.Pair;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import java.util.*;
import leaf.*;
import knn.*;

/**
 *
 * @author Mark
 */

public class ID3Classifiers extends Classifier {

    // set up a blank tree made of leaves
    Leaf tree;

    // method: takes instances of type instances
    @Override
    public void buildClassifier(Instances instances) throws Exception {
        
        // create list of instances of size instances' number of instances
        // create list of attributes of size instances' number of attributes
        List<Instance> instanceList = new ArrayList<>(instances.numInstances());
        List<Attribute> attributeList = new ArrayList<>(instances.numAttributes());
        
        // from index 0 to instances' number of instances, add instances' current
        // instance to the list of instances... mouthfull
        for (int i = 0; i < instances.numInstances(); i++) {
        
            instanceList.add(instances.instance(i));
        }
       
        // from index 0 to instances' number of attributes, if the index is not
        // equal to instances' class index... 
        for (int i = 0; i < instances.numAttributes(); i++) {
        
            if (i != instances.classIndex()) {
            
                // add instances' current attribute to the attribute list
                attributeList.add(instances.attribute(i));
            }
        }
    
        // set tree equal to the tree built by buildTree() using the instance
        // list and the attribute list
        tree = buildTree(instanceList, attributeList);
    }
    
    // method: takes an instance of type instance
    @Override
    public double classifyInstance(Instance instance) throws Exception {
	
        // gets the classification from for the instance using the instance
        // and the decision tree
        return getClassification(instance, tree);
    }
    
    // method: takes list of instances of type instance
    private Pair<Boolean, Double> sameClass(List<Instance> instances) {
        
        // sets the class index equal to the first instance's class index
        // creates tempValue of type double  which is equal to not a number
        int classIndex = instances.get(0).classIndex();
        double tmpValue = Double.NaN;
        
        // for each instance in instances...
        for (Instance instance : instances) {
            // if tmpValue of type double is not a number...
            if (Double.isNaN(tmpValue)) {
                
                // reassign tmpValue to the value of the instance at classIndex
                tmpValue = instance.value(classIndex);
            } else {
                
                // else assign val equal to the value of instance at classIndex
                double val = instance.value(classIndex);
                
                // if value of type double is a number... then if it is not 
                // equal to tmpValue...
                if (!Double.isNaN(val)) {
                    
                    if (val != tmpValue) {
                    
                        // then return a new Pair
                        return new Pair<>(false, Double.NaN);
                    }
                }
            }
        }

        // if you havent already... return a new Pair using tmpValue
        return new Pair<>(true, tmpValue);
    }

    private Leaf buildTree(List<Instance> instances, List<Attribute> attributes) {
        
        // create a new pair of type boolean and double equal to the value returned
        // from the sameClass() using instances
        // create attribute largestGain, from getMaxGain, using instances and 
        // attributes... create a new leaf using largest gain and instances
        Pair<Boolean, Double> classification = sameClass(instances);
        Attribute largestGain = getMaxGain(instances, attributes);
        Leaf node = new Leaf(instances, largestGain);

        // error checking chekcing to see if intances is empty, throw error
        if (instances.size() < 1) {
            
            throw new UnsupportedOperationException("Warning: empty list of instances");
        }
        
        // if classification has a key, create a new leaf (node) using the
        // classification's value
        if (classification.getKey()) {
            
            return new Leaf(classification.getValue());
        }
        
        // if there are no attributes...
        if (attributes.isEmpty()) {
            
            // then create a new leaf (node) using results from k nearest 
            // neighbor's classification
            return new Leaf(KNNClassifier.getClassification(instances));
        }

        // create two maps holding values and summaries... using instances,
        // largestGain, and values
        Map<Instance, Double> vals =  valuesByAttribute(instances, largestGain);
        Map<Double, Integer> summary = summarizeValues(vals);
        
        // create a new array list of attributes using attributes and remove
        // the largest gain from this list
        ArrayList<Attribute> newList = new ArrayList<>(attributes);
        newList.remove(largestGain);

        // for each value in summary's set of keys...
        for (Double value : summary.keySet()) {
        
            // create a new leaf (idLeaf) equal to the value returned from 
            // building a tree using vals, values, and list of attributes...
            // then add the add leaf node
            Leaf idLeaf = buildTree(subset(vals, value), newList);
            node.addChild(value, idLeaf);
        }

        return node;
    }

    // method: takes map of type map of instance and double, and a value
    private List<Instance> subset(Map<Instance, Double> map, double value) {
        
        // create new array list of instace
        ArrayList<Instance> list = new ArrayList<>();
        
        // for each instance in map's set of records
        for (Instance instance : map.keySet()) {
            
            // if map's value at each instance equals the value passed in...
            if (map.get(instance) == value) {
                
                // add it to the list
                list.add(instance);
            }
        }

        return list;
    }

    private Map<Instance, Double> valuesByAttribute(List<Instance> instances, Attribute attribute) {
        
        HashMap<Instance, Double> map = new HashMap<>();

        for (Instance instance : instances) {
            
            if (!Double.isNaN(instance.value(attribute))) {
                
                map.put(instance, instance.value(attribute));
            }
        }
        
        return map;
    }

    private Map<Double, Integer> summarizeValues(Map<Instance, Double> input) {
        
        HashMap<Double, Integer> hashMap = new HashMap<>();

        for (Instance instance : input.keySet()) {
            
            if (!hashMap.containsKey(input.get(instance)) || hashMap.get(instance) == null) {
                
                hashMap.put(input.get(instance), 1);
            } else {
                
                hashMap.put(input.get(instance), hashMap.get(instance) + 1);
            }
        }

        return hashMap;
    }

    // method: takes list of instances and a list of attributes
    public Attribute getMaxGain(List<Instance> instances, List<Attribute> attributes) {
        
        // create a new pair of attributes and doubles called maxGain and a 
        // double totalEntropy equal to the result of getEntropy() using 
        // instances
        Pair<Attribute, Double> maxGain = new Pair<>(null, Double.NEGATIVE_INFINITY);
        double totalEntropy = getEntropy(instances);
        
        // for each attribute in attributes...
        for (Attribute attribute : attributes) {
            
            // create tmpGain from totalEntropy, a new map of values, and a new 
            // hashset of values from the map
            double tmpGain = totalEntropy;
            Map<Instance, Double> values = valuesByAttribute(instances, attribute);
            HashSet<Double> valueSet = new HashSet<>(values.values());
            
            // for each val in the hashset of values...
            for (Double doubVal : valueSet) {
            
                // create a new list (sub) of instance and set tmpGain equal to the 
                // value tmpGain - the sub's size * 1/instance's size * the 
                // entropy of sub
                List<Instance> sub = subset(values, doubVal);
                tmpGain -= sub.size() * 1.0 / instances.size() * getEntropy(sub);
            }
            
            // if tmpGain is bigger than maxGain...
            if (tmpGain > maxGain.getValue()) {
                
                // set maxGain equal to a new Pair using attributes and tmpGain
                maxGain = new Pair<>(attribute, tmpGain);
            }
        }

        return maxGain.getKey();
    }

    // method: takes list of instances
    private double getEntropy(List<Instance> instances) {
                
        // create results double and map of type double and integer of summarized
        // values, using instances and the first instance's class index
        double result = 0;
        Map<Double, Integer> summary = summarizeValues(valuesByAttribute(instances, instances.get(0).classAttribute()));
        
        // exit if instances is empty
        if (instances.isEmpty()) {
            
            return 0;
        }
        
        // for each value in summary's list of values...
        for (Integer val : summary.values()) {
            
            // calculate the proportion of gain and subtract from result this 
            // value... as per formula
            double gainProportion = val * 1.0 / instances.size();
            result -= gainProportion * Math.log(gainProportion) / Math.log(2);
        }

        return result;
    }

    // method: takes an instance and a leaf node
    public double getClassification(Instance instance, Leaf node) {
        
        // set the maxCounts and max values
        int maxCount = -1;
        double maxValue = 0;
        
        // if the node is indeed a leaf...
        if (node.isLeaf()) {
            
            // go ahead are return the node's (leaf) value
            return node.getLeafValue();
        } else {
            
            // else... create a new attribute from node's attribute
            Attribute attribute = node.getAttribute();
            
            // if this attribute is a number
            if (!Double.isNaN(instance.value(attribute))) {
                
                // set a new double called val equal to the value of instance's
                // node's attribute's value... mouthful
                Double val = instance.value(node.getAttribute());
                
                // if this is not a number... set val equal to zero
                if (Double.isNaN(val)) {
                    
                    val = 0.0;
                }
                
                // is if node's attribute is not nominal... and if val is < 0.5
                if (!node.getAttribute().isNominal()) {
                    
                    if (val < 0.5) {
                        
                        // set that val to 0
                        val = 0.0;
                    } else {
                        
                        // if that val was >= to 0.5... reset it to 1
                        val = 1.0;
                    }
                }
                
                // overright child with node's value
                Leaf child = node.get(val);
                
                // if child is now null... retunr the classification based on k
                // nearest neighbors
                if (child == null) {
                    
                    return KNNClassifier.getClassification(node.getInstances());
                } else {
                    
                    // else return the classification based on getClassificaion
                    // using instance and node's value
                    return getClassification(instance, node.get(val));
                }
            } else {
                
                // else if the attribute is not a number... create a new map of 
                // type double and integer to hold the class counts
                Map<Double, Integer> classToCount = new HashMap<>();
                
                // for each child of node
                for (Leaf child : node.getChildren()) {
                    
                    // get the value of the classification using instance and child
                    Double value = getClassification(instance, child);
                    
                    // if classes to count does not contain the key matching value,
                    // and if classes to count's value is not null put the value in
                    // to classes to count at location matching it's value + 1
                    if (!classToCount.containsKey(value) && classToCount.get(value) != null) {
                        
                        classToCount.put(value, classToCount.get(value) + 1);
                    } else {
                        
                        // else just put value in the slot "1" of classes to count
                        classToCount.put(value, 1);
                    }
                }
                
                // for each double value in classes to counts set of keys... IF
                // classes to count's current key is greater than the max count,
                // and set the max value equal to the current double value
                for (Double doubVal : classToCount.keySet()) {
                    
                    if (classToCount.get(doubVal) > maxCount) {
                        
                        maxCount = classToCount.get(doubVal);
                        maxValue = doubVal;
                    }
                }
                
                return maxValue;
            }
        }
    }  
}
