package test_service.utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FakerUtils {
    private static final Logger logger = LoggerFactory.getLogger(FakerUtils.class);

    public static String getTimeStamp(){
        return String.valueOf(System.currentTimeMillis());
    }

    @Test
    void test(){
        logger.info(getTimeStamp());
    }
}
