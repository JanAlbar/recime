package com.janalb.recime.dto.ingredient;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IngredientUpdateDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String quantity;
    private boolean nonVegetarian; // default false: vegetarian-friendly
}
