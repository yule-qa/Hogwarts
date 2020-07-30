package test_service.testcase;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test_service.apiobject.DepartmentApiObj;
import test_service.apiobject.TokenHelper;
import test_service.utils.FakerUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

/**
 * 并发创建同样名字的部门，预期结果，只有一个创建成功
 */
public class WechatThreadTest {
    private static final Logger logger= LoggerFactory.getLogger(WechatThreadTest.class);
    static String accessToken;

    @BeforeAll
    public static void getAccessToken() throws Exception {
        accessToken = TokenHelper.getAccessToken();
        logger.info(accessToken);
    }

    @DisplayName("创建部门")
    @RepeatedTest(10)
    @Execution(CONCURRENT)
    void createDepartment(){
        String creatName = "creatName";
        String creatNameEn = "creatNamEn";

        Response creatResponse = DepartmentApiObj.creatDepartMent(creatName, creatNameEn, accessToken);
        assertEquals("0", creatResponse.path("errcode").toString());

    }




}
