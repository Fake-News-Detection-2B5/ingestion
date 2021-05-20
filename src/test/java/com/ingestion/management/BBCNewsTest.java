package com.ingestion.management;

import org.junit.jupiter.api.Test;
import com.ingestion.management.business.BBCNews;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class BBCNewsTest {

    private BBCNews controller = new BBCNews();

    @Test
    public void shouldScrapWithoutErrors() {
        assertThatCode(() -> controller.scrapMainPage("19-May-2021 23:10:35")).doesNotThrowAnyException();
    }

    @Test
    public void scrapWithoutErrorsPageContent() {
        assertThatCode(() -> controller.scrapPageContent("/news", "19-May-2021 23:10:35")).doesNotThrowAnyException();
    }
}
