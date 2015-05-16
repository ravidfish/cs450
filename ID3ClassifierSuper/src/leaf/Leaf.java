/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package leaf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weka.core.Attribute;
import weka.core.Instance;

/**
 *
 * @author Mark
 */

public class Leaf {
 
    // private members: 
    // maps and lists used to store "leaves" (nodes) of the decision tree
    // attributes and values of the data, and boolean control statments
    private List<Instance> instances;
    private Map<Leaf, Double> children = new HashMap<>();
    private Map<Double, Leaf> children2 = new HashMap<>();
    private Attribute attribute;
    private boolean isLeaf = false;
    private double leafValue;

    // constructor: takes instances (list of instance) and attribute
    public Leaf(List<Instance> instances, Attribute attribute) {
    
        this.instances = instances;
        this.attribute = attribute;
    }

    // constructor: takes leafValue (double)
    public Leaf(double leafValue) {
        
        isLeaf = true;
        this.leafValue = leafValue;
    }

    // accessor for isLeaf of type boolean
    public boolean isLeaf() {
        
        return isLeaf;
    }

    // accessor for attribute of type Attribute
    public Attribute getAttribute() {
        
        return attribute;
    }
    
    // accessor for leafValue of type double
    public double getLeafValue() {
    
        return leafValue;
    }

    // accessor for instances of type list of instance
    public List<Instance> getInstances() {
        
        return instances;
    }
    
    // method: takes inValue (double) and inLeaf (leaf) 
    public void addChild(Double inValue, Leaf inLeaf) {
        
        children.put(inLeaf, inValue);
        children2.put(inValue, inLeaf);
    }

    // method: takes inLeaf (leaf : key) returns children's value (double)
    public Double get(Leaf inLeaf) {
        
        return children.get(inLeaf);
    }

    // method: takes inDouble (double : key) returns children2's value (leaf)
    public Leaf get(Double inDouble) {
        
        // if children2's value is not null...
        if (children2.get(inDouble) != null) {
            
            // return it
            return children2.get(inDouble);
        } else {
            
            //else... set some default values
            Double nextKey = null;
            double nextDistance = Double.MAX_VALUE;
            
            // then... for each key in children2's set of keys
            for (Double key : children2.keySet()) {
            
                // if the absolute value of the input double - the key is less
                // than the distance to the next closest node... 
                if (Math.abs(inDouble - key) < nextDistance) {
                
                    // set the next closest distance equal to the absolute value
                    // of the input double - the key and set the next key equal
                    // to this one
                    nextDistance = Math.abs(inDouble - key);
                    nextKey = key;
                }
            }
            
            // return children2's next key
            return children2.get(nextKey);
        }
    }

    // method: returns the children's keySet - a set of leaf
    public Set<Leaf> getChildren() {
        
        return children.keySet();
    }
}
