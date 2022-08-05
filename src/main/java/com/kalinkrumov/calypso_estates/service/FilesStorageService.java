package com.kalinkrumov.calypso_estates.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FilesStorageService {

    private final Path root = Paths.get("uploads");

    public void init(){
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for uploads!");
        }
    }

    public void save(MultipartFile file){
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public Resource load(String filename){
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()){
                return resource;
            }else{
                throw new RuntimeException("Could not read the file: " + resource.getFilename());
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error" + e.getMessage());
        }
    }

}
