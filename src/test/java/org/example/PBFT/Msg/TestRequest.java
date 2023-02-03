package org.example.PBFT.Msg;

import cn.hutool.crypto.asymmetric.RSA;
import org.junit.Test;

public class TestRequest {
    Request request = new Request("test");
    RSA rsa = new RSA();

    @Test
    public void testSign() {
        request.Sign(rsa);
        System.out.println(request);
    }

    @Test
    public void testVerify() {
        request.Sign(rsa);
        System.out.println("Verify: " + request.Verify());

        String fakeData = "fake";
        request.setData(fakeData);
        System.out.println("fake data Verify: " + request.Verify());
    }

    @Test
    public void testSHA256() {
        request.Sign(rsa);
        request.SHA256("lastHash");
        System.out.println(request);
    }
}
