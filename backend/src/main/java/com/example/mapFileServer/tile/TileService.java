package com.example.mapFileServer.tile;

import com.example.mapFileServer.geoFeature.GeoFeatureRepository;
import com.example.mapFileServer.layer.LayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TileService {
    private final GeoFeatureRepository featureRepository;
    private final LayerRepository layerRepository;

}
