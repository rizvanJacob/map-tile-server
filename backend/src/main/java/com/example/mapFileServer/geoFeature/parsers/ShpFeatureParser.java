package com.example.mapFileServer.geoFeature.parsers;

import com.example.mapFileServer.geoFeature.GeoFeature;
import com.example.mapFileServer.geoFeature.GeoFeatureParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.geotools.api.feature.Property;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.locationtech.jts.geom.Geometry;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ShpFeatureParser implements GeoFeatureParser {
    private final ShapefileDataStoreFactory shapefileDataStoreFactory = new ShapefileDataStoreFactory();
    private final ObjectMapper mapper;

    @Override
    public boolean canParse(List<File> files) {
        return files.stream().anyMatch(file -> file.getName().toLowerCase().endsWith(".shp"));
    }

    @Override
    public List<GeoFeature> parse(List<File> files) throws IOException {
        File shpFile = files.stream()
                .filter(file -> file.getName().toLowerCase().endsWith(".shp"))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Missing required .shp file (note: .shx and .dbf are optional but recommended)"));

        List<GeoFeature> geoFeatures = new ArrayList<>();

        URL shpUrl = shpFile.toURI().toURL();
        ShapefileDataStore dataStore = null;
        try {
            dataStore = (ShapefileDataStore) shapefileDataStoreFactory.createDataStore(shpUrl);
            try (var featureReader = dataStore.getFeatureReader()) {
                while (featureReader.hasNext()) {
                    final var shpFeature = featureReader.next();
                    if (shpFeature.getDefaultGeometry() instanceof Geometry geometry) {
                        final var geoFeature = new GeoFeature();
                        geoFeature.setGeometry(geometry);
                        final var properties = new HashMap<String, Object>();
                        for (Property shpProperty : shpFeature.getProperties()) {
                            if (Objects.equals(shpProperty.getName().toString(), "the_geom")) {
                                continue;
                            }
                            properties.put(shpProperty.getName().toString(), shpProperty.getValue());
                        }
                        geoFeature.setProperties(mapper.writeValueAsString(properties));
                        geoFeatures.add(geoFeature);
                    }
                }
            }
        } finally {
            if (dataStore != null) {
                dataStore.dispose();
            }
        }

        return geoFeatures;
    }
}
