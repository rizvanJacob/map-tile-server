package com.example.mapFileServer.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;
import java.util.Objects;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Service
@Slf4j
public class FileSystemStorageService implements StorageService {

    @Value("${storage.root:/tmp/storage}")
    private Path storageRoot;

    private static final String TEMP_DIR_NAME = "temp";

    @PostConstruct
    @Override
    public void init() throws IOException {
        Files.createDirectories(storageRoot);
        Files.createDirectories(storageRoot.resolve(TEMP_DIR_NAME));
        log.debug("""
                Created directories:
                {}
                {}""", storageRoot.toAbsolutePath(),
                storageRoot.resolve(TEMP_DIR_NAME).toAbsolutePath());
    }

    @Override
    public void deleteAll() {
        try {
            deleteDir(storageRoot.resolve(TEMP_DIR_NAME));
        } catch (IOException ex) {
            log.error("Failed to delete Root Dir: {}", ex.getMessage());
            log.trace("IOException when deleting Root Dir", ex);
        }
    }

    @PreDestroy
    private void deleteAllTemp() {
        try {
            deleteDir(storageRoot.resolve(TEMP_DIR_NAME));
        } catch (IOException ex) {
            log.error("Failed to delete Temp Dir: {}", ex.getMessage());
            log.trace("IOException when deleting Temp Dir", ex);
        }
    }

    @Override
    public String storeTemp(List<MultipartFile> files) throws IOException {
        String tempId = UUID.randomUUID().toString();
        Path tempDir = storageRoot.resolve(TEMP_DIR_NAME).resolve(tempId);
        Files.createDirectories(tempDir);
        for (MultipartFile file : files) {
            Path target = tempDir.resolve(Objects.requireNonNull(file.getOriginalFilename()));
            file.transferTo(target.toFile());
        }
        return tempId;
    }

    @Override
    public List<File> readTemp(String tempId) throws IOException {
        Path dir = storageRoot.resolve(TEMP_DIR_NAME).resolve(tempId);
        try (Stream<Path> paths = Files.list(dir)) {
            return paths.map(Path::toFile).collect(Collectors.toList());
        }
    }

    @Override
    public void deleteTemp(String tempId) throws IOException {
        Path tempDir = storageRoot.resolve(TEMP_DIR_NAME).resolve(tempId);
        deleteDir(tempDir);
    }

    private static void deleteDir(Path dir) throws IOException {
        if (Files.exists(dir)) {
            try (Stream<Path> paths = Files.walk(dir)) {
                paths.sorted(Comparator.reverseOrder())
                        .forEach(path -> {
                            try {
                                Files.deleteIfExists(path);
                            } catch (IOException ignored) {
                            }
                        });
            }
        }
    }
}
