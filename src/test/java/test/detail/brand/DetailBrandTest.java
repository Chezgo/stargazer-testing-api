package test.detail.brand;

import api.detail.brand.DetailBrand;
import config.TokenType;
import data.TokenProvider;
import data.detail.brand.HelperDataTestDetailBrand;
import dataProvider.TestDataProviderDetailBrand;
import io.qameta.allure.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import models.data.test.detail.brand.DataTestCreateDetailBrand;
import models.data.test.detail.brand.DataTestGetAllDetailBrand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import test.BaseApiTest;

import static org.hamcrest.Matchers.lessThan;

@Epic("Stargazer Service API")
@Feature("Бренды деталей телескопов")
public class DetailBrandTest extends BaseApiTest {

    private static final Logger log = LoggerFactory.getLogger(DetailBrandTest.class);

    private final TokenProvider tokenProvider = new TokenProvider();
    private final DetailBrand detailBrand = new DetailBrand();
    private final HelperDataTestDetailBrand createDataTestDetailBrand = new HelperDataTestDetailBrand();

    private long idCreateBrand;
    private int statusCodeTest;
    private boolean createBrand;


    @BeforeMethod
    public void startTestDetailBrand() {
        log.info("================");
        log.info("Пошел новый тест");
        log.info("================");
    }

    @Story("Получение списка всех брендов")
    @Severity(SeverityLevel.CRITICAL)
    @Test(
            dataProvider = "GetBrandDetailTest",
            dataProviderClass = TestDataProviderDetailBrand.class,
            description = "Тесты getAllBrandDetail"
    )
    public void getAllDetailBrand(DataTestGetAllDetailBrand dataTest) {
        log.info("Тест: {}", dataTest.getTestName());
        BaseApiTest.setCurrentTestName(dataTest.getTestName());
        BaseApiTest.setCurrentTokenType(dataTest.getTokenType().name());

        // Получаем токен по типу
        String token = getTokenByType(dataTest.getTokenType());

        // Вызываем API метод
        Response response = getAllDetailBrandForTest(token);

        // Тесты
        verifyResponse(response, dataTest.getExpectedStatusCode(),
                dataTest.getPathSchemas(), dataTest.getResponseTime());
    }

    @Story("Создание бренда детали")
    @Severity(SeverityLevel.CRITICAL)
    @Test(
            dataProvider = "createBrandDetailTest",
            dataProviderClass = TestDataProviderDetailBrand.class,
            description = "Тесты createBrandDetail"
    )
    public void createDetailBrandTest(DataTestCreateDetailBrand dataTest){
        log.info("Тест: {}", dataTest.getTestName());
        BaseApiTest.setCurrentTestName(dataTest.getTestName());
        BaseApiTest.setCurrentTokenType(dataTest.getTokenType().name());

        // Получаем токен по типу
        String token = getTokenByType(dataTest.getTokenType());

        //Подготовка данных, если проверяем 409
        if(dataTest.getExpectedStatusCode() == 409){
            createDataTestDetailBrand.createDetailBrandForTest(token, dataTest.getNameBrand(), dataTest.getDescriptionNameBrand());
        }

        // Вызываем API метод
        Response response = createBrandTest(token, dataTest);

        //Зачищаем данные, если проверяем 409
        if(dataTest.getExpectedStatusCode() == 409){
            createDataTestDetailBrand.deleteDetailBrandForTest(token);
        }

        //Проверяем, создалась ли сущность, если да, то подготавливаем данные для будущего удаления после теста, важно это делать до тестов
        saveBrandIdIfCreated(response);

        // Тесты
        verifyResponse(response, dataTest.getExpectedStatusCode(),
                dataTest.getPathSchemas(), dataTest.getResponseTime());

    }

    @Step("Вызов метода createDetailBrand для тестов")
    private Response createBrandTest(String token, DataTestCreateDetailBrand dataTest){
        return detailBrand.createBrand(token, dataTest.getNameBrand(), dataTest.getDescriptionNameBrand());
    }

    @Step("Вызов метода getAllDetailBrand для тестов")
    private Response getAllDetailBrandForTest(String token){
        return detailBrand.getAllBrands(token);
    }

    @Step("Получение токена типа: {tokenType}")
    private String getTokenByType(TokenType tokenType) {
        return tokenProvider.getToken(tokenType);
    }

    @Step("Подготовка дубликата бренда для теста 409")
    private void prepareDuplicateBrand(String token, DataTestCreateDetailBrand dataTest) {
        createDataTestDetailBrand.createDetailBrandForTest(
                token, dataTest.getNameBrand(), dataTest.getDescriptionNameBrand()
        );
    }

    @Step("Сохранение ID созданного бренда для последующей очистки")
    private void saveBrandIdIfCreated(Response response) {
        if (response.getStatusCode() == 200) {
            idCreateBrand = response.jsonPath().getLong("id");
            createBrand = true;

            // Прикрепляем ID к отчету Allure
            Allure.addAttachment("Created Brand ID", "text/plain", String.valueOf(idCreateBrand));
            log.info("Создан бренд с ID: {}", idCreateBrand);
        } else {
            createBrand = false;
        }
    }

    @Step("Проверка ответа: статус {expectedStatus}, схема тела, время < {maxTime}ms")
    private void verifyResponse(Response response, int expectedStatus, String schemaPath, long maxTime) {
        response.then().statusCode(expectedStatus);
        log.info("Статус {} корректный", expectedStatus);

        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
        log.info("Схема ответа корректная");

        response.then().time(lessThan(maxTime));
        log.info("Время ответа корректное (< {}ms)", maxTime);
    }

    @AfterMethod
    public void dataTestEnd() {
        // Чистим тестовые данные после проверок, если созадалась сущность, то нам вернется true и бренд удаляется
        if (createBrand) {
            String token = tokenProvider.getToken(TokenType.ADMIN);
            log.info("================");
            log.info("Удаляем тестовые данные, ID: {}", idCreateBrand);
            log.info("================");

            // Прикрепляем информацию об очистке к отчету
            Allure.addAttachment("Cleanup Info", "text/plain",
                    "Deleted brand ID: " + idCreateBrand);

            detailBrand.deleteBrand(token, idCreateBrand);
        }
        log.info("================");
        log.info("Тест закончился");
        log.info("================");
    }
}