package ac.uk.k253.graphbrowser.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ac.uk.k253.graphbrowser.config.PagerConfig;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class RemoteResourceTest {
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
            remote_1.persist();
            final var remote_2 = assertDoesNotThrow(() -> RemoteResource.locate(remote_1, "main title"));
            assertInstanceOf(RemoteResource.class, remote_2);
            assertTrue(remote_2.isPersistent());
            assertEquals("main title", remote_2.getTitle());
        });
        QuarkusTransaction.requiringNew().run(() -> {
            final var url = "https://akinevz.com/";
            final var relocated = assertDoesNotThrow(() -> new RemoteResource(url, "main title"));
            assertEquals(relocated.getUrl(), relocated.getUrl());
            assertEquals("main title", relocated.getTitle());
        });
    }

    @Test
    void testCached() {
    }
}
