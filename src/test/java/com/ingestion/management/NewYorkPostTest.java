package com.ingestion.management;

import com.ingestion.management.business.NewYorkPost;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class NewYorkPostTest {
    private NewYorkPost newYorkPost = new NewYorkPost();

    @Test
    public void shouldScrapWithoutErrors() {
        assertThatCode(() -> newYorkPost.scrapMainPage()).doesNotThrowAnyException();
    }

    @Test
    public void scrapWithoutErrorsPageContent() {
        assertThatCode(() -> newYorkPost.scrapPageContent("https://nypost.com/2021/05/11/6-year-old-girl-fatally-shot-in-san-antonio-suspect-arrested/")).doesNotThrowAnyException();
    }
}
