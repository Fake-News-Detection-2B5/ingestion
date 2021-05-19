package com.ingestion.management;

import com.ingestion.management.model.News;
import com.ingestion.management.service.INewsService;
import io.swagger.v3.oas.models.security.SecurityScheme;
import com.ingestion.management.repository.NewsRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ingestion.management.controller.NewsController;
import org.springframework.http.ResponseEntity;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.ingestion.management.model.News;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NewsControllerTest {

    NewsRepository repo;
    INewsService newsService;

    private NewsController controller = new NewsController(repo, newsService);

    @Before
    public void setup() {
        repo = mock(NewsRepository.class);
    }

    @Test
    public void shouldReturnBadRequestWhenPassingNullToCreate() {
        ResponseEntity<News> obj = controller.create(null);
        assertEquals(400, obj.getStatusCode().value());
    }

    @Test
    public void shouldReturnCreatedWhenPassingNewsObjectToCreate() {
        repo = mock(NewsRepository.class);
        controller = new NewsController(repo, newsService);
        News news = new News();
        news.setId(UUID.randomUUID());
        news.setTitle("");
        news.setDescription("");
        news.setUrl("");
        news.setAuthor("");
        news.setPostDate(new Date());
        news.setThumbnail("");
        when(repo.save(news)).thenReturn(news);
        ResponseEntity<News> obj = controller.create(news);
        assertEquals(201, obj.getStatusCode().value());
    }

    @Test
    public void shouldReturnNewsSources() {
        repo = mock(NewsRepository.class);
        controller = new NewsController(repo, newsService);

        ResponseEntity<List<String>> sources = controller.getNewsSources();

        assertEquals(200, sources.getStatusCode().value());
        assertEquals(6, sources.getBody().size());
    }

    // @Test
    // public void shouldWorkWithoutException() {
    // repo = mock(NewsRepository.class);
    // controller = new NewsController(repo, newsService);
    //
    // assertThatCode(() -> controller.callNews()).doesNotThrowAnyException();
    // }

    @Test
    public void shouldReturnNotFound() {
        repo = mock(NewsRepository.class);
        controller = new NewsController(repo, newsService);

        when(repo.findById(any())).thenReturn(Optional.empty());

        ResponseEntity<News> actual = controller.getById(UUID.randomUUID());

        assertEquals(404, actual.getStatusCode().value());
    }

    @Test
    public void shouldReturnNews() {
        repo = mock(NewsRepository.class);
        controller = new NewsController(repo, newsService);

        when(repo.findById(any())).thenReturn(Optional.of(new News()));

        ResponseEntity<News> actual = controller.getById(UUID.randomUUID());

        assertEquals(200, actual.getStatusCode().value());
        assertNotNull(actual.getBody());
    }

    // @Test
    // public void shouldReturnFilteredListOFNews() {
    // repo = mock(NewsRepository.class);
    // controller = new NewsController(repo, newsService);

    // when(repo.findAll()).thenReturn(Collections.emptyList());

    // List<String> actual = controller.getInterval(3, 2);

    // assertEquals(2, actual.size());
    // }

    @Test
    public void shouldReturnFilteredNews() {
        repo = mock(NewsRepository.class);
        controller = new NewsController(repo, newsService);

        when(repo.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<News>> response = controller.getAllNews("source");

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void shouldReturnAllNews() {
        repo = mock(NewsRepository.class);
        controller = new NewsController(repo, newsService);

        when(repo.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<News>> response = controller.getAll();

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void shouldSearchCount() {
        repo = mock(NewsRepository.class);
        controller = new NewsController(repo, newsService);

        when(repo.findAll()).thenReturn(Collections.emptyList());

        controller.searchCount("string");
    }

    @Test
    public void shouldSearch() {
        repo = mock(NewsRepository.class);
        controller = new NewsController(repo, newsService);

        when(repo.findAll()).thenReturn(Collections.emptyList());

        controller.search("string", 3, 2);
    }
}
