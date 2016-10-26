import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public final class HttpClientUtil {

    private static HttpClientFactory simpleHttpClientFactory;

    private HttpClientUtil() {}

    private static final String DEFAULT_CHARSET = "UTF-8";

    static {
        simpleHttpClientFactory = new HttpClientFactory();
        simpleHttpClientFactory.setSimpleHttpConnectionManager(
                new PoolingHttpClientConnectionManager());
    }

    public static String get(String requestURI, Map<String, String> header, String charset) {
        HttpClient client = simpleHttpClientFactory.getHttpClient();
        HttpGet request = new HttpGet(requestURI);
        request.setConfig(RequestConfig.DEFAULT);
        try {
            if (Objects.nonNull(header) && header.size() > 0) {
                header.entrySet().forEach(entry -> request.addHeader(entry.getKey(), entry.getValue()));
            }
            HttpResponse response = client.execute(request);
            return EntityUtils.toString(response.getEntity(), charset);
        } catch (Exception error) {
            throw new RuntimeException(error);
        }
    }

    public static String get(String requestURI, Map<String, String> header) {
        return HttpClientUtil.get(requestURI, header, DEFAULT_CHARSET);
    }

    public static String get(String requestURI, String charset) {
        HttpClient client = simpleHttpClientFactory.getHttpClient();
        HttpGet request = new HttpGet(requestURI);
        request.setConfig(RequestConfig.DEFAULT);
        try {
            HttpResponse response = client.execute(request);
            return EntityUtils.toString(response.getEntity(), charset);
        } catch (Exception error) {
            throw new RuntimeException(error);
        }
    }

    public static String get(String requestURI) {
        return HttpClientUtil.get(requestURI, DEFAULT_CHARSET);
    }

    public static String post(String requestURI, Map<String, Object> params, Map<String, String> header, String charset) {
        HttpClient client = simpleHttpClientFactory.getHttpClient();
        HttpPost request = new HttpPost(requestURI);
        request.setConfig(RequestConfig.DEFAULT);
        List<NameValuePair> pairs = new ArrayList<>();
        for (String key : params.keySet()) {
            String val = params.get(key).toString();
            pairs.add(new BasicNameValuePair(key, val));
        }

        try {
            if (Objects.nonNull(header) && header.size() > 0) {
                header.entrySet().forEach(entry -> request.addHeader(entry.getKey(), entry.getValue()));
            }
            request.setEntity(new UrlEncodedFormEntity(pairs, charset));
            HttpResponse response = client.execute(request);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception error) {
            throw new RuntimeException(error);
        }
    }

    public static String post(String requestURI, Map<String, Object> params, Map<String, String> header) {
        return HttpClientUtil.post(requestURI, params, header, DEFAULT_CHARSET);
    }

    public static String post(String requestURI, String params, Map<String, String> header, String charset) {
        HttpClient client = simpleHttpClientFactory.getHttpClient();
        HttpPost request = new HttpPost(requestURI);
        request.setConfig(RequestConfig.DEFAULT);
        try {
            if (Objects.nonNull(header) && header.size() > 0) {
                header.entrySet().forEach(entry -> request.addHeader(entry.getKey(), entry.getValue()));
            }
            request.setEntity(new StringEntity(params, charset));
            HttpResponse response = client.execute(request);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception error) {
            throw new RuntimeException(error);
        }
    }

    public static String post(String requestURI, String params, Map<String, String> header) {
        return HttpClientUtil.post(requestURI, params, header, DEFAULT_CHARSET);
    }
}
