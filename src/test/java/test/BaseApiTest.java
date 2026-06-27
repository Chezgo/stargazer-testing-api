package test;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import logging.AllureElasticsearchFilter;
import org.testng.annotations.BeforeSuite;

public class BaseApiTest {

    private static final AllureElasticsearchFilter elasticsearchFilter = new AllureElasticsearchFilter();

    @BeforeSuite
    public void globalSetup() {
        System.out.println("Тесты стартовали");
        System.out.println("Логи включили");

        RestAssured.filters(
                new RequestLoggingFilter(LogDetail.ALL),
                new ResponseLoggingFilter(LogDetail.ALL),
                new AllureRestAssured(),
                elasticsearchFilter
        );
    }

    public static void setCurrentTestName(String testName) {
        elasticsearchFilter.setCurrentTestName(testName);
    }

    public static void setCurrentTokenType(String tokenType) {
        elasticsearchFilter.setCurrentTokenType(tokenType);
    }
}