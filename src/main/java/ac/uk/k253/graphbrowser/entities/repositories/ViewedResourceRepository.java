package ac.uk.k253.graphbrowser.entities.repositories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jboss.logging.Logger;

import ac.uk.k253.graphbrowser.entities.dto.PagertsDTO;
import ac.uk.k253.graphbrowser.entities.resources.RemoteResource;
import ac.uk.k253.graphbrowser.entities.resources.ViewedResource;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.narayana.jta.QuarkusTransaction;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@ApplicationScoped
public class ViewedResourceRepository implements PanacheRepository<ViewedResource> {

    private final Logger LOGGER = Logger.getLogger(getClass());

    @Inject
    RemoteResourceRepository repository;

    public Optional<ViewedResource> findByUrlOptional(final String url) {
        return find("url", url).firstResultOptional();
    }

    @Transactional(value = TxType.REQUIRES_NEW)
    public ViewedResource locate(final PagertsDTO pagertsDTO) {
        final var url = pagertsDTO.getUrl();
        LOGGER.info("locating for URL in viewed");
        final var title = pagertsDTO.getTitle();

        final var viewed = findByUrlOptional(url);
        LOGGER.info("result from viewedRepo " + viewed.toString());
        if (viewed.isPresent())
            return viewed.get();

        final var resources = pagertsDTO.getResources().stream().map(repository::locate).toList();
        LOGGER.info("constructing new resource: "+Stream.of(resources.toArray()).map(Object::toString).collect(Collectors.joining(",\n")));

        final var remote = repository.findByUrlOptional(url);
        if (remote.isPresent()) {
            LOGGER.info("result from remoteRepo" + remote.toString());
            final var existing = remote.get();
            return view(existing, title, resources);
        }

        final var resource = new ViewedResource();
        resource.setUrl(url);
        resource.setTitle(title);
        resource.setResources(resources);
        LOGGER.info("persisting viewed " + resource);
        persist(resource);
        return resource;
    }

    private ViewedResource view(final RemoteResource existing, final String title,
            final List<RemoteResource> resources) {
        LOGGER.info("viewing remote");
        final var viewed = new ViewedResource(existing, title, resources);
        LOGGER.info("persisting viewed");
        // repository.delete(existing);
        persist(viewed);
        return viewed;
    }

}
