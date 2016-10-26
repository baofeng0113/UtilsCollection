import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;

public final class SHAUtil {

    private static final String DEFAULT_CHARSET = "UTF-8";

    private SHAUtil() {}

    @SuppressWarnings("deprecation")
    public static String shaHex(String value, String charset) {
        try {
            return DigestUtils.shaHex(value.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("deprecation")
    public static String shaHex(String value) {
        return SHAUtil.shaHex(value, DEFAULT_CHARSET);
    }
}
