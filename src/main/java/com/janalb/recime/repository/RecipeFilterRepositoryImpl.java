package com.janalb.recime.repository;

import com.janalb.recime.dto.RecipeFilterDTO;
import com.janalb.recime.model.Recipe;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Repository
public class RecipeFilterRepositoryImpl implements RecipeFilterRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Recipe> findRecipeWithFilter(RecipeFilterDTO recipeFilter) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT r.* FROM recipe r ");
        sql.append("LEFT JOIN recipe_ingredients ig ON ig.recipe_id = r.id ");
        sql.append("LEFT JOIN recipe_instructions it ON it.recipe_id = r.id ");
        sql.append("GROUP BY r.id ");
        sql.append("HAVING 1=1 ");


        // Add vegetarian filter
        if (Boolean.TRUE.equals(recipeFilter.getIsVegetarianFriendly())) {
            sql.append("AND SUM(CASE WHEN ig.non_vegetarian = TRUE THEN 1 ELSE 0 END) = 0 ");
        }

        // Add serving filter
        if (recipeFilter.getServing() != null) {
            sql.append("AND r.serving >= :serving ");
        }

        // Add included ingredients filter
        List<String> includedIngredients = recipeFilter.getIncludedIngredients();
        addIngredientsFilter(sql, includedIngredients, true);

        // Add excluded ingredients filter
        List<String> excludedIngredients = recipeFilter.getExcludedIngredients();
        addIngredientsFilter(sql, excludedIngredients, false);

        // Add instruction filter
        if (!ObjectUtils.isEmpty(recipeFilter.getInstruction())) {
            sql.append("AND EXISTS (SELECT 1 FROM recipe_instructions it2 WHERE it2.recipe_id = r.id ");
            sql.append("AND LOWER(it2.description) LIKE CONCAT('%', LOWER(:instruction),'%')) ");
        }

        Query query = entityManager.createNativeQuery(sql.toString(), Recipe.class);

        if (recipeFilter.getServing() != null) {
            query.setParameter("serving", recipeFilter.getServing());
        }

        if (!ObjectUtils.isEmpty(includedIngredients)) {
            for (int i = 0; i < includedIngredients.size(); i++ ){
                query.setParameter("includedIngredient" + i, includedIngredients.get(i));
            }
        }

        if (!ObjectUtils.isEmpty(excludedIngredients)) {
            for (int i = 0; i < excludedIngredients.size(); i++ ){
                query.setParameter("excludedIngredient" + i, excludedIngredients.get(i));
            }
        }

        if (!ObjectUtils.isEmpty(recipeFilter.getInstruction())) {
            query.setParameter("instruction", recipeFilter.getInstruction());
        }

        return query.getResultList();
    }

    private void addIngredientsFilter(StringBuilder sql, List<String> ingredients, boolean isIncluded) {
        if (!ObjectUtils.isEmpty(ingredients)) {
            String includeOrExcludeIngredient;
            if (isIncluded) {
                sql.append("AND ");
                includeOrExcludeIngredient = ":includedIngredient";
            } else {
                sql.append("AND NOT ");
                includeOrExcludeIngredient = ":excludedIngredient";
            }
            sql.append("EXISTS (SELECT 1 FROM recipe_ingredients ig2 WHERE ig2.recipe_id = r.id AND ig2.name IN ( ");

            for(int i = 0; i < ingredients.size(); i++) {
                sql.append(includeOrExcludeIngredient).append(i);
                if (i < ingredients.size() - 1) {
                    sql.append(", ");
                }
            }

            sql.append(")) ");
        }
    }
}
