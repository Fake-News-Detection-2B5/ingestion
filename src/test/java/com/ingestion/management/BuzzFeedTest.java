package com.ingestion.management;

import org.junit.jupiter.api.Test;

import com.ingestion.management.business.BuzzFeed;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuzzFeedTest {

    private BuzzFeed controller = new BuzzFeed();

    @Test
    public void shouldScrapWithoutErrors() {
        assertThatCode(() -> controller.scrapMainPage()).doesNotThrowAnyException();
    }

    @Test
    public void scrapWithoutErrorsStaticMethod() {
        assertThatCode(() -> controller.scrapPageContent("https://www.buzzfeednews.com/article/christopherm51/craig-lang-ukraine-far-right-extremists-true-crime")).doesNotThrowAnyException();
    }
}
