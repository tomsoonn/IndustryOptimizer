package agh.classification;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SimpleLinearRegression;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProductionData {

    RandomForest tree = new RandomForest();
    MultilayerPerceptron mlp = new MultilayerPerceptron();

    J48 tsree = new J48();
    SimpleLinearRegression reg = new SimpleLinearRegression();

    public ProductionData() {
    }

    public void trainAndTest() {
        try {
            DataSource source1 = new DataSource("AlSi.arff");
            DataSource source2 = new DataSource("AlSit.arff");
            Instances trainData = source1.getDataSet();
            Instances testData = source2.getDataSet();
            if (trainData.classIndex() == -1)
                trainData.setClassIndex(trainData.numAttributes() - 1);
            if (testData.classIndex() == -1)
                testData.setClassIndex(testData.numAttributes() - 1);

//            Standardize filter = new Standardize();
//            filter.setInputFormat(trainData);
//            trainData = Filter.useFilter(trainData, filter);
//            testData = Filter.useFilter(testData, filter);

//            NumericToNominal toNominalFilter = new NumericToNominal();
//            toNominalFilter.setInputFormat(trainData);
//            String[] options = new String[2];
//            options[0] = "-R";                                    // "range"
//            options[1] = "2,4,6,8,10,12,14,16,18";
//            toNominalFilter.setOptions(options);     // set the options
//            trainData = Filter.useFilter(trainData, toNominalFilter);
//            testData = Filter.useFilter(testData, toNominalFilter);

//            String[] options = new String[1];
//            options[0] = "-U";            // unpruned tree
//            tree.setOptions(options);     // set the options

            tree.buildClassifier(trainData);

            mlp.setLearningRate(0.3);
            mlp.setMomentum(0.2);
            mlp.setTrainingTime(2000);
            mlp.setHiddenLayers("3");
            mlp.buildClassifier(trainData);

            Evaluation eval = new Evaluation(trainData);

            eval.evaluateModel(tree, testData);
            System.out.println(eval.toSummaryString("\nResults RandomForest\n==================\n", false));
            eval.evaluateModel(mlp, testData);
            System.out.println(eval.toSummaryString("\nResults MultilayerPerceptron\n================\n", false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void classify() {
        Instances unlabeled = null;
        try {
            unlabeled = new Instances(new BufferedReader(new FileReader("AlSitu.arff")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // set class attribute
        unlabeled.setClassIndex(unlabeled.numAttributes() - 1);


        //Standardize filter = new Standardize();
        //NumericToNominal filter1 = new NumericToNominal();
        try {
            //filter.setInputFormat(unlabeled);
            //filter1.setInputFormat(unlabeled);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //unlabeled = Filter.useFilter(unlabeled, filter);
            //unlabeled = Filter.useFilter(unlabeled, filter1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Instances labeled = new Instances(unlabeled);

        //System.out.println(labeled.toString());

        // label instances
        for (int i = 0; i < unlabeled.numInstances(); i++) {
            double clsLabel = 0;
            try {
                clsLabel = tree.classifyInstance(unlabeled.instance(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
            labeled.instance(i).setClassValue(clsLabel);
            //System.out.println(clsLabel + " -> " + unlabeled.classAttribute().value((int) clsLabel));
        }
        System.out.println(unlabeled.toString() + "\n\nunalbeled--------------------------------------------------");
        System.out.println(labeled.toString() + "\n\nlabeled--------------------------------------------------");

        // save labeled data
//        BufferedWriter writer = new BufferedWriter(
//                new FileWriter("/some/where/labeled.arff"));
//        writer.write(labeled.toString());
//        writer.newLine();
//        writer.flush();
//        writer.close();
    }

}
