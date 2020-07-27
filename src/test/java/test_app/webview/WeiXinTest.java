package test_app.webview;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;


public class WeiXinTest {


    public static AndroidDriver driver;


    @BeforeAll
    public static void setUp() throws Exception {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        //因为小程序的进程名与包名不一样，需要加上这个参数
        ChromeOptions chromeOptions=new ChromeOptions();
        chromeOptions.setExperimentalOption("androidProcess","com.tencent.mm:appbran0");
        desiredCapabilities.setCapability("goog:chromeOptions",chromeOptions);

        //必须得加上，因为默认生成browserName=chrome的设置，需要去掉
        desiredCapabilities.setCapability("browserName","");

        desiredCapabilities.setCapability("platformName", "android");
        desiredCapabilities.setCapability("deviceName", "88Y5T19A03028776");
        desiredCapabilities.setCapability("appPackage", "com.tencent.mm");
        desiredCapabilities.setCapability("appActivity", "com.tencent.mm.ui.LauncherUI");
        desiredCapabilities.setCapability("unicodeKeyboard", "true");
        desiredCapabilities.setCapability("resetKeyboard", "true");

        //高危操作，如果设置错误，聊天记录会被清空，建议使用小号测试
        desiredCapabilities.setCapability("noReset", "true");
        //通过自己的adb代理修复chromedriver的bug并解决@xweb_devtools_remote的问题
        desiredCapabilities.setCapability("adbPort", "5038");
        //加速
        desiredCapabilities.setCapability("skipLogcatCapture", "true");

        //用于快速测试
//        desiredCapabilities.setCapability("dontStopAppOnReset", "true");

        //当本地有多个chromedriver时，选择版本使用本地chromedriver。
        // 简单粗暴的解决方案,
        desiredCapabilities.setCapability("chromedriverExecutable", "/Users/christbella/chromedrivers/chromedriver_78.0.3904.11");

        //完善的版本选择方案，不过会优先找android webview默认实现
//        desiredCapabilities.setCapability("chromedriverExecutableDir", "/Users/christbella/chromedrivers");
//        desiredCapabilities.setCapability("chromedriverChromeMappingFile", "/Users/christbella/mapping.json");

        //打印更多chromedriver的log方便定位问题
        desiredCapabilities.setCapability("showChromedriverLog", true);

        //4273是服务端开的一个appium server的端口号，用于appium client（就是这个脚本）与appium server通讯。
        // 后续服务端会将4273映射到4274， 用于appium server与移动设备通讯
        URL url = new URL("http://localhost:4723/wd/hub");
        driver = new AndroidDriver<MobileElement>(url,desiredCapabilities);
        //todo: 等待优化
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@text='通讯录']"));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void wxmicroApplication(){
        Dimension size = driver.manage().window().getSize();
        new TouchAction<>(driver).longPress(LongPressOptions.longPressOptions().withDuration(Duration.ofSeconds(2))
                .withPosition(PointOption.point(size.width/2,size.height/2)))
                .moveTo(PointOption.point(size.width/2,size.height/10*9))
                .release()
                .perform();
        driver.findElement(By.className("android.widget.EditText")).click();
        driver.findElement(By.xpath("//*[@text='取消']"));
        driver.findElement(By.className("android.widget.EditText")).sendKeys("雪球");
        driver.findElement(By.className("android.widget.Button")).click();
        driver.findElement(By.xpath("//*[@text='自选']"));

        driver.getContextHandles().forEach(context -> {
            System.out.println(context.toString());
        });

        String webview = driver.getContextHandles().stream()
                .filter(context -> context.toString().contains("WEBVIEW_xweb"))
                .findFirst().get().toString();
        System.out.println(webview);
        driver.context(webview);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @AfterAll
    public static void tearDown() throws InterruptedException {
        Thread.sleep(20);
        driver.quit();
    }
}
