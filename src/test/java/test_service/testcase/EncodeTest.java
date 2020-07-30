package test_service.testcase;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseBuilder;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class EncodeTest {
//    @BeforeAll
//    static void beforeAll(){
//        RestAssured.filters((req,res,ctx)->{
//            //返回的Response不具备set方法，无法修改body
//            Response originResponse=ctx.next(req,res);
//            String encody_body=originResponse.getBody().asString();
//            //ResponseBuilder的作用主要是在Response的基础上建设出来一个新的可以修改body对象
//            ResponseBuilder responseBuilder=new ResponseBuilder().clone(originResponse);
//            //Response无法直接修改body，所以间接的通过ResponseBuilder构建
//            responseBuilder.setBody(Base64.getDecoder().decode(encody_body.trim()));
//            //responseBuilder在最后通过Build方法直接创建一个用于返回的，不可修改的Response
//            return  responseBuilder.build();
//        });
//    }

    @Test
    void ceshirenTest(){
        given().get("https://ceshiren.com/categories.json").then().body("category_list.categories[0].name",equalTo("霍格沃兹测试学院公众号"));
    }

    @Test
    void ceshiren_raw(){
        given().get("http://0.0.0.0:8000/TEST/android.json").then().body("data.items[0].quote.name",equalTo("阿里巴巴"));
    }

    @Test
    void ceshiren_encode(){
        given()
                .filter((req,res,ctx)->{
                    //返回的Response不具备set方法，无法修改body
                    Response originResponse=ctx.next(req,res);
                    String encody_body=originResponse.getBody().asString();
                    //ResponseBuilder的作用主要是在Response的基础上建设出来一个新的可以修改body对象
                    ResponseBuilder responseBuilder=new ResponseBuilder().clone(originResponse);
                    //Response无法直接修改body，所以间接的通过ResponseBuilder构建
                    responseBuilder.setBody(Base64.getDecoder().decode(encody_body.trim()));
                    //responseBuilder在最后通过Build方法直接创建一个用于返回的，不可修改的Response
                    return  responseBuilder.build();
                })
                .get("http://0.0.0.0:8000/TEST/androidBase64.json").then().body("data.items[0].quote.name",equalTo("阿里巴巴"));
    }

    @Test
    void ceshiren_doubleencode(){
        given()
                .filter((req,res,ctx)->{
                    Response originResponse=ctx.next(req,res);
                    String encody_body=originResponse.getBody().asString();
                    ResponseBuilder responseBuilder=new ResponseBuilder().clone(originResponse);
//                    String decodyBody = Base64.getDecoder().decode(encody_body.trim()).toString();
                    responseBuilder.setBody(Base64.getDecoder().decode(new String(Base64.getDecoder().decode(encody_body.trim())).trim()));

                    return  responseBuilder.build();
                })
                .get("http://0.0.0.0:8000/TEST/androidDoubleBase64.json").then().body("data.items[0].quote.name",equalTo("阿里巴巴"));
    }
}
