package com.codewitharjun.fullstackbackend.controller;

import com.codewitharjun.fullstackbackend.exception.UserNotFoundException;
import com.codewitharjun.fullstackbackend.model.User;
import com.codewitharjun.fullstackbackend.repository.UserRepository;
// Spring Boot to Quarkus Migration: Remove the Spring Boot-related import statements
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// Spring Boot to Quarkus Migration: Add the following Quarkus-related import statement
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonId;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

// Spring Boot to Quarkus Migration: Add the following import statements
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.inject.Inject;

/* Created by Arjun Gautam */
// Spring Boot to Quarkus Migration: Remove the Spring Boot @RequestMapping and @RestController annotations
// @RestController
// Spring Boot to Quarkus Migration: Add @Produces and @Consumes annotations
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

// Spring Boot to Quarkus Migration: Remove @CrossOrigin annotation
// @CrossOrigin("http://demoapp-frontend:8080")  // Updated by Paul Gilber
public class UserController {

    // Spring Boot to Quarkus Migration: Remove the Spring Boot @Autowired and @Inject annotations
    // @Autowired
    private UserRepository userRepository;

    // @PostMapping("/user")
    @Path("/user")
    @POST
    User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    // Spring Boot to Quarkus Migration: Remove the Spring Boot @Autowired and @Inject annotations
    // @GetMapping("/users")
    @Path("/users")
    @GET
    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // @GetMapping("/user/{id}")
    @Path("/user/{id}")
    @GET
    User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    // @PutMapping("/user/{id}")
    @Path("/user/{id}")
    @PUT
    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    return userRepository.save(user);
                }).orElseThrow(() -> new UserNotFoundException(id));
    }

    // @DeleteMapping("/user/{id}")
    @Path("/user/{id}")
    @DELETE
    String deleteUser(@PathVariable Long id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return  "User with id "+id+" has been deleted success.";
    }
}
