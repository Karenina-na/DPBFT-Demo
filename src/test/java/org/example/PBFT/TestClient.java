package org.example.PBFT;

import cn.hutool.crypto.asymmetric.RSA;
import org.example.PBFT.Msg.Impl.ACKType;
import org.example.PBFT.Msg.Impl.Commit;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class TestClient {
    Client client=new Client();

    @Test
    public void testSendACK(){
        Commit commit=new Commit(0,0,"0", ACKType.ACCEPT);
        RSA rsa=new RSA();
        commit.Sign(rsa);
        System.out.println(client.sendACK(commit));
    }

    @Test
    public void testGetReply(){
        testSendACK();
        System.out.println(client.getReply());
    }

    @Test
    public void testSendMsg(){
        int number = 2;
        List<Agent> list = new LinkedList<>();
        for (int i = 0; i < number; i++) {
            list.add(new Agent(String.valueOf(i)));
        }
        System.out.println( client.sendMsg(list,"Hello",1));
        System.out.println(list.get(1).getTemp());
    }
}
