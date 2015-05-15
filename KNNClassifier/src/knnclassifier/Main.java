/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knnclassifier;

import weka.classifiers.Evaluation;
import weka.core.Debug.Random;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Standardize;

/**
 *
 * @author Mark
 */
public class Main {
    
    //static final String file = "C:\FishFiles\School\2015\Spring2015\cs450\cs450\files";
    static final String file = "C:\\FishFiles\\School\\2015\\Spring2015\\cs450\\cs450\\files\\iris.csv";
        
    public static void main(String[] args) throws Exception {
        
        DataSource source = new DataSource(file);
        Instances dataSet = source.getDataSet();
        
        //Set up data
        dataSet.setClassIndex(dataSet.numAttributes() - 1);
        dataSet.randomize(new Random());
        
        int trainingSize = (int) Math.round(dataSet.numInstances() * .7);
        int testSize = dataSet.numInstances() - trainingSize;
        
        Instances training = new Instances(dataSet, 0, trainingSize);
        
        Instances test = new Instances(dataSet, trainingSize, testSize);
        
        Standardize standardizedData = new Standardize();
        standardizedData.setInputFormat(training);        
        
        Instances newTest = Filter.useFilter(test, standardizedData);
        Instances newTraining = Filter.useFilter(training, standardizedData);
        
        KNNClassifier knn = new KNNClassifier();
        knn.buildClassifier(newTraining);
        
        Evaluation eval = new Evaluation(newTraining);
        eval.evaluateModel(knn, newTest);
        
        System.out.println(eval.toSummaryString("\nResults\n======\n", false));
    }
}
