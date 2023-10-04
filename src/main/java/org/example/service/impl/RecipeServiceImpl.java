package org.example.service.impl;

import org.example.model.Recipe;
import org.example.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class RecipeServiceImpl implements RecipeService {

    public Map<Integer, Recipe> mapRecipe = new TreeMap<>();
    private static int id = 0;


    @Override
    public Recipe addRecipe(Recipe recipe) {
        Recipe newMapRecipe = mapRecipe.put(id++, recipe);
//
        return newMapRecipe;
    }

    @Override
    public Recipe getRecipeById(Integer id) {
        if (mapRecipe.containsKey(id)) {
            return mapRecipe.get(id);
        } else {
            throw new RuntimeException("Не удалось найти рецепт");
        }
    }

    @Override
    public Map<Integer, Recipe> getAllRecipes() {
        return mapRecipe;
    }

    @Override
    public Recipe updateRecipeById(Integer id, Recipe recipe) {
        for (Recipe res : mapRecipe.values()) {
            if (mapRecipe.containsKey(id)) {
                mapRecipe.put(id, recipe);
//                saveToFile();
                return res;
            }
        }
        return mapRecipe.get(id);
    }

    @Override
    public Recipe deleteAllRecipes() {
        mapRecipe.clear();
        return null;
    }

    @Override
    public boolean deleteRecipeById(Integer id) {
        var removed = mapRecipe.remove(id);
        return removed != null;
    }


}
