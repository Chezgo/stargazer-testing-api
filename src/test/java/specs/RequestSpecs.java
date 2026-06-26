package specs;

import config.TestConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecs {

    public static RequestSpecification brandDetailSpec(String token) {
        return new RequestSpecBuilder()
                .setBaseUri(TestConfig.getApiBaseUrl())
                .setBasePath("/v1/brand-detail")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }
}