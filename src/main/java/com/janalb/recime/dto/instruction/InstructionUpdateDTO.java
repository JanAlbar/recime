package com.janalb.recime.dto.instruction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InstructionUpdateDTO {
    @NotNull
    private Integer step;
    @NotBlank
    private String description;
}
