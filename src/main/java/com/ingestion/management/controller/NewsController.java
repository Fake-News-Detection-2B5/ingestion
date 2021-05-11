package com.ingestion.management.controller;

import com.ingestion.management.business.BBCNewsController;
import com.ingestion.management.business.BuzzFeedController;
import com.ingestion.management.business.NBCNewsController;
import com.ingestion.management.model.News;
import com.ingestion.management.service.NewsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "")
@RequestMapping("v1/api/news")
public class NewsController {
    private NewsRepository newsRepository;

    public NewsController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @PostMapping("")
    public ResponseEntity<News> create(@RequestBody News news) {
        if (news == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        News newNews = this.newsRepository.save(news);
        return new ResponseEntity<>(newNews, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<News>> getAll() {
        List<News> list = this.newsRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<News> getById(@PathVariable UUID id) {
        Optional<News> news = this.newsRepository.findById(id);

        if (!news.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(news.get(), HttpStatus.OK);
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

        // 1. parse jsons and create a List of News
        // 2. using saveAll from newsRepository save all the records into the
        // mongoDB collection
        // 3. eq this.newsRepository.saveAll(entities) where entities is out list
        // of News

    }
}
