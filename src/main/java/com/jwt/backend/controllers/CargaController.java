package com.jwt.backend.controllers;

import com.jwt.backend.ExcelValidation;
import com.jwt.backend.JsonResponse;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CargaController {

    @PostMapping("/carga")
    public ResponseEntity<JsonResponse> cargaArchivo(@RequestPart("file")MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            XSSFWorkbook libro = new XSSFWorkbook(inputStream);
            XSSFSheet hoja = libro.getSheetAt(0);

            if (hoja != null) {
                String fileName = file.getOriginalFilename();

                ExcelValidation validation = new ExcelValidation(hoja);

                if (validation.getErrorMsg().isEmpty()) {
                    return ResponseEntity.ok(new JsonResponse("Archivo subido y procesado exitosamente", fileName));
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new JsonResponse("Formato del archivo erroneo", validation.getErrorMsg()));
                }

            } else {
                List<String> error = new ArrayList<>();
                error.add("La hoja 'Datos_de_prueba' no fue encontrada en el archivo");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new JsonResponse("Error al subir el archivo", error));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new JsonResponse("Error al subir el archivo"));
        }
    }
}
