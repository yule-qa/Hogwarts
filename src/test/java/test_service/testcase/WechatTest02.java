package test_service.testcase;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import test_service.utils.FakerUtils;

import java.util.ArrayList;

/**
 * 优化记录：
 * 1、增加了入参实时获取的逻辑
 * 2、增加了脚本的独立性改造
 * 3、使用时间戳命名法避免数据重复的问题
 * 4、通过添加evnClear方法解决脚本无法重复运行的问题
 **/
public class WechatTest02 {
    static String accessToken;
    static String departmentId;
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
    @BeforeEach
    @AfterEach
    void evnClear(){
        Response listResponse=given()
                .when()
                .params("access_token",accessToken,"id",departmentId)
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then()
                .log()
                .body()
                .extract()
                .response();
        ArrayList<Integer> departMentIdList=listResponse.path(" department.id");
        for(int departMentId:departMentIdList){
            if(departMentId == 1){
                continue;
            }
            given()
                    .when()
                    .params("access_token",accessToken,"id",departMentId)
                    .get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                    .then()
                    .log().body()
                    .extract()
                    .response();
        }
    }

    @Test
    @Description("创建部门")
    void createDepartment(){
        String createName="createName"+FakerUtils.getTimeStamp();
        String createName_en="createEn"+FakerUtils.getTimeStamp();
        String createbody="{\n" +
                "   \"name\": \""+createName+"\",\n" +
                "   \"name_en\": \""+createName_en+"\",\n" +
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
        String creatName = "creatName" + FakerUtils.getTimeStamp();
        String creatNameEn = "creatNameEn" + FakerUtils.getTimeStamp();
        String createbody="{\n" +
                "   \"name\": \""+creatName+"\",\n" +
                "   \"name_en\": \""+creatNameEn+"\",\n" +
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
        String departmentId=createResponse.path("id") != null? createResponse.path("id").toString() :null;

        String updateName = "creatName" + FakerUtils.getTimeStamp();
        String updateNameEn = "createNamEn" + FakerUtils.getTimeStamp();
        String updatebody="{\n" +
                "   \"id\": "+departmentId+",\n" +
                "   \"name\": \""+updateName+"\",\n" +
                "   \"name_en\": \""+updateNameEn+"\",\n" +
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
    @Order(3)
    void searchDepartment(){
        String creatName = "creatName" + FakerUtils.getTimeStamp();
        String creatNameEn = "creatNameEn" + FakerUtils.getTimeStamp();
        String createbody="{\n" +
                "   \"name\": \""+creatName+"\",\n" +
                "   \"name_en\": \""+creatNameEn+"\",\n" +
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
        String departmentId=createResponse.path("id") != null? createResponse.path("id").toString() :null;

        Response searchResponse=given()
                .when()
                .params("access_token",accessToken,"id",departmentId)
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
    @Order(4)
    void deleteDepartment(){
        String creatName = "creatName" + FakerUtils.getTimeStamp();
        String creatNameEn = "creatNameEn" + FakerUtils.getTimeStamp();
        String createbody="{\n" +
                "   \"name\": \""+creatName+"\",\n" +
                "   \"name_en\": \""+creatNameEn+"\",\n" +
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
        String departmentId=createResponse.path("id") != null? createResponse.path("id").toString() :null;
        Response deleteDepartmentResponse=given()
                .when()
                .params("access_token",accessToken,"id",departmentId)
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then()
                .log()
                .body()
                .extract()
                .response();
        assertEquals("0",deleteDepartmentResponse.path("errcode").toString());
    }
}
