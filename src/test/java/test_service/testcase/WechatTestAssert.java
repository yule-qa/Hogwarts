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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *   优化记录：
 *  1、以数据驱动的方式将入参数据从代码剥离。
 */
public class WechatTestAssert {
        private static final Logger logger = LoggerFactory.getLogger(WechatTestAssert.class);

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
        @Description("查询部门")
        void searchDepartment(){
            String creatName = "creatName" + FakerUtils.getTimeStamp();    //加时间戳，解决幂等性问题
            String creatNameEn = "creatNamEn" + FakerUtils.getTimeStamp();
            //查询前，先增加，解决用例依赖，耦合性降低
            Response createResponse=DepartmentApiObj.creatDepartMent(creatName,creatNameEn,accessToken);
            //解决用例依赖，传参
            departmentId=createResponse.path("id") != null ? createResponse.path("id").toString() : null;
            //封装，obj方法，简化脚本
            Response searchResponse=DepartmentApiObj.searchDepartMent(accessToken,departmentId);
            //语法糖断言
            assertAll("返回值校验",
                    ()->assertEquals("0", searchResponse.path("errcode").toString()),
                    ()->assertEquals(departmentId,searchResponse.path("department.id[0]").toString()),
                    ()->assertEquals(creatName,searchResponse.path("department.name[0]")),
                    ()->assertEquals(creatNameEn,searchResponse.path("department.name_en[0]"))
                    );

        }


    }


