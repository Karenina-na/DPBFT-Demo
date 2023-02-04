package org.example;

import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        //RSA hutool
        RSA rsa = new RSA();
        String privateKey = rsa.getPrivateKeyBase64();
        String publicKey = rsa.getPublicKeyBase64();

        byte[] data = StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8);

        byte[] encrypt = rsa.encrypt(data, KeyType.PublicKey);
        byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);

        byte[] encrypt2 = rsa.encrypt(data, KeyType.PrivateKey);
        byte[] decrypt2 = rsa.decrypt(encrypt2, KeyType.PublicKey);

        System.out.println("privateKey: " + privateKey);
        System.out.println("publicKey: " + publicKey);
        System.out.println("data: " + StrUtil.str(data, CharsetUtil.CHARSET_UTF_8));
        System.out.println("encrypt: " + encrypt);
        System.out.println("decrypt: " + StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));
        System.out.println("encrypt2: " + encrypt2);
        System.out.println("decrypt2: " + StrUtil.str(decrypt2, CharsetUtil.CHARSET_UTF_8));
        System.out.println("encrypt == encrypt2: " +  CompareUtil.compare(StrUtil.str(data, CharsetUtil.CHARSET_UTF_8),StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8) , true));
    }
}