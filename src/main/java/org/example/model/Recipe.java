package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    private String nameRecipe;
    private int cookingTimeRecipe;
    private ArrayList<Ingredient> ingredientsListRecipe = new ArrayList<>();
    private ArrayList<Cooking> cookingStep = new ArrayList<>();

    @Override
    public String toString() {
        return "Рецепт: " + nameRecipe + ". Время приготовления: " + cookingTimeRecipe + ". Ингридиенты: " + ". Шаги приготовления: " + cookingStep;
    }
}
