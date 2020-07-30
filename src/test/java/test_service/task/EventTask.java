package test_service.task;

import io.restassured.response.Response;
import test_service.apiobject.DepartmentApiObj;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class EventTask {
    public static void evnClear(String accessToken){
        Response listResponse=DepartmentApiObj.searchDepartMent(accessToken,"");
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
}
