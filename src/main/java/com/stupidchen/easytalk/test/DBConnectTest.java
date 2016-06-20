package com.stupidchen.easytalk.test;

import com.stupidchen.easytalk.data.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Mike on 16/6/6.
 */

@Component
public class DBConnectTest implements GeneralTest {
    private static Logger logger = LoggerFactory.getLogger(DBConnectTest.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    public DBConnectTest() {
        SelfCheck.register(this);
    }

    @Override
    public boolean execute() {
        logger.info("DB connect test(From User): ");
        jdbcTemplate.query("select * from user", (rs, rowNum) -> new User(rs.getString("userId"),  rs.getString("password"))).forEach(user -> logger.info(user.toString()));
        return true;
    }
}
