package agh.classification;

public class MainClass {

    public static void main(String[] args) {

        ProductionData prod = new ProductionData();
        prod.train("TrainingData.arff");
        System.out.println(prod.test("TrainingData.arff", "TrainingData.arff", 0));

    }
}
