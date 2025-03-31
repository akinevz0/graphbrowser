package ac.uk.k253.graphbrowser.config;

import ac.uk.k253.graphbrowser.beans.PagerTS;
import ac.uk.k253.graphbrowser.exceptions.NoSuchProgramException;
import jakarta.enterprise.context.*;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class PagerConfig {
    
    @Produces
    public PagerTS getPager() throws NoSuchProgramException {
        return new PagerTS();
    }
    
}
