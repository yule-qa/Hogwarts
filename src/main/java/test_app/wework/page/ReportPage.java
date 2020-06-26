package test_app.wework.page;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class ReportPage extends AppBasePage {

    private final By save = byText("提交");
    private final By conmit = byText("确定");

    public ReportPage(AppiumDriver driver) {
        super(driver);
    }

    public ReportPage add(String report, List<String> content) {
        click(report);
        for (int i = 1; i < 4; i++) {
            By taskName = By.xpath("//android.webkit.WebView[@content-desc=\"" + report + "\"]/android.widget.EditText[" + i + "]");
            String contentStr = content.get(i - 1);
            sendKeys(taskName, contentStr);
        }
        swipeDown();
        driver.findElement(By.xpath("//android.view.View[@content-desc=\"提交\"]")).click();
        click(conmit);
        return this;
    }

    public  boolean isExist(String report) throws InterruptedException {
        Thread.sleep(10);
        try {
            driver.findElementsByAccessibilityId(report);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

