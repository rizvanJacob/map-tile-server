package com.example.mapFileServer.geoFeature;

import com.example.mapFileServer.layer.Layer;
import com.example.mapFileServer.storage.StorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeoFeatureService {
    private final GeoFeatureRepository repository;
    private final List<GeoFeatureParser> parsers;
    private final StorageService storageService;

    @Transactional
    public void importGeoFeatures(Layer layer, String tempDirId) throws IOException {
        var geoFeatures = new ArrayList<GeoFeature>();

        var files = storageService.readTemp(tempDirId);
        for (GeoFeatureParser parser : parsers) {
            if (parser.canParse(files)) {
                log.trace("Parsing {} files into layer {}:{} with {}",
                        files.size(),
                        layer.getId(),
                        layer.getName(),
                        parser.getClass().getSimpleName());
                var parsedFeatures = parser.parse(files);
                geoFeatures.addAll(parsedFeatures);
            }
        }

        geoFeatures.forEach(feature ->
                feature.setLayer(layer));
        log.debug("Ingested {} features into layer {}:{}",
                geoFeatures.size(),
                layer.getId(),
                layer.getName());
        repository.saveAll(geoFeatures);
    }
}
