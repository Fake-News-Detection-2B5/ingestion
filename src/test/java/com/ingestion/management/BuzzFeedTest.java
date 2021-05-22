package com.ingestion.management;

import com.ingestion.management.business.BuzzFeed;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class BuzzFeedTest {

    private BuzzFeed controller = new BuzzFeed();

    @Test
    public void shouldScrapWithoutErrors() {
        assertThatCode(() -> controller.scrapMainPage("19-May-2021 23:10:35")).doesNotThrowAnyException();
    }

    @Test
    public void scrapWithoutErrorsStaticMethod() {
        assertThatCode(() -> controller.scrapPageContent("https://www.buzzfeednews.com/article/christopherm51/craig-lang-ukraine-far-right-extremists-true-crime", "19-May-2021 23:10:35")).doesNotThrowAnyException();
    }
}
