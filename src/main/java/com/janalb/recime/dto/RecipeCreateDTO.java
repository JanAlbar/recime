package com.janalb.recime.dto;

import com.janalb.recime.dto.ingredient.IngredientCreateDTO;
import com.janalb.recime.dto.instruction.InstructionCreateDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RecipeCreateDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String description;

    @Valid
    @NotEmpty
    private List<IngredientCreateDTO> ingredients;
    @Valid
    @NotEmpty
    private List<InstructionCreateDTO> instructions;

    private Integer serving;

}
