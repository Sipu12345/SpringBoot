package com.fileoperation.service;

import com.fileoperation.entity.FileData;
import com.fileoperation.entity.ImageData;
import com.fileoperation.repository.FileRepository;
import com.fileoperation.repository.ImageRepository;
import com.fileoperation.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class StorageService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    FileRepository fileRepository;


    public static final String imagePath="/Users/dibyamohanpanda/Desktop/Image/";


    //UpLoad Image into database
    @Transactional
    public String uploadImage(MultipartFile file) {
        try {
            ImageData save = imageRepository.save(ImageData.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(ImageUtil.compressImage(file.getBytes())).build());
            if(save!=null)
                return "Image Uploaded Successfully";
            else
                return null;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;


    }
    //download image from database
    public byte[] downloadImage(String imageName){
        byte[] images=null;
        try {
            Optional<ImageData> imagedata=imageRepository.findByName(imageName);
            images= ImageUtil.decompressImage(imagedata.get().getImageData());

        }catch (Exception e){
            e.printStackTrace();
        }
        return  images;
    }

    public String uploadImageFile(MultipartFile file) {
        try {
            FileData files = fileRepository.save(FileData.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imagePath(imagePath+file.getOriginalFilename()).build());
            if(files!=null) {
                file.transferTo(new File(files.getImagePath()));
                return "Image Uploaded Successfully in Path- " + files.getImagePath();
            }else {
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public byte[] downloadImageFile(String imageName) {
        byte[] images=null;
        try {
            Optional<FileData> fileData=fileRepository.findByName(imageName);
            if(fileData!=null){
                images=Files.readAllBytes(Paths.get(fileData.get().getImagePath()));
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return  images;
    }
}
