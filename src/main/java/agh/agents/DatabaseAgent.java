package agh.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


import java.util.List;

public class DatabaseAgent extends Agent {
    protected void setup()
    {
        final int CHECK_AGENT = 100;
        Object [] args = getArguments();

        addBehaviour(new CyclicBehaviour(this)
        {
            public void action()
            {
                MessageTemplate checkState = MessageTemplate.MatchPerformative(CHECK_AGENT);
                MessageTemplate getDatabase = MessageTemplate.MatchPerformative(AgentMessages.GET_PROCESS_IDS);
                ACLMessage checkMsg = receive(checkState);
                ACLMessage dbMsg = receive(getDatabase);
                if (checkMsg!=null){
                    ACLMessage reply = new ACLMessage(CHECK_AGENT);
                    reply.setContent("success");
                    reply.addReceiver(new AID( args[0].toString(), AID.ISLOCALNAME));
                    send(reply);
                }
                else if(dbMsg!=null) {
                   /* List<ProcessJson> processes = DBManager.getINSTANCE().findAllProcesses();
                    String message = "";
                    for(ProcessJson process: processes){
                        message += process.getId().toString()+" ";
                    }

                    ACLMessage reply = new ACLMessage(AgentMessages.GET_PROCESS_IDS_ACK);
                    reply.setContent(message);
                    reply.addReceiver(new AID( args[0].toString(), AID.ISLOCALNAME));
                    send(reply);
                }*/
                }
                block();
            }
        });
    }
}