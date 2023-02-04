package org.example.PBFT.Msg;

import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.Data;
import lombok.ToString;
import lombok.extern.java.Log;

//  PrePrepare message
@Data
@ToString
@Log
public class PrePrepare {
    //  View
    private int view;
    //  Sequence
    private int sequence;
    //  Request
    private Request request;
    //  Signature
    private byte[] signature;
    //  Public key
    private String publicKey;

    //  Constructor
    public PrePrepare(int view, int sequence, Request request) {
        this.view = view;
        this.sequence = sequence;
        this.request = request;
    }

    //  Sign the message
    public void Sign(RSA rsa) {
        this.publicKey = rsa.getPublicKeyBase64();
        this.signature = rsa.encrypt(this.view+ this.sequence+ this.request.getHash(), KeyType.PrivateKey);
    }

    //  Verify the message
    public boolean Verify() {
        RSA rsa = new RSA(null, this.publicKey);
        String data = StrUtil.str(rsa.decrypt(this.signature, KeyType.PublicKey), CharsetUtil.CHARSET_UTF_8);
        return CompareUtil.compare(data, StrUtil.str(this.view+ this.sequence+ this.request.getHash(),CharsetUtil.CHARSET_UTF_8) ,true)==0;
    }
}
