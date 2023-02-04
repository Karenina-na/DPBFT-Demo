package org.example.PBFT;

import cn.hutool.crypto.asymmetric.RSA;
import lombok.Data;
import lombok.ToString;
import lombok.extern.java.Log;
import org.example.PBFT.Msg.Message;
import org.example.PBFT.Msg.Request;

import java.util.LinkedList;
import java.util.Queue;

//  PBFT Node
@Data
@ToString
@Log
public class Agent {
    //  Node ID
    private final Integer NodeID;
    //  Node state
    private State state;
    //  Node view
    private Integer view;
    //  Node sequence
    private Integer sequence;
    //  RSA
    private final RSA rsa;

    // Message Queue
    Queue<Message> msgQueue;

    // Client Message Queue
    Queue<Request> clientMsgQueue;

    //  Constructor
    public Agent(Integer NodeID) {
        this.NodeID = NodeID;
        this.state = State.INITIAL;
        this.view = 0;
        this.sequence = 0;
        this.rsa = new RSA();
        this.msgQueue = new LinkedList<>();
        this.clientMsgQueue = new LinkedList<>();
    }

    // Add Message
    public void addMsg(Message msg) {
        msgQueue.add(msg);
    }

    // Add Client Message
    public void addClientMsg(Request msg) {
        clientMsgQueue.add(msg);
    }

    
}
