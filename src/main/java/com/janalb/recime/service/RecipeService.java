package com.janalb.recime.service;

import com.janalb.recime.dto.RecipeFilterDTO;
import com.janalb.recime.dto.RecipeCreateDTO;
import com.janalb.recime.dto.RecipeResponseDTO;
import com.janalb.recime.dto.RecipeUpdateDTO;
import com.janalb.recime.exception.RecipeNotFoundException;
import com.janalb.recime.model.Recipe;
import com.janalb.recime.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;

    public RecipeService(RecipeRepository recipeRepository, ModelMapper modelMapper) {
        this.recipeRepository = recipeRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public RecipeResponseDTO createRecipe(RecipeCreateDTO recipeDto) {
        Recipe recipe = modelMapper.map(recipeDto, Recipe.class);
        return modelMapper.map(recipeRepository.save(recipe), RecipeResponseDTO.class);
    }

    public List<RecipeResponseDTO> getAllRecipe() {
        return mapToResponseDtoList(recipeRepository.findAll());
    }

    public List<RecipeResponseDTO> getRecipeByTitle(String title) {
        return mapToResponseDtoList(recipeRepository.findByTitleContainingIgnoreCase(title));
    }

    public List<RecipeResponseDTO> getRecipeWithFilter(RecipeFilterDTO recipeFilter) {
        // Removed duplicate on ingredients list for optimized filtering
        if (!ObjectUtils.isEmpty(recipeFilter.getIncludedIngredients())) {
            List<String> uniqueIncludedIngredients = recipeFilter.getIncludedIngredients().stream().distinct().toList();
            recipeFilter.setIncludedIngredients(uniqueIncludedIngredients);
        }

        return mapToResponseDtoList(recipeRepository.findRecipeWithFilter(recipeFilter));
    }

    @Transactional
    public void updateRecipe(Long id, RecipeUpdateDTO newRecipeDto) {
        Optional<Recipe> recipeSearchResult = recipeRepository.findById(id);
        if (recipeSearchResult.isEmpty()) {
           throw new RecipeNotFoundException("Unable to update ID: " + id);
        }

        Recipe recipeToUpdate = recipeSearchResult.get();

        // Clear ingredients / instructions before mapping to force overwrite
        if (!ObjectUtils.isEmpty(newRecipeDto.getIngredients())) {
            recipeToUpdate.getIngredients().clear();
        }
        if (!ObjectUtils.isEmpty(newRecipeDto.getInstructions())) {
            recipeToUpdate.getInstructions().clear();
        }

        modelMapper.map(newRecipeDto, recipeToUpdate);
        recipeRepository.save(recipeToUpdate);
    }

    @Transactional
    public void deleteRecipe(Long id) {
        if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id);
        } else {
            throw new RecipeNotFoundException("Unable to delete ID: " + id);
        }
    }

    private List<RecipeResponseDTO> mapToResponseDtoList(List<Recipe> recipes) {
        return recipes.stream()
                .map(recipe -> modelMapper.map(recipe, RecipeResponseDTO.class))
                .collect(Collectors.toList());
    }
}
