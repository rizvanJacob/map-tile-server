package com.example.mapFileServer.layer;

import com.example.mapFileServer.geoFeature.GeoFeatureService;
import com.example.mapFileServer.layer.dtos.LayerFolderResponseDTO;
import com.example.mapFileServer.layer.dtos.LayerResponseDTO;
import com.example.mapFileServer.storage.StorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class LayerService {
    private final GeoFeatureService geoFeatureService;
    private final StorageService storageService;
    private final LayerRepository repository;
    private final LayerFolderRepository folderRepository;

    private Layer createLayerImpl(String name) {
        final var layer = new Layer();
        layer.setName(name);

        return layer;
    }

    private Layer createLayerInFolderImpl(String name,
                                          Long folderId) {
        var layer = createLayerImpl(name);
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

    private Layer ingestLayerImpl(List<MultipartFile> files, Function<String, Layer> layerFunction) {
        String tempDirId = null;
        try {
            tempDirId = storageService.storeTemp(files);

            var name = parseLayerName(files);
            var layer = createLayerImpl(name);

            geoFeatureService.importGeoFeatures(layer, tempDirId);

            return repository.save(layer);
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        } finally {
            try {
                storageService.deleteTemp(tempDirId);
            } catch (IOException ex) {
                log.error("Failed to delete temp dir {}: {}", tempDirId, ex);
                log.trace("IOException", ex);
            }
        }
    }

    @Transactional
    public LayerResponseDTO createLayer (List<MultipartFile> files) {
        final var layer = ingestLayerImpl(files,
                this::createLayerImpl);
        return toDTO(layer);
    }

    @Transactional
    public LayerResponseDTO createLayerInFolder(List<MultipartFile> files,
                                         Long folderId) {
        final var layer =  ingestLayerImpl(files,
                name -> createLayerInFolderImpl(name, folderId));
        return toDTO(layer);
    }

    @Transactional
    public LayerFolderResponseDTO createLayerFolder(String name,
                                             @Nullable Double minZoomLevel,
                                             @Nullable Double maxZoomLevel) {
        var folder = createLayerFolderImpl(name, minZoomLevel, maxZoomLevel);
        return toDTO(folderRepository.save(folder));
    }

    @Transactional
    public LayerResponseDTO updateLayer(Long layerId,
                                        @Nullable Double minZoomLevel,
                                        @Nullable Double maxZoomLevel) {
        var layer = repository.findById(layerId)
                .orElseThrow();
        var metaData = Optional.ofNullable(layer.getMetaData())
                        .orElseGet(LayerMetaData::new);
        metaData.setMinZoomLevel(minZoomLevel);
        metaData.setMaxZoomLevel(maxZoomLevel);
        layer.setMetaData(metaData);
        return toDTO(repository.save(layer));
    }

    static LayerResponseDTO toDTO(Layer layer) {
        return new LayerResponseDTO();
    }

    static LayerFolderResponseDTO toDTO(LayerFolder layerFolder) {
        return new LayerFolderResponseDTO();
    }

    static String parseLayerName(List<MultipartFile> files) {
        var names = files.stream()
                .map(MultipartFile::getOriginalFilename)
                .filter(name -> name != null && name.chars().filter(ch -> ch == '.').count() == 1)
                .map(name -> name.substring(0, name.indexOf('.')))
                .distinct()
                .toList();

        if (names.isEmpty()) {
            throw new IllegalArgumentException("No valid filenames provided (must contain exactly one '.' character separating the name and extension)");
        }

        if (names.size() > 1) {
            throw new IllegalArgumentException("Filenames do not match: " + names);
        }

        return names.get(0);
    }
}
