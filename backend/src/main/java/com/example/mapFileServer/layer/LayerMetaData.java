package com.example.mapFileServer.layer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LayerMetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double minZoomLevel;
    private Double maxZoomLevel;
}
