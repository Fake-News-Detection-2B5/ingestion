package com.ingestion.management;

import org.junit.jupiter.api.Test;

import com.ingestion.management.business.NBCNews;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NBCNewsTest {

    private NBCNews controller = new NBCNews();

    @Test
    public void shouldScrapWithoutErrors() {
        assertThatCode(() -> controller.scrapMainPage()).doesNotThrowAnyException();
    }

    @Test
    public void scrapWithoutErrorsPageContent() {
        assertThatCode(() -> controller.scrapPageContent("https://www.nbcnews.com/news/nbcblk/how-trump-ignited-fight-over-critical-race-theory-schools-n1266701")).doesNotThrowAnyException();
    }
}
