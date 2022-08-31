package com.fileoperation.repository;

import com.fileoperation.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageData,Long> {
    Optional<ImageData> findByName(String filename);
}
