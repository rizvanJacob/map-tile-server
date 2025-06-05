package com.example.mapFileServer.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface StorageService {
    void init() throws IOException;
    String storeTemp(List<MultipartFile> files) throws IOException;
    List<File> readTemp(String tempId) throws IOException;
    void deleteTemp(String tempId) throws IOException;
    void deleteAll();
}