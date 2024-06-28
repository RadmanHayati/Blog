package com.scenius.mosaheb.services.impl;

import com.scenius.mosaheb.entities.ImageFileData;
import com.scenius.mosaheb.repositories.FileDataRepository;
import com.scenius.mosaheb.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {
    private final FileDataRepository fileDataRepository;


    @Override
    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        // change this to env
        String FOLDER_PATH = "/Users/radman/Documents/mosaheb/src/main/resources/static/blogImages/";
        String filePath= FOLDER_PATH +file.getOriginalFilename();

        ImageFileData fileData=fileDataRepository.save(ImageFileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath).build());

        file.transferTo(new File(filePath));

        return file.getOriginalFilename();
    }

    @Override
    public String generateImageUrl(String fileName) {
        return "http://localhost:8080/api/v1/user/images/fileSystem/" + fileName;
    }

    @Override
    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<ImageFileData> fileData = fileDataRepository.findByName(fileName);
        String filePath=fileData.get().getFilePath();
        return Files.readAllBytes(new File(filePath).toPath());
    }
}
