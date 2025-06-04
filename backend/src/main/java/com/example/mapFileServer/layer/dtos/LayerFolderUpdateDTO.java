package com.example.mapFileServer.layer.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LayerFolderUpdateDTO extends LayerFolderCreateDTO{
    private String description;
}
