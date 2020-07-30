package test_service.testcase;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;

public class DemoTest {

    private static String accessToken;

    @BeforeAll
    static void getTest(){
        useRelaxedHTTPSValidation();
        accessToken = given()
                 .params("corpid","ww1d52a5ca803a44ec","corpsecret","Wp6z_2pVC-7Y58k6v6SVCl71YE9hylVAx7LfuCIfHtk")   //"IuHfUuSuoUrGjGCifINXIQE3UF7cYQL9O98MZaFZHug"
                 .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                 .then()
                 .extract()
                 .response()
                 .path("access_token");
    }

    @Test
    void postTest(){
        //given开头表示输入数据
        given()
                //发送消息，消息内容为中文格式，不乱码
                .contentType("application/json;charset=utf-8")
                //发送的body
                .body("{\n" +
                        "   \"touser\" : \"@all\",\n" +
                        "   \"msgtype\" : \"text\",\n" +
                        "   \"agentid\" : 1000002,\n" +
                        "   \"text\" : {\n" +
                        "       \"content\" : \"你的快递已到，请携带工卡前往邮件中心领取。\\n出发前可查看<a href=\\\"http://work.weixin.qq.com\\\">邮件中心视频实况</a>，聪明避开排队。\"\n" +
                        "   },\n" +
                        "}")
                //发送的参数
                .queryParam("access_token",accessToken)
                //表示触发条件
                .when()
                .post("https://qyapi.weixin.qq.com/cgi-bin/message/send")
                //对结果进行断言
                .then()
                .log()
                .all()
                //状态码断言
                .statusCode(200)
                //字段断言
                .body("errmsg",equalTo("ok. Warning: wrong json format. "));
    }



}
