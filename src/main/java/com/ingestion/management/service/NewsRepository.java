package com.ingestion.management.service;

import java.util.UUID;

import com.ingestion.management.model.News;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NewsRepository extends MongoRepository<News, UUID> {

}
