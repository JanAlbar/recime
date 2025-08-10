package com.janalb.recime.dto;

import com.janalb.recime.dto.ingredient.IngredientResponseDTO;
import com.janalb.recime.dto.instruction.InstructionResponseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RecipeResponseDTO {
    private Long id;
    private String title;
    private String description;
    private List<IngredientResponseDTO> ingredients;
    private List<InstructionResponseDTO> instructions;
    private Integer serving;
}
