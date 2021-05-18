package com.ingestion.management;

import com.ingestion.management.business.DailyMail;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class DailyMailTest {

    private DailyMail controller = new DailyMail();

    @Test
    public void shouldScrapWithoutErrors() {
        assertThatCode(() -> controller.scrapMainPage()).doesNotThrowAnyException();
    }

    @Test
    public void scrapWithoutErrorsPageContent() {
        assertThatCode(() -> controller.scrapPageContent("/news/index.html")).doesNotThrowAnyException();
    }
}
