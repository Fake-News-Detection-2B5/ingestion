package com.ingestion.management;

import com.ingestion.management.business.NewYorkPost;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class NewYorkPostTest {
    private NewYorkPost newYorkPost = new NewYorkPost();

    @Test
    public void shouldScrapWithoutErrors() {
        assertThatCode(() -> newYorkPost.scrapMainPage("19-May-2021 23:10:35")).doesNotThrowAnyException();
    }

    @Test
    public void scrapWithoutErrorsPageContent() {
        assertThatCode(() -> newYorkPost.scrapPageContent("https://nypost.com/2021/05/11/6-year-old-girl-fatally-shot-in-san-antonio-suspect-arrested/", "19-May-2021 23:10:35")).doesNotThrowAnyException();
    }
}
