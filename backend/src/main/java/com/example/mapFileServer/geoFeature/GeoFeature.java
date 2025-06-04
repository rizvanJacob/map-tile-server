package com.example.mapFileServer.geoFeature;

import com.example.mapFileServer.layer.Layer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Geometry;

@Entity
@Getter
@Setter
public class GeoFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "geometry")
    private Geometry geometry;
    @Column(columnDefinition = "jsonb")
    private String properties;
    @ManyToOne
    private Layer layer;
}
