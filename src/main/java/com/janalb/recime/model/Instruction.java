package com.janalb.recime.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class Instruction {
    @NotNull
    private Integer step;
    @NotBlank
    private String description;
}
