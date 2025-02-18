package apitests.tests;
import apitests.helpers.JsonBodyModelBuilder;
import apitests.models.UserTestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import io.qameta.allure.*;
import static config.ResponseData.executeHttpRequest;
import static org.junit.jupiter.api.Assertions.*;


@DisplayName("CRUD тесты для пользователя")
@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestUserCrud {
    private final String BASE_URL = "https://petstore.swagger.io/v2/user";
    private final JsonBodyModelBuilder json = new JsonBodyModelBuilder();
    private UserTestData expectedUserData;
    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        expectedUserData = UserTestData.generate();
        executeHttpRequest("POST",
                json.fullRequestBody(expectedUserData),
                200, BASE_URL);
    }

    @Step("Получение данных о пользователе через GET-запрос")
    @Test
    @DisplayName("Получение информации о пользователе через GET-запрос")
    public void getUserTest() {
        String responseBody = executeHttpRequest("GET", null, 200, BASE_URL + "/" + expectedUserData.getUserName());
        try {
            UserTestData actualResponse = mapper.readValue(responseBody, UserTestData.class);
            assertAll("Проверка данных пользователя",
                    () -> assertEquals(expectedUserData.getUserName(), actualResponse.getUserName(), "Поле 'username' не совпадает"),
                    () -> assertEquals(expectedUserData.getFirstName(), actualResponse.getFirstName(), "Поле 'firstName' не совпадает"),
                    () -> assertEquals(expectedUserData.getLastName(), actualResponse.getLastName(), "Поле 'lastName' не совпадает")
            );
        } catch (Exception e) {
            fail("Ошибка при выполнении теста: " + e.getMessage());
        }
    }

    @Step("Обновление данных пользователя через PUT-запрос")
    @Test
    @DisplayName("Обновление данных пользователя через PUT-запрос")
    public void updateUserTest() {
        String updatedFirstName = expectedUserData.getFirstName() + "_updated";
        String updatedLastName = expectedUserData.getLastName() + "_updated";
        String updatedEmail = "updated_" + expectedUserData.getEmail();
        expectedUserData.setFirstName(updatedFirstName);
        expectedUserData.setLastName(updatedLastName);
        expectedUserData.setEmail(updatedEmail);

        executeHttpRequest("PUT",
                json.fullRequestBody(expectedUserData),
                200, BASE_URL + "/" + expectedUserData.getUserName());

        String responseBody  = executeHttpRequest("GET", null, 200, BASE_URL + "/" + expectedUserData.getUserName());
        try {
            UserTestData actualResponse = mapper.readValue(responseBody, UserTestData.class);
            assertAll("Проверка обновленных данных пользователя",
                    () -> assertEquals(expectedUserData.getUserName(), actualResponse.getUserName(), "Поле 'username' не совпадает"),
                    () -> assertEquals(expectedUserData.getFirstName(), actualResponse.getFirstName(), "Поле 'firstName' не совпадает"),
                    () -> assertEquals(expectedUserData.getLastName(), actualResponse.getLastName(), "Поле 'lastName' не совпадает"),
                    () -> assertEquals(expectedUserData.getEmail(), actualResponse.getEmail(), "Поле 'email' не совпадает")
            );
        } catch (Exception e) {
            fail("Ошибка при выполнении теста: " + e.getMessage());
        }
    }

    @Step("Удаление пользователя через DELETE-запрос")
    @Test
    @DisplayName("Удаление пользователя через DELETE-запрос")
    public void deleteUserTest() {
        executeHttpRequest("DELETE", null, 200, BASE_URL + "/" + expectedUserData.getUserName());
        String getResponse = executeHttpRequest("GET", null, 404, BASE_URL + "/" + expectedUserData.getUserName());
        assertTrue(getResponse.contains("User not found"), "Ожидаемое сообщение 'User not found' отсутствует");
    }
}
