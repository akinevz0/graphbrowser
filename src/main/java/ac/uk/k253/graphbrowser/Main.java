package ac.uk.k253.graphbrowser;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Main {

    // jboss logger
    private static final org.jboss.logging.Logger log = org.jboss.logging.Logger.getLogger(Main.class);

    @PostConstruct
    public void init() {
        log.info("Application started");
    }

    

}