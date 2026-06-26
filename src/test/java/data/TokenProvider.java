package data;

import api.keycloak.KeycloakAuth;
import config.TokenType;

/**
 * Провайдер токенов для тестов.
 * Возвращает токен в зависимости от типа (admin/user/invalid).
 */
public class TokenProvider {

    private final KeycloakAuth keycloakAuth = new KeycloakAuth();

    /**
     * Получение токена по типу.
     *
     * @param type тип токена (ADMIN, USER, INVALID)
     * @return строка токена для заголовка Authorization
     */
    public String getToken(TokenType type) {
        System.out.println("================");
        System.out.println("Получаем токен: " + type.getDescription());
        System.out.println("================");

        String token = switch (type) {
            case ADMIN -> keycloakAuth.getAdminAccessToken();
            case USER -> keycloakAuth.getUserAccessToken();
            case INVALID -> "incorrect_token_" + System.currentTimeMillis();
        };

        System.out.println("================");
        System.out.println("Получили токен: " + maskToken(token));
        System.out.println("================");

        return token;
    }

    /**
     * Маскирование токена для логов (показываем только первые 10 символов).
     */
    private String maskToken(String token) {
        if (token == null || token.length() <= 10) {
            return "***";
        }
        return token.substring(0, 10) + "...";
    }
}