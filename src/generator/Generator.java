package generator;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import main.Main;

import java.util.concurrent.ThreadLocalRandom;

public class Generator {
    public static void generate(int quantity, String name){
        DBCollection collection = Main.database.getCollection(name);
        for (int j=0; j<quantity; j++){
            DBObject prod = new BasicDBObject("_id", j)
                    .append("name", "stop niklu")
                    .append("parametry", new BasicDBObject("metale", new BasicDBObject("nikiel", 60)
                            .append("miedź", 20)
                            .append("żelazo", 10)
                            .append("mangan", 5)
                            .append("magnez", 5))
                            .append("temp1", ThreadLocalRandom.current().nextInt(800, 1200 + 1))
                            .append("czas1", ThreadLocalRandom.current().nextInt(100, 200 + 1))
                            .append("temp2", ThreadLocalRandom.current().nextInt(1000, 1500 + 1))
                            .append("czas2", ThreadLocalRandom.current().nextInt(100, 200 + 1)))
                    .append("jakość", ThreadLocalRandom.current().nextInt(1, 5 + 1));

            collection.insert(prod);
        }
    }
}

