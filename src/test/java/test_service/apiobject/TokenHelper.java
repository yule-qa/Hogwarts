package test_service.apiobject;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;

public class TokenHelper {
    public static String getAccessToken() {
        useRelaxedHTTPSValidation();
        String accessToken = given()
                .when()
                .params("corpid","ww1d52a5ca803a44ec","corpsecret","IuHfUuSuoUrGjGCifINXIQE3UF7cYQL9O98MZaFZHug")   //
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then()
                .extract()
                .response()
                .path("access_token");

        return  accessToken;
    }
}
