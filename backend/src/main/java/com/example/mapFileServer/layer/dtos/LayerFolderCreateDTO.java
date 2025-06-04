package com.example.mapFileServer.layer.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LayerFolderCreateDTO{
    private String name;
    private Double minZoomLevel;
    private Double maxZoomLevel;
}
