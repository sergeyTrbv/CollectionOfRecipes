package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.example.model.Recipe;
import org.example.service.FilesService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController()
@Tag(name = "Рецепты(работа с файлами)", description = "Input/Output и другие эндпоинты для работы с файлами Рецептов")
public class RecipeFilesController {

    private final FilesService filesService;


    public RecipeFilesController(FilesService filesService) {
        this.filesService = filesService;
    }


    //Эндпоинт "Выгрузка файла в формате json/Возможность скачать файл рецептов в web"
    @GetMapping("/downloadfile")
    @Operation(summary = "Скачать файл со всеми рецептами в формате json", description = "Возможность скачать файл со всеми рецептами(файл json)")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл с рецептами скачан",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                    )
            )
    })
    public ResponseEntity<InputStreamResource> downloadDataFile() throws FileNotFoundException {

        //находим файл с рецептами, который хранится в корне

        File recipeFile = filesService.getDataFile();

        //если получилось найти файл, то выполняем...

        if (recipeFile.exists()) {

            //данный код создает объект resource, который содержит данные из файла recipeFile и может быть использован
            //для чтения данных из этого файла. Теперь мы можем использовать объект resource в коде дальше,
            //например, для передачи данных в другие методы или классы.

            InputStreamResource resource = new InputStreamResource(new FileInputStream(recipeFile));

            //возвращаем успешный ответ, где...

            return ResponseEntity.ok()

                    //устанавливаем тип содержимого ответа как application/json для скачивания

                    .contentType(MediaType.APPLICATION_JSON)

                    //где указываем длину/объем файла

                    .contentLength(recipeFile.length())

                    //устанавливает заголовок Content-Disposition с именем файла "RecipeFile.json"
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=\"RecipeFile.json\"")

                    //где в теле ответа есть сам объект с содержимым


                    .body(resource);
        } else {

            //если файл не нашелся, то не будет возвращено содержимое и код ответа будет 204 (нет содержимого).

            return ResponseEntity.noContent().build();
        }
    }


    @PostMapping(value = "/importfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузить файл рецептов", description = "Возможность загрузить файл с рецептами")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл с рецептами загружен",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                    )
            )
    })
    public ResponseEntity<Void> uploadDataFile(@RequestParam MultipartFile file) throws IOException {

        //вызываем метод который очищает файл в котором хранятся рецепты

        filesService.cleanDataFile();

        //Создаём новый файл с рецептами, который хранится в корне и берем инф. о нем

        File recipeFile = filesService.getDataFile();


        //Открываем выходной поток чтобы записать файлы
        try (FileOutputStream fos = new FileOutputStream(recipeFile)){

            //Вызываем у класса-обертки коллекции apache метод copy, который (Открывая входящий поток и стчитывая данные) копирует данные из загружаемого файла в наш файл

                IOUtils.copy(file.getInputStream(), fos);

                return ResponseEntity.ok().build();
            } catch (IOException e) {
                e.printStackTrace();
            }

        //Если ошибка - возвращаем ошибку 500

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}
