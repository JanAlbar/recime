package com.janalb.recime.dto.ingredient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IngredientResponseDTO {
    private String name;
    private String quantity;
    private boolean nonVegetarian; // default false: vegetarian-friendly
}
