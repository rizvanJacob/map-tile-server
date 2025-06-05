package com.example.mapFileServer.geoFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface GeoFeatureParser {
    boolean canParse(List<File> files);
    List<GeoFeature> parse(List<File> files) throws IOException;
}