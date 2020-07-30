package test_service.testcase;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test_service.apiobject.DepartmentApiObj;
import test_service.apiobject.TokenHelper;
import test_service.task.EventTask;
import test_service.utils.FakerUtils;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *   优化记录：
 *  1、增加了入参实时获取的逻辑
 *  2、对脚本行了分层，减少了重复代码，减少了很多维护成本
 */
public class WechatTest03 {
        private static final Logger logger = LoggerFactory.getLogger(WechatTest03.class);

        static String accessToken;
        static String departmentId;

        @BeforeAll
        static void getAccessToken(){
            accessToken=TokenHelper.getAccessToken();
        }
        @BeforeEach
        @AfterEach
        void evnClear(){
            EventTask.evnClear(accessToken);
        }

        @Test
        @Description("创建部门")
        void createDepartment(){
            String creatName = "creatName" + FakerUtils.getTimeStamp();
            String creatNameEn = "creatNamEn" + FakerUtils.getTimeStamp();

            Response creatResponse = DepartmentApiObj.creatDepartMent(creatName, creatNameEn, accessToken);
            departmentId = creatResponse.path("id") != null ? creatResponse.path("id").toString() : null;
            assertEquals("0", creatResponse.path("errcode").toString());

        }

        @Test
        @Description("更新部门")
        void updateDepartment(){
            Response createResponse=DepartmentApiObj.creatDepartMent(accessToken);
            departmentId=createResponse.path("id") != null ? createResponse.path("id").toString() : null;

            String updateName = "creatName" + FakerUtils.getTimeStamp();
            String updateNameEn = "creatNamEn" + FakerUtils.getTimeStamp();

            Response updateResponse=DepartmentApiObj.updateDepartMent(updateName,updateNameEn,departmentId,accessToken);
            assertEquals("0",updateResponse.path("errcode").toString());

        }
        @Test
        @Description("查询部门")
        @Order(3)
        void searchDepartment(){
            Response createResponse=DepartmentApiObj.creatDepartMent(accessToken);
            departmentId=createResponse.path("id") != null ? createResponse.path("id").toString() : null;

            Response searchResponse=DepartmentApiObj.searchDepartMent(accessToken,departmentId);
            assertEquals("0",searchResponse.path("errcode").toString());
        }

        @Test
        @Description("删除部门")
        @Order(4)
        void deleteDepartment(){
            Response createResponse=DepartmentApiObj.creatDepartMent(accessToken);
            departmentId=createResponse.path("id") != null? createResponse.path("id").toString() :null;

            Response deleteResponse=DepartmentApiObj.searchDepartMent(accessToken,departmentId);
            assertEquals("0",deleteResponse.path("errcode").toString());
        }
    }


