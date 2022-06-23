package com.example.demo.service;

import com.example.demo.controller.dto.ProductRequestDTO;
import com.example.demo.entity.Product;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;


public abstract class AbstractTest {
    public static final String ANY_NAME = "anyName";
    public static final String ANY_DESCRIPTION = "anyDescription";
    public static final String ANY_NUMBER = "";

    @Value("${api.version}")
    protected String versionApi;

    protected static final ObjectMapper mapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .registerModule(new JavaTimeModule());


    public Product createProduct(){

        return Product.builder()
                .price(1D)
                .description(ANY_DESCRIPTION)
                .name(ANY_NAME)
                .brand(ANY_DESCRIPTION)
                .id("1234")
                .build();
    }


    public Product createProduct2(){

        return Product.builder()
                .price(2D)
                .description(ANY_DESCRIPTION)
                .name(ANY_NAME)
                .brand(ANY_DESCRIPTION)
                .id("123")
                .build();
    }

    public ProductRequestDTO createProductRequestDTO(){
        return ProductRequestDTO.builder()
                .id(ANY_NUMBER)
                .price(1D)
                .brand(ANY_DESCRIPTION)
                .description(ANY_DESCRIPTION)
                .name(ANY_NAME)
                .build();
    }

}