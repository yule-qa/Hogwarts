package test_service.testcase;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test_service.apiobject.DepartmentApiObj;
import test_service.apiobject.TokenHelper;
import test_service.task.EventTask;
import test_service.utils.FakerUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *   优化记录：
 *  1、以数据驱动的方式将入参数据从代码剥离。
 */
public class WechatTest04 {
        private static final Logger logger = LoggerFactory.getLogger(WechatTest04.class);

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

        @CsvFileSource(resources ="/serviceData/createDepartment.csv",numLinesToSkip = 1)         //跳过第一行表头
        @ParameterizedTest
        @Description("创建部门")
        void createDepartment(String creatName,String creatNameEn,String returncode){
            Response creatResponse = DepartmentApiObj.creatDepartMent(creatName, creatNameEn, accessToken);
            departmentId = creatResponse.path("id") != null ? creatResponse.path("id").toString() : null;
            assertEquals(returncode, creatResponse.path("errcode").toString());

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


