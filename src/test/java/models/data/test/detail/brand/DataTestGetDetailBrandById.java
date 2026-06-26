package models.data.test.detail.brand;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import config.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataTestGetDetailBrandById {

    private int expectedStatusCode;

    private String testName;

    private TokenType tokenType;

    private String pathSchemas;

    private long responseTime;

    private long idBrand;

}
