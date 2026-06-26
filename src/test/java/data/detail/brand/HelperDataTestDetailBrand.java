package data.detail.brand;

import api.detail.brand.DetailBrand;
import io.restassured.response.Response;

public class HelperDataTestDetailBrand {

    DetailBrand detailBrand = new DetailBrand();
    long idCreateBrand;

    public void createDetailBrandForTest(String token, String name, String description){

        Response response = detailBrand.createBrand(token, name, description);

        idCreateBrand = response.jsonPath().getLong("id");
    }

    public void deleteDetailBrandForTest(String token){
        detailBrand.deleteBrand(token, idCreateBrand);
    }
}
