package org.example.controller;

import org.example.model.Recipe;
import org.example.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @PostMapping("/")
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Integer id) {
        return ResponseEntity.ok(recipeService.getRecipeById(id));
    }

    @GetMapping("/getall")
    public Map<Integer, Recipe> getAllRecipes() {
        return this.recipeService.getAllRecipes();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Recipe> updateRecipeById(@PathVariable Integer id, @RequestBody Recipe recipe) {
        Recipe newRecipe = recipeService.updateRecipeById(id, recipe);
        if (recipe == null || id == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<Recipe> deleteAllRecipes() {
        recipeService.deleteAllRecipes();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable Integer id){
        if (recipeService.deleteRecipeById(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
