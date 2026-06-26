package api.detail.brand;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.request.CreateBrandRequest;
import models.request.UpdateBrandRequest;
import specs.RequestSpecs;

/**
 * API-методы для работы с брендами деталей телескопов.
 */
public class DetailBrand {

    /**
     * Получение списка всех брендов деталей.
     */
    public Response getAllBrands(String token) {
        return RestAssured.given()
                .spec(RequestSpecs.brandDetailSpec(token))
                .when()
                .get();
    }

    /**
     * Получение бренда детали по ID.
     */
    public Response getBrandById(String token, Long id) {
        return RestAssured.given()
                .spec(RequestSpecs.brandDetailSpec(token))
                .when()
                .get("/{id}", id);
    }

    /**
     * Создание нового бренда детали.
     *
     * @param token JWT токен для авторизации
     * @return Response с данными созданного бренда
     */
    public Response createBrand(String token, String nameBrand, String descriptionNameBrand) {
        //Формируем тело запроса
        CreateBrandRequest request = CreateBrandRequest.builder()
                .name(nameBrand)
                .description(descriptionNameBrand)
                .build();

        return RestAssured.given()
                .spec(RequestSpecs.brandDetailSpec(token))
                .body(request)  // RestAssured автоматически сериализует в JSON
                .when()
                .post();
    }

    /**
     * Обновление бренда детали по ID.
     *
     * @param token JWT токен для авторизации
     * @param id ID бренда
     * @param request DTO с обновленными данными
     * @return Response с обновленными данными бренда
     */
    public Response updateBrand(String token, Long id, UpdateBrandRequest request) {
        return RestAssured.given()
                .spec(RequestSpecs.brandDetailSpec(token))
                .body(request)
                .when()
                .put("/{id}", id);
    }

    /**
     * Удаление бренда детали по ID.
     */
    public Response deleteBrand(String token, Long id) {
        return RestAssured.given()
                .spec(RequestSpecs.brandDetailSpec(token))
                .when()
                .delete("/{id}", id);
    }
}