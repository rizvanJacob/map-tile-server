package com.example.mapFileServer.geoFeature;

import com.example.mapFileServer.layer.Layer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeoFeatureService {
    private final GeoFeatureRepository repository;
//    @Transactional
//    public List<GeoFeature> importGeoFeatures(Path filePath, Layer layer){
//
//    }
}
