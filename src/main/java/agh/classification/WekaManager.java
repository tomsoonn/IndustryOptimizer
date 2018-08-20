package agh.classification;

import agh.generator.Generator;

public class WekaManager {

    public static String[] makePrediction(String[] data){
        String[] result = new String[3];

        String line = new String();

        for (int i=0; i<data.length-1; i++)
            line += data[i] +",";
        line += "?";

        Generator.printLine("Predicion.arff", line);


        return result;
    }
}
