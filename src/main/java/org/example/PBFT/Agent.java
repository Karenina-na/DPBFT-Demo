package org.example.PBFT;

import cn.hutool.crypto.asymmetric.RSA;
import lombok.Data;
import lombok.ToString;
import lombok.extern.java.Log;
import org.example.PBFT.Msg.Impl.ACKType;
import org.example.PBFT.Msg.Impl.Commit;
import org.example.PBFT.Msg.Impl.PrePare;
import org.example.PBFT.Msg.Impl.PrePrepare;
import org.example.PBFT.Msg.Request;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//  PBFT Node
@Data
@ToString
@Log
public class Agent {
    //  Node ID
    private final String NodeID;
    //  Node state
    private State state;
    //  Node view
    private Integer view;
    //  Node sequence
    private Integer sequence;
    //  RSA
    private final RSA rsa;

    //  temp
    private final Queue<Request> temp = new LinkedList<>();

    //  Blockchain
    private final List<Request> blockchain = new LinkedList<>();

    //  Constructor
    public Agent(String NodeID) {
        this.NodeID = NodeID;
        this.state = State.INITIAL;
        this.view = 0;
        this.sequence = 0;
        this.rsa = new RSA();
        Request request = new Request("Genesis Block");
        request.setTimestamp(10000);
        request.SHA256("Genesis Block");
        blockchain.add(request);
    }

    //  GetLastHash
    public String getLastHash() {
        assert  blockchain.size() > 0;
        return blockchain.get(blockchain.size() - 1).getHash();
    }

    //  send Request
    public boolean sendRequest(Request request) {
        if (request.Verify() && request.verifyHash(getLastHash())) {
            temp.add(request);
            return true;
        }
        return false;
    }

    //  send PrePrepare
    public boolean sendPrePrepare(List<Agent> list) {
        if (temp.size() > 0) {
            Request request = temp.element();
            PrePrepare prePrepare = new PrePrepare(view, sequence,request);
            prePrepare.Sign(rsa);
            list.forEach((Agent agent)->{
                if (!agent.getNodeID().equals(NodeID)) {
                    agent.PrePrepare(prePrepare);
                }
            });
            return true;
        }
        return false;
    }

    //  Prepare
    public boolean PrePrepare(PrePrepare pre) {
        if (pre.Verify()){
            Request request = pre.getRequest();
            if (request.Verify() && request.verifyHash(getLastHash())) {
                temp.add(request);
                return true;
            }
            return false;
        }
        return false;
    }

    //  prepare and commit
    public boolean prepareAndCommit(List<Agent> list, Client client) {
        List<Boolean> CommitACK = new LinkedList<>();
        if (temp.size() > 0) {
            Request request = temp.poll();
            PrePare prePrepare = new PrePare(this.NodeID,this.view, this.sequence,request);
            prePrepare.Sign(rsa);
            list.forEach((Agent agent)->{
                if (!agent.getNodeID().equals(NodeID)) {
                    CommitACK.add(agent.CheckHash(prePrepare));
                }
            });

            if (CommitACK.size() > list.size() / 2) {
                Commit commit = new Commit(view,sequence,NodeID, ACKType.ACCEPT);
                commit.Sign(rsa);
                client.sendACK(commit);
                blockchain.add(request);
                temp.clear();
                return true;
            }
            return false;
        }
        return false;
    }

    //  CheckHash
    public boolean CheckHash(PrePare pre){
        if (pre.Verify()) {
            Request request = pre.getRequest();
            return request.Verify() && request.verifyHash(getLastHash());
        }
        return true;
    }
}
