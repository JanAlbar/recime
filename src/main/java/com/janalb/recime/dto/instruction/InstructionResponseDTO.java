package com.janalb.recime.dto.instruction;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InstructionResponseDTO {
    private Integer step;
    private String description;
}
