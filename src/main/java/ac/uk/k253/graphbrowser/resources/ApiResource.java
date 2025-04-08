package ac.uk.k253.graphbrowser.resources;

import java.net.*;
import java.util.List;
import java.util.Objects;

import org.jboss.resteasy.reactive.RestQuery;

import ac.uk.k253.graphbrowser.entities.resources.RemoteResource;
import ac.uk.k253.graphbrowser.entities.resources.ViewedResource;
import ac.uk.k253.graphbrowser.services.PageService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/api")
public class ApiResource {

    @Inject
    PageService service;

    @GET
    @Path("/view")
    @Produces(MediaType.APPLICATION_JSON)
    public ViewedResource view(@RestQuery final String q) {
        try {
            if (Objects.requireNonNull(q).isBlank() || new URI(q).toURL() == null)
                throw new MalformedURLException(String.format("%s is not a valid url", q));
        } catch (MalformedURLException | URISyntaxException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        } catch (final NullPointerException e) {
            throw new IllegalArgumentException("Null ?q= parameter provided", e);
        }
        return service.viewResource(q);

    }

    @GET
    @Path("/all")
    public List<RemoteResource> getAll() {
        return service.getAllPages();
    }

    @GET
    @Path("/count")
    public int getCount() {
        return service.getAllPages().size();
    }

}
