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

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertEquals(obj.getStatusCode().value(), 400);
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
        assertEquals(obj.getStatusCode().value(), 201);
    }

}