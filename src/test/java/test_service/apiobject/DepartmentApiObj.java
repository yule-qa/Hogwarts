package test_service.apiobject;

import io.restassured.response.Response;
import test_service.utils.FakerUtils;

import static io.restassured.RestAssured.given;

public class DepartmentApiObj {

    //增加
    public static Response creatDepartMent(String creatName, String creatNameEn, String accessToken) {
        String creatBody = "{\n" +
                "   \"name\": \"" + creatName + "\",\n" +
                "   \"name_en\": \"" + creatNameEn + "\",\n" +
                "   \"parentid\": 1,\n" +
                "   \"order\": 1}";
        Response creatResponse = given()
                .when()
                .contentType("application/json")
                .body(creatBody)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=" + accessToken)
                .then()
                .log().body()
                .extract()
                .response();
        return creatResponse;
    }

    public static Response creatDepartMent(String accessToken) {
        String creatName = "creatName" + FakerUtils.getTimeStamp();
        String creatNameEn = "creatNamEn" + FakerUtils.getTimeStamp();
        return creatDepartMent(creatName, creatNameEn, accessToken);
    }


    //修改
    public static Response updateDepartMent(String updateName, String updateNameEn, String departmentId, String accessToken){

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
        return updateResponse;
    }

    public static Response searchDepartMent(String accessToken,String departmentId){
        Response searchResponse=given()
                .when()
                .params("access_token",accessToken,"id",departmentId)
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then()
                .log()
                .body()
                .extract()
                .response();

        return searchResponse;
    }

    public static Response deleteDepartMent(String accessToken,String departmentId){
        Response deleteDepartmentResponse=given()
                .when()
                .params("access_token",accessToken,"id",departmentId)
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then()
                .log()
                .body()
                .extract()
                .response();

        return deleteDepartmentResponse;
    }
}
