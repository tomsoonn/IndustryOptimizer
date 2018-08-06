package agh.generator;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;


import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import agh.Main;

public class Generator {

    public static void generate(int quantity, String name){
        DBCollection collection = Main.database.getCollection(name);
        for (int j=0; j<quantity; j++){
            ArrayList<Integer> load = n_random();
            DBObject prod = new BasicDBObject("_id", j)
                    .append("name", "stop niklu")
                    .append("parametry", new BasicDBObject("metale", new BasicDBObject("nikiel", load.get(0))
                            .append("miedź", load.get(1))
                            .append("żelazo", load.get(2))
                            .append("mangan", load.get(3))
                            .append("magnez", load.get(4)))
                            .append("temp1", ThreadLocalRandom.current().nextInt(800, 1200 + 1))
                            .append("czas1", ThreadLocalRandom.current().nextInt(100, 200 + 1))
                            .append("temp2", ThreadLocalRandom.current().nextInt(1000, 1500 + 1))
                            .append("czas2", ThreadLocalRandom.current().nextInt(100, 200 + 1)))
                    .append("jakość", ThreadLocalRandom.current().nextInt(1, 5 + 1));

            collection.insert(prod);
        }
    }

    public static ArrayList n_random() {
        Random r = new Random();
        ArrayList<Integer> load = new ArrayList<Integer>();
        int temp;
        int sum = 0;
        for (int i = 1; i <= 5; i++) {
            if (!(i == 5)) {
                temp = r.nextInt((100 - sum) / (5 - i)) + 1;
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
}

