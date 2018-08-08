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
    private Scanner in = new Scanner(System.in);

    public void generate(int q, String filename) {
        try{
            writer = new PrintWriter(filename, "UTF-8");
            writer.print("@RELATION " + filename + "\n\n");
            writer.print("@ATTRIBUTE temperatura numeric\n");
            writer.print("@ATTRIBUTE Aluminium numeric\n");
            writer.print("@ATTRIBUTE wAl numeric\n");
            writer.print("@ATTRIBUTE Miedz numeric\n");
            writer.print("@ATTRIBUTE wMi numeric\n");
            writer.print("@ATTRIBUTE Nikiel numeric\n");
            writer.print("@ATTRIBUTE wNi numeric\n");
            writer.print("@ATTRIBUTE Cynk numeric\n");
            writer.print("@ATTRIBUTE wCy numeric\n");
            writer.print("@ATTRIBUTE Olow numeric\n");
            writer.print("@ATTRIBUTE wOl numeric\n");
            writer.print("@ATTRIBUTE Cyna numeric\n");
            writer.print("@ATTRIBUTE wCy numeric\n");
            writer.print("@ATTRIBUTE Magnez numeric\n");
            writer.print("@ATTRIBUTE wMa numeric\n");
            writer.print("@ATTRIBUTE Zelazo numeric\n");
            writer.print("@ATTRIBUTE wZe numeric\n");
            writer.print("@ATTRIBUTE Krzem numeric\n");
            writer.print("@ATTRIBUTE wKr numeric\n");

            writer.print("\n@DATA");

            System.out.println("start");
            Random r = new Random();
            int temperature =0;
            double ratio =0;
            for (int j=0; j<q; j++){
                ArrayList<Integer> load = n_random(9);
                writer.print("\n");
                temperature = r.nextInt(1500)+500;
                writer.print(temperature);
                writer.print(",");
                for(int i=0; i<load.size()-2; i++){
                    writer.print(load.get(i));
                    writer.print(",");
                    if(temperature<getMinTemperature(i+1))
                        ratio = 0;
                    else if (temperature<1000) {
                        ratio = getRatio(i+1);
                    }
                    else
                        ratio = getRatio(i+1)+(temperature/5000);
                    writer.print(ratio);
                    writer.print(",");
                }
                writer.print(load.get(load.size()-1));
                writer.print(",");
                if(temperature<getMinTemperature(load.size()-1+1))
                    writer.print(0.0f);
                else if (temperature<1000) {
                    writer.print(getRatio(load.size()-1+1));
                }
                else
                    writer.print(getRatio(load.size()-1+1)+(temperature/5000));

            }
            writer.close();
        } catch (IOException e) {
            writer.close();
        }
        System.out.println("koniec");
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

    public int getMinTemperature(int i){
        switch (i) {
            case 1 : return 660;
            case 2 : return 1084;
            case 3 : return 1453;
            case 4 : return 420;
            case 5 : return 327;
            case 6 : return 232;
            case 7 : return 650;
            case 8 : return 1500;
            case 9 : return 1410;
            default: return 0;
        }

    }

    public double getRatio(int i){
        switch (i) {
            case 1 : return 2;
            case 2 : return 1.5;
            case 3 : return 1;
            case 4 : return 2.3;
            case 5 : return 2.5;
            case 6 : return 2.8;
            case 7 : return 2;
            case 8 : return 1;
            case 9 : return 1;
            default: return 0;

        }

    }
}

