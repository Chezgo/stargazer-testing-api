package logging;

import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class AllureElasticsearchFilter implements OrderedFilter {

    private String currentTestName;

    public void setCurrentTestName(String testName) {
        this.currentTestName = testName;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext filterContext) {

        long startTime = System.currentTimeMillis();

        Response response = filterContext.next(requestSpec, responseSpec);

        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;

        // Логируем в Elasticsearch
        ElasticsearchLogger.logRequest(
                currentTestName != null ? currentTestName : "Unknown Test",
                requestSpec.getMethod(),
                requestSpec.getURI(),
                response.getStatusCode(),
                responseTime,
                "Unknown" // Можно улучшить, получая из токена
        );

        return response;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE; // Выполняется последним
    }
}