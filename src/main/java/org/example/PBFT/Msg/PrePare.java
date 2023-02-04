package org.example.PBFT.Msg;

import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.Data;
import lombok.ToString;
import lombok.extern.java.Log;

//  PrePare message
@Data
@ToString
@Log
public class PrePare {
    //  View
    private int view;
    //  Sequence
    private int sequence;
    //  Request
    private Request request;
    //  Signature
    private byte[] signature;
    //  nodeID
    private String nodeID;
    //  Public key
    private String publicKey;

    //  Constructor
    public PrePare(String nodeID,int  view, int sequence, Request request) {
        this.nodeID = nodeID;
        this.view = view;
        this.sequence = sequence;
        this.request = request;
    }

    //  Sign the message
    public void Sign(RSA rsa) {
        this.publicKey = rsa.getPublicKeyBase64();
        this.signature = rsa.encrypt(this.view+ this.sequence+ this.request.getHash()+this.nodeID, KeyType.PrivateKey);
    }

    //  Verify the message
    public boolean Verify() {
        RSA rsa = new RSA(null, this.publicKey);
        String data = StrUtil.str(rsa.decrypt(this.signature, KeyType.PublicKey), CharsetUtil.CHARSET_UTF_8);
        return CompareUtil.compare(data, StrUtil.str(this.view+ this.sequence+ this.request.getHash()+this.nodeID, CharsetUtil.CHARSET_UTF_8) ,true)==0;
    }
}
