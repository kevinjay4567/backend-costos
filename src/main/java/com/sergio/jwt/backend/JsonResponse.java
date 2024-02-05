package com.sergio.jwt.backend;

import java.util.ArrayList;
import java.util.List;

public class JsonResponse {

    public String message;
    public String fileName;
    public List<String> errors = new ArrayList<>();

    public JsonResponse(String message) {
        this.message = message;
    }

    public JsonResponse(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public JsonResponse(String message, String fileName) {
        this.message = message;
        this.fileName = fileName;
    }

}