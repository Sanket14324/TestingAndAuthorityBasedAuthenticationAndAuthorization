package com.spring.authorization.testing.model;


import com.spring.authorization.testing.authoritiesAndRoles.Role;
import com.spring.authorization.testing.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {


    @Id
    private String id;


    @NotNull
    @Size(min = 3, max = 30, message = "Name must contain min 3 characters or max 30 characters")
    private String name;


    @NotNull
    @UniqueEmail
    @Email(message = "Email must be in proper format")
    private String email;

    @NotNull
    @Size(min= 8, message = "Password must be contain 8 or more than 8 character")
    private String password;

    @NotNull(message = "User must have role")
    private Role role;
}
