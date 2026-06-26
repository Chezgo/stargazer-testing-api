package dataProvider;

import config.TokenType;
import models.data.test.detail.brand.DataTestCreateDetailBrand;
import models.data.test.detail.brand.DataTestGetAllDetailBrand;
import models.data.test.detail.brand.DataTestGetDetailBrandById;
import org.testng.annotations.DataProvider;

public class TestDataProviderDetailBrand {

    @DataProvider(name = "GetBrandDetailTest")
    public static Object[][] getBrandDetailData() {
        return new Object[][] {
                {new DataTestGetAllDetailBrand(
                        200,
                        "Успешный вызов, токен админа",
                        TokenType.ADMIN,
                        "schemas/detail/brand/get-all-brand-success.json",
                        2000L)},
                {new DataTestGetAllDetailBrand(
                        200,
                        "Успешный вызов, токен пользователя",
                        TokenType.USER,
                        "schemas/detail/brand/get-all-brand-success.json",
                        2000L)},
                {new DataTestGetAllDetailBrand(
                        401,
                        "Ошибка авторизации, некорректный токен",
                        TokenType.INVALID,
                        "schemas/gateway/gateway-error-401.json",
                        2000L
                )}
        };
    }

    @DataProvider(name = "createBrandDetailTest")
    public static Object[][] createBrandDetailData() {
        return new Object[][] {
                {new DataTestCreateDetailBrand(
                        200,
                        "Успешный вызов, токен админа",
                        TokenType.ADMIN,
                        "schemas/detail/brand/create-brand-success.json",
                        2000L,
                        "Тестовый бренд",
                        "Тестовый бренд для проверки создания нового бренда детали"
                )},
                {new DataTestCreateDetailBrand(
                        400,
                        "Ошибка валидации, пустое имя бренда, токен админа",
                        TokenType.ADMIN,
                        "schemas/detail/brand/create-brand-error-400.json",
                        2000L,
                        "",
                        "Тестовый бренд для проверки создания нового бренда детали"
                )},
                {new DataTestCreateDetailBrand(
                        400,
                        "Ошибка валидации, длина имени бренда меньше 3 символов, токен админа",
                        TokenType.ADMIN,
                        "schemas/detail/brand/create-brand-error-400.json",
                        2000L,
                        "Те",
                        "Тестовый бренд для проверки создания нового бренда детали"
                )},
                {new DataTestCreateDetailBrand(
                        400,
                        "Ошибка валидации, длина именни бренда более 50 символов, токен админа",
                        TokenType.ADMIN,
                        "schemas/detail/brand/create-brand-error-400.json",
                        2000L,
                        "1234567890123456789012345678901234567890123456789052",
                        "Тестовый бренд для проверки создания нового бренда детали"
                )},
                {new DataTestCreateDetailBrand(
                        400,
                        "Ошибка валидации, null в имени бренда, токен админа",
                        TokenType.ADMIN,
                        "schemas/detail/brand/create-brand-error-400.json",
                        2000L,
                        null,
                        "Тестовый бренд для проверки создания нового бренда детали"
                )},
                {new DataTestCreateDetailBrand(
                        400,
                        "Ошибка валидации, пустое описание бренда, токен админа",
                        TokenType.ADMIN,
                        "schemas/detail/brand/create-brand-error-400.json",
                        2000L,
                        "Тестовый бренд",
                        ""
                )},
                {new DataTestCreateDetailBrand(
                        400,
                        "Ошибка валидации, длина в описании бренда меньше 3 символов, токен админа",
                        TokenType.ADMIN,
                        "schemas/detail/brand/create-brand-error-400.json",
                        2000L,
                        "Тестовый бренд",
                        "Те"
                )},
                {new DataTestCreateDetailBrand(
                        400,
                        "Ошибка валидации, длина в описании бренда более 255 символов, токен админа",
                        TokenType.ADMIN,
                        "schemas/detail/brand/create-brand-error-400.json",
                        2000L,
                        "Тестовый бренд",
                        "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"
                )},
                {new DataTestCreateDetailBrand(
                        400,
                        "Ошибка валидации, null в описании бренда, токен админа",
                        TokenType.ADMIN,
                        "schemas/detail/brand/create-brand-error-400.json",
                        2000L,
                        "Тестовый бренд",
                        null
                )},
                {new DataTestCreateDetailBrand(
                        401,
                        "Ошибка авторизации, некорректный токен",
                        TokenType.INVALID,
                        "schemas/gateway/gateway-error-401.json",
                        2000L,
                        "Тестовый бренд",
                        "Тестовый бренд для проверки создания нового бренда детали"
                )},
                {new DataTestCreateDetailBrand(
                        403,
                        "Недостаточно прав, пользовательский токен",
                        TokenType.USER,
                        "schemas/gateway/gateway-error-403.json",
                        2000L,
                        "Тестовый бренд",
                        "Тестовый бренд для проверки создания нового бренда детали"
                )},
                {new DataTestCreateDetailBrand(
                        409,
                        "Ошибка бизнес логики, название бренда уже существует, токен админа",
                        TokenType.ADMIN,
                        "schemas/detail/brand/create-brand-error-409.json",
                        2000L,
                        "Тестовый бренд",
                        "Тестовый бренд для проверки создания нового бренда детали"
                )}

        };

    }

    @DataProvider(name = "GetBrandDetailByIdTest")
    public static Object[][] getBrandDetailByIdData() {
        return new Object[][] {
                {new DataTestGetDetailBrandById(
                        200,
                        "Успешный вызов, существующий бренд, токен админа",
                        TokenType.ADMIN,
                        "schemas/detail/brand/get-all-brand-success.json",
                        2000L,
                        1
                )},
                {new DataTestGetDetailBrandById(
                        200,
                        "Успешный вызов, существующий бренд, токен пользователя",
                        TokenType.USER,
                        "schemas/detail/brand/get-all-brand-success.json",
                        2000L,
                        1
                )},

        };

    }
}
