package com.ntnghia.bookmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class NghianguyenBookManagementApplicationTests {

	@Value("${spring.datasource.url}")
	private String dataSourceUrl;

	@Test
	public void contextLoads() {
	}

	@Test
	public void test_dataSourceUrl() {
		assertEquals(dataSourceUrl, "jdbc:postgresql://localhost:5432/book_management_test");
	}
}
