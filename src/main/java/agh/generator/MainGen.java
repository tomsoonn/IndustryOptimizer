package agh.generator;

public class MainGen {

    public static void main(String[] args) {
        Generator gen =new Generator();

        //gen.generateRatio(10, Metals.Aluminium, true);
        //gen.generateRatioForAll(10, true);

        int[] metals = {80, 20, 0, 0, 0, 0, 0, 0, 0};
        int[] metals2 = {80, 15, 5, 0, 0, 0, 0, 0, 0};
        int[] metals3 = {80, 12, 2, 6, 0, 0, 0, 0, 0};
        int[] metals4 = {8, 2, 90, 0, 0, 0, 0, 0, 0};
        int[] metals5 = {7, 0, 90, 0, 3, 0, 0, 0, 0};
        int[] metals6 = {0, 0, 0, 98, 0, 2, 0, 0, 0};
        int[] metals7 = {0, 0, 0, 70, 30, 0, 0, 0, 0};
        int[] metals8 = {0, 0, 0, 30, 0, 0, 67, 3, 0};
        int[] metals9 = {0, 0, 0, 2, 0, 3, 0, 0, 95};
        gen.generateQ("AlSi.arff", metals, 1000);
        gen.generateQ("AlSiMg.arff", metals2, 1000);
        gen.generateQ("AlSiMgCu.arff", metals3, 1000);
        gen.generateQ("MgAlSi.arff", metals4, 1000);
        gen.generateQ("MgAlZn.arff", metals5, 1000);
        gen.generateQ("CuSn.arff", metals6, 1000);
        gen.generateQ("CuZn.arff", metals7, 1000);
        gen.generateQ("NiCuFe.arff", metals8, 1000);
        gen.generateQ("PbZnCu.arff", metals9, 1000);

    }
}
