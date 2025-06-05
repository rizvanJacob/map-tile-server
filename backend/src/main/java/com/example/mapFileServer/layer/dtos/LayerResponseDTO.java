package com.example.mapFileServer.layer.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LayerResponseDTO {
    private String name;
    private String description;
    private Double minZoomLevel;
    private Double maxZoomLevel;
}