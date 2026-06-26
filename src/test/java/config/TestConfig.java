package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {
    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream input = TestConfig.class.getClassLoader()
                .getResourceAsStream("test.properties")) {

            if (input == null) {
                throw new RuntimeException("Файл test.properties не найден в src/test/resources");
            }

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки test.properties", e);
        }
    }

    // Keycloak
    public static String getKeycloakUrl() {
        return properties.getProperty("keycloak.url");
    }

    public static String getKeycloakClientId() {
        return properties.getProperty("keycloak.client.id");
    }

    public static String getAdminUsername() {
        return properties.getProperty("keycloak.admin.username");
    }

    public static String getAdminPassword() {
        return properties.getProperty("keycloak.admin.password");
    }

    public static String getUserUsername() {
        return properties.getProperty("keycloak.user.username");
    }

    public static String getUserPassword() {
        return properties.getProperty("keycloak.user.password");
    }

    // API base url
    public static String getApiBaseUrl() {
        return properties.getProperty("api.base.url");
    }
}