package com.albert.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于RSA的SHA-1签名
 * 利用rsa生成一对公私钥
 * SHA1withRSA进行签名与验签
 *
 * 参考：http://blog.csdn.net/linkunhao123/article/details/50378263
 * 参考：https://docs.oracle.com/javase/tutorial/security/apisign/index.html
 */
public class RSAUtil {
    static final Map<String, String> KEY_PAIR = new HashMap<String, String>(2);
    static final String PUBLIC_KEY = "publicKey";
    static final String PRIVATE_KEY = "privateKey";
    static final String KEY_ALGORITHM = "RSA";
    static final String KEY_SIGNATURE = "SHA1withRSA";
    /** RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024 */
    static final int KEY_SIZE = 1024;
    /** 默认字符编码 */
    static final String CHAR_ENCODING = "UTF-8";
    /** /算法/模式/补码方式 */
    static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    /**
     * RSA最大加密明文大小
     */
    private static final int    MAX_ENCRYPT_BLOCK   = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int    MAX_DECRYPT_BLOCK   = 128;
    /**
     * 生成公钥和私钥
     */
    static void init(){
        try {
            KeyPairGenerator gen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            gen.initialize(KEY_SIZE);
            KeyPair pair = gen.generateKeyPair();
            //rsa生成一对公私钥
            PublicKey publicKey  = pair.getPublic();
            PrivateKey privateKey  = pair.getPrivate();
            ;
            KEY_PAIR.put(PUBLIC_KEY, new BASE64Encoder().encode(publicKey.getEncoded()));
            KEY_PAIR.put(PRIVATE_KEY, new BASE64Encoder().encode(privateKey.getEncoded()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 还原公钥，X509EncodedKeySpec 用于构建公钥的规范
     *
     * @param publicKey - Base64 String
     * @return PublicKey
     */
    static PublicKey restorePublicKey(String publicKey) {

        byte[] keyBytes = new byte[0];
        try {
            keyBytes = new BASE64Decoder().decodeBuffer(new String(publicKey.getBytes(),"utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey key = factory.generatePublic(x509EncodedKeySpec);
            return key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原私钥，PKCS8EncodedKeySpec 用于构建私钥的规范
     *
     * @param privateKey - Base64 String
     * @return PrivateKey
     */
    static PrivateKey restorePrivateKey(String privateKey) {
        byte[] keyBytes = new byte[0];
        try {
            keyBytes = new BASE64Decoder().decodeBuffer(privateKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey key = factory.generatePrivate(pkcs8EncodedKeySpec);
            return key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 签名
     * @param data - 数据
     * @param privateKey - Base64 String
     * @return 签名
     */
    public static String sign(String data, String privateKey) throws Exception {
        //获取私钥
        PrivateKey key  = restorePrivateKey(privateKey);
        //SHA1withRSA算法 - 进行签名
        Signature sign = Signature.getInstance(KEY_SIGNATURE);
        //初始化这个用于签名的对象
        sign.initSign(key);
        //更新用于签名的数据
        sign.update(data.getBytes(Charset.forName(CHAR_ENCODING)));
        //数据的签名/指纹
        byte[] signature = sign.sign();
        return new BASE64Encoder().encode(signature);
    }
    public static String sinaSign(String text, String privateKey, String charset) throws Exception {

        byte[] keyBytes = new BASE64Decoder().decodeBuffer(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(KEY_SIGNATURE);
        signature.initSign(privateK);
        signature.update(getContentBytes(text, charset));
        byte[] result = signature.sign();
        String encode = new BASE64Encoder().encode(result);

        return encode.replaceAll("\r\n","");

    }
    /**
     * 验签
     * @param data - 数据
     * @param sign - 签名
     * @param publicKey - Base64 String
     * @return 结果
     */
    public static boolean verify(String data, String sign, String publicKey) throws Exception {
        //获取公钥
        PublicKey key  = restorePublicKey(publicKey);
        //SHA1withRSA算法 - 进行验证
        Signature verifySign = Signature.getInstance(KEY_SIGNATURE);
        //初始化此用于验证的对象
        verifySign.initVerify(key);
        //用于验签的数据
        verifySign.update(data.getBytes(Charset.forName(CHAR_ENCODING)));
        //验证签名
        boolean verify = verifySign.verify(new BASE64Decoder().decodeBuffer(sign));
        return verify;
    }

    /**
     * 加密 - RSA
     * @param data - 源数据
     * @return 加密数据
     */
    public static byte[] encrypt(byte[] data, String publicKey) throws Exception {
        byte[] keyBytes = new BASE64Decoder().decodeBuffer(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        System.out.println("encrypt->" + cipher.getProvider().getClass().getName());
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
//        byte[] encryptedData = out.toByteArray();
        out.close();
        return out.toByteArray();
    }

    /**
     * 解密 - RSA
     * @param data - 加密数据
     * @return 源数据
     */
    public static byte[] decrypt(String data, String privateKey) throws Exception {
        Key key = RSAUtil.restorePrivateKey(privateKey);
        /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
        Cipher cipher = Cipher.getInstance(key.getAlgorithm());
//        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        System.out.println("encrypt->" + cipher.getProvider().getClass().getName());
        byte[] encrypt = new BASE64Decoder().decodeBuffer(data);
        /** 执行解密操作 */
//        return cipher.doFinal(encrypt);
//        byte[] encrypt = data.getBytes();
        int inputLen = encrypt.length;
        int offLen = 0;
        int i = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while(inputLen - offLen > 0){
            byte[] cache;
            if(inputLen - offLen > MAX_DECRYPT_BLOCK){
                cache = cipher.doFinal(encrypt,offLen,MAX_DECRYPT_BLOCK);
            }else{
                cache = cipher.doFinal(encrypt,offLen,inputLen - offLen);
            }
            out.write(cache);
            i++;
            offLen = MAX_DECRYPT_BLOCK * i;
        }
        out.close();
//        byte[] byteArray = out.toByteArray();
        return out.toByteArray();
    }

    public static void main(String[] args) throws IOException {
        init();
        System.out.println(KEY_PAIR.get(PUBLIC_KEY));
        System.out.println(KEY_PAIR.get(PRIVATE_KEY));
        //原始数据
        String source = "中关村e世界@大狗";
        System.out.println(source);
        try {
            byte[] data = encrypt(source.getBytes(), KEY_PAIR.get(PUBLIC_KEY));
            String encryptDataString = new BASE64Encoder().encode(data);
            String sign = sign(data.toString(), KEY_PAIR.get(PRIVATE_KEY));
            System.out.println("verify ->" + verify(data.toString(), sign, KEY_PAIR.get(PUBLIC_KEY)));
            System.out.println("source ->" + new String(decrypt(encryptDataString, KEY_PAIR.get(PRIVATE_KEY))));
            System.out.println(data);
            System.out.println(sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 签名字符串
     *
     * @param text
     *            需要签名的字符串
     * @param sign
     *            客户签名结果
     * @param publicKey
     *            公钥(BASE64编码)
     * @param charset
     *            编码格式
     * @return 验签结果
     */
    public static boolean verifySina(String text, String sign, String publicKey, String charset)
            throws Exception {
        byte[] keyBytes = new BASE64Decoder().decodeBuffer(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(KEY_SIGNATURE);
        signature.initVerify(publicK);
        signature.update(getContentBytes(text, charset));
        return signature.verify(new BASE64Decoder().decodeBuffer(sign));

    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
}
