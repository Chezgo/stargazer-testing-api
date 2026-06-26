package config;

/**
 * Типы токенов для тестирования.
 * Каждый тип описывает, какой токен нужно получить для теста.
 */
public enum TokenType {

    /**
     * Токен администратора (chezgo_admin)
     */
    ADMIN("Токен администратора"),

    /**
     * Токен обычного пользователя (first_user)
     */
    USER("Токен пользователя"),

    /**
     * Некорректный токен (для проверки 401)
     */
    INVALID("Некорректный токен");

    private final String description;

    TokenType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}