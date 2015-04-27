package hardcodedclassifier;

import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.Debug.Random;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemovePercentage;
import weka.core.converters.ConverterUtils.DataSource;

public class Main {
	public static void main(String[] args) throws Exception {
		String file = "C:\\FishFiles\\School\\2015\\Spring2015\\cs450\\cs450\\HardCodedClassifier\\files\\iris.csv";
		DataSource source = new DataSource(file);
		Instances data = source.getDataSet();
		data.randomize(new Random());
		
		RemovePercentage filter = new RemovePercentage();
		filter.setPercentage(30);
		filter.setInputFormat(data);
		Instances training = Filter.useFilter(data, filter);
		
		System.out.println("number of training: " + training.numInstances());
		
		training.setClassIndex(training.numAttributes() - 1);
		filter.setInputFormat(data);
		filter.setInvertSelection(true);
		Instances test = Filter.useFilter(data, filter);
		
		System.out.println("number of testing: " + test.numAttributes());
		
		test.setClassIndex(test.numAttributes() - 1);
		
		HardCodedClassifier classifier = new HardCodedClassifier();
		
		Evaluation eval = new Evaluation(training);
		eval.evaluateModel(classifier, test);
		
		String summary = eval.toSummaryString();
		System.out.println(summary);
	}
}