package ac.uk.k253.graphbrowser;

import java.util.List;

import org.jboss.resteasy.reactive.RestQuery;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@ApplicationScoped
@Path("/api")
public class ApiController {

    @Inject
    PageService service;

    @GET
    public Page getWebResource(@RestQuery final String resource){
        return service.getWebResource(resource);
    }

    @GET
    @Path("/all")
    public List<Page> getAll(){
        return service.getAllCachedPages();
    }

    @GET
    @Path("/count")
    public int getCount(){
        return service.getAllCachedPages().size();
    }

}
