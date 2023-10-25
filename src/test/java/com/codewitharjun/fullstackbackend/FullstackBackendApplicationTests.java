package com.codewitharjun.fullstackbackend;

import org.junit.jupiter.api.Test;
// Spring Boot to Quarkus Migration: Remove the Spring Boot-related import statements
// import org.springframework.boot.test.context.SpringBootTest;

// Spring Boot to Quarkus Migration: Add the following Quarkus-related import statement
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonId;

@SpringBootTest
class FullstackBackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
