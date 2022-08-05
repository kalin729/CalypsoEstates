package com.kalinkrumov.calypso_estates.web;

import com.kalinkrumov.calypso_estates.service.FilesStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {

    private final FilesStorageService filesStorageService;

    public FileController(FilesStorageService filesStorageService) {
        this.filesStorageService = filesStorageService;
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = filesStorageService.load(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                + file.getFilename() + "\"").body(file);
    }

}
