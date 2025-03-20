package ac.uk.k253.graphbrowser;

import org.jboss.resteasy.reactive.RestQuery;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@ApplicationScoped
@Path("/api/webresource")
public class WebResourceController {

    @Inject
    WebResourceService service;

    @GET
    public WebResource getWebResource(@RestQuery final String resource){
        return service.getWebResource(resource);
    }

}
