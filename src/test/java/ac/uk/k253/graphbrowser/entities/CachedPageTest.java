package ac.uk.k253.graphbrowser.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import ac.uk.k253.graphbrowser.config.PagerConfig;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class CachedPageTest {
    @Inject
    PagerConfig config;

    @Test
    void testGetTitle() {
        final var remoteUrl = "https://akinevz.com/addons";
        final var remote_1 = assertDoesNotThrow(() -> new RemoteResource(remoteUrl, "title"));
        assertInstanceOf(RemoteResource.class, remote_1);
        assertFalse(remote_1.isPersistent());
        assertEquals(remoteUrl, remote_1.getUrl());
        assertEquals("title", remote_1.getTitle());
        QuarkusTransaction.requiringNew().run(() -> {
            final var cached_1 = assertDoesNotThrow(() -> remote_1.view(new ArrayList<>()));
            // cached_1.persist();
            assertInstanceOf(RemoteResource.class, cached_1);
            assertFalse(cached_1.isPersistent());
            assertEquals(remoteUrl, cached_1.getUrl());
            assertEquals("title", cached_1.getTitle());
        });
    }

    @Test
    void testCached() {
    }
}
