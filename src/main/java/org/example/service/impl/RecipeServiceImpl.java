package org.example.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Recipe;
import org.example.service.FilesService;
import org.example.service.RecipeService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TreeMap;

@Service
public class RecipeServiceImpl implements RecipeService {

    public Map<Integer, Recipe> mapRecipe = new TreeMap<>();
    private static int id = 0;
    final private FilesService filesService;

    public RecipeServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    //Метод, который всегда запускается и проверяет файл на наличие рецептов
    @PostConstruct
    private void checkingTheFile() {
        readFromFile();
        id=mapRecipe.size();
    }

    //Метод добавления и сохранения рецепта
    @Override
    public Recipe addRecipe(Recipe recipe) {
        Recipe newMapRecipe = mapRecipe.put(id++, recipe);
        saveToFile();
        return newMapRecipe;
    }

    //Метод получения рецепта по идентификатору
    @Override
    public Recipe getRecipeById(Integer id) {
        if (mapRecipe.containsKey(id)) {
            return mapRecipe.get(id);
        } else {
            throw new RuntimeException("Не удалось найти рецепт");
        }
    }

    //Метод получения всех рецептов
    @Override
    public Map<Integer, Recipe> getAllRecipes() {
        return mapRecipe;
    }

    //Метод обновления рецепта по идентификатору и его сохранение
    @Override
    public Recipe updateRecipeById(Integer id, Recipe recipe) {
        for (Recipe res : mapRecipe.values()) {
            if (mapRecipe.containsKey(id)) {
                mapRecipe.put(id, recipe);
                saveToFile();
                return res;
            }
        }
        return mapRecipe.get(id);
    }

    //Метод удаления всех рецептов
    @Override
    public Recipe deleteAllRecipes() {
        mapRecipe.clear();
        return null;
    }

    //Метод удаления рецепта по идентификатору
    @Override
    public boolean deleteRecipeById(Integer id) {
        var removed = mapRecipe.remove(id);
        return removed != null;
    }

    //Метод, который преобразует мапу в стрингу и записывает её в файл с помощью savoToFile()
    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(mapRecipe);
            filesService.savoToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    //Метод, который читает файл и содержимое преобразует в мапу и добавляет в mapRecipe
    private void readFromFile() {
        try {
            String json = filesService.readFromFile();
            mapRecipe = new ObjectMapper().readValue(json, new TypeReference<Map<Integer, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Не удалось прочитать файл");
        }
    }
}
