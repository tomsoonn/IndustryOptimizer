package agh.classification;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.meta.*;
import weka.classifiers.trees.*;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.*;

public class ProductionData {

    private MultilayerPerceptron mlp = new MultilayerPerceptron();
    private RandomForest forest = new RandomForest();
    private M5P m5p = new M5P();
    private Vote vote = new Vote();
    private Classifier actualClassifier;

    public ProductionData() {
    }

    public void train(String trainFile) {
        try {
            DataSource source1 = new DataSource(trainFile);
            Instances trainData = source1.getDataSet();
            if (trainData.classIndex() == -1)
                trainData.setClassIndex(trainData.numAttributes() - 1);

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

            forest.buildClassifier(trainData);
            m5p.buildClassifier(trainData);
            //mlp.setLearningRate(0.3);
            //mlp.setMomentum(0.2);
            //mlp.setTrainingTime(2000);
            //mlp.setHiddenLayers("3");
            mlp.buildClassifier(trainData);
            vote.setClassifiers(new Classifier[]{new M5P(), new RandomForest(), new MultilayerPerceptron()});
            vote.buildClassifier(trainData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String test(String trainFile, String testFile, int classifier) {
        try {
            DataSource source1 = new DataSource(trainFile);
            DataSource source2 = new DataSource(testFile);
            Instances trainData = source1.getDataSet();
            Instances testData = source2.getDataSet();
            if (trainData.classIndex() == -1)
                trainData.setClassIndex(trainData.numAttributes() - 1);
            if (testData.classIndex() == -1)
                testData.setClassIndex(testData.numAttributes() - 1);
            Evaluation eval = new Evaluation(trainData);

            setActualClassifier(classifier);

            eval.evaluateModel(actualClassifier, testData);
            return eval.toSummaryString("\nResults: " + actualClassifier.toString() + "\n==================\n", true);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String classify(String unlabeledFile, String labeledFile, int classifier) {
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

        setActualClassifier(classifier);

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

        String result = labeled.toString().split(",")[16].substring(0, 6);


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
        return result;
    }

    private void setActualClassifier(int classifier) {
        switch (classifier) {
            case 0:
                actualClassifier = mlp;
                break;
            case 1:
                actualClassifier = m5p;
                break;
            case 2:
                actualClassifier = forest;
                break;
            case 3:
                actualClassifier = vote;
                break;
            default:
                actualClassifier = mlp;
                break;

        }
    }
}
