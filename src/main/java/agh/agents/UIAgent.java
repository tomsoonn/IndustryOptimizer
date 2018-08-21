package agh.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
//import pl.edu.agh.parameter.ProcessJson;

import java.util.ArrayList;
import java.util.Arrays;

public class UIAgent extends Agent implements InterfaceUI{

    private Object [] args;
    private int runProcessStep = 0;
    private int DBQueryStartStep = 0;
    private String agentResult;
    private boolean runProcessFinished = false;
    private boolean dbQueryFinished = false;

    //private List<ProcessJson> processes = null;

    public UIAgent(){
        registerO2AInterface(InterfaceUI.class, this);
    }

    //db access start for GUI
   /* public List<ProcessJson> startQuery() {
        while(!AgentQuery());
        dbQueryFinished = false;
        return processes;
    }*/

    public boolean AgentQuery(){
        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                switch(DBQueryStartStep) {
                    case (0):
                        ACLMessage msgQueryInit = new ACLMessage(AgentMessages.GET_PROCESS_IDS);
                        msgQueryInit.setContent("");
                        msgQueryInit.addReceiver(new AID(args[0].toString(), AID.ISLOCALNAME));
                        send(msgQueryInit);
                        DBQueryStartStep = 1;
                    case (1):
                        MessageTemplate msgTmp = MessageTemplate.MatchPerformative(AgentMessages.GET_PROCESS_IDS_ACK);
                        ACLMessage msgReceive = receive(msgTmp);
                        //processes = new ArrayList<>();
                        if(msgReceive!=null){
                            System.out.println("Hmm");
                            String [] stringPIDs = msgReceive.getContent().split(" ");
                            long []longPIDs = new long[stringPIDs.length];
                            /*for(int i = 0; i<stringPIDs.length;i++){
                                ProcessJson process = new ProcessJson();
                                process.setId(Long.parseLong(stringPIDs[i]));
                                processes.add(process);
                            }*/
                            dbQueryFinished = true;
                        }
                        break;
                    default:
                        break;
                }
                block();
            }

            @Override
            public boolean done() {
                return dbQueryFinished;
            }
        });

        return dbQueryFinished;
    }

    //process run interface for GUI
//    public double runProcess(Double[] metals, int mass, int meltingTemp, int metlingTime, int coolingTemp1, int heatingTime1, int coolingTemp2, int heatingTime2, int level){
//        addBehaviour(new Behaviour() {
//            @Override
//            public void action() {
//                //System.out.println("UIAgent----" + Arrays.toString(metals));
//                String msgContent = Arrays.toString(metals)+" "+
//                        Integer.toString(mass)+" "+
//                        Integer.toString(meltingTemp)+" "+
//                        Integer.toString(metlingTime)+" "+
//                        Integer.toString(coolingTemp1)+" "+
//                        Integer.toString(heatingTime1)+" "+
//                        Integer.toString(coolingTemp2)+" "+
//                        Integer.toString(heatingTime2)+" "+
//                        Integer.toString(level);
//                MessageTemplate msgTmp;
//                ACLMessage msgReceive;
//                switch(runProcessStep){
//
//                    case(0):
//                        ACLMessage msgProcessInit = new ACLMessage(AgentMessages.START_PROCESS_AGENT);
//                        msgProcessInit.setContent("");
//                        msgProcessInit.addReceiver(new AID( args[0].toString(), AID.ISLOCALNAME));
//                        send(msgProcessInit);
//                        runProcessStep = 1;
//                        block();
//
//                    case(1):
//                        msgTmp = MessageTemplate.MatchPerformative(AgentMessages.START_PROCESS_AGENT_ACK);
//                        msgReceive = receive(msgTmp);
//                        if(msgReceive!=null){
//                            runProcessStep = 2;
//                        }
//                        break;
//
//                    case(2):
//                        ACLMessage msgSetValues = new ACLMessage(AgentMessages.SET_PROCESS_VALUES);
//                        msgSetValues.setContent(msgContent);
//                        msgSetValues.addReceiver(new AID( args[0].toString(), AID.ISLOCALNAME));
//                        send(msgSetValues);
//                        runProcessStep = 3;
//                        break;
//
//                    case(3):
//                        msgTmp = MessageTemplate.MatchPerformative(AgentMessages.SET_PROCESS_VALUES_ACK);
//                        msgReceive = receive(msgTmp);
//                        if(msgReceive!=null){
//                            runProcessStep = 4;
//                        }
//                        break;
//
//                    case(4):
//                        ACLMessage msgStartProcess = new ACLMessage(AgentMessages.START_PROCESS);
//                        msgStartProcess.setContent("");
//                        msgStartProcess.addReceiver(new AID( args[0].toString(), AID.ISLOCALNAME));
//                        send(msgStartProcess);
//                        runProcessStep = 5;
//                        break;
//
//                    case(5):
//                        msgTmp = MessageTemplate.MatchPerformative(AgentMessages.RECEIVE_RESULT);
//                        msgReceive = receive(msgTmp);
//                        if(msgReceive!=null){
//                            runProcessFinished = true;
//                            agentResult = Double.parseDouble(msgReceive.getContent());
//                        }
//                        break;
//                    default:
//                        break;
//                }
//            }
//
//            @Override
//            public boolean done() {
//                return runProcessFinished;
//            }
//        });
//
//        runProcessFinished = false;
//        runProcessStep = 0;
//        return agentResult;
//    }

    public String runProcess(String[] data, int classifier) {
        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                //System.out.println("UIAgent----" + Arrays.toString(metals));
                String msgContent = Arrays.toString(data)+" "+
                        Integer.toString(classifier);
                MessageTemplate msgTmp;
                ACLMessage msgReceive;
                switch(runProcessStep){

                    case(0):
                        ACLMessage msgProcessInit = new ACLMessage(AgentMessages.START_PROCESS_AGENT);
                        msgProcessInit.setContent("");
                        msgProcessInit.addReceiver(new AID( args[0].toString(), AID.ISLOCALNAME));
                        send(msgProcessInit);
                        runProcessStep = 1;
                        block();

                    case(1):
                        msgTmp = MessageTemplate.MatchPerformative(AgentMessages.START_PROCESS_AGENT_ACK);
                        msgReceive = receive(msgTmp);
                        if(msgReceive!=null){
                            runProcessStep = 2;
                        }
                        break;

                    case(2):
                        ACLMessage msgSetValues = new ACLMessage(AgentMessages.SET_PROCESS_VALUES);
                        msgSetValues.setContent(msgContent);
                        msgSetValues.addReceiver(new AID( args[0].toString(), AID.ISLOCALNAME));
                        send(msgSetValues);
                        runProcessStep = 3;
                        break;

                    case(3):
                        msgTmp = MessageTemplate.MatchPerformative(AgentMessages.SET_PROCESS_VALUES_ACK);
                        msgReceive = receive(msgTmp);
                        if(msgReceive!=null){
                            runProcessStep = 4;
                        }
                        break;

                    case(4):
                        ACLMessage msgStartProcess = new ACLMessage(AgentMessages.START_PROCESS);
                        msgStartProcess.setContent("");
                        msgStartProcess.addReceiver(new AID( args[0].toString(), AID.ISLOCALNAME));
                        send(msgStartProcess);
                        runProcessStep = 5;
                        break;

                    case(5):
                        msgTmp = MessageTemplate.MatchPerformative(AgentMessages.RECEIVE_RESULT);
                        msgReceive = receive(msgTmp);
                        if(msgReceive!=null){
                            runProcessFinished = true;
                            agentResult = msgReceive.getContent();

                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public boolean done() {
                return runProcessFinished;
            }
        });

        runProcessFinished = false;
        runProcessStep = 0;
        while (agentResult==null) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return agentResult;
    }


    protected void setup()
    {
        args = getArguments();

        addBehaviour(new CyclicBehaviour(this)
        {
            public void action()
            {
                ArrayList<MessageTemplate> templates = new ArrayList<>();
                templates.add(MessageTemplate.MatchPerformative(AgentMessages.CHECK_AGENT));
                templates.add(MessageTemplate.MatchPerformative(1));
                ACLMessage [] checkMsg = new ACLMessage[templates.size()];

                int counter=0;
                for(MessageTemplate checkState: templates){
                    checkMsg[counter++] = receive(checkState);
                }

                for(ACLMessage msg: checkMsg){
                    if(msg != null){
                        //confirming that agent is working
                        if (msg.getPerformative() == AgentMessages.CHECK_AGENT) {
                            ACLMessage reply = new ACLMessage(AgentMessages.CHECK_AGENT);
                            reply.setContent("success");
                            reply.addReceiver(new AID(args[0].toString(), AID.ISLOCALNAME));
                            send(reply);
                        }

                        else if (msg.getPerformative() == 1){
                            System.out.println(msg.getContent());
                        }
                    }

                block();
                }
            }
        });
    }
}
