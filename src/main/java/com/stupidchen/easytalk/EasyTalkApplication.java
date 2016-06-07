package com.stupidchen.easytalk;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class EasyTalkApplication {
	public static SqlSessionFactory sqlSessionFactory;

	public static void initSqlSessionFactory() throws IOException {
		InputStream configInputStream = Resources.getResourceAsStream("config/mybatis-config.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(configInputStream);
	}

	public static void main(String[] args) throws IOException {
		initSqlSessionFactory();
		SpringApplication.run(EasyTalkApplication.class, args);
	}
}
