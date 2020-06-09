package test_app.wework.page;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Sleeper;

import java.time.Duration;

public class Wework extends BasePage{
    public Wework() {
        super("com.tencent.wework", ".launch.LaunchSplashActivity");
    }
    public void backhome(){
        click(By.id("gyb"));
    }
    public CalendarPage cp(){
        click(By.xpath("//*[@text='日程']"));
        return new CalendarPage(driver);
    }

    public BacklogPage bp(){
        click(By.id("gwu"));
        return new BacklogPage(driver);
    }

    public ReportPage rp() {
        click("工作台");
        swipeDown();
        click("汇报");
        return new ReportPage(driver);
    }


}
