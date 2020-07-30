package test_service.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.Result;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertTrue;

/**
 * 这是基于wiremock 工具的代码
 */

public class StubTest {

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void beforeAll(){
        //启动一个服务， 可以理解成，就是一个桩，一套假的服务，假对象
        //No-args constructor will start on port 8080, no HTTPS
        wireMockServer = new WireMockServer(wireMockConfig().port(8089));
        wireMockServer.start();
        configureFor("localhost", 8089);

    }

    @Test
    //这种方法，单纯的桩，返回数据写死
    public void stub() throws InterruptedException {
        stubFor(get(urlEqualTo("/user/d"))
                .withHeader("Accept", equalTo("text/xml"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/xml")
                        .withBody("<response>user d's info</response>")));

       Thread.sleep(10000);
    }

    @Test
    //这种方法，体现了mock的可控性，返回数据可控
    public void mockOnStub() throws InterruptedException {
        stubFor(get(urlEqualTo("/user/d"))
                .withHeader("Accept", equalTo("text/xml"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/xml")
                        .withBody("<response>user d's info</response>")));

        Thread.sleep(10000);
        //重新设置假对象，返回另外一个假数据
        reset();

        stubFor(get(urlEqualTo("/user/d"))
                .withHeader("Accept", equalTo("text/xml"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/xml")
                        .withBody("<response>this is a exception </response>")));

        Thread.sleep(10000);
    }

    @Test
    //proxy --透明代理，客户端发送服务端，经过了代理，代理没有做任何操作，直接转发给服务端
    public void proxy() throws InterruptedException {
        stubFor(get(urlMatching(".*"))
                .atPriority(10)     //设置优先级
                .willReturn(aResponse()
                        .proxiedFrom("https://ceshiren.com")));
        Thread.sleep(100000);
    }

    @Test
    //intercept --拦截， 将要返回的消息拦截处理成要测试的内容，并返回给客户端
    public void mockOnProxy() throws InterruptedException, IOException {
        stubFor(get(urlMatching(".*"))
                .atPriority(10)    //设置优先级、数越大优先级越高
                .willReturn(aResponse()
                        .proxiedFrom("https://ceshiren.com")));

        stubFor(get(urlMatching("/categories_and_latest"))
                .atPriority(1)
                .willReturn(aResponse()
                     .withBody(Files.readAllBytes(Paths.get("/Users/christbella/IdeaProjects/MyJava/src/main/resources/mock_local.json")))));
        Thread.sleep(100000);
    }


}
