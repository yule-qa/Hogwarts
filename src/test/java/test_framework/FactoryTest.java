package test_framework;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FactoryTest {

    @Test
    void setUp() {
        BasePage basePage=Factory.create("web");
        UIAuto uiAuto=basePage.load("/test_framework/webuiauto.yaml");
        basePage.run(uiAuto);
    }


}