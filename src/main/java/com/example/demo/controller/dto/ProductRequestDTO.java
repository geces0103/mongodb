package com.example.demo.controller.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {

    private String id;
    @NonNull
    private String description;
    @NonNull
    private String name;
    @NonNull
    private Double price;
    @NonNull
    private String brand;
}
