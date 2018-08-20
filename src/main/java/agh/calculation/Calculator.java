package agh.calculation;

import agh.generator.Metals;

import java.util.Locale;

public class Calculator {

    public static String calculateInput(String[] data){
        String result = new String();
        Double mass = Double.parseDouble(data[data.length-1]);
        Double temp = Double.parseDouble(data[9]);

        for(int i=0; i<Metals.values().length; i++){

            Double tmp = (mass*(Double.parseDouble(data[i])/100.0)*((100.0 + Metals.getRatio(Metals.values()[i])+(temp/2000.0))/100.0));
            result += Metals.values()[i].toString() + " : "+ String.format(Locale.US, "%.2g", tmp) +"\n";
        }

        return result;
    }

    public static String calculateCost(String[] data){
        String result = new String();
        //TODO

        return result;
    }
}
