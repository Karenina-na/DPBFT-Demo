package org.example.PBFT.Msg;

import cn.hutool.crypto.asymmetric.RSA;
import org.example.PBFT.Msg.Impl.ACKType;
import org.example.PBFT.Msg.Impl.Commit;
import org.junit.Test;

public class TestCommit {
    Integer view = 1;
    Integer sequence = 1;
    RSA rsa = new RSA();
    String NodeId = "1";
    Commit commit= new Commit(view, sequence, NodeId, ACKType.ACCEPT);
    @Test
    public void testSign() {
        commit.Sign(rsa);
        System.out.println(commit);
    }
    @Test
    public void testVerify() {
        commit.Sign(rsa);
        System.out.println("Verify: " + commit.Verify());
        assert commit.Verify();

        commit.setAckType(ACKType.REJECT);
        System.out.println("fake data Verify: " + commit.Verify());
        assert !commit.Verify();
    }
}
