package test_app.wework.page;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarPage extends BasePage{
    private final By taskName=By.id("b2k");
    private final By save=byText("保存");
//    private final By taskList=By.id("gug");
    private final By taskList=By.id("gg_");
    private By add=By.id("gym");

    public CalendarPage(AppiumDriver<MobileElement> driver){
        super(driver);
    }
    public CalendarPage add(String name,String time){
        click(add);
        sendKeys(taskName,name);
        click(save);
        return this;
    }

    public List<String> getCalendar(String day){
        if(day!=null){
            //todo:选择日期
        }
        //这种写法是可以的
        List<MobileElement> list = driver.findElements(taskList);
        List<String> collect = list.stream().map(x -> x.getText()).collect(Collectors.toList());
        return collect;

//        MobileElement el1=(MobileElement) driver.findElement(taskList);
//        return  Arrays.asList(el1.getText(),day);
   }
}
