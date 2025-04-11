package ac.uk.k253.graphbrowser.services;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import ac.uk.k253.graphbrowser.beans.PagerTS;
import ac.uk.k253.graphbrowser.entities.repositories.RemoteResourceRepository;
import ac.uk.k253.graphbrowser.entities.repositories.ViewedResourceRepository;
import ac.uk.k253.graphbrowser.entities.resources.RemoteResource;
import ac.uk.k253.graphbrowser.entities.resources.ViewedResource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PageService {

    @Inject
    PagerTS pager;

    @Inject
    ViewedResourceRepository viewed;

    @Inject
    RemoteResourceRepository remote;

    @Inject
    URLService urlService;

    @Transactional
    public ViewedResource viewResource(final String resource) throws MalformedURLException, URISyntaxException {
        final var url = urlService.clean(resource);
        final var found = viewed.findByUrlOptional(url);
        if (found.isPresent())
            return found.get();

        final var dto = pager.intoDto(resource);
        return viewed.locate(dto);
    }

    @Transactional
    public List<RemoteResource> getAllPages() {
        return remote.findAll().list();
    }

}
