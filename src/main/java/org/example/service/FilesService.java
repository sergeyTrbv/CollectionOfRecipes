package org.example.service;

import java.io.File;
import java.io.IOException;

public interface FilesService {
    //Метод, который принимает объкт типа String и сохраняет его("рецепт") в файл
    boolean savoToFile(String json);

    //Метод который читает файл и передаёт стрингу с туда, откуда вызывался
    String readFromFile();

    //Метод который очищает файл в котором хранятся рецепты
    boolean cleanDataFile() throws IOException;

    //Метод, который возвращает файл (только его свойства: размер, имя, путь)
    File getDataFile();
}
