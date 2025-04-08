package ac.uk.k253.graphbrowser.entities.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ac.uk.k253.graphbrowser.entities.dto.RemoteResourceDTOFactory;
import ac.uk.k253.graphbrowser.entities.resources.RemoteResource;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class RemoteResourceRepositoryTest {
    @Inject
    RemoteResourceRepository repository;

    @Test
    void testFindByUrlOptional() {
        final var remote = new RemoteResource();
        QuarkusTransaction.requiringNew().run(() -> {
            remote.setUrl("https://akinevz.com/");
            remote.setTitle("home");
            repository.persist(remote);
        });

        QuarkusTransaction.requiringNew().run(() -> {
            final var result = repository.findByUrlOptional("https://akinevz.com/");
            assertEquals(remote.getTitle(), result.get().getTitle());
        });
    }

    @Test
    void testLocate() {
        final var remote = new RemoteResource();
        QuarkusTransaction.requiringNew().run(() -> {
            remote.setUrl("https://akinevz.com/");
            remote.setTitle("home");
            repository.persist(remote);
        });
        QuarkusTransaction.requiringNew().run(() -> {
            final var dto = RemoteResourceDTOFactory.of("https://akinevz.com/");
            final var located = repository.locate(dto);
        
            assertEquals(remote.getTitle(), located.getTitle());
        });
    }
}
