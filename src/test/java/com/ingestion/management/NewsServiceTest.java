package com.ingestion.management;

import com.ingestion.management.model.News;
import com.ingestion.management.repository.NewsRepository;
import com.ingestion.management.service.NewsService;

import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NewsServiceTest {

    private NewsRepository repository;

    private NewsService service;

    @Test
    public void findPaginated() {
        repository = mock(NewsRepository.class);

        Page page = mock(Page.class);

        when(repository.findAll(any(Pageable.class))).thenReturn(page);
        when(page.toList()).thenReturn(new ArrayList<>());

        service = new NewsService(repository);
        assertThatCode(() -> service.findPaginated(3, 2)).doesNotThrowAnyException();
    }
}
