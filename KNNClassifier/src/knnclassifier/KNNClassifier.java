/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knnclassifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.Instance;
import weka.core.EuclideanDistance;

/**
 *
 * @author Mark
 */
public class KNNClassifier extends Classifier{

    private Instances temp;
    private EuclideanDistance dist;
    
    @Override
    public void buildClassifier(Instances i) throws Exception {
        
        temp = new Instances(i);
    }
    
    /**
     *
     * @param testIndex
     * @return
     */
    @Override
    public double classifyInstance(Instance testIndex) {
	
        int numNeighbors = 5;
        int totalInstances = temp.numInstances();
        List<Double> indexDistances = new ArrayList<>(totalInstances);
        List<Instance> closestNeighbors = new ArrayList<>(numNeighbors);
        Map indexDist = new HashMap();
        
        for (int i = 0; i < totalInstances; i++) {
            
            double tempAdd = dist.distance(temp.instance(i), testIndex);
            indexDistances.add(tempAdd);           
            indexDist.put(indexDistances.get(i), temp.instance(i));
        } 
        
        Map<Double, Instance> map = new TreeMap<> (indexDist);
        
        for (int j = 0; j < numNeighbors; j++) {
        
            closestNeighbors.add(map.get(j));
        }
        
        System.out.println(closestNeighbors.get(1));
        System.out.println(closestNeighbors.get(2));
        System.out.println(closestNeighbors.get(3));
        
            // sort array, grab first "k" which is the numNeighbors, which
            // should actually be passed in, then determin what there is more
            // of and assign the testIndex to that.
        
        
        return 0;
    }
}
