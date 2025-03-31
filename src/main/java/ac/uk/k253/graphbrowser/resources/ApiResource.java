package ac.uk.k253.graphbrowser.resources;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import org.jboss.resteasy.reactive.RestQuery;

import ac.uk.k253.graphbrowser.entities.*;
import ac.uk.k253.graphbrowser.services.PageService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.resource.spi.ApplicationServerInternalException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@ApplicationScoped
@Path("/api")
public class ApiResource {

    @Inject
    PageService service;

    @GET
    @Path("/view")
    public ViewedResource view(@RestQuery final String q) throws ApplicationServerInternalException {
        try {
            return service.viewResource(q);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new ApplicationServerInternalException("URL parse failed", e);
        }
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
