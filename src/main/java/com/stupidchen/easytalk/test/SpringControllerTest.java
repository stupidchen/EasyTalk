package com.stupidchen.easytalk.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Mike on 16/6/6.
 */

@Component
public class SpringControllerTest implements GeneralTest {
    private static Logger logger = LoggerFactory.getLogger(SpringControllerTest.class);

    public SpringControllerTest() {
        SelfCheck.register(this);
    }

    @Override
    public boolean execute() {
        logger.info("Executing Hello test");
        return true;
    }

}
