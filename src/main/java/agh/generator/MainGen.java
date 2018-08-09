package agh.generator;

public class MainGen {

    public static void main(String[] args) {
        Generator gen =new Generator();

        //gen.generateRatio(10, Metals.Aluminium, true);
        //gen.generateRatioForAll(10, true);

        int[] metals = {80, 20, 0, 0, 0, 0, 0, 0, 0};
        gen.generateQ("testt1.arff", metals, 100);

    }
}
