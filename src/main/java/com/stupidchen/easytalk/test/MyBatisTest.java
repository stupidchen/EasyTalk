package com.stupidchen.easytalk.test;

import com.stupidchen.easytalk.EasyTalkApplication;
import com.stupidchen.easytalk.data.Mapper.UserMapper;
import com.stupidchen.easytalk.data.User;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Mike on 16/6/7.
 */
@Component
public class MyBatisTest implements GeneralTest {
    private static Logger logger = LoggerFactory.getLogger(MyBatisTest.class);

    public MyBatisTest() {
        SelfCheck.register(this);
    }

    @Override
    public boolean execute() {
        boolean result = true;
        logger.info("Executing MyBatis test...");

        SqlSession session = EasyTalkApplication.sqlSessionFactory.openSession();
        try {
            logger.info("Inserting into user!");
            UserMapper userMapper = session.getMapper(UserMapper.class);
            userMapper.insertUser("348811243", "stupidchen", "123");
            User test = userMapper.selectUser("348811243");
            logger.info("Insert user success! User: " + test);
            userMapper.deleteUser("348811243");
            result = true;
        } catch (Exception e) {
            logger.warn("Inserting exception!" + e);
            result = false;
        } finally {
            session.commit();
            session.close();
            logger.info("Session closed.");
        }

        return result;
    }
}
