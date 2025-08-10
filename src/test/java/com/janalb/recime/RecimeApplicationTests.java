package com.janalb.recime;

import com.janalb.recime.dto.RecipeCreateDTO;
import com.janalb.recime.dto.RecipeResponseDTO;
import com.janalb.recime.dto.RecipeUpdateDTO;
import com.janalb.recime.dto.ingredient.IngredientCreateDTO;
import com.janalb.recime.dto.instruction.InstructionCreateDTO;
import com.janalb.recime.service.RecipeService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class RecimeApplicationTests {

	@Autowired
	private RecipeService recipeService;

	@Test
	void contextLoads() {
	}

	@Test
	void testCreateReadUpdateDeleteRecipe() {
		// GIVEN: a new recipe to create
		RecipeCreateDTO recipe = getRecipeCreateDTO();

		// WHEN: the recipe is created
		RecipeResponseDTO newlyCreatedRecipe = recipeService.createRecipe(recipe);

		// THEN: the recipe should be created successfully
		assertThat(newlyCreatedRecipe).isNotNull();
		assertThat(newlyCreatedRecipe.getId()).isEqualTo(1L);

		assertThat(recipeService.getRecipeByTitle("Adobo")).isNotEmpty();

		// GIVEN: an updated title for the recipe
		RecipeUpdateDTO updatedRecipe = new RecipeUpdateDTO();
		updatedRecipe.setTitle("Adobo V2");

		// WHEN: the recipe is updated
		recipeService.updateRecipe(1L, updatedRecipe);

		// THEN: the recipe title should be updated successfully
		assertThat(recipeService.getRecipeByTitle("Adobo V2")).isNotEmpty();

		// WHEN: the recipe is deleted
		recipeService.deleteRecipe(1L);

		// THEN: the recipe should no longer exist
		assertThat(recipeService.getRecipeByTitle("Adobo V2")).isEmpty();
	}

	private static RecipeCreateDTO getRecipeCreateDTO() {
		RecipeCreateDTO recipe = new RecipeCreateDTO();
		recipe.setTitle("Chicken Adobo");
		recipe.setDescription("A classic Filipino dish made with chicken braised in soy sauce, vinegar, garlic, and spices.");

		IngredientCreateDTO ingredient = new IngredientCreateDTO();
		ingredient.setName("Chicken Thighs");
		ingredient.setQuantity("1 kg");
		recipe.setIngredients(List.of(ingredient));

		InstructionCreateDTO instruction = new InstructionCreateDTO();
		instruction.setStep(1);
		instruction.setDescription("In a pot, combine chicken, soy sauce, vinegar, garlic, bay leaves, peppercorns, and water.");
		recipe.setInstructions(List.of(instruction));
		return recipe;
	}
}
