package com.ingestion.management;

import com.ingestion.management.business.HuffingtonPost;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class HuffingtonPostTest {
    private HuffingtonPost huffingtonPost = new HuffingtonPost();

    @Test
    public void shouldScrapWithoutErrors() {
        assertThatCode(() -> huffingtonPost.scrapMainPage("19-May-2021 23:10:35")).doesNotThrowAnyException();
    }

    @Test
    public void scrapWithoutErrorsPageContent() {
        assertThatCode(() -> huffingtonPost.scrapPageContent("https://www.huffpost.com/entry/fda-approves-pfizer-vaccine-12-to-15-year-olds_n_6070be53c5b6616dcd780ef5", "19-May-2021 23:10:35")).doesNotThrowAnyException();
    }
}
