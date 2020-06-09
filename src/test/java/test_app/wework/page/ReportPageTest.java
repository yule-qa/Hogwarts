package test_app.wework.page;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportPageTest {
    private static Wework wework;
    @BeforeAll
    static void beforeAll() {

        wework = new Wework();
    }


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
//        wework.backhome();
        wework.quit();
    }

    @Test
    void addDailyReport() {
        List<String> list=new ArrayList<String>();
        list.add("日报测试");
        list.add("日报测试");
        list.add("日报测试");
       assertTrue( wework.rp().add("日报",list).getReport().contains("日报测试"));
    }
    @Test
    void addWeeklyReport(){
        List<String> list=new ArrayList<String>();
        list.add("周报测试");
        list.add("周报测试");
        list.add("周报测试");
        assertTrue( wework.rp().add("周报",list).getReport().contains("周报测试"));
    }

}