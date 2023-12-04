package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.model.Recipe;
import org.example.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/recipe")
@Tag(name = "Рецепты", description = "CRUD-операции и другие эндпоинты для работы с Рецептами")
public class RecipeController {

    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @PostMapping("/")
    @Operation(summary = "Добавление рецепта", description = "Для добавления рецепта нужно создать тело (JSON)")
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск определённого рецепта", description = "Нужно указать id рецепта и он , скорее всего, найдётся")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Integer id) {
        return ResponseEntity.ok(recipeService.getRecipeById(id));
    }

    @GetMapping("/getall")
    @Operation(summary = "Список всех рецептов", description = "Выведет список всех рецептов")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Все рецепты найдены",
                    content = @Content(
                            mediaType = "application/JSON",
                            array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                    )
            )
    })
    public Map<Integer, Recipe> getAllRecipes() {
        return this.recipeService.getAllRecipes();
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Изменение определённого рецепта", description = "Нужно указать id рецепта, который мы хотим изменить и изменить JSON-файл")
    public ResponseEntity<Recipe> updateRecipeById(@PathVariable Integer id, @RequestBody Recipe recipe) {
        Recipe newRecipe = recipeService.updateRecipeById(id, recipe);
        if (recipe == null || id == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @DeleteMapping("/deleteall")
    @Operation(summary = "Удаление всех рецептов", description = "Удалит все рецепты из приложения")
    public ResponseEntity<Recipe> deleteAllRecipes() {
        recipeService.deleteAllRecipes();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удаление определённого рецепта", description = "Нужно указать id рецепта и после, удалить его")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable Integer id){
        if (recipeService.deleteRecipeById(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
