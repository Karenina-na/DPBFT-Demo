package org.example.PBFT;

import cn.hutool.crypto.asymmetric.RSA;
import org.example.PBFT.Msg.Impl.PrePrepare;
import org.example.PBFT.Msg.Request;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class TestAgent {
    Agent agent=new Agent("0");
    RSA rsa=new RSA();

    @Test
    public void testGetLastHash(){
        System.out.println(agent.getLastHash());
    }

    @Test
    public void testSendRequest(){
        Request request=new Request("Hello World");
        request.Sign(rsa);
        String lastHash=agent.getLastHash();
        request.SHA256(lastHash);
        System.out.println(agent.sendRequest(request));
        System.out.println(agent.getTemp());
    }

    @Test
    public void testSendPrepare(){
        Request request=new Request("Hello World");
        request.Sign(rsa);
        String lastHash=agent.getLastHash();
        request.SHA256(lastHash);
        System.out.println(agent.sendRequest(request));

        List<Agent> list = new LinkedList<>();
        list.add(this.agent);
        list.add(new Agent("1"));
        list.add(new Agent("2"));

        System.out.println(agent.sendPrePrepare(list));
        list.forEach(System.out::println);
    }

    @Test
    public void testPrepare(){
    Request request=new Request("Hello World");
        request.Sign(rsa);
        String lastHash=agent.getLastHash();
        request.SHA256(lastHash);
        System.out.println(agent.sendRequest(request));

        PrePrepare prePare=new PrePrepare(0,0,request);
        prePare.Sign(rsa);

        System.out.println(agent.PrePrepare(prePare));
    }

    @Test
    public void testPrepareAndCommit(){
        List<Agent> list = new LinkedList<>();
        list.add(this.agent);
        list.add(new Agent("1"));
        list.add(new Agent("2"));

        Client c=new Client();
        c.sendMsg(list,"Hello","0");

        agent.sendPrePrepare(list);

        list.forEach((Agent agent)-> System.out.println("Agent "+agent.getNodeID()+" :"+
                agent.prepareAndCommit(list,c)));

        System.out.println(list);
        System.out.println(c.getReply());
    }
}
