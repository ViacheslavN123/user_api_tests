package apitests.helpers;
import com.github.underscore.U;
import apitests.models.UserTestData;

public class JsonBodyModelBuilder {
    public String fullRequestBody(UserTestData userData) {
        U.Builder body = U.objectBuilder()
                .add("id", userData.getId())
                .add("username", userData.getUserName())
                .add("firstName", userData.getFirstName())
                .add("lastName", userData.getLastName())
                .add("email", userData.getEmail())
                .add("password", userData.getPassword())
                .add("phone", userData.getPhone())
                .add("userStatus", userData.getUserStatus());
        return body.toJson();
    }
}