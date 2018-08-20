package agh.classification;

import agh.Main;
import agh.generator.Generator;

public class WekaManager {

    public static String makePrediction(String[] data, int classifier) {

        String line = new String();
        for (int i = 0; i < data.length - 1; i++)
            line += data[i] + ",";
        line += "?";

        Generator.printLine("Prediction.arff", line);
        String quality = Main.productionData.classify("Prediction.arff", "Predicted.arff", classifier);

        return quality;
    }

}
