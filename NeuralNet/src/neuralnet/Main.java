package neuralnet;

import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.Debug.Random;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Standardize;

public class Main {
	
    static final String file = "C:/FishFiles/School/2015/Spring2015/cs450/cs450/files/iris.csv";
	
    public static void main(String[] args) throws Exception {
        
        DataSource source = new DataSource(file);
       
        Instances dataSet = source.getDataSet();
        dataSet.setClassIndex(dataSet.numAttributes() - 1);
        dataSet.randomize(new Random(1));
        
        int trainingSize = (int) Math.round(dataSet.numInstances() * .7);
        int testSize = dataSet.numInstances() - trainingSize;
        
        Instances training = new Instances(dataSet, 0, trainingSize);
        Instances test = new Instances(dataSet, trainingSize, testSize);
        
        Standardize standardizedData = new Standardize();
        standardizedData.setInputFormat(training);        
        
        Instances newTest = Filter.useFilter(test, standardizedData);
        Instances newTraining = Filter.useFilter(training, standardizedData);
        
        NeuralNetworkClassifier classifier = new NeuralNetworkClassifier();
        classifier.buildClassifier(newTraining);
        
        Evaluation eval = new Evaluation(newTraining);
        eval.evaluateModel(classifier, newTest);
        
        System.out.println(eval.toSummaryString("\nResults\n------\n", false));
    }  
}
