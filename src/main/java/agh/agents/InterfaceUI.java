package agh.agents;

//import pl.edu.agh.parameter.ProcessJson;

public interface InterfaceUI {
    //public double runProcess(Double[] metals, int mass, int meltingTemp, int metlingTime, int coolingTemp1, int heatingTime1, int coolingTemp2, int heatingTime2, int level);
    public String runProcess(String[] data, int classifier);
    //public List<ProcessJson> startQuery();
}
