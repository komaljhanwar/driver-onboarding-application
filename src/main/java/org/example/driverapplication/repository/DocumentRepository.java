package org.example.driverapplication.repository;

import org.example.driverapplication.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    Optional<Document> findByDriverId(Long driverId);
}
