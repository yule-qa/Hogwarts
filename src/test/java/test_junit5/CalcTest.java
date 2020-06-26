package test_junit5;

import io.qameta.allure.Allure;
import io.qameta.allure.Link;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalcTest {

    @Test
    @Tag("This is a Tag")
    @Link(name="allure report link",type = "mylink",url="https://www.baidu.com")
    void div(){
        Allure.step("第一步");
        Allure.link("https://baidu.com");
        Calc calc=new Calc();
        Allure.step("第二步");
        Allure.addAttachment("comment","comment content");
        Allure.addAttachment("picture","image/jpg",this.getClass().getResourceAsStream("/hemin.jpg"),".jpg");
        assertEquals(calc.div(2,1),2);
    }
}
