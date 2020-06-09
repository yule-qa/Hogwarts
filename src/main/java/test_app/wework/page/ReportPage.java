package test_app.wework.page;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class ReportPage extends BasePage {

    private final By save = byText("提交");
    private final By conmit = By.id("b_O");

    public ReportPage(AppiumDriver driver) {
        super(driver);
    }

    public ReportPage add(String report, List<String> content) {
        click(report);
//        driver.context("WEBVIEW_com.tencent.mm:appbrand0");
        for (int i = 1; i < 4; i++) {
            By taskName = By.xpath("//android.webkit.WebView[@content-desc=\"" + report + "\"]/android.widget.EditText[" + i + "]");
            String contentStr = content.get(i - 1);
            sendKeys(taskName, contentStr);
        }
        swipeDown();
        driver.findElement(By.xpath("//android.view.View[@content-desc=\"提交\"]")).click();
        driver.context("NATIVE_APP");
        click(conmit);
        return this;
    }

    public String getReport() {
        return driver.findElement(By.xpath("//*[contains(@text,'今日工作')]/following-sibling::*[1]")).getText();
    }
}

