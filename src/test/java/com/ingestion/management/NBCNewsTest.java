package com.ingestion.management;

import com.ingestion.management.business.NBCNews;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class NBCNewsTest {

    private NBCNews controller = new NBCNews();

    @Test
    public void shouldScrapWithoutErrors() {
        assertThatCode(() -> controller.scrapMainPage("19-May-2021 23:10:35")).doesNotThrowAnyException();
    }

    @Test
    public void scrapWithoutErrorsPageContent() {
        assertThatCode(() -> controller.scrapPageContent("https://www.nbcnews.com/news/nbcblk/how-trump-ignited-fight-over-critical-race-theory-schools-n1266701", "19-May-2021 23:10:35")).doesNotThrowAnyException();
    }
}
