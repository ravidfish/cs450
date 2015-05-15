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
 
    private List<Instance> instances;
    private Map<Leaf, Double> children = new HashMap<>();
    private Map<Double, Leaf> children2 = new HashMap<>();
    private Attribute attribute;
    private boolean isLeaf = false;
    private double leafValue;

    public Leaf(List<Instance> instances, Attribute attribute) {
    
        this.instances = instances;
        this.attribute = attribute;
    }

    public Leaf(double leafValue) {
        
        isLeaf = true;
        this.leafValue = leafValue;
    }

    public boolean isLeaf() {
        
        return isLeaf;
    }

    public Attribute getAttribute() {
        
        return attribute;
    }
    
    public double getLeafValue() {
    
        return leafValue;
    }

    public List<Instance> getInstances() {
        
        return instances;
    }
    
    public void addChild(Double value, Leaf n) {
        
        children.put(n, value);
        children2.put(value, n);
    }

    public Double get(Leaf n) {
        
        return children.get(n);
    }

    public Leaf get(Double d) {
        
        return children2.get(d);
    }

    public Set<Leaf> getChildren() {
        
        return children.keySet();
    }
}
