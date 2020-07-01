package test_framework;

import org.junit.jupiter.api.Test;

class UiAutoFactoryTest {

    @Test
    void setUp() {
        BasePage basePage= UiAutoFactory.create("web");
        UIAuto uiAuto=basePage.load("/test_framework/webuiauto.yaml");
        basePage.run(uiAuto);
    }


}