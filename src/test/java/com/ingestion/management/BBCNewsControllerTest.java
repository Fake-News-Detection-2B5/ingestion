package com.ingestion.management;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import com.ingestion.management.business.BBCNewsController;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class BBCNewsControllerTest {

    private BBCNewsController controller = new BBCNewsController();

    @Test
    public void shouldScrapWithoutErrors() throws IOException {
        assertThatCode(() -> controller.scrapMainPage()).doesNotThrowAnyException();
    }

    @Test
    public void scrapWithoutErrorsStaticMethod() {
        assertThatCode(() -> controller.scrapPageContent("/news")).doesNotThrowAnyException();
    }
}
