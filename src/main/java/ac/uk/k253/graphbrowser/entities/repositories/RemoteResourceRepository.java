package ac.uk.k253.graphbrowser.entities.repositories;

import java.util.Optional;

import org.jboss.logging.Logger;

import ac.uk.k253.graphbrowser.entities.dto.PagertsResourceDTO;
import ac.uk.k253.graphbrowser.entities.resources.RemoteResource;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RemoteResourceRepository implements PanacheRepository<RemoteResource> {

    private final Logger LOGGER = Logger.getLogger(getClass());

    public Optional<RemoteResource> findByUrlOptional(final String url) {
        return find("url", url).firstResultOptional();
    }

    // @Transactional(value = TxType.REQUIRES_NEW)
    public RemoteResource locate(final PagertsResourceDTO pagertsResourceDTO) {
        final var url = pagertsResourceDTO.getLink().getUrl();
        final var text = pagertsResourceDTO.getText().getText();
        final var remote = findByUrlOptional(url);
        LOGGER.info("checking if " + url + " is in " + remote);
        if (remote.isPresent())
            return remote.get();

        LOGGER.info("constructing new remote for "+url);
        final var resource = new RemoteResource();
        resource.setUrl(url);
        resource.setTitle(text);

        LOGGER.info("persisting remote "+resource);
        persist(resource);
        LOGGER.info("returning remote "+resource);
        return resource;
    }

}
