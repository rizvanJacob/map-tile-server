package com.example.mapFileServer.layer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LayerFolderRepository extends JpaRepository<LayerFolder, Long> {
}
