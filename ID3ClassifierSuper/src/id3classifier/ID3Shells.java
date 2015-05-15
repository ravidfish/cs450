/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id3classifier;

import java.util.Random;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Standardize;

/**
 *
 * @author Mark
 */

public class ID3Shells {
    
    public static void main(String[] args) throws Exception {
            
        ConverterUtils.DataSource source = new ConverterUtils.DataSource("C:\\FishFiles\\School\\2015\\Spring2015\\cs450\\cs450\\files\\iris.csv");
        Instances dataSet = source.getDataSet();
            
        Standardize standardize = new Standardize();
        standardize.setInputFormat(dataSet);
        dataSet = Filter.useFilter(dataSet, standardize);
            
        dataSet.setClassIndex(dataSet.numAttributes() - 1);
        dataSet.randomize(new Random()); 
            
        int trainingSize = (int) Math.round(dataSet.numInstances() * .7);
        int testSize = dataSet.numInstances() - trainingSize;
            
        Instances trainingData = new Instances(dataSet, 0, trainingSize);
        Instances testData = new Instances(dataSet, trainingSize, testSize);
            
        ID3Classifiers classifier = new ID3Classifiers();
        classifier.buildClassifier(trainingData);
            
        Evaluation eval = new Evaluation(trainingData);
        eval.evaluateModel(classifier, testData);
            
        System.out.println(eval.toSummaryString("\nResults:\n", false));
        
        System.exit(0);
    }
}
