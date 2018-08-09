package agh.generator;

import agh.classification.ProductionData;

import java.io.IOException;

import static java.lang.System.exit;

public class MainGen {

    public static void main(String[] args) throws IOException, InterruptedException {
        Generator gen =new Generator();
        //gen.generate(5000, "train.arff");
        //gen.generate(1000, "test.arff");
        gen.generateRatio(5, Metals.Aluminium, true);

        //ProductionData prod = new ProductionData();
        //prod.trainAndTest(12);
        //prod.trainAndTest(16);

    }
}
