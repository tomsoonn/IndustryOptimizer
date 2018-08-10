package agh.generator;

public class MainGen {

    public static void main(String[] args) {
        Generator gen =new Generator();

        //gen.generateRatio(10, Metals.Aluminium, true);
        //gen.generateRatioForAll(10, true);

        boolean numeric = true;
        int[] alsi = {80, 20, 0, 0, 0, 0, 0, 0, 0};
        gen.generateQ("AlSi.arff", alsi, 10000, true, numeric);
        gen.generateQ("AlSit.arff", alsi, 1000, true, numeric);
        gen.generateQ("AlSitu.arff", alsi, 10, false, numeric);

    }
}
