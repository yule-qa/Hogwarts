package test_service.testcase;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class WechatTest {
    private static String accessToken;

    @BeforeAll
    static void getAccessToken(){
        useRelaxedHTTPSValidation();
        accessToken = given()
                .when()
                .params("corpid","ww1d52a5ca803a44ec","corpsecret","IuHfUuSuoUrGjGCifINXIQE3UF7cYQL9O98MZaFZHug")   //
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then()
                .extract()
                .response()
                .path("access_token");
    }

    @Test
    @Description("创建部门")
    void createDepartment(){
        String createbody="{\n" +
                "   \"name\": \"沈阳研发中心\",\n" +
                "   \"name_en\": \"SGZ\",\n" +
                "   \"parentid\": 1,\n" +
                "   \"order\": 1,\n" +
                "}";
        Response createResponse=given()
                .when()
                .contentType("application/json")
                .body(createbody)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token="+accessToken)
                .then()
                .log()
                .body()
                .extract()
                .response();
        assertEquals("0", createResponse.path("errcode").toString());


    }

    @Test
    @Description("更新部门")
    void updateDepartment(){
        String updatebody="{\n" +
                "   \"id\": 2,\n" +
                "   \"name\": \"广州研发中心update\",\n" +
                "   \"name_en\": \"RDGZupdate\",\n" +
                "   \"parentid\": 1,\n" +
                "   \"order\": 1\n" +
                "}";

        Response updateResponse=given()
                .when()
                .contentType("application/json")
                .body(updatebody)
                .post(" https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token="+accessToken)
                .then()
                .log()
                .body()
                .extract()
                .response();
        assertEquals("0",updateResponse.path("errcode").toString());

    }
    @Test
    @Description("查询部门")
    void searchDepartment(){
        Response searchResponse=given()
              .when()
                .params("access_token",accessToken,"id","2")
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then()
                .log()
                .body()
                .extract()
                .response();
        assertEquals("0",searchResponse.path("errcode").toString());
    }

    @Test
    @Description("删除部门")
    void deleteDepartment(){
        Response deleteDepartmentResponse=given()
                .when()
                .params("access_token",accessToken,"id","2")
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then()
                .log()
                .body()
                .extract()
                .response();
        assertEquals("0",deleteDepartmentResponse.path("errcode").toString());
    }

}
