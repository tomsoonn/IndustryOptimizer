package agh.classification;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.GaussianProcesses;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMOreg;
import weka.classifiers.functions.SimpleLinearRegression;
import weka.classifiers.lazy.IBk;
import weka.classifiers.lazy.KStar;
import weka.classifiers.lazy.LWL;
import weka.classifiers.meta.*;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.*;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductionData {

    private MultilayerPerceptron mlp = new MultilayerPerceptron();
    private RandomForest forest = new RandomForest();
    private Classifier actualClassifier = forest;
    private List<Classifier> classifiers = new ArrayList<>();

    public ProductionData() {
        initailizeClassifiers();
    }

    //asForNow
    private void initailizeClassifiers() {
        classifiers.add(new MultilayerPerceptron());
        classifiers.add(new RandomForest());
        classifiers.add(new REPTree());
        classifiers.add(new DecisionTable());
        classifiers.add(new M5P());
        classifiers.add(new RandomCommittee());
        classifiers.add(new RandomForest());
        classifiers.add(new RandomTree());
        classifiers.add(new KStar());
        classifiers.add(new AdditiveRegression());
    }

    public void trainAndTest(String trainFile, String testFile) {
        try {
            DataSource source1 = new DataSource(trainFile);
            DataSource source2 = new DataSource(testFile);
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


            actualClassifier.buildClassifier(trainData);

            mlp.setLearningRate(0.3);
            mlp.setMomentum(0.2);
            mlp.setTrainingTime(2000);
            mlp.setHiddenLayers("3");
            //mlp.buildClassifier(trainData);

            Evaluation eval = new Evaluation(trainData);

            eval.evaluateModel(actualClassifier, testData);
            System.out.println(eval.toSummaryString("\nResults: "+actualClassifier.toString()+"\n==================\n", false));
            //eval.evaluateModel(mlp, testData);
            //System.out.println(eval.toSummaryString("\nResults MultilayerPerceptron\n================\n", false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void classify(String unlabeledFile, String labeledFile) {
        Instances unlabeled = null;
        try {
            unlabeled = new Instances(new BufferedReader(new FileReader(unlabeledFile)));
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
                clsLabel = actualClassifier.classifyInstance(unlabeled.instance(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
            labeled.instance(i).setClassValue(clsLabel);
            //System.out.println(clsLabel + " -> " + unlabeled.classAttribute().value((int) clsLabel));
        }
        System.out.println(unlabeled.toString() + "\n\nunalbeled--------------------------------------------------\n");
        System.out.println(labeled.toString() + "\n\nlabeled--------------------------------------------------\n");

        // save labeled data
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(labeledFile));
            writer.write(labeled.toString());
            writer.newLine();
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
