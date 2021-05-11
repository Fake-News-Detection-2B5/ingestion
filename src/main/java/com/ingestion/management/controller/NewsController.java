package com.ingestion.management.controller;

import com.google.gson.Gson;

//import com.ingestion.management.business.BBCNewsController;


import com.ingestion.management.business.*;
//import com.ingestion.management.business.NewsSources;

//import com.ingestion.management.business.BuzzFeedController;
//import com.ingestion.management.business.NBCNewsController;
import com.ingestion.management.model.News;
import com.ingestion.management.repository.NewsRepository;
import com.ingestion.management.service.INewsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "")
@RequestMapping("v1/api/news")
public class NewsController {
    private NewsRepository newsRepository;

    public NewsController(NewsRepository newsRepository, INewsService newsService) {
        this.newsRepository = newsRepository;
        // this.newsService = newsService;
    }

    @Autowired
    private INewsService newsService;

    /* get request cu paginare */
    @GetMapping("/paging/{pageNo}/{pageSize}")
    public List<News> getPaginatedNews(@PathVariable int pageNo, @PathVariable int pageSize) {
        return newsService.findPaginated(pageNo, pageSize);
    }

    @PostMapping("")
    public ResponseEntity<News> create(@RequestBody News news) {
        if (news == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        News newNews = this.newsRepository.save(news);
        return new ResponseEntity<>(newNews, HttpStatus.CREATED);
    }

    @GetMapping("/newssources")
    public ResponseEntity<List<String>> getNewsSources() {
        List<String> newsSources = new ArrayList<>();

        newsSources.add("bbc");
        newsSources.add("buzzfeednews");
        newsSources.add("huffpost");
        newsSources.add("nbcnews");
        newsSources.add("nypost");

        return new ResponseEntity<>(newsSources, HttpStatus.OK);
    }

    @GetMapping("{newsSource}")
    public ResponseEntity<List<News>> getAllNews(@RequestParam String newsSource) {
        List<News> list = this.newsRepository.findAll();

        List<News> filteredList = new ArrayList<>();

        for (News news : list) {
            if (news.getUrl() != null && newsSource != null && news.getUrl().contains(newsSource.toLowerCase())) {
                filteredList.add(news);
            }
        }

        return new ResponseEntity<>(filteredList, HttpStatus.OK);
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
}
