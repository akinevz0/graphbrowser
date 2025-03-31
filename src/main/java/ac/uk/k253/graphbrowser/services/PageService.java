package ac.uk.k253.graphbrowser.services;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import ac.uk.k253.graphbrowser.beans.PagerTS;
import ac.uk.k253.graphbrowser.entities.RemoteResource;
import ac.uk.k253.graphbrowser.entities.ViewedResource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PageService {

    @Inject
    PagerTS pager;

    @Transactional
    public ViewedResource viewResource(final String resource) throws URISyntaxException, MalformedURLException {
        final var viewed = pager.view(resource);
        viewed.persist();
        return viewed;
    }

    public List<RemoteResource> getAllPages() {
        return RemoteResource.findAll().list();
    }

}
