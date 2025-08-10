package com.janalb.recime.dto;

import com.janalb.recime.dto.ingredient.IngredientUpdateDTO;
import com.janalb.recime.dto.instruction.InstructionUpdateDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RecipeUpdateDTO {

    @Size(min = 1, message = "Title must not be empty if provided")
    private String title;
    @Size(min = 1, message = "Description must not be empty if provided")
    private String description;
    @Valid
    private List<IngredientUpdateDTO> ingredients;
    @Valid
    private List<InstructionUpdateDTO> instructions;
    private Integer serving;
}
