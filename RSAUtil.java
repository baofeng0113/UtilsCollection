import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;

public final class RSAUtil {

    private RSAUtil() {}

    public static PrivateKey getPrivateKey(String key) {
        try {
            return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(
                    Base64Util.decode(key).getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String encrypt(String value, String key, String charset) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64Util.decode(key).getBytes());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(priPKCS8);

            Signature signature = Signature.getInstance("SHA1WithRSA");

            signature.initSign(privateKey);
            signature.update(value.getBytes(charset));

            return Base64Util.encode(signature.sign());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String value, String key, String charset) {
        try {
            PrivateKey privateKey = getPrivateKey(key);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            InputStream stream = new ByteArrayInputStream(Base64Util.decode(value).getBytes());
            ByteArrayOutputStream writer = new ByteArrayOutputStream();
            byte[] buffer = new byte[128];
            int size;

            while ((size = stream.read(buffer)) != -1) {
                byte[] block = null;
                if (buffer.length == size) {
                    block = buffer;
                } else {
                    block = new byte[size];
                    for (int i = 0; i < size; i++) {
                        block[i] = buffer[i];
                    }
                }
                writer.write(cipher.doFinal(block));
            }
            return new String(writer.toByteArray(), charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
