package com.ingestion.management.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ingestion.management.business.BBCNewsController;
import com.ingestion.management.business.BuzzFeedController;
import com.ingestion.management.business.NBCNewsController;
import com.ingestion.management.model.Ingestion;
import com.ingestion.management.service.IngestionRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "")
@RequestMapping("v1/api/ingestions")
public class IngestionsController {
    private IngestionRepository ingestionRepository;

    public IngestionsController(IngestionRepository ingestionRepository) {
        this.ingestionRepository = ingestionRepository;
    }

    @PostMapping("")
    public ResponseEntity<Ingestion> create(@RequestBody Ingestion ingestion) {
        if (ingestion == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ingestion newIngestion = this.ingestionRepository.save(ingestion);
        return new ResponseEntity<>(newIngestion, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<Ingestion>> getAll() {
        return new ResponseEntity<>(this.ingestionRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Ingestion> getById(@PathVariable UUID id) {
        Optional<Ingestion> ingestion = this.ingestionRepository.findById(id);

        if (!ingestion.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Ingestion>(ingestion.get(), HttpStatus.OK);
    }

    @GetMapping("callNews")
    public void callNews() throws IOException {
        // call BBC news
        BBCNewsController bbcNewsController = new BBCNewsController();
        bbcNewsController.scrapMainPage();

        // call NBC news
        NBCNewsController nbcNewsController = new NBCNewsController();
        nbcNewsController.scrapMainPage();

        // call BuzzFeed news
        BuzzFeedController buzzFeedController = new BuzzFeedController();
        buzzFeedController.scrapMainPage();

        // 1. parse jsons and create a List of Ingestions
        // 2. using saveAll from ingestionRepository save all the records into the
        // mongoDB collection
        // 3. eq this.ingestionRepository.saveAll(entities) where entities is out list
        // of Ingestions

    }
}
