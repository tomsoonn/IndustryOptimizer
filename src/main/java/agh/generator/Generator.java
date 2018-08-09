package agh.generator;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import agh.Main;

public class Generator {
    private PrintWriter writer;

    public void generateRatio(int q,  Metals metal, boolean labeled) {
        String filename = "w"+metal.toString();
        try{
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
            writer.print("@ATTRIBUTE w"+metal.toString()+" numeric\n");

            writer.print("\n@DATA");

            System.out.println("generation started");
            Random r = new Random();
            int temperature =0;
            double ratio =0;
            for (int j=0; j<q; j++){
                ArrayList<Integer> load = n_random(9);
                writer.print("\n");
                temperature = r.nextInt(1500)+500;
                writer.print(temperature);
                writer.print(",");
                for(int i=0; i<load.size(); i++){
                    writer.print(load.get(i));
                    writer.print(",");
                }
                if(temperature<getMinTemperature(metal)) {
                    ratio = 0;
                }
                else if (temperature<1000) {
                    ratio = getRatio(metal);
                }
                else {
                    ratio = getRatio(metal)+(temperature/2000.0);
                }
                if(labeled) writer.print(ratio);
                else writer.print("?");

            }
            writer.close();
        } catch (IOException e) {
            writer.close();
        }
        System.out.println("generation ended");
    }

    public static ArrayList n_random(int n) {
        Random r = new Random();
        ArrayList<Integer> load = new ArrayList<Integer>();
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

    public int getMinTemperature(Metals metal){
        switch (metal) {
            case Aluminium : return 660;
            case Miedz : return 1084;
            case Nikiel : return 1453;
            case Cynk : return 420;
            case Olow : return 327;
            case Cyna : return 232;
            case Magnez : return 650;
            case Zelazo : return 1500;
            case Krzem : return 1410;
            default: return -1;
        }

    }

    public double getRatio(Metals metal){
        switch (metal) {
            case Aluminium : return 2;
            case Miedz : return 1.5;
            case Zelazo : return 1;
            case Cynk : return 2.3;
            case Olow : return 2.5;
            case Cyna : return 2.8;
            case Magnez : return 2;
            case Krzem : return 1;
            case Nikiel : return 1;
            default: return -1;

        }

    }
}

