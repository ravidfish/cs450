/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id3classifier;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Mark
 */
public class ID3ClassifierIT {
    
    public ID3ClassifierIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of buildClassifier method, of class ID3Classifiers.
     */
    @Test
    public void testBuildClassifier() throws Exception {
        System.out.println("buildClassifier");
        Instances instances = null;
        ID3Classifiers instance = new ID3Classifiers();
        instance.buildClassifier(instances);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of classifyInstance method, of class ID3Classifiers.
     */
    @Test
    public void testClassifyInstance() throws Exception {
        System.out.println("classifyInstance");
        Instance instance_2 = null;
        ID3Classifiers instance = new ID3Classifiers();
        double expResult = 0.0;
        double result = instance.classifyInstance(instance_2);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxGain method, of class ID3Classifiers.
     */
    @Test
    public void testGetMaxGain() {
        System.out.println("getMaxGain");
        List<Instance> instances = null;
        List<Attribute> attributes = null;
        ID3Classifiers instance = new ID3Classifiers();
        Attribute expResult = null;
        Attribute result = instance.getMaxGain(instances, attributes);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printTree method, of class ID3Classifiers.
     */
    @Test
    public void testPrintTree() {
        System.out.println("printTree");
        Node node = null;
        int level = 0;
        Double value = null;
        ID3Classifiers instance = new ID3Classifiers();
        instance.printTree(node, level, value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getClassification method, of class ID3Classifiers.
     */
    @Test
    public void testGetClassification() {
        System.out.println("getClassification");
        Instance instance_2 = null;
        Node n = null;
        ID3Classifiers instance = new ID3Classifiers();
        double expResult = 0.0;
        double result = instance.getClassification(instance_2, n);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
