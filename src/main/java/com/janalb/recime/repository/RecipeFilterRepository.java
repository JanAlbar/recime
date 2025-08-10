package com.janalb.recime.repository;

import com.janalb.recime.dto.RecipeFilterDTO;
import com.janalb.recime.model.Recipe;

import java.util.List;

public interface RecipeFilterRepository {
    List<Recipe> findRecipeWithFilter(RecipeFilterDTO recipeFilter);
}
