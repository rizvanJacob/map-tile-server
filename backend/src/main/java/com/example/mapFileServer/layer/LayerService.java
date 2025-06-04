package com.example.mapFileServer.layer;

import com.example.mapFileServer.geoFeature.GeoFeatureService;
import com.example.mapFileServer.layer.dtos.LayerFolderResponseDTO;
import com.example.mapFileServer.layer.dtos.LayerResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
@RequiredArgsConstructor
public class LayerService {
    private final GeoFeatureService geoFeatureService;
    private final LayerRepository repository;
    private final LayerFolderRepository folderRepository;

    private Layer createLayerImpl(String name,
                                  @Nullable Double minZoomLevel,
                                  @Nullable Double maxZoomLevel) {
        final var layer = new Layer();
        layer.setName(name);

        if (maxZoomLevel != null &&
                minZoomLevel != null) {
            final var metaData = new LayerMetaData();
            metaData.setMinZoomLevel(minZoomLevel);
            metaData.setMaxZoomLevel(maxZoomLevel);

            layer.setMetaData(metaData);
        }

        return layer;
    }

    private Layer createLayerInFolderImpl(String name,
                                          @Nullable Double minZoomLevel,
                                          @Nullable Double maxZoomLevel,
                                          Long folderId) {
        var layer = createLayerImpl(name, minZoomLevel, maxZoomLevel);
        var folder = folderRepository.findById(folderId)
                .orElseThrow();
        layer.setParentFolder(folder);
        return layer;
    }

    private LayerFolder createLayerFolderImpl(String name,
                                              @Nullable Double minZoomLevel,
                                              @Nullable Double maxZoomLevel) {
        final var folder = new LayerFolder();
        folder.setName(name);

        if (maxZoomLevel != null &&
                minZoomLevel != null) {
            final var metaData = new LayerMetaData();
            metaData.setMinZoomLevel(minZoomLevel);
            metaData.setMaxZoomLevel(maxZoomLevel);

            folder.setMetaData(metaData);
        }

        return folder;
    }

    @Transactional
    LayerResponseDTO createLayer(String name,
                                 @Nullable Double minZoomLevel,
                                 @Nullable Double maxZoomLevel) {
        var layer = createLayerImpl(name, minZoomLevel, maxZoomLevel);
        return toDTO(repository.save(layer));
    }

    @Transactional
    LayerResponseDTO createLayerInFolder(String name,
                                         @Nullable Double minZoomLevel,
                                         @Nullable Double maxZoomLevel,
                                         Long folderId) {
        var layer = createLayerInFolderImpl(name, minZoomLevel, maxZoomLevel, folderId);
        return toDTO(repository.save(layer));
    }

    @Transactional
    LayerFolderResponseDTO createLayerFolder(String name,
                                             @Nullable Double minZoomLevel,
                                             @Nullable Double maxZoomLevel) {
        var folder = createLayerFolderImpl(name, minZoomLevel, maxZoomLevel);
        return toDTO(folderRepository.save(folder));
    }

    private static LayerResponseDTO toDTO(Layer layer) {
        return new LayerResponseDTO();
    }

    private static LayerFolderResponseDTO toDTO(LayerFolder layerFolder) {
        return new LayerFolderResponseDTO();
    }
}
