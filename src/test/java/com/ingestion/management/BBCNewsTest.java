package com.ingestion.management;

import org.junit.jupiter.api.Test;
import com.ingestion.management.business.BBCNewsController;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class BBCNewsTest {

    private BBCNewsController controller = new BBCNewsController();

    @Test
    public void shouldScrapWithoutErrors() {
        assertThatCode(() -> controller.scrapMainPage()).doesNotThrowAnyException();
    }

    @Test
    public void scrapWithoutErrorsPageContent() {
        assertThatCode(() -> controller.scrapPageContent("/news")).doesNotThrowAnyException();
    }
}
