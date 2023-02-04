package org.example.PBFT.Msg;

import cn.hutool.crypto.asymmetric.RSA;
import org.example.PBFT.Msg.Impl.PrePare;
import org.junit.Test;

public class TestPrepare {
    Integer view = 1;
    Integer sequence = 1;
    RSA rsa = new RSA();
    String NodeId = "1";
    PrePare prePare;
    private void prepareData(){
        Request request = new Request("test");
        request.Sign(rsa);
        request.SHA256("lastHash");
        prePare = new PrePare(NodeId,view, sequence, request);
    }

    @Test
    public void testSign() {
        prepareData();
        prePare.Sign(rsa);
        System.out.println(prePare);
    }

    @Test
    public void testVerify() {
        prepareData();
        prePare.Sign(rsa);
        System.out.println("Verify: " + prePare.Verify());
        assert prePare.Verify();

        String fakeData = "fake";
        prePare.setRequest(new Request(fakeData));
        System.out.println("fake data Verify: " + prePare.Verify());
        assert !prePare.Verify();
    }
}
