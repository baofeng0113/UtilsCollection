import java.nio.charset.Charset;

import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public final class HttpClientFactory {

    private HttpClientConnectionManager simpleHttpConnectionManager;

    public void setSimpleHttpConnectionManager(HttpClientConnectionManager simpleHttpConnectionManager) {
        this.simpleHttpConnectionManager = simpleHttpConnectionManager;
    }

    public CloseableHttpClient getHttpClient() {
        ConnectionConfig config = ConnectionConfig.custom().setCharset(Charset.forName("UTF-8")).build();
        return HttpClients.custom().setConnectionManager(simpleHttpConnectionManager)
                .setDefaultConnectionConfig(config).build();
    }
}
