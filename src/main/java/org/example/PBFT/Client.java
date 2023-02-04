package org.example.PBFT;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.Data;
import lombok.ToString;
import lombok.extern.java.Log;
import org.example.PBFT.Msg.Impl.Commit;
import org.example.PBFT.Msg.Request;

import java.util.LinkedList;
import java.util.List;

//  Client
@Data
@ToString
@Log
public class Client {
    //  RSA
    RSA rsa;

    //Reply Queue
    public static List<Commit> replyList = new LinkedList<>();

    //  Constructor
    public Client() {
        this.rsa = new RSA();
    }

    //  Send Message
    public boolean sendMsg(List<Agent> list, String msg, String NodeID) {
        //  Create Request
        Request request = new Request(msg);
        request.Sign(rsa);
        //  Find Agent
        for (Agent agent : list) {
            if (StrUtil.compare(agent.getNodeID(), NodeID, true) == 0) {
                String hash = agent.getLastHash();
                request.SHA256(hash);
                return agent.sendRequest(request);
            }
        }
        return false;
}

    //  Send ACK
    public boolean sendACK(Commit commit) {
        if (commit.Verify()) {
            replyList.add(commit);
            return true;
        }
        return false;
    }

    // Get Reply
    public List<Commit> getReply() {
        return replyList;
    }
}
