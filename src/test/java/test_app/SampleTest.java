package test_app;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.concurrent.TimeUnit;


public class SampleTest {

    public static AndroidDriver driver;


    @Before
    public void setUp() throws Exception {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "android");
        desiredCapabilities.setCapability("deviceName", "emulator-5554");
        desiredCapabilities.setCapability("appPackage", "com.xueqiu.android");
        desiredCapabilities.setCapability("appActivity", ".view.WelcomeActivityAlias");
        desiredCapabilities.setCapability("noReset", "true");
//        desiredCapabilities.setCapability("dontStopAppOnReset", "true");
        URL url = new URL("http://localhost:4723/wd/hub");
        driver = new AndroidDriver<MobileElement>(url,desiredCapabilities);
        //todo: 等待优化
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void sampleTest() {
        MobileElement el4 = (MobileElement) driver.findElementById("com.xueqiu.android:id/home_search");
        el4.click();
        MobileElement el5 = (MobileElement) driver.findElementById("com.xueqiu.android:id/search_input_text");
        el5.sendKeys("alibaba");
        MobileElement el6 = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.LinearLayout/androidx.recyclerview.widget.RecyclerView/android.widget.RelativeLayout[1]/android.widget.LinearLayout/android.widget.TextView[1]");
        el6.click();
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
