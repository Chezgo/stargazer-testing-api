package logging;

import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class AllureElasticsearchFilter implements OrderedFilter {

    private String currentTestName;
    private String currentTokenType;

    public void setCurrentTestName(String testName) {
        this.currentTestName = testName;
    }

    public void setCurrentTokenType(String tokenType) {
        this.currentTokenType = tokenType;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext filterContext) {

        long startTime = System.currentTimeMillis();

        Response response = filterContext.next(requestSpec, responseSpec);

        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;

        String tokenType = determineTokenType(requestSpec);

        ElasticsearchLogger.logRequest(
                currentTestName != null ? currentTestName : "Unknown Test",
                requestSpec.getMethod(),
                requestSpec.getURI(),
                response.getStatusCode(),
                responseTime,
                tokenType
        );

        return response;
    }

    /**
     * Определяет тип токена из заголовка Authorization.
     * Если токен начинается с "incorrect_token_" — это INVALID.
     * Иначе — проверяем длину токена (админский обычно длиннее).
     */
    private String determineTokenType(FilterableRequestSpecification requestSpec) {
        String authHeader = requestSpec.getHeaders().getValue("Authorization");

        if (authHeader == null) {
            return "No Auth";
        }

        // Убираем "Bearer " префикс
        String token = authHeader.replace("Bearer ", "");

        if (token.startsWith("incorrect_token_")) {
            return "INVALID";
        }

        return "Authenticated";
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE; // Выполняется последним
    }
}