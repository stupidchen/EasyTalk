package com.stupidchen.easytalk.test;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike on 16/6/6.
 */

@Controller
public class SelfCheck {
    private static Logger logger = LoggerFactory.getLogger(SelfCheck.class);

    private static List<GeneralTest> testList = new ArrayList<GeneralTest>();

    public static void register(GeneralTest test) {
        testList.add(test);
    }

    @RequestMapping("/test")
    public void execute() {
        logger.info("into SelfCheck.. Test number: " + testList.size());

        boolean result = true;
        int testNo = 0;
        for (GeneralTest test: testList) {
            boolean thisResult = test.execute();
            result = result && thisResult;
            if (thisResult)
                logger.info("Success");
            else
                logger.warn("Failed!");
        }
    }
}
