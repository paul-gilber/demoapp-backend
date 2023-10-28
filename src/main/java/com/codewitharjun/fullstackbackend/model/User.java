package com.codewitharjun.fullstackbackend.model;

// Spring Boot 2.x using Java EE APIs javax.*
// import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;

// Spring Boot 3 migrated all Java EE APIs javax.* to their equivalent Jakarta EE variant jakarta.* due to the transition from Oracle to the Eclipse Foundation.
// To fix this, we should use the Jakarta Persistence (JPA) APIs under the jakarta.persistence package instead of javax.persistence.
// Spring Boot 3 using Jakarta EE APIs jakarta.*
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;    // Unused
import jakarta.persistence.Id;

/* Created by Arjun Gautam */
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String name;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
