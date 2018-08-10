package agh.classification;

import agh.generator.Generator;

public class MainClass {

    public static void main(String[] args) {

        //Generator gen = new Generator();
        //lint[] alsi = {80, 20, 0, 0, 0, 0, 0, 0, 0};
        //gen.generateQ("AlSitu.arff", alsi, 10, false);

        ProductionData prod = new ProductionData();
        prod.trainAndTest();
        prod.classify();

    }
}
