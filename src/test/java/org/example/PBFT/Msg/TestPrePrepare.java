package org.example.PBFT.Msg;

import cn.hutool.crypto.asymmetric.RSA;
import org.example.PBFT.Msg.Impl.PrePrepare;
import org.junit.Test;

public class TestPrePrepare {
    Integer view = 1;
    Integer sequence = 1;
    PrePrepare prePrepare;
    RSA rsa = new RSA();

    private void prepareData(){
        Request request = new Request("test");
        request.Sign(rsa);
        request.SHA256("lastHash");
        prePrepare = new PrePrepare(view, sequence, request);
    }

    @Test
    public void testSign() {
        prepareData();
        prePrepare.Sign(rsa);
        System.out.println(prePrepare);
    }

    @Test
    public void testVerify() {
        prepareData();
        prePrepare.Sign(rsa);
        System.out.println("Verify: " + prePrepare.Verify());
        assert prePrepare.Verify();

        String fakeData = "fake";
        prePrepare.setRequest(new Request(fakeData));
        System.out.println("fake data Verify: " + prePrepare.Verify());
        assert !prePrepare.Verify();
    }
}
