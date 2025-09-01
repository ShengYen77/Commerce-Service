package com.gtelant.commerce.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
public class CommerceServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(CommerceServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CommerceServiceApplication.class, args);
	}

	// 啟動時自動檢查資料庫連線
	@Bean
	public CommandLineRunner checkDatabaseConnection(DataSource dataSource) {
		return args -> {
			try (Connection conn = dataSource.getConnection()) {
				if (conn.isValid(2)) {  // 2秒 timeout
					logger.info("MySQL connection successful!!!");
				} else {
					logger.error("MySQL connection failed!!!");
				}
			} catch (Exception e) {
				logger.error("Unable to connect to MySQL database!!!", e);
			}

			logger.info("Commerce Service API started!!!");
		};
	}
}
