package agh.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;

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
                MessageTemplate checkState = MessageTemplate.MatchPerformative(CHECK_AGENT);
                MessageTemplate startLearning = MessageTemplate.MatchPerformative(AgentMessages.START_LEARNING);
                MessageTemplate startLearningAck = MessageTemplate.MatchPerformative(AgentMessages.START_LEARNING_ACK);
                ACLMessage checkMsg = receive(checkState);
                ACLMessage learnMsg = receive(startLearning);
                if (checkMsg!=null){
                    ACLMessage reply = new ACLMessage(CHECK_AGENT);
                    reply.setContent("success ");
                    reply.addReceiver(new AID( args[0].toString(), AID.ISLOCALNAME));
                    send(reply);
                }

                else if(learnMsg!=null) {
                    productionData.train("TrainingData.arff");
                    ACLMessage reply = new ACLMessage(AgentMessages.START_LEARNING_ACK);
                    reply.setContent("success ");
                    reply.addReceiver(new AID( args[0].toString(), AID.ISLOCALNAME));
                    send(reply);
                }
                block();
            }
        });
    }
}
