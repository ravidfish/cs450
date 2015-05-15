/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id3classifier;

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

class Node {
 
    List<Instance> instances;
    private Map<Node, Double> children = new HashMap<>();
    private Map<Double, Node> children2 = new HashMap<>();
    Attribute attribute;
    boolean isLeaf = false;
    double leafValue;

    public Node(List<Instance> instances, Attribute attribute) {
    
        this.instances = instances;
        this.attribute = attribute;
    }

    public Node(double leafValue) {
        
        isLeaf = true;
        this.leafValue = leafValue;
    }

    public boolean isLeaf() {
        
        return isLeaf;
    }

    public Attribute getAttribute() {
        
        return attribute;
    }

    public void addChild(Double value, Node n) {
        
        children.put(n, value);
        children2.put(value, n);
    }

    public Double get(Node n) {
        
        return children.get(n);
    }

    public Node get(Double d) {
        
        return children2.get(d);
    }

    public Set<Node> getChildren() {
        
        return children.keySet();
    }
}
