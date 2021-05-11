package com.ingestion.management;

import org.junit.jupiter.api.Test;

import com.ingestion.management.business.BBCNews;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class BBCNewsTest {

    private BBCNews controller = new BBCNews();

    @Test
    public void shouldScrapWithoutErrors() {
        assertThatCode(() -> controller.scrapMainPage()).doesNotThrowAnyException();
    }

    @Test
    public void scrapWithoutErrorsStaticMethod() {
        assertThatCode(() -> controller.scrapPageContent("/news")).doesNotThrowAnyException();
    }
}
