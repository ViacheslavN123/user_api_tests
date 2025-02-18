package config;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

import static config.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;

public class ResponseData {
    public static String executeHttpRequest(String method, String body, int status, String endpoint){
        Response response = performHttpRequest(method, body, status, endpoint);
        return response.getBody().asString();
    }

    public static Response performHttpRequest(String method, String body, int status, String endpoint) {
        switch (method.toUpperCase()) {
            case "POST":
                return given()
                        .filter(withCustomTemplates())
                        .contentType(ContentType.JSON)
                        .body(body)
                        .log().body()
                        .when()
                        .post(endpoint)
                        .then().log().body().statusCode(status)
                        .extract().response();
            case "PUT":
                return given()
                        .filter(withCustomTemplates())
                        .contentType(ContentType.JSON)
                        .body(body)
                        .log().body()
                        .when()
                        .put(endpoint)
                        .then().log().body().statusCode(status)
                        .extract().response();

            case "GET":
                return given()
                        .filter(withCustomTemplates())
                        .contentType(ContentType.JSON)
                        .log().body()
                        .when()
                        .get(endpoint)
                        .then().log().body().statusCode(status)
                        .extract().response();

            case "DELETE":
                return given()
                        .filter(withCustomTemplates())
                        .contentType(ContentType.JSON)
                        .log().body()
                        .when()
                        .delete(endpoint)
                        .then().log().body().statusCode(status)
                        .extract().response();

            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
    }
}
