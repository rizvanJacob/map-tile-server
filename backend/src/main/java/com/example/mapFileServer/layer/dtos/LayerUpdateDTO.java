package com.example.mapFileServer.layer.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LayerUpdateDTO {
    private String description;
    private Double minZoomLevel;
    private Double maxZoomLevel;
}
