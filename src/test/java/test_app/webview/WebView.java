package test_app.webview;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.concurrent.TimeUnit;


public class WebView {

    public static AndroidDriver driver;


    @Before
    public void setUp() throws Exception {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "android");
        desiredCapabilities.setCapability("deviceName", "Pixel_XL_API_29");
        desiredCapabilities.setCapability("appPackage", "com.xueqiu.android");
        desiredCapabilities.setCapability("appActivity", ".view.WelcomeActivityAlias");
        desiredCapabilities.setCapability("noReset", "true");
//        desiredCapabilities.setCapability("dontStopAppOnReset", "true");
        URL url = new URL("http://localhost:4723/wd/hub");
        driver = new AndroidDriver<MobileElement>(url,desiredCapabilities);
        //todo: 等待优化
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@text='交易']"));
    }
    @Test
    public void webview_native(){
        driver.findElement(By.xpath("//*[@text='交易']")).click();
        //android 10系统
        driver.findElement(By.xpath("//*[@text='基金开户']")).click();
        //android 7系统
//        driver.findElement(By.xpath("//*[@content-desc='基金开户']")).click();

    }
    @Test
    public void webview_web() throws InterruptedException {
        driver.findElement(By.xpath("//*[@text='交易']")).click();
        for (int i = 0; i < 3; i++) {
            //这个能获取到手机里， 所有的webview 和native，和app没关系，而是手机里所有的
            driver.getContextHandles().forEach(context -> System.out.println(context.toString()));
            Thread.sleep(1000);
        }
            //切换到要测试的上下文
            driver.context(driver.getContextHandles().toArray()[1].toString());

            //切换到刚刚那个上下文里的所有的窗口
            driver.getWindowHandles().forEach(window -> {
                System.out.println(window);
                driver.switchTo().window(window);
//                System.out.println(driver.getPageSource());
            });

            //切换到想要的窗口
//            方法1
//            driver.getWindowHandles().stream().filter(win ->{
//                driver.switchTo().window(win);
//                return driver.getTitle().contains("实盘交易");
//            });


//            方法2
        Object[] array= driver.getWindowHandles().toArray();
        driver.switchTo().window(array[0].toString());

        //找到基金开会，点击
//        driver.findElement(By.cssSelector(".trade_home_info_3aI")).click();
        //作业
        //找到A股开户
        driver.findElement(By.cssSelector(".trade_home_onsale_3Ia")).click();
        Thread.sleep(10000);
        // 切换到新的页面，需要重新切换窗口
        Object[] array1= driver.getWindowHandles().toArray();
        driver.switchTo().window(array1[3].toString());

        System.out.println(driver.getPageSource());
        driver.findElement(By.cssSelector("#phone-number")).sendKeys("18611544329");
        driver.findElement(By.cssSelector("#code")).sendKeys("3455");
        driver.findElement(By.cssSelector(".btn-submit")).click();
        }



    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(20);
        driver.quit();
    }
}
