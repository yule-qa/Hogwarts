package test_app.wework.page;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.*;

class CalendarPageTest {

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
        wework.backhome();
        wework.quit();

    }

    @Test
    void add() {
        assertTrue(wework.cp().add("上班打卡", "6-10").getCalendar("6-10").contains("上班打卡"));
    }
}