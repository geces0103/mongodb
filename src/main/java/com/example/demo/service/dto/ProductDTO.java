package com.example.demo.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class ProductDTO implements Serializable {
    private String id;
    private String description;
    private String name;
    private Double price;
    private String brand;
}
