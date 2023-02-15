package com.task.bookmark;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookmarkApplicationTests extends PostgreSqlContainer {

	@Test
	void contextLoads() {
		System.out.println("Everything is good.");
	}

}
