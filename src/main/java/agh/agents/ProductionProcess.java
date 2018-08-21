package agh.agents;

import agh.calculation.Calculator;
import agh.classification.WekaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductionProcess {

    private final static Logger logger = LoggerFactory.getLogger(ProductionProcess.class);

    private String[] data;
    private int classifier;
    Calculator calculator;


    private Long pid;

    private ProductionProcess() {
        pid = System.currentTimeMillis();
        //DBManager.getINSTANCE().saveProcess(new ProcessJson(pid));
    }

    public ProductionProcess(String[] data, int classifier) {
        this();
        this.data = data;
        this.classifier = classifier;
        calculator = new Calculator();
    }

    private String firstStep() {    //wyliczenie rzeczywistego składu
        return calculator.calculateInput(data);
    }

    private void saveCurrentStage(int stageNum) {
        /*DBManager.getINSTANCE()
                .saveProductionInput(new ProductionInput(
                        System.currentTimeMillis(), stageNum, pid,
                        Stream.of(temperature, flexibility, surface, stiffness, amount, mass, volume)
                                .map(it -> it.toJson(pid, stageNum))
                                .collect(Collectors.toList())
                                .toArray(new ParameterJson[7])));
                                */
    }

    private String secondStep() {   //wyliczenie kosztu
        //saveCurrentStage(2);
        return calculator.calculateCost(data);
    }

    private String thirdStep(){     //wyznaczenie jakości
        return WekaManager.makePrediction(data, classifier);
    }

    public String[] runProcess() {
        String[] result = new String[3];

        result[0] = firstStep();
        result[1] = secondStep();
        result[2] = thirdStep();

        return result;
    }

}
