package ac.uk.k253.graphbrowser;

import java.beans.JavaBean;
import java.net.*;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.persistence.*;

@JavaBean
@Entity
public class Page extends ResourceReference {

    public Page() {

    }

    public Page(final URL url) {
        super(url);
    }

    public Page(final String uri) throws MalformedURLException, URISyntaxException {
        super(new URI(uri));
    }

    @Inject
    @Transient
    private PagerService service;

    @Entity
    public static class Cached extends Page {

        @Column()
        private String title;
        @OneToMany(cascade = CascadeType.ALL)
        private List<ResourceReference> references;

        private Cached(final Page parent, final Pager pager) {
            super(parent.getURL());
            this.title = pager.getTitle(parent);
            this.references = pager.getReferences(parent);
        }

        private Cached() {
            super();
        }

    }

    public Cached getCached() {
        return new Cached(this, service.getPager());
    }

}
