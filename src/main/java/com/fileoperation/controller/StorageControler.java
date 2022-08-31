package com.fileoperation.controller;

import com.fileoperation.service.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class StorageControler {

    @Autowired
    StorageService storageService;


    @PostMapping(value="/uploadImage",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file)  {
        try {
            String message=storageService.uploadImage(file);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

    }

    @GetMapping("/downloadImage/{imageName}")
    public ResponseEntity<?> downloadImage(@PathVariable("imageName") String imageName){
        byte[] bytes = storageService.downloadImage(imageName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(bytes);
    }
}
