package test_framework_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 *  代表的是测试用例，提供读取测试用例、执行功能
 */
public class ApiTestCaseModel {
    public String name="";
    public String description="";
    public List<HashMap<String,Object>> steps;

    /**
     * 加载一个yaml文件，并转换成测试用例的模型类
     * @param path
     * @return
     * @throws IOException
     */

    public static ApiTestCaseModel load(String path) throws IOException {
        ObjectMapper objectMapper=new ObjectMapper(new YAMLFactory());
        return objectMapper.readValue(new File(path),ApiTestCaseModel.class);
    }

    /**
     * 借助baseApi，去运行对应的测试用例，主要是运行其中的测试用例
     * 主要是运行其中的测试步骤steps，将来可能会断言
     * @param baseApi
     */
    public void run(BaseApi baseApi){
        steps.stream().forEach(step ->{
            if (step.get("api") !=null) {
                baseApi.run(step.get("api").toString(), step.get("action").toString());
            }else if(step.get("actual")!=null){
                assertAll(()->{
                        if(step.get("matcher").equals("equalTo")) {
                            assertThat(step.get("actual"), equalTo(step.get("expect")));
                        }

                });
            }
        });

    }

}
