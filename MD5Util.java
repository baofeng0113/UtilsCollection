import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;

public final class MD5Util {

    private static final String DEFAULT_CHARSET = "UTF-8";

    private MD5Util() {}

    public static String md5Hex(String value, String charset) {
        try {
            return DigestUtils.md5Hex(value.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String md5Hex(String value) {
        return MD5Util.md5Hex(value, DEFAULT_CHARSET);
    }
}
