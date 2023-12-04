package org.example.service.impl;

import org.example.service.FilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServiceImpl implements FilesService {

    @Value("${path.to.data.fileRecipes}")
    private String dataFilePath;
    @Value("${name.of.data.fileRecipe}")
    private String dataFileName;


    //Метод, который принимает объкт типа String и сохраняет его("рецепт") в файл
    @Override
    public boolean savoToFile(String json) {
        try {
            cleanDataFile(); //НЕ ЗНАЮ ЗАЧЕМ
            Files.writeString(Path.of(dataFilePath, dataFileName), json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    //Метод который читает файл и передаёт стрингу с туда, откуда вызывался
    @Override
    public String readFromFile() {
        try {
            return Files.readString(Path.of(dataFilePath, dataFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Метод который очищает файл в котором хранятся рецепты
    @Override
    public boolean cleanDataFile() throws IOException {
        try {
            Path path = Path.of(dataFilePath, dataFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    //Метод, который возвращает файл (только его свойства: размер, имя, путь)
    @Override
    public File getDataFile() {
        return new File(dataFilePath + "/" + dataFileName);
    }






















}
