package com.amazon.product.dto;

import java.util.Set;


import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    @NotEmpty
    private String name;
    @DecimalMin(value = "0.0" ,inclusive = false)
    private double price;
    private double mrp;
    @NotEmpty
    private String description;
    private Set<String> category;
    private String poster;
    private String posterurl;
}
