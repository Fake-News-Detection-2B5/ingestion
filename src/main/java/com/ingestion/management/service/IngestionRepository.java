package com.ingestion.management.service;

import java.util.UUID;

import com.ingestion.management.model.Ingestion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IngestionRepository extends MongoRepository<Ingestion, UUID> {

}
