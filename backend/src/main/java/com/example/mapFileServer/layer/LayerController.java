package com.example.mapFileServer.layer;

import com.example.mapFileServer.layer.dtos.LayerCreateDTO;
import com.example.mapFileServer.layer.dtos.LayerFolderCreateDTO;
import com.example.mapFileServer.layer.dtos.LayerFolderResponseDTO;
import com.example.mapFileServer.layer.dtos.LayerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/layers")
@RequiredArgsConstructor
public class LayerController {
    private final LayerService service;

    @PostMapping
    public ResponseEntity<LayerResponseDTO> createLayer(@RequestBody LayerCreateDTO createDTO) {
        final var responseBody = service.createLayer(createDTO.getName(),
                createDTO.getMinZoomLevel(),
                createDTO.getMaxZoomLevel());
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/{folderId}")
    public ResponseEntity<LayerResponseDTO> createLayerInFolder(@PathVariable Long folderId, @RequestBody LayerCreateDTO createDTO){
        final var responseBody = service.createLayerInFolder(createDTO.getName(),
                createDTO.getMinZoomLevel(),
                createDTO.getMaxZoomLevel(),
                folderId);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/folders")
    public ResponseEntity<LayerFolderResponseDTO> createLayerFolder(@RequestBody LayerFolderCreateDTO createDTO) {
        final var responseBody = service.createLayerFolder(createDTO.getName(),
                createDTO.getMinZoomLevel(),
                createDTO.getMaxZoomLevel());
        return ResponseEntity.ok(responseBody);
    }
}
