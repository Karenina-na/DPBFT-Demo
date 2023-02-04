package org.example.PBFT.Msg;

import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.Data;
import lombok.ToString;
import lombok.extern.java.Log;

//  Commit message
@ToString
@Data
@Log
public class Commit {
    //  View
    private int view;
    //  Sequence
    private int sequence;
    //  Signature
    private byte[] signature;
    //  Public key
    private String publicKey;
    //  Node ID
    private String nodeID;
    //  ACK type
    private ACKType ackType;

    public Commit(int view, int seq, String nodeID, ACKType ackType) {
        this.view = view;
        this.sequence = seq;
        this.nodeID = nodeID;
        this.ackType = ackType;
    }

    //  Sign the message
    public void Sign(RSA rsa) {
        this.publicKey = rsa.getPublicKeyBase64();
        this.signature = rsa.encrypt(this.view+ this.sequence+this.nodeID+this.ackType, KeyType.PrivateKey);
    }

    //  Verify the message
    public boolean Verify() {
        RSA rsa = new RSA(null, this.publicKey);
        String data = StrUtil.str(rsa.decrypt(this.signature, KeyType.PublicKey), CharsetUtil.CHARSET_UTF_8);
        return CompareUtil.compare(data, StrUtil.str(this.view+ this.sequence+this.nodeID+this.ackType,CharsetUtil.CHARSET_UTF_8) ,true)==0;
    }
}
