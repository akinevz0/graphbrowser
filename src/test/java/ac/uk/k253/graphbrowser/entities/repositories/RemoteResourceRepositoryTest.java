package ac.uk.k253.graphbrowser.entities.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ac.uk.k253.graphbrowser.entities.dto.*;
import ac.uk.k253.graphbrowser.entities.resources.RemoteResource;
import ac.uk.k253.graphbrowser.services.URLService;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class RemoteResourceRepositoryTest {
    @Inject
    RemoteResourceRepository repository;

    @Inject
    URLService urlService;

    @Test
    void testFindByUrlOptional() {
        QuarkusTransaction.requiringNew().run(() -> {
            final var dto = RemoteResourceDTOFactory.of("https://akinevz.com/");
            final var remote = repository.locate(dto);

            repository.persist(remote);
            final var url = urlService.clean("https://akinevz.com/");
            final var result = repository.findByUrlOptional(url);
            assertEquals(remote.getTitle(), result.get().getTitle());
        });

    }

    @Test
    void testLocate() {
        QuarkusTransaction.requiringNew().run(() -> {
            final PagertsResourceDTO dto = new PagertsResourceDTO();
            final PagertsLinkDTO link = new PagertsLinkDTO();
            link.setKey("test");
            link.setUrl("https://akinevz.com/");
            dto.setLink(link);
            final PagertsTextDTO text = new PagertsTextDTO();
            text.setKey("test");
            text.setValue("https://akinevz.com/");
            dto.setText(text);
            final var remote = repository.locate(dto);
            repository.persist(remote);
            QuarkusTransaction.requiringNew().run(() -> {
                final var anotherDto = RemoteResourceDTOFactory.of("https://akinevz.com/");
                final var located = repository.locate(anotherDto);

                assertEquals(remote.getTitle(), located.getTitle());
            });
        });

    }
}
