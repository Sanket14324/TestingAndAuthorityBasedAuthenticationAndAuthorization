package com.spring.authorization.testing.model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    private String id;

    @NotNull(message = "must have name")
    private String name;

    @NotNull(message = "Must have price")
    @Positive(message = "Must be positive")
    @Min(value = 1, message = "must be more than 0")
    private Integer price;
}
