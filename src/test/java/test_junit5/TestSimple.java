package test_junit5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSimple {
    @Test
    void demoTest(){
        assertEquals(1,1);
        System.out.println("你好");
    }
}
