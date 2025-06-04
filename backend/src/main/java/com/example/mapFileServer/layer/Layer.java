package com.example.mapFileServer.layer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;

@Entity
@Getter
@Setter
public class Layer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private LayerFolder parentFolder;
    @OneToOne
    @Nullable
    private LayerMetaData metaData;
}
