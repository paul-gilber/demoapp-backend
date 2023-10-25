package com.codewitharjun.fullstackbackend.repository;

import com.codewitharjun.fullstackbackend.model.User;
// Spring Boot to Quarkus Migration: Remove the Spring Boot-related import statements
import org.springframework.data.jpa.repository.JpaRepository;


// Spring Boot to Quarkus Migration: Add the following Quarkus-related import statement
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonId;

public interface UserRepository extends JpaRepository<User,Long> {
}
