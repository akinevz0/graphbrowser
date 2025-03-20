package ac.uk.k253.graphbrowser;

import org.jobrunr.utils.mapper.jackson.JacksonJsonMapper;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WebResourceService {

    final JacksonJsonMapper mapper = new JacksonJsonMapper();
    private final ExternalProgram getter;

    public WebResourceService() throws NoSuchProgramException {
        this.getter = new ExternalProgram("treepage");
    }

    public WebResource getWebResource(final String resource) {
        throw new UnsupportedOperationException("Unimplemented method 'getWebResource'");
    }

}
