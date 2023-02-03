package org.example.PBFT.Msg;

import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

import java.util.Arrays;

//  This is the request message
@ToString
@Data
public class Request {
    //  Data
    private String data;
    //  timestamp
    private long timestamp;
    //  hash
    private String hash;
    //  signature
    private byte[] signature;
    //  public key
    private String publicKey;

    //  Constructor
    public Request(String data) {
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    //  Sign the request
    public void Sign(RSA rsa) {
        this.publicKey = rsa.getPublicKeyBase64();
        this.signature = rsa.encrypt(this.data+ this.timestamp +publicKey, KeyType.PrivateKey);
    }

    //  Verify the request
    public boolean Verify() {
        RSA rsa = new RSA(null, this.publicKey);
        String data =StrUtil.str(rsa.decrypt(this.signature, KeyType.PublicKey), CharsetUtil.CHARSET_UTF_8);
        return CompareUtil.compare(data, StrUtil.str(this.data+ this.timestamp+publicKey,CharsetUtil.CHARSET_UTF_8) ,true)==0;
    }

    //  SHA256
    public void SHA256(String lastHash) {
        this.hash = DigestUtil.sha256Hex(this.data+ this.timestamp+
                StrUtil.str(this.signature,CharsetUtil.CHARSET_UTF_8) +this.publicKey+
                lastHash);
    }
}
