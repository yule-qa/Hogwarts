package test_framework_service;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ApiObjectModelTest {

    private static ApiObjectModel api;

    @BeforeAll
    static void beforeAll() throws IOException{
        api = ApiObjectModel.load("src/main/resources/test_framework_service/wework_api.yaml");

    }

    @Test
    void load()  {
        assertThat(api.name,equalTo("wework"));
    }

    @Test
    void run() {
        Response response = api.methods.get("get_token").run();
        assertThat(response.path("errcode"),equalTo(0));
    }
}