import java.util.Objects;

import org.apache.commons.codec.binary.Base64;

public final class Base64Util {

    private Base64Util() {}

    public static String encode(byte[] bytes) {
        if (Objects.isNull(bytes) || bytes.length == 0) {
            return "";
        } else {
            return Base64Util.encode(bytes);
        }
    }

    public static String encode(String value) {
        return Base64Util.encode(value.getBytes());
    }

    public static String decode(String value) {
        if (Objects.isNull(value) || value.equals("")) {
            return "";
        }
        return new String(Base64.decodeBase64(value));
    }
}
