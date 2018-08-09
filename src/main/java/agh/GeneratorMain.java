package agh;

import agh.generator.Generator;

public class GeneratorMain {
    public static void main(String[] args) {
        Generator generator = new Generator();
        //generator.generateQuality(20, "AlSi2.arff", "110000000", 20, 0.666, 20, 0.5, 20,70);
        //generator.generateQuality(20, "AlSi3.arff", "110000000", 25, 0.666, 10, 0.5, 25,85);
        //generator.generateQuality(20, "AlSi4.arff", "110000000", 10, 0.666, 20, 0.5, 15,65);
        //generator.generateQuality(20, "AlSi5.arff", "110000000", 10, 0.666, 20, 0.5, 15,57);
        //generator.generateQuality(20, "AlSi6.arff", "110000000", 10, 0.666, 20, 0.5, 15,35);
        //generator.generateQuality(20, "AlSi7.arff", "110000000", 10, 0.86, 40, 0.8, 15,20);
        //generator.generateQuality(20, "AlSi8.arff", "111111000", 30, 0.666, 10, 0.5, 25,80);
        //generator.generateQuality(10, "MgAlSi.arff", "111000000", 30, 0.666, 10, 0.5, 25,95);
        //generator.generateQuality(10, "MgAlSi1.arff", "111000000", 30, 0.666, 10, 0.5, 25,65);
        //generator.generateQuality(10, "MgAlSi2.arff", "111000000", 30, 0.666, 10, 0.5, 25,10);
        //generator.generateQuality(10, "MgAlSi3.arff", "111000000", 20, 0.666, 20, 0.5, 20,70);
        //generator.generateQuality(10, "MgAlSi4.arff", "111000000", 25, 0.666, 10, 0.5, 20,85);
        //generator.generateQuality(10, "MgAlSi5.arff", "111000000", 10, 0.666, 30, 0.5, 15,50);
        //generator.generateQuality(10, "MgAlSi8.arff", "111000000", 5, 0.666, 20, 0.5, 20,25);
        //generator.generateQuality(10, "MgAlSi8.arff", "111000000", 30, 0.26, 10, 0.5, 23,35);

        //generator.generateQuality(10, "NiCuFe.arff", "000100110", 30, 0.666, 10, 0.5, 25,95);
        //generator.generateQuality(10, "NiCuFe1.arff", "000100110", 30, 0.666, 10, 0.5, 25,65);
        //generator.generateQuality(10, "NiCuFe2.arff", "000100110", 30, 0.666, 10, 0.5, 25,10);
        //generator.generateQuality(10, "NiCuFe3.arff", "000100110", 20, 0.666, 20, 0.5, 20,70);
        //generator.generateQuality(10, "NiCuFe4.arff", "000100110", 25, 0.666, 10, 0.5, 20,85);
        //generator.generateQuality(10, "NiCuFe5.arff", "000100110", 10, 0.666, 30, 0.5, 15,50);
        //generator.generateQuality(10, "NiCuFe8.arff", "000100110", 5, 0.666, 20, 0.5, 20,25);
        //generator.generateQuality(10, "NiCuFe8.arff", "111000000", 30, 0.26, 10, 0.5, 23,35);

        //generator.generateQuality(10, "CuZn.arff", "000110000", 30, 0.666, 10, 0.5, 25,95);
        //generator.generateQuality(10, "CuZn1.arff", "000110000", 30, 0.666, 10, 0.5, 25,65);
        //generator.generateQuality(10, "CuZn2.arff", "000110000", 30, 0.666, 10, 0.5, 25,10);
        //generator.generateQuality(10, "CuZn3.arff", "000110000", 20, 0.666, 20, 0.5, 20,70);
        //generator.generateQuality(10, "CuZn4.arff", "000110000", 25, 0.666, 10, 0.5, 20,85);
        //generator.generateQuality(10, "CuZn5.arff", "000110000", 10, 0.666, 30, 0.5, 15,50);
        //generator.generateQuality(10, "CuZn6.arff", "000110000", 5, 0.666, 20, 0.5, 20, 25, 300);
        int[] metals = {80, 20, 0, 0, 0, 0, 0, 0, 0};
        generator.generateQ("testt1.arff", metals, 100);
    }
}

//minTime = 30, coeff = 2/3, minHeatingTime = 10, coolingTempCoeff2 = 0.5, minHeatTime2 = 25