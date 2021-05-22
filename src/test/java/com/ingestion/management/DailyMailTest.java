package com.ingestion.management;

import com.ingestion.management.business.DailyMail;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class DailyMailTest {

    private DailyMail controller = new DailyMail();

    @Test
    public void shouldScrapWithoutErrors() {
        assertThatCode(() -> controller.scrapMainPage("19-May-2021 23:10:35")).doesNotThrowAnyException();
    }

    @Test
    public void scrapWithoutErrorsPageContent() {
        assertThatCode(() -> controller.scrapPageContent("/news/index.html", "19-May-2021 23:10:35")).doesNotThrowAnyException();
    }
}
