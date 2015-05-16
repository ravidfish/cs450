/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id3classifier;

import weka.classifiers.Evaluation;
import weka.core.Debug;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Discretize;
import weka.filters.unsupervised.attribute.Standardize;

/**
 *
 * @author Mark
 */

public class Main {
    
    // Files to test
    // accuracys: 95, 80, 80, 55, 80
    static final String file = "C:\\FishFiles\\School\\2015\\Spring2015\\cs450\\cs450\\files\\iris.csv";
    // static final String file = "C:\\FishFiles\\School\\2015\\Spring2015\\cs450\\cs450\\files\\crx.csv";
    // static final String file = "C:\\FishFiles\\School\\2015\\Spring2015\\cs450\\cs450\\files\\house-votes-84.csv";
    // static final String file = "C:\\FishFiles\\School\\2015\\Spring2015\\cs450\\cs450\\files\\krkopt.csv";
    // static final String file = "C:\\FishFiles\\School\\2015\\Spring2015\\cs450\\cs450\\files\\lenses.csv";
        
    public static void main(String[] args) throws Exception {
        
        ConverterUtils.DataSource source = new ConverterUtils.DataSource(file);
        Instances dataSet = source.getDataSet();
        
        // discretize the dataset
        Discretize filter = new Discretize();
        filter.setInputFormat(dataSet);
        dataSet = Filter.useFilter(dataSet, filter);
    
        // standardize the dataset
        Standardize standardizedData = new Standardize();
        standardizedData.setInputFormat(dataSet);
        dataSet = Filter.useFilter(dataSet, standardizedData);
    
        // randomize the dataset
        dataSet.setClassIndex(dataSet.numAttributes() - 1);
        dataSet.randomize(new Debug.Random());
        
        // get the sizes of the training and testing sets and split
        int trainingSize = (int) Math.round(dataSet.numInstances() * .7);
        int testSize = dataSet.numInstances() - trainingSize;
        Instances training = new Instances(dataSet, 0, trainingSize);
        Instances test = new Instances(dataSet, trainingSize, testSize);       
               
        // set up the ID3 classifier on the training data
        ID3Classifiers classifier = new ID3Classifiers();
        classifier.buildClassifier(training);
        
        // set up the evaluation and test using the classifier and test set
        Evaluation eval = new Evaluation(dataSet);
        eval.evaluateModel(classifier, test);
        
        // outup and kill, important to exit here to stop javaFX
        System.out.println(eval.toSummaryString("\nResults\n======\n", false));
        System.exit(0);
    }
}
