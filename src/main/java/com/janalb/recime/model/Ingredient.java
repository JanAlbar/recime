package com.janalb.recime.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class Ingredient {
    @NotBlank
    private String name;
    @NotBlank
    private String quantity;

    private boolean nonVegetarian; // default false: vegetarian-friendly
}
