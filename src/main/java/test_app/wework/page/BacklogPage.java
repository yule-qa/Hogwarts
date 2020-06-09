package test_app.wework.page;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BacklogPage extends BasePage{
    private final By taskName=By.id("b2k");
    private final By add=By.id("gym");
    private final By save=byText("保存");
    private final By taskList=By.id("gw9");

    public BacklogPage(AppiumDriver<MobileElement> driver) {
        super(driver);
    }
    public BacklogPage add(String content){
        click(add);
        sendKeys(taskName,content);
        click(save);
        return this;
    }
    public List<String> getBacklog(){
        List<MobileElement> list=driver.findElements(taskList);
        List<String> collect=list.stream().map(x->x.getText()).collect(Collectors.toList());
        return collect;
//        MobileElement el1=(MobileElement) driver.findElement(taskList);
//        return  Arrays.asList(el1.getText());
    }


}
