package agh.agents;

import agh.calculation.Calculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductionProcess {

    private final static Logger logger = LoggerFactory.getLogger(ProductionProcess.class);

    private String[] data;
    Calculator calculator;

    private double cost;

    private Long pid;

    private ProductionProcess() {
        pid = System.currentTimeMillis();
        //DBManager.getINSTANCE().saveProcess(new ProcessJson(pid));
    }

    public ProductionProcess(String[] data) {
        this();
        this.data = data;
        calculator = new Calculator();
    }

    private String firstStep() {
        //wyliczenie rzeczywistego składu
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

    private String secondStep() {
        //saveCurrentStage(2);
        return calculator.calculateCost(data);
    }

    private double thirdStep(){     //calculate cost
        return 0.0;
    }


    public String[] runProcess() {
        String[] result = new String[2];

        result[0] = firstStep();

        result[1] = secondStep();

        return result;
    }

}
