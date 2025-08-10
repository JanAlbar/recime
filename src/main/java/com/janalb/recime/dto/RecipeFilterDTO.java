package com.janalb.recime.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RecipeFilterDTO {
    private Boolean isVegetarianFriendly;
    private Integer serving;
    private List<String> excludedIngredients;
    private List<String> includedIngredients;
    private String instruction;
}
