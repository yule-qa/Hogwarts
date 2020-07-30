package test_service.mock;

import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.filters.ResponseFilter;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import org.junit.jupiter.api.*;

/**
 * 这是基于browermob——proxy 工具的代码
 */

public class MockOnProxyTest {


    @Test
    void mockOnProxy() throws InterruptedException {
        BrowserMobProxy proxy=new BrowserMobProxyServer();
        proxy.start(8083);
        proxy.addResponseFilter(new ResponseFilter() {
            @Override
            public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
                contents.setTextContents(contents.getTextContents().replace("霍格沃兹","mock"));
            }
        });

        Thread.sleep(100000);
        //app自动化
        //selenium自动化

    }
}
