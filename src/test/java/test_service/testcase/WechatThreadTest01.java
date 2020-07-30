package test_service.testcase;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.parallel.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test_service.apiobject.DepartmentApiObj;
import test_service.apiobject.TokenHelper;
import test_service.utils.FakerUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

/**
 * 1. 并发创建多个不同名字的部门，预期结果全部创建成功
 * 2. 并发更新多个不同名字的部门，预期结果全部更新成功
 */
public class WechatThreadTest01 {
    private static final Logger logger= LoggerFactory.getLogger(WechatThreadTest01.class);
    static String accessToken;
    static String departmentId;

    @BeforeAll
    public static void getAccessToken() throws Exception {
        accessToken = TokenHelper.getAccessToken();
        logger.info(accessToken);
    }

    @DisplayName("创建部门")
    @RepeatedTest(10)
    @Execution(CONCURRENT)
    void createDepartment(){
        String backenstr=Thread.currentThread().getId()+FakerUtils.getTimeStamp();
        String creatName = "creatName"+backenstr;
        String creatNameEn = "creatNamEn"+backenstr;

        Response creatResponse = DepartmentApiObj.creatDepartMent(creatName, creatNameEn, accessToken);
        assertEquals("0", creatResponse.path("errcode").toString());

    }

    @DisplayName("更新部门")
    @RepeatedTest(10)
    @Execution(CONCURRENT)
    void updateDepartment(){
        String backenstr=Thread.currentThread().getId()+FakerUtils.getTimeStamp();
        String creatName = "creatName"+backenstr;
        String creatNameEn = "creatNamEn"+backenstr;

        Response createResponse=DepartmentApiObj.creatDepartMent(creatName,creatNameEn,accessToken);
        departmentId=createResponse.path("id") != null ? createResponse.path("id").toString() : null;

        String updateName = "updateName" + backenstr;
        String updateNameEn = "updateNamEn" + backenstr;

        Response updateResponse=DepartmentApiObj.updateDepartMent(updateName,updateNameEn,departmentId,accessToken);
        assertEquals("0",updateResponse.path("errcode").toString());


    }





}
