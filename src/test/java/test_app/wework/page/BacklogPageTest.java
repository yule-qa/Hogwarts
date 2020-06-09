package test_app.wework.page;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BacklogPageTest {

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
        assertTrue(wework.bp().add("下午开会").getBacklog().contains("下午开会"));
    }
}