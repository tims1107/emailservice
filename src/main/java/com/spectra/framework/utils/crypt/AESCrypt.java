//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.spectra.framework.utils.crypt;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public final class AESCrypt {
    private static final String ALGORITHM = "AES";

    public AESCrypt() {
    }

    public static String encrypt(String value, String key) throws Exception {
        byte[] encryptedValue64 = null;
        if (value != null && key != null) {
            Key kee = generateKey(key);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, kee);
            byte[] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
            encryptedValue64 = Base64.getEncoder().encode(encryptedByteValue);
        }

        return encryptedValue64.toString();
    }

    public static String decrypt(String value, String key) throws Exception {
        String decryptedValue = null;
        if (value != null && key != null) {
            Key kee = generateKey(key);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, kee);
            //byte[] decryptedValue64 = (new BASE64Decoder()).decodeBuffer(value);
            byte[] decryptedValue64 = Base64.getDecoder().decode(value);
            byte[] decryptedByteValue = cipher.doFinal(decryptedValue64);
            decryptedValue = new String(decryptedByteValue, "utf-8");
        }

        return decryptedValue;
    }

    private static Key generateKey(String key) throws Exception {
        Key kee = null;
        if (key != null) {
            kee = new SecretKeySpec(key.getBytes(), "AES");
        }

        return kee;
    }
}
