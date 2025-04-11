package ac.uk.k253.graphbrowser.services;

import java.net.*;
import java.nio.charset.Charset;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class URLService {

    public URL clean(final String url) {
        final var pathSlashIndex = url.lastIndexOf("/");
        final var base = url.substring(0, pathSlashIndex);
        final var path = url.substring(pathSlashIndex);
        final var encoded = URLEncoder.encode(path, Charset.defaultCharset());
        final var sanitised = base + encoded;
        try {
            return new URI(sanitised).toURL();
        } catch (MalformedURLException | URISyntaxException|IllegalArgumentException e) {
            final var message = String.format("failed to sanitise URL: %s", url);
            throw new RuntimeException(message, e);
        }
    }

}
