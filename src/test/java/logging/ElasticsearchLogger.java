package logging;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ElasticsearchLogger {

    private static final String ELASTICSEARCH_URL = "http://localhost:9200";
    private static final String INDEX_NAME = "api-test-logs";

    private static final ElasticsearchClient client;

    static {
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200, "http")
        ).build();

        client = new ElasticsearchClient(
                new RestClientTransport(restClient, new JacksonJsonpMapper())
        );
    }

    public static void logRequest(String testName, String method, String url,
                                  int statusCode, long responseTime, String tokenType) {
        try {
            Map<String, Object> document = new HashMap<>();
            document.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            document.put("testName", testName);
            document.put("method", method);
            document.put("url", url);
            document.put("statusCode", statusCode);
            document.put("responseTime", responseTime);
            document.put("tokenType", tokenType);
            document.put("success", statusCode >= 200 && statusCode < 300);

            IndexRequest<Map<String, Object>> request = IndexRequest.of(i -> i
                    .index(INDEX_NAME)
                    .document(document)
            );

            IndexResponse response = client.index(request);
            System.out.println("Лог записан в Elasticsearch: " + response.result());

        } catch (IOException e) {
            System.err.println("Ошибка записи в Elasticsearch: " + e.getMessage());
        }
    }
}