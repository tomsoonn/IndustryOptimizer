package agh.generator;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class Generator {
    private PrintWriter writer;

    public void generateRatioForAll(int quantity, boolean labeled){
        for (Metals metal : Metals.values()){
            generateRatio(quantity,metal,labeled);
        }
    }

    public void generateRatio(int quantity, Metals metal, boolean labeled) {
        String filename = "w" + metal.toString() + ".arff";
        try {
            writer = new PrintWriter(filename, "UTF-8");
            writer.print("@RELATION " + filename + "\n\n");
            writer.print("@ATTRIBUTE temperatura numeric\n");
            writer.print("@ATTRIBUTE Aluminium numeric\n");
            writer.print("@ATTRIBUTE Miedz numeric\n");
            writer.print("@ATTRIBUTE Nikiel numeric\n");
            writer.print("@ATTRIBUTE Cynk numeric\n");
            writer.print("@ATTRIBUTE Olow numeric\n");
            writer.print("@ATTRIBUTE Cyna numeric\n");
            writer.print("@ATTRIBUTE Magnez numeric\n");
            writer.print("@ATTRIBUTE Zelazo numeric\n");
            writer.print("@ATTRIBUTE Krzem numeric\n");
            writer.print("@ATTRIBUTE w" + metal.toString() + " numeric\n");

            writer.print("\n@DATA");

            System.out.println("generation started");
            Random r = new Random();
            int temperature;
            double ratio;
            for (int j = 0; j < quantity; j++) {
                ArrayList load = n_random(9);
                writer.print("\n");
                temperature = r.nextInt(1500) + 500;
                writer.print(temperature);
                writer.print(",");
                for (Object aLoad : load) {
                    writer.print(aLoad);
                    writer.print(",");
                }
                if (temperature < getMinTemperature(metal)) {
                    ratio = 0;
                } else if (temperature < 1000) {
                    ratio = getRatio(metal);
                } else {
                    ratio = getRatio(metal) + (temperature / 2000.0);
                }
                if (labeled) writer.print(String.format(Locale.US, "%.2g", ratio));
                else writer.print("?");

            }
            writer.close();
        } catch (IOException e) {
            writer.close();
        }
        System.out.println("generation ended");
    }


    private static ArrayList n_random(int n) {
        Random r = new Random();
        ArrayList<Integer> load = new ArrayList<>();
        int temp;
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            if (!(i == n)) {
                temp = r.nextInt((100 - sum) / (n - i)) + 1;
                load.add(temp);
                sum += temp;

            } else {
                int last = (100 - sum);
                load.add(last);
                sum += last;
            }
        }
        return load;
    }

    private int getHighestTemperatureArray(int[] metalList){
        int temperature = 0;
        for (Metals metal : Metals.values()){
            if (metalList[metal.getValue()-1] != 0){
                if (getMinTemperature(metal) > temperature)
                    temperature = getMinTemperature(metal);
            }
        }
        return temperature;
    }


    public void generateQ(String filename, int[] metals, int quantity){
        try {
            writer = new PrintWriter(filename, "UTF-8");
            writer.print("@RELATION " + filename + "\n\n");
            writer.print("@ATTRIBUTE Aluminium numeric\n");
            writer.print("@ATTRIBUTE Krzem numeric\n");
            writer.print("@ATTRIBUTE Magnez numeric\n");
            writer.print("@ATTRIBUTE Miedz numeric\n");
            writer.print("@ATTRIBUTE Cynk numeric\n");
            writer.print("@ATTRIBUTE Cyna numeric\n");
            writer.print("@ATTRIBUTE Nikiel numeric\n");
            writer.print("@ATTRIBUTE Zelazo numeric\n");
            writer.print("@ATTRIBUTE Olow numeric\n");
            writer.print("@ATTRIBUTE TemperaturaWytapiania numeric\n");
            writer.print("@ATTRIBUTE CzasWytapiania numeric\n");
            writer.print("@ATTRIBUTE TemperaturaStudzenia numeric\n");
            writer.print("@ATTRIBUTE CzasPodgrzewania numeric\n");
            writer.print("@ATTRIBUTE TemperaturaStudzenia2 numeric\n");
            writer.print("@ATTRIBUTE CzasPodgrzewania2 numeric\n");
            writer.print("@ATTRIBUTE StopieńUszlachetniania numeric\n");
            writer.print("@ATTRIBUTE Jakość numeric\n");

            writer.print("\n@DATA");

            System.out.println("start");
            Random r = new Random();
            int temperatureLevel = getHighestTemperatureArray(metals);
            for (int i = 0; i < quantity; i++){
                int quality = 90;
                writer.print("\n");
                for (int metal : metals) {
                    writer.print(metal);
                    writer.print(",");
                }
                int temperatureBound = (int) (0.7*temperatureLevel);
                int minTemperature = (int) (0.5*temperatureLevel);
                int temperature = (r.nextInt(temperatureBound) + minTemperature);
                writer.print(temperature);
                writer.print(",");

                if (temperature >= temperatureLevel){
                    quality += (temperature-temperatureLevel)/((minTemperature+temperatureBound-temperatureLevel)/10);
                } else {
                    quality -= (temperatureLevel - temperature)/(temperatureLevel*0.5/90);
                }

                int time = (r.nextInt(40) + 30);
                writer.print(time);
                writer.print(",");

                if (quality!=0)
                    quality -= Math.abs(time-50)/(20.0/(0.6*quality));

                int optimalCoolingTemperature = (int) (0.6666*temperature);

                int coolingTemperature = (r.nextInt((int) (0.25*temperature)) + optimalCoolingTemperature - (int) (0.25*temperature)/2);
                writer.print(coolingTemperature);
                writer.print(",");

                if (quality!=0)
                    quality -= Math.abs(coolingTemperature-optimalCoolingTemperature)/(((0.25*temperature)/2)/(0.3*quality));

                int heatingTime = (r.nextInt(20) + 5);
                writer.print(heatingTime);
                writer.print(",");

                if (quality!=0)
                    quality -= Math.abs(heatingTime-15)/(10.0/(0.1*quality));

                int optimalCoolingTemperature2 = (int) (0.5*optimalCoolingTemperature);

                int coolingTemperature2 = (r.nextInt((int) (0.25*coolingTemperature)) + optimalCoolingTemperature2 - (int) (0.25*optimalCoolingTemperature)/2);
                writer.print(coolingTemperature2);
                writer.print(",");

                if (quality!=0)
                    quality -= Math.abs(coolingTemperature2-optimalCoolingTemperature2)/(((0.25*optimalCoolingTemperature)/2)/(0.1*quality));

                int heatingTime2 = (r.nextInt(30) + 10);
                writer.print(heatingTime2);
                writer.print(",");

                if (quality!=0)
                    quality -= Math.abs(heatingTime2-25)/(15.0/(0.05*quality));

                int level = (r.nextInt(5) + 1);
                writer.print(level);
                writer.print(",");

                switch (level){
                    case 1: quality += 0;
                    break;
                    case 2: quality += 2;
                    break;
                    case 3: quality += 4;
                    break;
                    case 4: quality += 6;
                    break;
                    case 5: quality += 8;
                }

                writer.print(quality);
            }


            writer.close();
        } catch (IOException e) {
            writer.close();
        }
        System.out.println("koniec");
    }


    private int getMinTemperature(Metals metal) {
        switch (metal) {
            case Aluminium:
                return 660;
            case Miedz:
                return 1084;
            case Nikiel:
                return 1453;
            case Cynk:
                return 420;
            case Olow:
                return 327;
            case Cyna:
                return 232;
            case Magnez:
                return 650;
            case Zelazo:
                return 1500;
            case Krzem:
                return 1410;
            default:
                return -1;
        }

    }

    private double getRatio(Metals metal) {
        switch (metal) {
            case Aluminium:
                return 2;
            case Miedz:
                return 1.5;
            case Zelazo:
                return 1;
            case Cynk:
                return 2.3;
            case Olow:
                return 2.5;
            case Cyna:
                return 2.8;
            case Magnez:
                return 2;
            case Krzem:
                return 1;
            case Nikiel:
                return 1;
            default:
                return -1;

        }

    }
}

