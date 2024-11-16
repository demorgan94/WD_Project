package com.dm.wd_backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import static org.mockito.Mockito.mock;

@SpringBootTest
class WdBackendApplicationTests {

	@Configuration
	static class TestConfig {
		@Bean
		public DataSource dataSource() {
			return mock(DataSource.class);
		}
	}

	@Test
	void contextLoads() {

	}

}
