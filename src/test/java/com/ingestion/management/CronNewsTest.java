package com.ingestion.management;

import com.ingestion.management.cron.CronNews;
import com.ingestion.management.repository.NewsRepository;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.Mockito.mock;

public class CronNewsTest {

    @Test
    public void shouldCallNY() throws IOException {
        NewsRepository repo = mock(NewsRepository.class);
        CronNews cronNews = new CronNews(repo);

        assertThatCode(() -> cronNews.callNY()).doesNotThrowAnyException();
    }

    @Test
    public void shouldCallBBC() throws IOException {
        NewsRepository repo = mock(NewsRepository.class);
        CronNews cronNews = new CronNews(repo);

        assertThatCode(() -> cronNews.callBBCNews()).doesNotThrowAnyException();
    }

    @Test
    public void shouldCallNBC() throws IOException {
        NewsRepository repo = mock(NewsRepository.class);
        CronNews cronNews = new CronNews(repo);

        assertThatCode(() -> cronNews.callNBC()).doesNotThrowAnyException();
    }

    @Test
    public void shouldCallHuffingtonPost() throws IOException {
        NewsRepository repo = mock(NewsRepository.class);
        CronNews cronNews = new CronNews(repo);

        assertThatCode(() -> cronNews.callHuffingtonPost()).doesNotThrowAnyException();
    }
}
