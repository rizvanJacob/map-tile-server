package com.example.mapFileServer.layer;

import com.example.mapFileServer.layer.dtos.LayerFolderCreateDTO;
import com.example.mapFileServer.layer.dtos.LayerFolderResponseDTO;
import com.example.mapFileServer.layer.dtos.LayerResponseDTO;
import com.example.mapFileServer.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/layers")
@RequiredArgsConstructor
public class LayerController {
    private final LayerService layerService;
    @PostMapping
    public LayerResponseDTO createLayer(@RequestParam("files") List<MultipartFile> files) {
        return layerService.createLayer(files);
    }

    @PostMapping("/{folderId}")
    public LayerResponseDTO createLayerInFolder(@PathVariable Long folderId, @RequestParam("files") List<MultipartFile> files) {
        return layerService.createLayerInFolder(files, folderId);
    }

    @PostMapping("/folders")
    public LayerFolderResponseDTO createLayerFolder(@RequestBody LayerFolderCreateDTO createDTO) {
        return layerService.createLayerFolder(createDTO.getName(),
                createDTO.getMinZoomLevel(),
                createDTO.getMaxZoomLevel());
    }
}
