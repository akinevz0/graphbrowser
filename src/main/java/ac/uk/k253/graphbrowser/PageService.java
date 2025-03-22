package ac.uk.k253.graphbrowser;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PageService {

    public Page getWebResource(final String resource) {
        throw new UnsupportedOperationException("Unimplemented method 'getWebResource'");
    }

    public List<Page> getAllCachedPages() {
        return Page.Cached.findAll().list();
    }

}
