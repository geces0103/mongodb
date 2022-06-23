package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products_collection")
public class Product {

    @Id
    private String id;

    @Field(name = "name")
    private String name;

    @Field(name = "description")
    private String description;

    @Field(name = "price")
    private Double price;

    @Field(name = "brand")
    private String brand;

}
