package com.janalb.recime.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;
    private String description;

    @Valid
    @ElementCollection
    @CollectionTable(name = "recipe_ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
    private List<Ingredient> ingredients;

    @Valid
    @ElementCollection
    @CollectionTable(name = "recipe_instructions", joinColumns = @JoinColumn(name = "recipe_id"))
    private List<Instruction> instructions;

    private Integer serving;
}
