package org.example.PBFT;

import cn.hutool.core.util.StrUtil;
import org.example.PBFT.Msg.Impl.Commit;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

//  PBFT Main
public class PBFT {

    private final List<Agent> agentList;
    private final Client client;
    private final String[] NodeID;

    //  constructor
    public PBFT() {
        int nodeNum = 4;
        this.NodeID = new String[]{"111", "222", "333", "444"};
        this.agentList = new LinkedList<>();
        for (int i = 0; i < nodeNum; i++) {
            this.agentList.add(new Agent(NodeID[i]));
        }
        this.client = new Client();
    }

    public void main(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            int op = operator();
            switch (op){
                case 1://  Send a message
                    System.out.println("Please input the message you want to send:");
                    String data = scanner.next();
                    System.out.println("Please input the receiver's ID:"+NodeID[0]+" "+NodeID[1]+" "+NodeID[2]+" "+NodeID[3]);
                    String receiverID = scanner.next();

                    List<Boolean> ACKList = new LinkedList<>();

                    //  Send the message
                    client.sendMsg(agentList,data,receiverID);
                    //  Send PrePrepare
                    agentList.forEach((Agent agent)->{
                        if (StrUtil.compare(receiverID,receiverID,true)==0){
                            agent.sendPrePrepare(agentList);
                        }
                    });
                    //  Send Prepare and Commit
                    agentList.forEach((Agent agent)-> ACKList.add(agent.prepareAndCommit(agentList,client)));
                    System.out.println("Send successfully! ACKList:"+ACKList);
                    break;
                case 2://  Get the reply
                    List<Commit> reply = client.getReply();
                    reply.forEach((Commit commit)->{
                        System.out.println("Reply-"+commit.getNodeID()+" :"+reply);
                        System.out.println();
                    });
                    break;
                case 3://  Get sb's BlockChain
                    System.out.println("Please input the receiver's ID:"+NodeID[0]+" "+NodeID[1]+" "+NodeID[2]+" "+NodeID[3]);
                    String receiverID2 = scanner.next();
                    agentList.forEach((Agent agent)->{
                        if (StrUtil.compare(agent.getNodeID(),receiverID2,true)==0){
                            System.out.println(agent.getNodeID()+":"+agent.getBlockChain());
                            System.out.println();
                        }
                    });
                    break;
                case 4://  Get All BlockChain
                    agentList.forEach((Agent agent)->{
                        System.out.println(agent.getNodeID()+":"+agent.getBlockChain());
                        System.out.println();
                    });
                    break;
                case 5://  Exit
                    return;
                default:
                    System.out.println("Please input the correct number!");
            }
        }
    }

    int operator(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("##------------------------------------");
        System.out.println("Please input the number of the operation you want to perform:");
        System.out.println("1. Send a message");
        System.out.println("2. Get the reply");
        System.out.println("3. Get sb's BlockChain");
        System.out.println("4. Get All BlockChain");
        System.out.println("5. Exit");
        System.out.println("------------------------------------##");
        return scanner.nextInt();
    }
}
