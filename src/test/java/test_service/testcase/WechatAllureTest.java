package test_service.testcase;

import io.qameta.allure.*;
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
@Epic("Epic企业微信接口测试用例2")
@Feature("Feature部门相关功能测试2")
public class WechatAllureTest {
    private static final Logger logger = LoggerFactory.getLogger(WechatAllureTest.class);

    static String accessToken;
    static String departmentId;

    @BeforeAll
    static void getAccessToken() {
        accessToken = TokenHelper.getAccessToken();
    }

    @BeforeEach
    @AfterEach
    void evnClear() {
        EventTask.evnClear(accessToken);
    }

    @Description("Description这个测试方法会测试创建部门的功能-入参数据驱动")
    @Story("Story创建部门测试")
    @DisplayName("DisplayName创建部门")
    @Severity(SeverityLevel.BLOCKER)
    @Issue("123")
    @TmsLink("test-1")
    @CsvFileSource(resources = "/serviceData/createDepartment.csv", numLinesToSkip = 1)
    @ParameterizedTest
    void creatDepartment(String creatName, String creatNameEn, String returnCode) {
        Response creatResponse = DepartmentApiObj.creatDepartMent(creatName, creatNameEn, accessToken);
        departmentId = creatResponse.path("id") != null ? creatResponse.path("id").toString() : null;
        assertEquals(returnCode, creatResponse.path("errcode").toString());
    }

    @Description("Description这个测试方法会测试修改部门的功能")
    @Story("Story修改部门测试")
    @DisplayName("DisplayName修改部门")
    @Issue("123")
    @TmsLink("test-1")
    @Test
    void updateDepartment() {
        Response creatResponse = DepartmentApiObj.creatDepartMent(accessToken);
        departmentId = creatResponse.path("id") != null ? creatResponse.path("id").toString() : null;

        String updateName = "creatName" + FakerUtils.getTimeStamp();
        String updateNameEn = "creatNamEn" + FakerUtils.getTimeStamp();

        Response updateResponse = DepartmentApiObj.updateDepartMent(updateName, updateNameEn, departmentId, accessToken);
        assertEquals("0", updateResponse.path("errcode").toString());

    }

    @DisplayName("查询部门")
    @Description("这个测试方法会测试查询部门的功能")
    @Story("查询部门测试")
    @Issue("123")
    @TmsLink("test-1")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    void listDepartment() {
        String creatName = "creatNam" + FakerUtils.getTimeStamp();
        String creatNameEn = "creatNamEn" + FakerUtils.getTimeStamp();

        Response creatResponse = DepartmentApiObj.creatDepartMent(creatName, creatNameEn, accessToken);
        departmentId = creatResponse.path("id") != null ? creatResponse.path("id").toString() : null;

        Response listResponse = DepartmentApiObj.searchDepartMent(accessToken,departmentId);

        assertAll("返回值校验!",
                () -> assertEquals("0", listResponse.path("errcode").toString()),
                () -> assertEquals(departmentId, listResponse.path("department.id[0]").toString()),
                () -> assertEquals(creatName, listResponse.path("department.name[0]").toString()),
                () -> assertEquals(creatNameEn, listResponse.path("department.name_en[0]").toString())
        );



    }

    @DisplayName("删除部门")
    @Description("这个测试方法会测试删除部门的功能")
    @Story("删除部门测试")
    @Severity(SeverityLevel.MINOR)
    @Issue("123")
    @TmsLink("test-1")
    @Test
    void deletDepartment() {
        Response creatResponse = DepartmentApiObj.creatDepartMent(accessToken);
        departmentId = creatResponse.path("id") != null ? creatResponse.path("id").toString() : null;

        Response deleteResponse = DepartmentApiObj.deleteDepartMent(accessToken,departmentId);
        assertEquals("0", deleteResponse.path("errcode").toString());
    }
}