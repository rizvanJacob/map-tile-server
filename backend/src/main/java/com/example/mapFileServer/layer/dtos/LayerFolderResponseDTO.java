package com.example.mapFileServer.layer.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class LayerFolderResponseDTO {
    private String name;
    private String description;
    private Double minZoomLevel;
    private Double maxZoomLevel;
    private List<LayerResponseDTO> layers;
}
