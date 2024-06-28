package com.scenius.mosaheb.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {



    public String uploadImageToFileSystem(MultipartFile file) throws IOException;

    public String generateImageUrl(String fileName);

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException ;

}
