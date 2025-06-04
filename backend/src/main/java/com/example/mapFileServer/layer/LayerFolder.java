package com.example.mapFileServer.layer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class LayerFolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private LayerFolder parentFolder;
    @OneToOne
    @Nullable
    private LayerMetaData metaData;
    @OneToMany(mappedBy = "parentFolder")
    private List<LayerFolder> subFolders = new ArrayList<>();
    @OneToMany(mappedBy = "parentFolder")
    private List<Layer> layers = new ArrayList<>();
}
