package com.codewitharjun.fullstackbackend;

// Spring Boot to Quarkus Migration: Remove the Spring Boot-related import statements
// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// Spring Boot to Quarkus Migration: Add the following Quarkus-related import statement
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonId;
import io.quarkus.runtime.annotations.QuarkusMain;
import io.quarkus.runtime.Quarkus;

// Spring Boot to Quarkus Migration: Replace @SpringBootApplication annotation with @QuarkusMain
// @SpringBootApplication
@QuarkusMain

public class FullstackBackendApplication {
	public static void main(String[] args) {
		// SpringApplication.run(FullstackBackendApplication.class, args);
		Quarkus.run(FullstackBackendApplication.class, args);
	}
}
