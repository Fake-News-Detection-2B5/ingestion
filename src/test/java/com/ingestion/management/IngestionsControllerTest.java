package com.ingestion.management;

import com.ingestion.management.model.Ingestion;
import com.ingestion.management.service.IngestionRepository;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;

import com.ingestion.management.controller.IngestionsController;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.ingestion.management.model.Ingestion;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class IngestionsControllerTest {

    @Mock
    IngestionRepository repo;

    private IngestionsController controller = new IngestionsController(repo);

    @Test
    public void shouldReturnBadRequestWhenPassingNullToCreate() {
        ResponseEntity<Ingestion> obj = controller.create(null);
        assertEquals(obj.getStatusCode().value(), 400);
    }

    @Test
    public void shouldReturnCreatedWhenPassingIngestionObjectToCreate() {
        Ingestion ingestion = new Ingestion();
        ingestion.setId(UUID.randomUUID());
        ingestion.setContent("");
        ingestion.setUrl("");
        ingestion.setAuthor("");
        when(repo.save(ingestion)).thenReturn(ingestion);
        ResponseEntity<Ingestion> obj = controller.create(ingestion);
        assertEquals(obj.getStatusCode().value(), 201);
    }

}
