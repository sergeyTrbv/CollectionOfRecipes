package org.example.service;

import org.example.model.Recipe;

import java.util.Map;

public interface RecipeService {


    Recipe addRecipe(Recipe recipe);

    Recipe getRecipeById(Integer id);

    Map<Integer, Recipe> getAllRecipes();

    Recipe updateRecipeById(Integer id, Recipe recipe);

    Recipe deleteAllRecipes();

    boolean deleteRecipeById(Integer id);
}
