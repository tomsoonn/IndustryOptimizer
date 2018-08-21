package agh.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.Arrays;

import static agh.Main.productionData;

public class LearningAgent extends Agent {

    protected void setup()
    {
        final int CHECK_AGENT = 100;

        Object [] args = getArguments();

        addBehaviour(new CyclicBehaviour(this)
        {
            public void action()
            {
                ACLMessage reply;

                ArrayList<MessageTemplate> templates = new ArrayList<>();
                templates.add(MessageTemplate.MatchPerformative(AgentMessages.CHECK_AGENT));
                templates.add(MessageTemplate.MatchPerformative(AgentMessages.START_LEARNING));

                ACLMessage [] checkMsg = new ACLMessage[templates.size()];
                int counter = 0;
                for(MessageTemplate checkState: templates){
                    checkMsg[counter++] = receive(checkState);
                }
                for(ACLMessage msg: checkMsg) {
                    if (msg != null) {
                        switch(msg.getPerformative()) {
                            case (AgentMessages.CHECK_AGENT):
                                reply = new ACLMessage(AgentMessages.CHECK_AGENT);
                                reply.setContent("success");
                                reply.addReceiver(new AID( args[0].toString(), AID.ISLOCALNAME));
                                send(reply);
                                break;
                            case (AgentMessages.START_LEARNING):
                                System.out.println("Training in agent");
                                productionData.train("TrainingData.arff");
                                reply = new ACLMessage(AgentMessages.START_LEARNING_ACK);
                                System.out.println("Training done");
                                reply.setContent("success ");
                                reply.addReceiver(new AID( args[0].toString(), AID.ISLOCALNAME));
                                send(reply);
                        }
                    }
                }

                block();
            }
        });
    }
}
