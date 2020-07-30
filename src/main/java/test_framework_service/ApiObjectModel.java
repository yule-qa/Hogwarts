package test_framework_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import test_framework.PageObjectModel;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * 代表了每个api object
 */
public class ApiObjectModel {
    public String name;
    public HashMap <String,ApiObjectMethodModel> methods;

    /**
     * api object 支持从yaml 文件中读取
     * @param path
     * @return
     * @throws IOException
     */
    public static ApiObjectModel load(String path) throws IOException {
        ObjectMapper objectMapper=new ObjectMapper(new YAMLFactory());
        return objectMapper.readValue(new File(path),ApiObjectModel.class);

    }

    /**
     * 运行这个api object 中的某个封装方法
     * @param method
     */

    public static void run(ApiObjectMethodModel method){
        method.run();
    }


}
