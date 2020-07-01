package test_framework;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MainCodeRunTest {
    @BeforeAll
    static void beforeAll() {
        //toDo:加载通用配置
    }

    @BeforeEach
    void setUp() {
        //toDo:每个用例相关
    }

    private static BasePage basePage;

    @ParameterizedTest(name="index--->{index}--->{0}")
    @MethodSource
    void classic(UIAuto uiAuto){
        basePage.run(uiAuto);
    }

    static Stream<UIAuto> classic(){
        basePage = UiAutoFactory.create("web");
        List<UIAuto> all= new ArrayList<UIAuto>();

        Arrays.asList(
                "/test_framework/webuiauto-1.yaml",
                "/test_framework/webuiauto-2.yaml"
        ).stream().forEach(path-> {
            UIAuto uiAuto = basePage.load(path);
            all.add(uiAuto);
        });

        return all.stream();

    }
}

