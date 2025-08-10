package com.janalb.recime.controller;

import com.janalb.recime.dto.RecipeFilterDTO;
import com.janalb.recime.dto.RecipeCreateDTO;
import com.janalb.recime.dto.RecipeResponseDTO;
import com.janalb.recime.dto.RecipeUpdateDTO;
import com.janalb.recime.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    public RecipeResponseDTO createRecipe(@Valid @RequestBody RecipeCreateDTO recipe) {
        return recipeService.createRecipe(recipe);
    }

    @GetMapping
    public List<RecipeResponseDTO> getAllRecipe() {
        return recipeService.getAllRecipe();
    }

    @GetMapping("/title/{title}")
    public List<RecipeResponseDTO> getRecipeByTitle(@PathVariable("title") String title) {
        return recipeService.getRecipeByTitle(title);
    }

    @GetMapping("/filter")
    public List<RecipeResponseDTO> getRecipeWithFilter(@Valid @RequestBody RecipeFilterDTO recipeFilter) {
        return recipeService.getRecipeWithFilter(recipeFilter);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateRecipe(@PathVariable("id") Long id, @Valid @RequestBody RecipeUpdateDTO newRecipeDto) {
        recipeService.updateRecipe(id, newRecipeDto);
        return ResponseEntity.ok(Map.of("message", "Recipe updated successfully!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteRecipe(@PathVariable("id") Long id){
        recipeService.deleteRecipe(id);
        return ResponseEntity.ok(Map.of("message", "Recipe deleted successfully!"));
    }
}
