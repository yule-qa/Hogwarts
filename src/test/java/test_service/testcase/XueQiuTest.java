package test_service.testcase;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.hamcrest.CoreMatchers.equalTo;

public class XueQiuTest {
    @Test
    void xueqiuLoginTest(){
        useRelaxedHTTPSValidation();
        given()
                .header("User-Agent","Xueqiu iPhone 12.14.1")
                .queryParam("_s","1acd76")
                .queryParam("_t","123BDC7D-53B8-4536-B3BA-307F9BAF6C52.1554299803.1594880995459.1594881385866"	)
                .cookie("xq_a_token","5d52bdbe1ac5bb3b28b2a5cfc1e18a8e2146f727")
                .cookie("xq_id_token","eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1aWQiOjE1NTQyOTk4MDMsImlzcyI6InVjIiwiZXhwIjoxNTk3NDczMzI1LCJjdG0iOjE1OTQ4ODEzODU4MTEsImNpZCI6IldpQ2lteHBqNUgifQ.D6UYT0nYiJB2CMO0nepFQMrA-zawbpC4swMdSoDaKFSK9GOCpdLpgSs1zCp3U_W647tl28xQkV8ML_xU6o6xLpB0LKPegJOKQS5qB3CDIOdGt1mwFvX6LTFQy7_zZWymnyKyiEt0elsTMUkj4MHPXyuHsBMGiEKlq_dBruVy8RXY1fXZxoI2BPUjBEHE3RhXUPBcbBEU4EJ2MD48TYLnZPA4cC-4AFbGwOe3m-pMhp7HBsiAVdbCQNq5kNSwcc1E2sjLV2PnmaSXLmGV9c2vfQJd42OJSLjUuSWfOGKV6po5zgdEa-4Ecwl1gRveefQgPhFm4uYf39I_a_uG6aGRAQ")
                .cookie("u","1554299803")
                .formParam("client_id","WiCimxpj5H")
                .formParam("client_secret","TM69Da3uPkFzIdxpTEm6hp")
                .formParam("device_uuid","123BDC7D-53B8-4536-B3BA-307F9BAF6C52")
                .formParam("grant_type","password")
                .formParam("areacode","86")
                .formParam("is_register","0")
                .formParam("password","01465acf8e1b28659f0afc32e4951eee")
                .formParam("sid","123BDC7D-53B8-4536-B3BA-307F9BAF6C52")
                .formParam("telephone","18611544329")
                .when().post("https://api.xueqiu.com/provider/oauth/token")
                .then().body("uid",equalTo(1269109935));
    }
}
