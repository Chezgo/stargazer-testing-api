package test;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeSuite;

public class BaseApiTest {

    @BeforeSuite
    public void globalSetup(){
        System.out.println("Тесты стратовали");
        System.out.println("Логи включили");
        RestAssured.filters(
                new RequestLoggingFilter(LogDetail.ALL),
                new ResponseLoggingFilter(LogDetail.ALL),
                new AllureRestAssured()
        );
    }
}
