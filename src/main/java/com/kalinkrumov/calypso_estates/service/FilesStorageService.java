package com.kalinkrumov.calypso_estates.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FilesStorageService {

    private final Path ROOT = Paths.get("uploads");

    public void init(){
        try {
            Files.createDirectory(ROOT);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for uploads!");
        }
    }

    public boolean save(MultipartFile file, String newFileName){
        try {
//            Files.copy(file.getInputStream(), this.ROOT.resolve(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), this.ROOT.resolve(newFileName));
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
        return true;
    }

    public boolean delete(String filename){
        File file = new File(String.valueOf(ROOT.resolve(filename)));
        return file.delete();
    }

    public Resource load(String filename){
        try {
            Path file = ROOT.resolve(filename);
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
