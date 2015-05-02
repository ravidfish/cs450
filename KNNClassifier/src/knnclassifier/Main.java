/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knnclassifier;

import weka.classifiers.Evaluation;
import weka.core.Debug;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Standardize;
import weka.filters.unsupervised.instance.RemovePercentage;

/**
 *
 * @author Mark
 */
public class Main {
    public static void main(String[] args) throws Exception {
        
        String file = "C:\\FishFiles\\School\\2015\\Spring2015\\cs450\\cs450\\files\\iris.csv";
        ConverterUtils.DataSource source = new ConverterUtils.DataSource(file);
        Instances data = source.getDataSet();
        data.randomize(new Debug.Random());

        RemovePercentage filter = new RemovePercentage();
        filter.setPercentage(30);
        filter.setInputFormat(data);
        Instances training = Filter.useFilter(data, filter);
        Standardize standard1 = new Standardize();
        standard1.setInputFormat(training);
        Instances processed_train = Filter.useFilter(training, standard1);
        processed_train.setClassIndex(processed_train.numAttributes() - 1);

        filter.setInputFormat(data);
        filter.setInvertSelection(true);
        Instances test = Filter.useFilter(data, filter);
        Standardize standard2 = new Standardize();
        standard2.setInputFormat(test);
        Instances processed_test = Filter.useFilter(test, standard2);
        processed_test.setClassIndex(processed_test.numAttributes() - 1);

        KNNClassifier classifier = new KNNClassifier();

        Evaluation eval = new Evaluation(processed_train);
        eval.evaluateModel(classifier, processed_test);

        String summary = eval.toSummaryString();
        System.out.println(summary);
    }
}
