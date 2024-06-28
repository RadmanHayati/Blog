package com.scenius.mosaheb.repositories;

import com.scenius.mosaheb.entities.ImageFileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileDataRepository extends JpaRepository<ImageFileData,Integer> {
    Optional<ImageFileData> findByName(String fileName);
}